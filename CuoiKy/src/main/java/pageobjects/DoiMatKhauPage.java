package pageobjects;

import Common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class DoiMatKhauPage {
    //locators
    private final By txtMatKhauCu = By.xpath("(//input[@type='password'])[1]");
    private final By txtMatKhauMoi = By.xpath("(//input[@type='password'])[2]");
    private final By txtXacNhanMKMoi = By.xpath("(//input[@type='password'])[3]");
    private final By btnDoiMatKhau = By.xpath("(//button[text()=\"Đổi mật khẩu\"])[2]");
    private final By lblSuccessMsg = By.xpath("//div[text()='Mật khẩu của bạn đã được cập nhật']");
    private final By lblErorMsg = By.xpath("//div[text()='Mật khẩu mới và xác nhận mật khẩu không khớp']");
    private final By lblInvalidErrorMsg = By.xpath("//div[contains(text(), 'Mật khẩu mới phải có ít nhất 8 ký tự')]");
    private WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

    //elements
    public WebElement getTxtMatKhauCu(){
        return Constant.WEBDRIVER.findElement(txtMatKhauCu);
    }
    public WebElement getTxtMatKhauMoi(){
        return Constant.WEBDRIVER.findElement(txtMatKhauMoi);
    }
    public WebElement getTxtXacNhanMK(){
        return Constant.WEBDRIVER.findElement(txtXacNhanMKMoi);
    }
    public WebElement getBtnDoiMatKhau(){
        return Constant.WEBDRIVER.findElement(btnDoiMatKhau);
    }
    public String getLblSuccessMsg(){
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        return Constant.WEBDRIVER.findElement(lblSuccessMsg).getText();
    }
    public String getLblErrorMsg(){
        return Constant.WEBDRIVER.findElement(lblErorMsg).getText();
    }
    public String getLblInvalidErrorMsg(){
        return Constant.WEBDRIVER.findElement(lblInvalidErrorMsg).getText();
    }

    //methods
    //hàm điền form đổi mâtj khẩu
    public void fillChangePasswordForm(String oldPass, String newPass, String confirmNewPass) {
        // Đợi ô input hiển thị rồi nhập liệu
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtMatKhauCu)).sendKeys(oldPass);
        Constant.WEBDRIVER.findElement(txtMatKhauMoi).sendKeys(newPass);
        Constant.WEBDRIVER.findElement(txtXacNhanMKMoi).sendKeys(confirmNewPass);
        Constant.WEBDRIVER.findElement(btnDoiMatKhau).click();
    }




}
