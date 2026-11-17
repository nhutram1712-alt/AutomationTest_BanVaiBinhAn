package testcases;


import Common.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.DangNhapPage;
import pageobjects.NhanVienPage;


import java.time.Duration;


public class NhanVienTests extends BaseTest {


    private NhanVienPage staffPage;


    // ========== SETUP / TEARDOWN ==========


    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
        Constant.WEBDRIVER.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        Constant.WEBDRIVER.get(Constant.LOGIN_URL);
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);


        new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(15))
                .until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        try { Thread.sleep(1500); } catch (Exception ignored) {}


        staffPage = new NhanVienPage();
        staffPage.navigateToStaffPage();
    }


    @AfterMethod
    public void teardown() {
        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
            Constant.WEBDRIVER = null;
        }
    }


    // =========================================================
    // TEST CASES
    // =========================================================


    @Test(description = "TC01 - Thêm nhân viên thành công với dữ liệu hợp lệ")
    public void TC01_AddStaffSuccess() {
        staffPage.clickCreateNew();


        long ts = System.currentTimeMillis();
        staffPage.fillStaffForm(
                "NV Test " + ts,
                "09" + String.format("%08d", (int)(Math.random() * 100000000)),
                "nvtest" + ts + "@gmail.com",
                "Nhân viên bán hàng",
                "Bán hàng"
        );
        staffPage.clickSave();


        String toast = staffPage.getToastMessage();
        System.out.println("=> TOAST TC01: [" + toast + "]");
        Assert.assertTrue(
                toast.toLowerCase().contains("thành công"),
                "TC01 FAILED: Không có thông báo thành công. Actual toast: [" + toast + "]");
    }


    /**
     * TC02 - Hủy thao tác thêm nhân viên
     * Manual expected: quay về màn hình danh sách nhân viên → PASS
     */
    @Test(description = "TC02 - Hủy thao tác thêm nhân viên")
    public void TC02_CancelAddStaff() {
        staffPage.clickCreateNew();
        staffPage.fillStaffForm("NV Hủy Test", "0901111222", "huytest@gmail.com",
                "Nhân viên bán hàng", "Bán hàng");
        staffPage.clickCancel();


        Assert.assertTrue(staffPage.isOnStaffListPage(),
                "TC02 FAILED: Sau khi Bỏ qua không quay về danh sách nhân viên");
    }


    /**
     * TC03 - Tìm kiếm nhân viên không tồn tại
     * Manual expected: "Hệ thống hiển thị không tìm thấy nhân viên" → PASS
     */
    @Test(description = "TC03 - Tìm kiếm nhân viên không tồn tại")
    public void TC03_SearchNonExistentStaff() {
        WebElement searchInput = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                        "input[placeholder='Nhập tên nhân viên, số điện thoại']")));


        String reactSetJs =
                "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype,'value').set;" +
                        "setter.call(arguments[0], arguments[1]);" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));" +
                        "arguments[0].dispatchEvent(new Event('change', {bubbles: true}));";


        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript(reactSetJs, searchInput, "NhanVienKhongTonTai_XYZ_999");
        searchInput.sendKeys(org.openqa.selenium.Keys.ENTER);


        try { Thread.sleep(3000); } catch (Exception ignored) {}


        String bodyText = Constant.WEBDRIVER.findElement(By.tagName("body")).getText().toLowerCase();
        boolean hasEmptyMessage = bodyText.contains("không tìm thấy") || bodyText.contains("trống")
                || bodyText.contains("không có") || bodyText.contains("no data") || bodyText.contains("chưa có");
        boolean isTableEmpty = Constant.WEBDRIVER.findElements(By.cssSelector("table tbody tr")).isEmpty();


        Assert.assertTrue(hasEmptyMessage || isTableEmpty,
                "TC03 FAILED: Web vẫn hiển thị nhân viên dù nhập sai!");
    }


    // =========================================================
    // HELPER — click vào nhân viên đầu tiên và chờ trang chi tiết
    // =========================================================


    private void clickFirstStaffAndWaitDetail() throws Exception {
        WebElement nameCell = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("table tbody tr:first-child td:nth-child(2)")));


        System.out.println("DEBUG: NV sẽ click: [" + nameCell.getText() + "]");
        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].click();", nameCell);


        new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(15))
                .until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(text(),'Thông tin nhân viên')]")),
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//button[contains(.,'Xóa')]")),
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//button[contains(.,'Chỉnh sửa')]"))
                ));


        try { Thread.sleep(1000); } catch (Exception ignored) {}


        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        try { Thread.sleep(500); } catch (Exception ignored) {}
    }


    /**
     * TC04 - Sửa thông tin nhân viên thành công
     * Manual expected: "Hệ thống hiển thị thông báo thành công, đã cập nhật
     * thông tin nhân viên" → PASS
     */
    @Test(description = "TC04 - Sửa thông tin nhân viên thành công")
    public void TC04_EditStaffSuccess() {
        try {
            clickFirstStaffAndWaitDetail();


            WebElement btnEdit = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(.,'Chỉnh sửa')]")));
            ((JavascriptExecutor) Constant.WEBDRIVER)
                    .executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", btnEdit);


            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Lưu']")));
            try { Thread.sleep(500); } catch (Exception ignored) {}


            staffPage.enterName("NV Edited " + System.currentTimeMillis());
            staffPage.clickSave();


            String toast = staffPage.getToastMessage();
            System.out.println("=> TOAST TC04: [" + toast + "]");


            Assert.assertTrue(
                    toast.toLowerCase().contains("thành công") || toast.toLowerCase().contains("cập nhật"),
                    "TC04 FAILED: Actual toast: [" + toast + "]");


        } catch (Exception e) {
            Assert.fail("TC04 FAILED: " + e.getMessage());
        }
    }


    /**
     * TC05 - Hủy thao tác sửa thông tin nhân viên
     * Manual expected: "quay về màn hình chi tiết thông tin nhân viên" → PASS
     */
    @Test(description = "TC05 - Hủy thao tác sửa thông tin nhân viên")
    public void TC05_CancelEditStaff() {
        try {
            clickFirstStaffAndWaitDetail();


            WebElement btnEdit = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(.,'Chỉnh sửa')]")));
            ((JavascriptExecutor) Constant.WEBDRIVER)
                    .executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", btnEdit);


            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Lưu']")));
            try { Thread.sleep(500); } catch (Exception ignored) {}


            staffPage.enterName("Tên Sẽ Không Được Lưu");
            staffPage.clickCancel();
            try { Thread.sleep(500); } catch (Exception ignored) {}


            Assert.assertTrue(
                    Constant.WEBDRIVER.getCurrentUrl().contains("employees"),
                    "TC05 FAILED: URL: " + Constant.WEBDRIVER.getCurrentUrl());


        } catch (Exception e) {
            Assert.fail("TC05 FAILED: " + e.getMessage());
        }
    }


    /**
     * TC06 - Tìm kiếm nhân viên theo tên
     *
     * Manual expected: "Hệ thống hiển thị thông tin nhân viên theo tên" → PASS
     *
     * Root cause lỗi cũ: searchStaff() dùng stalenessOf(oldTable) nhưng React
     * không unmount tbody khi re-render → wait timeout → test fail.
     *
     * Fix: searchStaff() dùng JS native setter để trigger React onChange,
     *      bỏ stalenessOf, chờ bằng Thread.sleep(2500) + presenceOfElementLocated.
     *      hasSearchResult() đọc rows trực tiếp từ DOM để tránh stale reference.
     */
    @Test(description = "TC06 - Tìm kiếm nhân viên theo tên")
    public void TC06_SearchStaffByName() {
        // Lấy tên thực tế của NV đầu tiên trong danh sách
        WebElement firstNameCell = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("table tbody tr:first-child td:nth-child(2)")));
        String targetName = firstNameCell.getText().trim();
        System.out.println("DEBUG TC06 - Tìm theo tên: [" + targetName + "]");


        staffPage.searchStaff(targetName);


        Assert.assertTrue(
                staffPage.hasSearchResult(),
                "TC06 FAILED: Không tìm thấy nhân viên với tên: [" + targetName + "]");
    }


    /**
     * TC07 - Tìm kiếm nhân viên theo số điện thoại
     *
     * Manual expected: "Hệ thống hiển thị thông tin nhân viên theo số điện thoại" → PASS
     *
     * Cùng root cause và fix như TC06.
     */
    @Test(description = "TC07 - Tìm kiếm nhân viên theo số điện thoại")
    public void TC07_SearchStaffByPhone() {
        // Lấy SĐT thực tế của NV đầu tiên trong danh sách
        WebElement firstPhoneCell = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("table tbody tr:first-child td:nth-child(3)")));
        String targetPhone = firstPhoneCell.getText().trim();
        System.out.println("DEBUG TC07 - Tìm theo SĐT: [" + targetPhone + "]");


        staffPage.searchStaff(targetPhone);


        Assert.assertTrue(
                staffPage.hasSearchResult(),
                "TC07 FAILED: Không tìm thấy nhân viên với SĐT: [" + targetPhone + "]");
    }


    /**
     * TC08 - Hủy thao tác xóa nhân viên
     *
     * Manual expected: "Hệ thống hủy thao tác không lưu dữ liệu và quay về
     * màn hình CHI TIẾT thông tin nhân viên" → PASS
     *
     * Root cause lỗi cũ:
     * 1. Assert chỉ kiểm tra url.contains("employees") — đúng về URL nhưng
     *    không verify được dialog đã đóng và NV chưa bị xóa.
     * 2. Selector //button[contains(.,'Xóa')] bắt cả nút Xóa trong dialog
     *    lẫn nút Xóa trên trang → false positive.
     * 3. Sleep sau click "Bỏ qua" quá ngắn (1000ms) — dialog animation chưa
     *    kịp đóng hoàn toàn → tiếp theo tìm nút Xóa có thể vẫn thấy nút trong dialog.
     *
     * Fix:
     * - Tăng sleep lên 1500ms sau click "Bỏ qua".
     * - Verify 3 điều kiện độc lập bằng isOnStaffDetailPage():
     *   (a) URL vẫn chứa "employees"
     *   (b) Dialog đóng: không còn //div[@role='dialog']//*[contains(text(),'Xóa nhân viên')]
     *   (c) NV chưa bị xóa: nút Xóa/Chỉnh sửa trên trang chi tiết vẫn còn
     */
    @Test(description = "TC08 - Hủy thao tác xóa nhân viên")
    public void TC08_CancelDeleteStaff() {
        try {
            clickFirstStaffAndWaitDetail();


            // Bước 1: Click nút "Xóa" trên trang chi tiết
            WebElement btnDelete = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(.,'Xóa')]")));
            ((JavascriptExecutor) Constant.WEBDRIVER)
                    .executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", btnDelete);
            try { Thread.sleep(1000); } catch (Exception ignored) {}


            // Bước 2: Chờ dialog xác nhận xuất hiện
            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Xóa nhân viên')]")));
            try { Thread.sleep(500); } catch (Exception ignored) {}


            // Bước 3: Click "Bỏ qua" trong dialog
            WebElement btnBoQua = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Bỏ qua']")));
            ((JavascriptExecutor) Constant.WEBDRIVER)
                    .executeScript("arguments[0].click();", btnBoQua);


            // Tăng sleep để dialog animation đóng hoàn toàn trước khi assert
            try { Thread.sleep(1500); } catch (Exception ignored) {}


            // Bước 4: Verify theo đúng manual spec —
            // "quay về màn hình chi tiết thông tin nhân viên, NV không bị xóa"
            Assert.assertTrue(
                    staffPage.isOnStaffDetailPage(),
                    "TC08 FAILED: Không còn ở trang chi tiết nhân viên hoặc dialog chưa đóng. URL: "
                            + Constant.WEBDRIVER.getCurrentUrl());


        } catch (Exception e) {
            Assert.fail("TC08 FAILED: " + e.getMessage());
        }
    }


    /**
     * TC09 - [BUG] Thêm NV với SĐT không đủ 10 số
     * Manual expected: "Số điện thoại phải đủ 10 số" → Status: FAILED (bug)
     */
    @Test(description = "TC09 - [BUG] Thêm NV với SĐT không đủ 10 số")
    public void TC09_AddStaffInvalidPhoneLessThan10() {
        staffPage.clickCreateNew();
        staffPage.fillStaffForm("NV Sai SĐT", "090", "saisd@gmail.com",
                "Nhân viên bán hàng", "Bán hàng");
        staffPage.clickSave();
        try { Thread.sleep(1500); } catch (Exception ignored) {}


        String toast = staffPage.getToastMessage().toLowerCase();
        boolean hasError = toast.contains("10 số") || toast.contains("không hợp lệ") || toast.contains("nhập lại");
        Assert.assertTrue(hasError,
                "TC09 FAILED (BUG): Hệ thống cho phép thêm NV với SĐT < 10 số! Toast: [" + toast + "]");
    }


    /**
     * TC10 - Thêm NV với SĐT đã tồn tại
     * Manual expected: "Số điện thoại đã được sử dụng" → PASS
     */
    @Test(description = "TC10 - Thêm NV với SĐT đã tồn tại → hệ thống báo lỗi")
    public void TC10_AddStaffDuplicatePhone() {
        WebElement firstPhoneCell = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("table tbody tr:first-child td:nth-child(3)")));
        String existingPhone = firstPhoneCell.getText().trim();


        staffPage.clickCreateNew();
        staffPage.fillStaffForm("NV Trùng SĐT", existingPhone, "trung@gmail.com",
                "Nhân viên bán hàng", "Bán hàng");
        staffPage.clickSave();
        try { Thread.sleep(1500); } catch (Exception ignored) {}


        String toast = staffPage.getToastMessage();
        Assert.assertTrue(
                toast.contains("đã được sử dụng") || toast.contains("tồn tại")
                        || toast.contains("trùng") || !toast.toLowerCase().contains("thành công"),
                "TC10 FAILED: Cho phép trùng SĐT [" + existingPhone + "]. Toast: [" + toast + "]");
    }


    /**
     * TC11 - [BUG] Sửa mức lương <= 0
     * Manual expected: "Mức lương phải lớn hơn 0, vui lòng nhập lại" → Status: FAILED (bug)
     */
    @Test(description = "TC11 - [BUG] Sửa mức lương <= 0 vẫn lưu thành công")
    public void TC11_EditSalaryZeroOrNegative() {
        try {
            WebElement targetRow = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("table tbody tr:first-child")));
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", targetRow);


            staffPage.clickEdit();


            WebElement salaryInput = Constant.WEBDRIVER.findElement(
                    By.xpath("//label[contains(translate(.,'LƯƠNG','lương'),'lương')]/ancestor::div[1]//input" +
                            " | //label[contains(.,'Mức lương') or contains(.,'mức lương')]/following-sibling::*//input"));
            salaryInput.click();
            salaryInput.sendKeys(org.openqa.selenium.Keys.chord(
                    org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
            salaryInput.sendKeys("0");


            staffPage.clickSave();
            Thread.sleep(1500);


            String toast = staffPage.getToastMessage().toLowerCase();
            Assert.assertFalse(toast.contains("thành công"),
                    "TC11 PASSED (BUG BỊ BẮT): Hệ thống cho phép cập nhật lương = 0!");
        } catch (Exception e) {
            Assert.fail("TC11 lỗi script: " + e.getMessage());
        }
    }


    /**
     * TC12 - [BUG] Sửa SĐT < 10 số vẫn lưu thành công
     */
    @Test(description = "TC12 - [BUG] Sửa SĐT nhân viên < 10 số vẫn lưu thành công")
    public void TC12_EditPhoneLessThan10Digits() {
        try {
            WebElement targetRow = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("table tbody tr:first-child")));
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", targetRow);


            staffPage.clickEdit();


            staffPage.enterPhone("090123");
            staffPage.clickSave();
            Thread.sleep(2000);


            String toast = staffPage.getToastMessage();
            boolean hasPhoneError = toast.contains("10 số") || toast.contains("không hợp lệ")
                    || toast.contains("yêu cầu nhập lại") || toast.contains("Số điện thoại");


            Assert.assertTrue(hasPhoneError,
                    "TC12 FAILED (BUG BỊ BẮT): Hệ thống cho phép cập nhật SĐT < 10 số!");
        } catch (Exception e) {
            Assert.fail("TC12 lỗi script: " + e.getMessage());
        }
    }
}

