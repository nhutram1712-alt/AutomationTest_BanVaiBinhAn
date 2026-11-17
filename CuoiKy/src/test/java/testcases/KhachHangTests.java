package testcases;

import Common.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.BaoCaoPage_OwnerHomePage;
import pageobjects.KhachHangPage;
import pageobjects.DangNhapPage;

public class KhachHangTests extends BaseTest  {

    //  Khai báo biến dùng chung cho TC10 - TC19
    private static final String EXISTING_CUSTOMER = "Nguyễn Lan Anh";
    private static final String EXISTING_PHONE    = "0912345678";

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");

        // Tự động tải ChromeDriver để tránh lỗi phiên bản
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); // ← chạy ẩn danh, không có password manager

        Constant.WEBDRIVER = new ChromeDriver(options);
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
        }
    }


    @Test
    public void TC01() {
        System.out.println("TC01 - Thêm khách hàng thành công với dữ liệu hợp lệ...");
        String randomPart = String.valueOf(System.currentTimeMillis()).substring(8);
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        KhachHangPage khachHangPage = baoCaoPage.gotToKhachHangPage();
        loginPage.waitForLoginToastToDisappear();

        String tenKH = "Nguyễn Lan Anh " + randomPart;
        String sdtKH = "0912" + randomPart;
        String emailKH = "lananh" + randomPart + "@gmail.com";

        khachHangPage.addNewCustomer(tenKH, sdtKH, emailKH);

        String successMsg = khachHangPage.getMsgSucces().getText();
        Assert.assertEquals(successMsg, "Đã thêm khách hàng mới");
    }

    @Test
    public void TC02() {
        System.out.println("TC04 - Thêm khách hàng mới từ màn hình bán hàng");
        String randomPart = String.valueOf(System.currentTimeMillis()).substring(8);
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        KhachHangPage khachHangPage = baoCaoPage.gotToKhachHangPage();
        loginPage.waitForLoginToastToDisappear();
        khachHangPage.getBtnBanHang().click();
        khachHangPage.getBtnThemKHForm().click();

        String tenKH = "Nguyễn Lan Anh " + randomPart;
        String sdtKH = "0912" + randomPart;
        String emailKH = "lananh" + randomPart + "@gmail.com";

        khachHangPage.addNewCustomer_TC4(tenKH, sdtKH, emailKH);

        String successMsg = khachHangPage.getMsgSucces().getText();
        Assert.assertEquals(successMsg, "Đã thêm khách hàng mới");
    }

    @Test
    public void TC03() {
        System.out.println("TC05 - Không cho thêm khách hàng khi bỏ trống tên");
        String randomPart = String.valueOf(System.currentTimeMillis()).substring(8);
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        KhachHangPage khachHangPage = baoCaoPage.gotToKhachHangPage();
        loginPage.waitForLoginToastToDisappear();

        String tenKH = "";
        String sdtKH = "0912" + randomPart;
        String emailKH = "lananh" + randomPart + "@gmail.com";

        khachHangPage.addNewCustomer(tenKH, sdtKH, emailKH);

        String successMsg = khachHangPage.getMsgError().getText();
        Assert.assertEquals(successMsg, "Vui lòng nhập tên và số điện thoại");
    }

    @Test
    public void TC04() {
        System.out.println("TC06 - Không cho thêm khách hàng khi bỏ trống email");
        String randomPart = String.valueOf(System.currentTimeMillis()).substring(8);
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        KhachHangPage khachHangPage = baoCaoPage.gotToKhachHangPage();
        loginPage.waitForLoginToastToDisappear();

        String tenKH = "Nguyễn Lan Anh " + randomPart;
        String sdtKH = "0912" + randomPart;
        String emailKH = "";

        khachHangPage.addNewCustomer(tenKH, sdtKH, emailKH);

        String successMsg = khachHangPage.getMsgError().getText();
        Assert.assertEquals(successMsg, "email must be an email");
    }

    @Test
    public void TC05() {
        System.out.println("TC07 - Không cho thêm khách hàng khi bỏ trống số điện thoại");
        String randomPart = String.valueOf(System.currentTimeMillis()).substring(8);
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        KhachHangPage khachHangPage = baoCaoPage.gotToKhachHangPage();
        loginPage.waitForLoginToastToDisappear();

        String tenKH = "Nguyễn Lan Anh " + randomPart;
        String sdtKH = "";
        String emailKH = "lananh" + randomPart + "@gmail.com";

        khachHangPage.addNewCustomer(tenKH, sdtKH, emailKH);

        String successMsg = khachHangPage.getMsgError().getText();
        Assert.assertEquals(successMsg, "Vui lòng nhập tên và số điện thoại");
    }

    @Test
    public void TC06() {
        System.out.println("TC08 - Không cho thêm khách hàng khi SĐT ít hơn 10 số");
        String randomPart = String.valueOf(System.currentTimeMillis()).substring(8);
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        KhachHangPage khachHangPage = baoCaoPage.gotToKhachHangPage();
        loginPage.waitForLoginToastToDisappear();

        String tenKH = "Nguyễn Lan Anh " + randomPart;
        String sdtKH = "092" + randomPart;
        String emailKH = "lananh" + randomPart + "@gmail.com";

        khachHangPage.addNewCustomer(tenKH, sdtKH, emailKH);

        String successMsg = khachHangPage.getMsgSucces().getText();
        Assert.assertEquals(successMsg, "Số điện thoại phải đủ 10 số!");
    }

    @Test
    public void TC07() {
        System.out.println("TC09 - Hủy thêm khách hàng");
        String randomPart = String.valueOf(System.currentTimeMillis()).substring(8);
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        KhachHangPage khachHangPage = baoCaoPage.gotToKhachHangPage();
        loginPage.waitForLoginToastToDisappear();

        String tenKH = "Nguyễn Lan Anh " + randomPart;
        String sdtKH = "0912" + randomPart;
        String emailKH = "lananh" + randomPart + "@gmail.com";

        khachHangPage.cancelAddCustomer(tenKH, sdtKH, emailKH);

        boolean isClosed = khachHangPage.isAddFormClosed();
        Assert.assertTrue(isClosed);
    }

    @Test
    public void TC08() {
        System.out.println("TC08_Search - Tra cứu khách hàng theo số điện thoại chính xác");
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        KhachHangPage khPage = baoCaoPage.gotToKhachHangPage();
        loginPage.waitForLoginToastToDisappear();

        String sdtCanTim = "091209659";
        khPage.searchCustomer(sdtCanTim);

        Assert.assertTrue(khPage.getTenKHDauTien().isDisplayed(), "Không tìm thấy kết quả phù hợp");
    }

    @Test
    public void TC09() {
        System.out.println("TC08_Search2 - Tra cứu khách hàng theo Tên chính xác");
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        KhachHangPage khPage = baoCaoPage.gotToKhachHangPage();
        loginPage.waitForLoginToastToDisappear();

        String tenCanTim = "Nguyễn Lan Anh";
        khPage.searchCustomer(tenCanTim);

        Assert.assertTrue(khPage.getTenKHDauTien().isDisplayed(), "Không tìm thấy kết quả phù hợp");
    }



    private KhachHangPage loginAndNavigateToKhachHang() {
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        KhachHangPage khachHangPage = baoCaoPage.gotToKhachHangPage();
        try { loginPage.waitForLoginToastToDisappear(); } catch (Exception e) {}
        return khachHangPage;
    }

    @Test(description = "TC10 - Tra cứu SĐT không tồn tại → không có kết quả")
    public void TC10_SearchPhoneNotExist() {
        KhachHangPage khachHangPage = loginAndNavigateToKhachHang();
        khachHangPage.search("0000000000");
        Assert.assertEquals(khachHangPage.getResultCount(), 0, "TC10 FAILED: Vẫn ra kết quả khi tìm SĐT không tồn tại!");
    }

    @Test(description = "TC11 - Tra cứu tên không tồn tại → không có kết quả")
    public void TC11_SearchNameNotExist() {
        KhachHangPage khachHangPage = loginAndNavigateToKhachHang();
        khachHangPage.search("XYZABC_KHONG_TON_TAI_9999");
        Assert.assertEquals(khachHangPage.getResultCount(), 0, "TC11 FAILED: Vẫn ra kết quả khi tìm tên không tồn tại!");
    }

    @Test(description = "TC12 - [BUG] Sửa KH thành công nhưng thông báo sai")
    public void TC12_UpdateCustomerBugWrongMessage() {
        KhachHangPage khachHangPage = loginAndNavigateToKhachHang();
        khachHangPage.search(EXISTING_CUSTOMER);
        khachHangPage.clickFirstRow();
        khachHangPage.clickEdit();

        khachHangPage.updateCustomer(
                "KH Edited " + System.currentTimeMillis(),
                "edited_" + System.currentTimeMillis() + "@gmail.com",
                EXISTING_PHONE
        );
        khachHangPage.clickSave();
        try { Thread.sleep(2000); } catch (Exception ignored) {}

        String toast = khachHangPage.getMessage();
        Assert.assertTrue(
                toast.contains("Cập nhật thành công"),
                "TC12 FAILED (BUG): Thông báo sai nội dung."
                        + "\n  Expected: [Cập nhật thành công]"
                        + "\n  Actual:   [" + toast + "]");
    }

    @Test(description = "TC13 - Không cập nhật khi bỏ trống email")
    public void TC13_UpdateEmptyEmail() {
        KhachHangPage khachHangPage = loginAndNavigateToKhachHang();
        khachHangPage.search(EXISTING_CUSTOMER);
        khachHangPage.clickFirstRow();
        khachHangPage.clickEdit();

        khachHangPage.updateCustomer(EXISTING_CUSTOMER, "", EXISTING_PHONE);
        khachHangPage.clickSave();
        try { Thread.sleep(2000); } catch (Exception ignored) {}

        String message = khachHangPage.getMessage();
        Assert.assertTrue(
                message.toLowerCase().contains("email must be an email") || message.toLowerCase().contains("email"),
                "TC13 FAILED: Hệ thống không báo lỗi khi email trống. Thực tế: [" + message + "]");
    }

    @Test(description = "TC14 - Không cập nhật khi bỏ trống tên")
    public void TC14_UpdateEmptyName() {
        KhachHangPage khachHangPage = loginAndNavigateToKhachHang();
        khachHangPage.search(EXISTING_CUSTOMER);
        khachHangPage.clickFirstRow();
        khachHangPage.clickEdit();

        khachHangPage.updateCustomer("", "valid@gmail.com", EXISTING_PHONE);
        khachHangPage.clickSave();
        try { Thread.sleep(2000); } catch (Exception ignored) {}

        String message = khachHangPage.getMessage();
        Assert.assertTrue(
                message.contains("Vui lòng nhập tên và số điện thoại") || message.toLowerCase().contains("nhập tên"),
                "TC14 FAILED: Hệ thống không báo lỗi khi tên trống. Thực tế: [" + message + "]");
    }

    @Test(description = "TC15 - Không cập nhật khi bỏ trống SĐT")
    public void TC15_UpdateEmptyPhone() {
        KhachHangPage khachHangPage = loginAndNavigateToKhachHang();
        khachHangPage.search(EXISTING_CUSTOMER);
        khachHangPage.clickFirstRow();
        khachHangPage.clickEdit();

        khachHangPage.updateCustomer(EXISTING_CUSTOMER, "valid@gmail.com", "");
        khachHangPage.clickSave();
        try { Thread.sleep(2000); } catch (Exception ignored) {}

        String message = khachHangPage.getMessage();
        Assert.assertTrue(
                message.contains("Vui lòng nhập tên và số điện thoại") || message.toLowerCase().contains("số điện thoại"),
                "TC15 FAILED: Hệ thống không báo lỗi khi SĐT trống. Thực tế: [" + message + "]");
    }

    @Test(description = "TC16 - [BUG] Cho cập nhật khi SĐT < 10 số")
    public void TC16_UpdateInvalidPhoneBug() {
        KhachHangPage khachHangPage = loginAndNavigateToKhachHang();
        khachHangPage.search(EXISTING_CUSTOMER);
        khachHangPage.clickFirstRow();
        khachHangPage.clickEdit();

        khachHangPage.updateCustomer(EXISTING_CUSTOMER, "valid@gmail.com", "090123");
        khachHangPage.clickSave();
        try { Thread.sleep(2000); } catch (Exception ignored) {}

        String message = khachHangPage.getMessage();
        Assert.assertTrue(
                message.contains("Số điện thoại phải đủ 10 số") || message.contains("10 số"),
                "TC16 FAILED (BUG): Hệ thống cho phép lưu SĐT < 10 số. Thực tế: [" + message + "]");
    }

    @Test(description = "TC17 - Hủy thao tác sửa KH → không lưu thay đổi")
    public void TC17_CancelEdit() {
        KhachHangPage khachHangPage = loginAndNavigateToKhachHang();
        khachHangPage.search(EXISTING_CUSTOMER);
        khachHangPage.clickFirstRow();
        khachHangPage.clickEdit();

        khachHangPage.updateCustomer("Tên Sẽ Không Được Lưu", "noluu@gmail.com", "0999999999");
        khachHangPage.clickCancel();
        try { Thread.sleep(1500); } catch (Exception ignored) {}

        String pageText = Constant.WEBDRIVER.findElement(org.openqa.selenium.By.tagName("body")).getText();
        Assert.assertFalse(pageText.toLowerCase().contains("đã cập nhật"), "TC17 FAILED: Nhấn Bỏ qua nhưng vẫn lưu thay đổi!");
    }

    @Test(description = "TC18 - Xóa KH thành công")
    public void TC18_DeleteCustomerSuccess() {
        KhachHangPage khachHangPage = loginAndNavigateToKhachHang();
        khachHangPage.search(EXISTING_CUSTOMER);
        khachHangPage.clickFirstRow();
        khachHangPage.clickDelete();
        khachHangPage.confirmDelete();
        try { Thread.sleep(2000); } catch (Exception ignored) {}

        String toast = khachHangPage.getMessage();
        Assert.assertTrue(
                toast.toLowerCase().contains("xóa") || toast.toLowerCase().contains("thành công"),
                "TC18 FAILED: Lỗi chức năng xóa KH. Thực tế: [" + toast + "]");
    }

    @Test(description = "TC19 - Hủy thao tác xóa KH → KH vẫn còn trong danh sách")
    public void TC19_CancelDelete() {
        KhachHangPage khachHangPage = loginAndNavigateToKhachHang();
        khachHangPage.search(EXISTING_CUSTOMER);
        khachHangPage.clickFirstRow();
        khachHangPage.clickDelete();
        khachHangPage.cancelDelete();
        try { Thread.sleep(1500); } catch (Exception ignored) {}

        Common.Constant.WEBDRIVER.navigate().refresh();
        try { Thread.sleep(2000); } catch (Exception ignored) {}

        khachHangPage.search(EXISTING_CUSTOMER);
        int count = khachHangPage.getResultCount();
        Assert.assertTrue(count > 0, "TC19 FAILED: Nhấn Bỏ qua nhưng KH bị xóa mất! Count: " + count);
    }
}