package pageobjects;

import Common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DangXuat extends GeneralPage {

    private final By _btnUserAvatar = By.xpath(
            "//button[contains(@class,'rounded-full') or contains(@class,'avatar')]" +
                    " | //header//*[name()='svg' and (contains(@class,'user') or contains(@class,'person'))]/ancestor::button[1]" +
                    " | //button[contains(.,'manager') or @aria-label='user menu' or @aria-label='User menu']"
    );

    private final By _menuItemDangXuat = By.xpath(
            "//*[normalize-space()='Đăng xuất'] | //button[contains(.,'Đăng xuất')] | //a[contains(.,'Đăng xuất')]"
    );

    private final By _dialogConfirm = By.xpath(
            "//*[contains(text(),'Bạn có chắc chắn muốn đăng xuất')]" +
                    " | //*[contains(text(),'đăng xuất')]"
    );

    private final By _btnDongY = By.xpath(
            "//button[normalize-space()='Đồng ý']" +
                    " | //button[normalize-space()='Xác nhận']" +
                    " | //button[normalize-space()='OK']"
    );

    private final By _btnBoQua = By.xpath(
            "//button[normalize-space()='Bỏ qua']" +
                    " | //button[normalize-space()='Hủy']" +
                    " | //button[normalize-space()='Cancel']"
    );

    private final By _inputUsername = By.xpath(
            "//input[@type='text' or @placeholder='Tên đăng nhập' or @name='username' or @id='username']"
    );

    public WebElement getBtnUserAvatar() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(_btnUserAvatar));
    }

    public WebElement getMenuItemDangXuat() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_menuItemDangXuat));
    }

    public WebElement getDialogConfirm() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_dialogConfirm));
    }

    public WebElement getBtnDongY() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.elementToBeClickable(_btnDongY));
    }

    public WebElement getBtnBoQua() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.elementToBeClickable(_btnBoQua));
    }

    public DangXuat clickUserAvatar() {
        getBtnUserAvatar().click();
        Constant.pause(800);
        return this;
    }

    public DangXuat clickMenuDangXuat() {
        getMenuItemDangXuat().click();
        Constant.pause(800);
        return this;
    }

    public DangXuat clickDongY() {
        getBtnDongY().click();
        Constant.pause(1000);
        return this;
    }

    public DangXuat clickBoQua() {
        getBtnBoQua().click();
        Constant.pause(800);
        return this;
    }

    public boolean isConfirmDialogDisplayed() {
        try {
            return getDialogConfirm().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnLoginPage() {
        try {
            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.urlContains("/login"),
                            ExpectedConditions.visibilityOfElementLocated(_inputUsername)
                    ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isStillLoggedIn() {
        String currentUrl = Constant.WEBDRIVER.getCurrentUrl();
        return !currentUrl.contains("/login");
    }

    public DangXuat performLogout() {
        clickUserAvatar();
        clickMenuDangXuat();
        clickDongY();
        return this;
    }
}