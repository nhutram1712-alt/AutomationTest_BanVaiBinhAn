package pageobjects;
import Common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
// chứa các
public class GeneralPage {
    //Locators
    private final By tabHangHoa = By.xpath("//button[normalize-space()='Hàng hóa']");    protected WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
    private final By tabTaiKhoan = By.xpath("//button[contains(@class, 'transition') and .//*[contains(@class, 'lucide-user')]]");
    private final By tabDoiMatKhau2 = By.xpath("(//button[.//descendant::*[local-name()='svg' and contains(@class,'lucide-user')]])[2]");
    private final By tabDoiMatKhau3 = By.xpath("//button[text()='Đổi mật khẩu']");
    private final By tabKhachHang = By.xpath("(//button[text()='Khách hàng'])[1]");
    //Elenment

    protected WebDriverWait getWait() {
        return new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
    }
    public WebElement getTabTaiKhoan(){
        return Constant.WEBDRIVER.findElement(tabTaiKhoan);
    }
    public WebElement getTabDoiMatKhau2(){
        return Constant.WEBDRIVER.findElement(tabDoiMatKhau2);
    }
    public WebElement getTabDoiMatKhau3(){
        return Constant.WEBDRIVER.findElement(tabDoiMatKhau3);
    }
    public WebElement getTabKhachHang(){
        return Constant.WEBDRIVER.findElement(tabKhachHang);
    }



    protected WebElement getTabHangHoa(){
        return  getWait().until(ExpectedConditions.elementToBeClickable(tabHangHoa));
    }
    //Mothods
    public HangHoaPage gotoHangHoaPage(){
        this.getTabHangHoa().click();
        return new HangHoaPage();
    }
    //di den trang doi mat khau
    public DoiMatKhauPage goToDoiMatKhauPage(){
        // Bước 2: Click icon hình người góc phải
        wait.until(ExpectedConditions.elementToBeClickable(tabTaiKhoan)).click();

        // Bước 3: Đợi menu xuất hiện và Click vào tên người dùng (Vị trí 2)
        // Dùng JS Click để tránh lỗi "Element Intercepted" do menu dropdown gây ra
        WebElement profileItem = wait.until(ExpectedConditions.visibilityOfElementLocated(tabDoiMatKhau2));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", profileItem);

        // Bước 4: Chọn tab “Đổi mật khẩu”
        wait.until(ExpectedConditions.elementToBeClickable(tabDoiMatKhau3)).click();
        return new DoiMatKhauPage();
    }
    public KhachHangPage gotToKhachHangPage(){
        wait.until(ExpectedConditions.elementToBeClickable(tabKhachHang)).click();        return new KhachHangPage();
    }
    /// --------------------
    private final By inputUsername = By.cssSelector("form.space-y-5 .space-y-2:first-child input[type='text']");
    private final By inputPassword = By.cssSelector("form.space-y-5 input[type='password']");
    private final By btnLogin      = By.xpath("//button[normalize-space()='Đăng nhập']");

    protected WebElement getInputUsername() {
        return Constant.WEBDRIVER.findElement(inputUsername);
    }

    protected WebElement getInputPassword() {
        return Constant.WEBDRIVER.findElement(inputPassword);
    }

    protected WebElement getBtnLogin() {
        return Constant.WEBDRIVER.findElement(btnLogin);
    }

    public GeneralPage gotoLoginPage() {
        Constant.WEBDRIVER.get(Constant.LOGIN_URL);
        return new GeneralPage();
    }

    public GeneralPage login(String username, String password) {
        getInputUsername().clear();
        getInputUsername().sendKeys(username);
        getInputPassword().clear();
        getInputPassword().sendKeys(password);
        getBtnLogin().click();
        return new GeneralPage();
    }
    //---------------------------------------
    protected WebDriver driver;

    public GeneralPage() {
        this.driver = Constant.WEBDRIVER;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isUrlContaining(String text) {
        return driver.getCurrentUrl().contains(text);
    }
}

