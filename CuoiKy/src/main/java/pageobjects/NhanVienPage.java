package pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;


public class NhanVienPage extends GeneralPage {


    // ========== LOCATORS — TRANG DANH SÁCH ==========


    @FindBy(xpath = "//button[contains(.,'Tạo mới')]")
    private WebElement btnCreateNew;


    @FindBy(css = "input[placeholder='Nhập tên nhân viên, số điện thoại']")
    private WebElement txtSearch;


    @FindBy(css = "table tbody tr")
    private List<WebElement> staffRows;


    @FindBy(css = "li[data-state='open']")
    private WebElement toastNotification;


    // ========== LOCATORS — FORM THÊM/SỬA ==========


    @FindBy(xpath = "//button[contains(.,'Chỉnh sửa')]")
    private WebElement btnEdit;


    @FindBy(xpath = "//label[contains(.,'Tên nhân viên')]/following-sibling::input | //label[contains(.,'Tên nhân viên')]/..//input")
    private WebElement txtName;


    @FindBy(xpath = "//label[contains(.,'Số điện thoại')]/following-sibling::input | //label[contains(.,'Số điện thoại')]/..//input")
    private WebElement txtPhone;


    @FindBy(css = "input[type='email']")
    private WebElement txtEmail;


    @FindBy(css = "input[placeholder*='Nhân viên bán hàng']")
    private WebElement txtPosition;


    @FindBy(css = "input[placeholder*='Bán hàng, Kế toán']")
    private WebElement txtDepartment;


    @FindBy(xpath = "//button[normalize-space()='Lưu']")
    private WebElement btnSave;


    @FindBy(xpath = "//button[normalize-space()='Bỏ qua']")
    private WebElement btnCancel;


    // ========== CONSTRUCTOR ==========
    public NhanVienPage() {
        super();
    }


    // =========================================================
    // PRIVATE HELPER
    // =========================================================


    /**
     * [FIX TC01] sendKeys() thuần không trigger onChange của React controlled input
     * vì React dùng synthetic event system, không lắng nghe DOM event trực tiếp.
     *
     * Giải pháp chuẩn: dùng JS nativeInputValueSetter để ghi thẳng vào property
     * của HTMLInputElement prototype, sau đó dispatch 'input' + 'change' event
     * để React reconciler nhận biết và cập nhật state.
     * Kết hợp sendKeys(Keys.END) để trigger thêm React onKeyDown/onKeyUp nếu cần.
     */
    private void setReactValue(WebElement input, String value) {
        ((JavascriptExecutor) driver).executeScript(
                "var nativeSetter = Object.getOwnPropertyDescriptor(" +
                        "window.HTMLInputElement.prototype, 'value').set;" +
                        "nativeSetter.call(arguments[0], arguments[1]);" +
                        "arguments[0].dispatchEvent(new Event('input',  {bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                input, value);
        // Gửi một phím trắng để React nhận thêm keyboard event (trigger validation nếu có)
        input.sendKeys(Keys.END);
        try { Thread.sleep(150); } catch (Exception ignored) {}
    }


    // =========================================================
    // ACTIONS — TRANG DANH SÁCH
    // =========================================================


    public void navigateToStaffPage() {
        driver.get("http://localhost:3000/employees");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h3.tracking-tight")));
    }


    /**
     * [FIX TC01] Thêm sleep 800ms sau khi form mở để React khởi tạo xong
     * tất cả controlled input state trước khi điền dữ liệu.
     */
    public void clickCreateNew() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h3.tracking-tight")));
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Tạo mới')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//h2[contains(.,'Thêm nhân viên')]" +
                        " | //h1[contains(.,'Thêm nhân viên')]" +
                        " | //*[contains(.,'Thêm nhân viên') and contains(@class,'font-bold')]")));
        // Đợi React hoàn tất render + khởi tạo controlled inputs
        try { Thread.sleep(800); } catch (Exception ignored) {}
    }


    /**
     * [FIX TC06, TC07]
     *
     * Root cause gốc: stalenessOf(oldTable) LUÔN timeout vì React re-render
     * table IN-PLACE — tbody DOM node không bị unmount/remount, chỉ có nội dung
     * bên trong thay đổi, nên element tham chiếu không bao giờ stale.
     *
     * Fix:
     * 1. Dùng setReactValue để trigger React onChange trên search input
     *    (search input cũng là controlled input, sendKeys không đủ).
     * 2. Bỏ stalenessOf, thay bằng Thread.sleep(2500) để chờ debounce + API call
     *    + React re-render hoàn tất.
     * 3. Verify tbody presence sau sleep để confirm data đã load.
     */
    public void searchStaff(String keyword) {
        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                        "input[placeholder='Nhập tên nhân viên, số điện thoại']")));


        // Xóa giá trị cũ qua JS
        ((JavascriptExecutor) driver).executeScript(
                "var nativeSetter = Object.getOwnPropertyDescriptor(" +
                        "window.HTMLInputElement.prototype, 'value').set;" +
                        "nativeSetter.call(arguments[0], '');" +
                        "arguments[0].dispatchEvent(new Event('input',  {bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                searchInput);
        try { Thread.sleep(300); } catch (Exception ignored) {}


        // Nhập từ khóa qua JS native setter để trigger React onChange
        ((JavascriptExecutor) driver).executeScript(
                "var nativeSetter = Object.getOwnPropertyDescriptor(" +
                        "window.HTMLInputElement.prototype, 'value').set;" +
                        "nativeSetter.call(arguments[0], arguments[1]);" +
                        "arguments[0].dispatchEvent(new Event('input',  {bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                searchInput, keyword);


        // Gửi ENTER để trigger search submit
        searchInput.sendKeys(Keys.ENTER);


        // Chờ React debounce + re-render (không dùng stalenessOf — React giữ nguyên DOM node)
        try { Thread.sleep(2500); } catch (Exception ignored) {}


        // Xác nhận tbody đã present (dù có hay không có kết quả)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody")));
    }


    /**
     * [FIX TC06, TC07] Đọc rows trực tiếp từ DOM thay vì @FindBy staffRows
     * để tránh StaleElementReferenceException sau khi React re-render.
     */
    public boolean hasSearchResult() {
        try {
            List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
            if (rows.isEmpty()) return false;
            String firstRowText = rows.get(0).getText().toLowerCase();
            return !firstRowText.contains("không tìm thấy")
                    && !firstRowText.contains("trống")
                    && !firstRowText.contains("no data")
                    && !firstRowText.contains("không có")
                    && !firstRowText.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isNoResultShown() {
        try {
            Thread.sleep(1000);
            List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
            if (rows.isEmpty()) return true;
            if (rows.size() == 1) {
                String text = rows.get(0).getText().toLowerCase();
                return text.contains("không tìm thấy") || text.contains("không có dữ liệu")
                        || text.contains("no data") || text.contains("no results")
                        || text.contains("trống");
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    public void clickEdit() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Chỉnh sửa')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[normalize-space()='Lưu']")));
    }


    // =========================================================
    // ACTIONS — FORM THÊM / SỬA
    // =========================================================


    /** [FIX TC01] Dùng setReactValue thay sendKeys thuần */
    public void enterName(String name) {
        try {
            WebElement input = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//label[contains(.,'Tên nhân viên')]/ancestor::div[1]//input)[1]")));
            input.click();
            setReactValue(input, name);
        } catch (Exception e) {
            try {
                List<WebElement> inputs = driver.findElements(
                        By.cssSelector("input[placeholder='Bắt buộc']"));
                if (!inputs.isEmpty()) setReactValue(inputs.get(0), name);
            } catch (Exception ignored) {}
        }
    }


    /** [FIX TC01] Dùng setReactValue thay sendKeys thuần */
    public void enterPhone(String phone) {
        try {
            WebElement input = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//label[contains(.,'Số điện thoại')]/ancestor::div[1]//input)[1]")));
            input.click();
            setReactValue(input, phone);
        } catch (Exception e) {
            try {
                List<WebElement> inputs = driver.findElements(
                        By.cssSelector("input[placeholder='Bắt buộc']"));
                if (inputs.size() > 1) setReactValue(inputs.get(1), phone);
            } catch (Exception ignored) {}
        }
    }


    /** [FIX TC01] Dùng setReactValue thay sendKeys thuần */
    public void enterEmail(String email) {
        try {
            waitForVisible(txtEmail);
            txtEmail.click();
            setReactValue(txtEmail, email);
        } catch (Exception e) {
            System.out.println("DEBUG enterEmail: " + e.getMessage());
        }
    }


    /** [FIX TC01] Dùng setReactValue thay sendKeys thuần */
    public void enterPosition(String position) {
        try {
            waitForVisible(txtPosition);
            txtPosition.click();
            setReactValue(txtPosition, position);
        } catch (Exception e) {
            System.out.println("DEBUG enterPosition: " + e.getMessage());
        }
    }


    /** [FIX TC01] Dùng setReactValue thay sendKeys thuần */
    public void enterDepartment(String department) {
        try {
            waitForVisible(txtDepartment);
            txtDepartment.click();
            setReactValue(txtDepartment, department);
        } catch (Exception e) {
            System.out.println("DEBUG enterDepartment: " + e.getMessage());
        }
    }


    public void fillStaffForm(String name, String phone, String email,
                              String position, String department) {
        enterName(name);
        enterPhone(phone);
        enterEmail(email);
        enterPosition(position);
        enterDepartment(department);
    }


    public void clickSave() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Lưu']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }


    public void clickCancel() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Bỏ qua']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }


    // =========================================================
    // GETTERS
    // =========================================================


    public String getToastMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("li[data-state='open']")));
            return driver.findElement(
                    By.cssSelector("li[data-state='open']")).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }


    public boolean isOnStaffListPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("h3.tracking-tight")));
            return driver.getCurrentUrl().contains("/employees");
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * [FIX TC08] Expected behavior từ Excel manual: "quay về màn hình CHI TIẾT
     * thông tin nhân viên" — KHÔNG phải quay về list.
     * Dùng method này để verify TC08 đúng với manual spec.
     */
    public boolean isOnStaffDetailPage() {
        try {
            // Trang chi tiết: có nút Chỉnh sửa hoặc Xóa, dialog đã đóng
            boolean hasDetailButtons = !driver.findElements(By.xpath(
                            "//button[contains(.,'Xóa')] | //button[contains(.,'Chỉnh sửa')]"))
                    .isEmpty();
            boolean dialogClosed = driver.findElements(By.xpath(
                            "//div[@role='dialog']//*[contains(text(),'Xóa nhân viên')]"))
                    .isEmpty();
            return driver.getCurrentUrl().contains("employees")
                    && hasDetailButtons && dialogClosed;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isAddFormVisible() {
        try {
            return driver.findElement(By.xpath(
                    "//*[contains(.,'Thêm nhân viên')]")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

