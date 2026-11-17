package testcases;

import Common.Constant;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.*;

public class DoiMatKhauTests extends BaseTest{
    @BeforeMethod
    public void beforeMethod() {

        System.out.println("Pre-condition");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); // ← chạy ẩn danh, không có password manager

        Constant.WEBDRIVER = new ChromeDriver(options);
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();
    }

    @Test
    public void TC01() {
        System.out.println("TC01-Đổi mật khẩu thành công với thông tin hợp lệ");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang đổi mật khẩu, điền đúng thông tin tài khoản đã tồn tại
        DoiMatKhauPage doiMatKhauPage = baoCaoPage.goToDoiMatKhauPage();
        doiMatKhauPage.fillChangePasswordForm("123456", "Abc@12345", "Abc@12345");

        // Step 3: Kiểm tra thông báo
        String actualToast = doiMatKhauPage.getLblSuccessMsg();
        Assert.assertEquals(actualToast, "Mật khẩu của bạn đã được cập nhật");
    }

    @Test
    public void TC02() {
        System.out.println("TC02-Đổi mật khẩu thất bại khi nhập sai mật khẩu cũ");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang đổi mật khẩu, điền đúng thông tin tài khoản đã tồn tại
        DoiMatKhauPage doiMatKhauPage = baoCaoPage.goToDoiMatKhauPage();
        doiMatKhauPage.fillChangePasswordForm("123456", "Abc@12345", "Abc@123456");

        // Step 3: Kiểm tra thông báo
        String actualToast = doiMatKhauPage.getLblErrorMsg();
        Assert.assertEquals(actualToast,"Không thể đổi mật khẩu!Vui lòng thử lại sau");
    }

    @Test
    public void TC03() {
        System.out.println("TC03-Đổi mật khẩu thất bại khi nhập sai mật khẩu cũ");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang đổi mật khẩu, điền mật khẩu chỉ có 7 ký tự số
        DoiMatKhauPage doiMatKhauPage = baoCaoPage.goToDoiMatKhauPage();
        doiMatKhauPage.fillChangePasswordForm("123456", "Abc@12345", "Abc@123456");

        // Step 3: Kiểm tra thông báo
        String actualToast = doiMatKhauPage.getLblErrorMsg();
        Assert.assertEquals(actualToast, "Mật khẩu xác nhận không khớp");
    }

    @Test
    public void TC04() {
        System.out.println("TC04-Mật khẩu mới không đủ điều kiện");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang đổi mật khẩu, điền mật khẩu chỉ có 7 ký tự số
        DoiMatKhauPage doiMatKhauPage = baoCaoPage.goToDoiMatKhauPage();
        doiMatKhauPage.fillChangePasswordForm("123456", "1234567", "1234567");

        // Step 3: Kiểm tra thông báo
        String actualToast = doiMatKhauPage.getLblInvalidErrorMsg();
        Assert.assertEquals(actualToast, "Mật khẩu mới phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt");
    }
}
