package testcases;

import Common.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.DangNhapPage;

import java.time.Duration;

public class DangNhapTests {

    private DangNhapPage loginPage;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
        Constant.WEBDRIVER.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @BeforeMethod
    public void reset() {
        // LẦN 1: Đi tới trang Login để "nhận diện" tên miền (Domain).
        // Lúc này kệ cho React nó có đá bạn về Dashboard hay không, không quan tâm!
        Constant.WEBDRIVER.get(Constant.LOGIN_URL);

        // TIẾN HÀNH "SÁT THỦ": Giết sạch mọi Token đang lẩn trốn trong bộ nhớ
        Constant.WEBDRIVER.manage().deleteAllCookies();
        try {
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("window.localStorage.clear();");
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("window.sessionStorage.clear();");
        } catch (Exception ignored) {}


        Constant.WEBDRIVER.get(Constant.LOGIN_URL);


        new org.openqa.selenium.support.ui.WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("tenDangNhap")));


        loginPage = new DangNhapPage();
    }

    @AfterClass
    public void teardown() {
        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
        }
    }

    @Test
    public void TC01_LoginSuccess() {
        loginPage.login("owner", "123456");
        Assert.assertTrue(loginPage.isLoginSuccessful(), "TC01 FAILED: Vẫn ở trang login sau khi đăng nhập đúng");
    }

    @Test
    public void TC02_WrongPassword() {
        loginPage.login("owner", "wrongpass");
        String error = loginPage.getErrorMessage();
        Assert.assertFalse(error.isEmpty(), "TC02 FAILED: Không hiển thị thông báo lỗi.");
    }

    @Test
    public void TC03_UserNotExist() {
        loginPage.login("user_xyz_999", "123456");
        String error = loginPage.getErrorMessage();
        Assert.assertFalse(error.isEmpty(), "TC03 FAILED: Không hiển thị thông báo lỗi.");
    }

    @Test
    public void TC04_LockAccountAfter5Fails() {
        for (int i = 1; i <= 5; i++) {
            loginPage.login("owne", "wrong" + i);
        }
        String error = loginPage.getErrorMessage().toLowerCase();
        Assert.assertTrue(error.contains("khóa") || error.contains("vượt quá"),
                "TC04 FAILED (BUG): Hệ thống không khóa tài khoản. Lỗi hiện tại là: " + error);
    }

    @Test
    public void TC05_EmptyBoth() {
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isOnLoginPage(), "TC05 FAILED: Hệ thống cho phép submit form rỗng");
    }

    @Test
    public void TC06_MissingUsername() {
        loginPage.enterPassword("123456");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isOnLoginPage(), "TC06 FAILED: Hệ thống cho phép submit khi thiếu username");
    }

    @Test
    public void TC07_MissingPassword() {
        loginPage.enterUsername("owner");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isOnLoginPage(), "TC07 FAILED: Hệ thống cho phép submit khi thiếu password");
    }

    @Test
    public void TC08_StaffLogin() {
        loginPage.login("staff1", "123456");
        Assert.assertTrue(loginPage.isLoginSuccessful(), "TC08 FAILED: Staff login FAILED");
    }

    @Test
    public void TC09() {
        loginPage.login("owner", "123456");
        Assert.assertTrue(loginPage.isLoginSuccessful(), "TC09 FAILED: Owner login FAILED");
    }

    @Test
    public void TC10() {
        loginPage.login("manager", "123456");
        Assert.assertTrue(loginPage.isLoginSuccessful(), "TC10 FAILED: Manager login FAILED");
        Assert.assertTrue(Constant.WEBDRIVER.findElements(By.tagName("body")).size() > 0,
                "TC10 FAILED: Dashboard container not loaded");
    }
}