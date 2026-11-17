package pageobjects;

import Common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Thư viện để thiết lập thời gian chờ (ví dụ: chờ 5 giây, 10 giây)
import java.time.Duration;
public class HangHoaCreateForm {
    // locators
    private final By txtTenHH = By.xpath("//input[@placeholder='Bắt buộc']");
//    private final By txtGiaBan = By.xpath("(//input[@placeholder='0'])[1]");
//    private final By txtTonKho = By.xpath("(//input[@placeholder='0')[2]");
//    private final By btnLuu = By.xpath("(//button[text()='Lưu'])");
//    private WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
    // Input "Tên hàng"
//    private By txtTenHH = By.xpath("//label[contains(text(),'Tên hàng')]/following::input[1]");
    private WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

    // Input "Đơn vị tính"
    private By txtDonVi = By.xpath("//label[contains(text(),'Đơn vị tính')]/following::input[1]");

    // Input "Giá bán"
    private By txtGiaBan = By.xpath("//label[contains(text(),'Giá bán')]/following::input[1]");

    // Input "Tồn kho"
    private By txtTonKho = By.xpath("//label[contains(text(),'Tồn kho')]/following::input[1]");

    // Textarea "Ghi chú"
    private By txtGhiChu = By.xpath("//label[contains(text(),'Ghi chú')]/following::textarea[1]");

    // Button "Lưu"
    private By btnLuu = By.xpath("//button[.//text()='Lưu']");

    // Button "Bỏ qua"
    private By btnBoQua = By.xpath("//button[text()='Bỏ qua']");
    //Elements
    public WebElement getTxtTenHH(){
        return Constant.WEBDRIVER.findElement(txtTenHH);
    }

    public WebElement getTxtGiaBan(){
        return Constant.WEBDRIVER.findElement(txtGiaBan);
    }

    public WebElement getTxtTonKho(){
        return Constant.WEBDRIVER.findElement(txtTonKho);
    }
    protected WebElement getBtnLuu(){
        return Constant.WEBDRIVER.findElement(btnLuu);
    }
    protected WebElement getBtnBoQua(){
        return Constant.WEBDRIVER.findElement(btnBoQua);
    }



    //Methods


    public HangHoaPage ThemHH (String tenHH, String giaBan, String tonKho){
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtTenHH));
        this.getTxtTenHH().sendKeys(tenHH);
        this.getTxtGiaBan().sendKeys(giaBan);
        this.getTxtTonKho().sendKeys(tonKho);
        this.getBtnLuu().click();
        return new HangHoaPage();
    }
    public HangHoaPage clickBoQua() {
        this.getBtnBoQua().click();
        return new HangHoaPage();
    }

    //hàm kiểm tra đã đóng form chưa
    public boolean isAddFormClosed(){
        return Constant.WEBDRIVER.findElements(btnBoQua).isEmpty();
    }

    //hàm kiểm tra hàng hóa mới có được thêm vào hệ thống chưa, (có hiện lên gridview chưa)


}


