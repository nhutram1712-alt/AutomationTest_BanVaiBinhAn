package pageobjects;

import Common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DangNhapPage extends GeneralPage {

    @FindBy(id = "tenDangNhap")
    private WebElement txtUsername;

    @FindBy(id = "matKhau")
    private WebElement txtPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement btnLogin;

    public DangNhapPage() {
        super();
        PageFactory.initElements(Constant.WEBDRIVER, this);
    }

    public DangNhapPage open() {
        Constant.WEBDRIVER.get(Constant.LOGIN_URL);
        return this;
    }

//    public void enterUsername(String username) {
//        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(txtUsername));
//        wait.until(ExpectedConditions.elementToBeClickable(txtUsername));
//
//        try { txtUsername.click(); } catch (Exception ignored) {}
//
//        txtUsername.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//        txtUsername.sendKeys(Keys.DELETE);
//        txtUsername.sendKeys(username);
//    }

    public void enterUsername(String username) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(txtUsername));
        wait.until(ExpectedConditions.elementToBeClickable(txtUsername));


        try { Thread.sleep(1000); } catch (Exception ignored) {}


        for (int i = 0; i < 5; i++) {
            try {
                txtUsername.click(); // Click vào để lấy focus
                txtUsername.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
                txtUsername.sendKeys(username);


                Thread.sleep(500);


                if (txtUsername.getAttribute("value").equals(username)) {
                    break;
                }
            } catch (Exception ignored) {}
        }
    }

    public void enterPassword(String password) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(txtPassword));
        wait.until(ExpectedConditions.elementToBeClickable(txtPassword));

        try { txtPassword.click(); } catch (Exception ignored) {}

        txtPassword.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        txtPassword.sendKeys(Keys.DELETE);
        txtPassword.sendKeys(password);
    }

    public void clickLogin() {
        try {
            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(btnLogin));
            btnLogin.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", btnLogin);
        }
    }

    // ==========================================
    // CÁC HÀM ĐĂNG NHẬP (TRẢ ĐỦ KHÔNG THIẾU 1 HÀM NÀO)
    // ==========================================

    public DangNhapPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        try {
            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10)).until(
                    ExpectedConditions.or(
                            ExpectedConditions.not(ExpectedConditions.urlContains("login")),
                            ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'không') or contains(text(),'sai') or contains(text(),'thất bại') or contains(text(),'tồn tại') or contains(text(),'khóa') or contains(text(),'vượt quá')]"))
                    )
            );
        } catch (Exception ignored) {}
        return this;
    }

    public BaoCaoPage_OwnerHomePage loginOwner(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        try {
            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.not(ExpectedConditions.urlContains("login")));
        } catch (Exception ignored) {}
        return new BaoCaoPage_OwnerHomePage();
    }

    public ChamCongPage_NVHomePage loginNV(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        try {
            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.not(ExpectedConditions.urlContains("login")));
        } catch (Exception ignored) {}
        return new ChamCongPage_NVHomePage();
    }

    public void loginWithEnter(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        txtPassword.sendKeys(Keys.ENTER);
    }

    // ==========================================
    // CÁC HÀM GETTER VÀ VALIDATION
    // ==========================================

    public String getErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
            By errorLocator = By.xpath("//*[contains(text(),'không') or contains(text(),'sai') or contains(text(),'thất bại') or contains(text(),'tồn tại') or contains(text(),'khóa') or contains(text(),'vượt quá')] | //*[@class and contains(@class, 'red-500')] | //*[@class and contains(@class, 'destructive')]");

            wait.until(ExpectedConditions.presenceOfElementLocated(errorLocator));

            List<WebElement> elements = Constant.WEBDRIVER.findElements(errorLocator);
            for (WebElement el : elements) {
                if (el.isDisplayed() && !el.getText().trim().isEmpty()) {
                    return el.getText().trim();
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isLoginSuccessful() {
        try {
            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.not(ExpectedConditions.urlContains("login")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnLoginPage() {
        try {
            return Constant.WEBDRIVER.getCurrentUrl().contains("login");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordMasked() {
        return txtPassword.getAttribute("type").equals("password");
    }

    // HÀM TRẢ LẠI ĐỂ FILE KHACH HANG HẾT BÁO ĐỎ
    public void waitForLoginToastToDisappear() {
        try {
            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Đăng nhập thành công')]")));
        } catch (Exception ignored) {}
    }
}