package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ChamCongPage_NVHomePage extends GeneralPage{
    //locators
    private final By tabKhachhang = By.xpath("//button[text()='Khách hàng']");

    //elements
    protected WebElement getTabKhachhang(){
//        return Constant.WEBDRIVER.findElement(btnTaoMoi);
        return  getWait().until(ExpectedConditions.elementToBeClickable(tabKhachhang));
    }

    public KhachHangPage goToKhachHangPage(){
        wait.until(ExpectedConditions.elementToBeClickable(tabKhachhang)).click();
        return new KhachHangPage();
    }
}
