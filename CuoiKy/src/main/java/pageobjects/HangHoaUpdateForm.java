package pageobjects;

import Common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
public class HangHoaUpdateForm {

    private final By txtTenHH = By.xpath("//input[@placeholder='Bắt buộc']");
    // Input "Đơn vị tính"
    private By txtDonVi = By.xpath("//label[contains(text(),'Đơn vị tính')]/following::input[1]");
    // Input "Giá bán"
    private By txtGiaBan = By.xpath("//label[contains(.,'Giá bán')]/following::input[@type='number'][1]");
    // Input "Tồn kho"
    private By txtTonKho = By.xpath("(//label[contains(text(),'Tồn kho')]/following::input[1])[2]");
    // Textarea "Ghi chú"
    private By txtGhiChu = By.xpath("//label[contains(text(),'Ghi chú')]/following::textarea[1]");
    // Button "Lưu"
    private By btnLuu = By.xpath("//button[.//text()='Lưu']");
    // Button "Bỏ qua"
    private By btnBoQua = By.xpath("//button[text()='Bỏ qua']");
    private WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
    private final By msgError = By.xpath("//div[contains(text(),'Tên sản phẩm đã tồn tại')]");
    private final By msgPriceError = By.xpath("//div[contains(text(),'price must not be less than 0')]");

    //Elements
    public WebElement getTxtTenHH() {
        return Constant.WEBDRIVER.findElement(txtTenHH);
    }

    public WebElement getTxtGiaBan() {
        return Constant.WEBDRIVER.findElement(txtGiaBan);
    }

    public WebElement getTxtTonKho() {
        return Constant.WEBDRIVER.findElement(txtTonKho);
    }

    protected WebElement getBtnLuu() {
        return Constant.WEBDRIVER.findElement(btnLuu);
    }

    public WebElement getBtnBoQua() {
        return Constant.WEBDRIVER.findElement(btnBoQua);
    }


    //methods
    public HangHoaPage UpdateHH(String tenHH, String giaBan) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtTenHH));
        this.getTxtTenHH().sendKeys(tenHH);
        this.getTxtGiaBan().sendKeys(giaBan);
        this.getBtnLuu().click();
        return new HangHoaPage();
    }

    public HangHoaPage UpdateTenHH(String tenHH) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtTenHH));
        this.getTxtTenHH().clear();
        this.getTxtTenHH().sendKeys(tenHH);
        this.getBtnLuu().click();
        return new HangHoaPage();
    }

    public HangHoaPage UpdateGiaHH(String giaBan) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtGiaBan));
        this.getTxtGiaBan().clear();
        this.getTxtGiaBan().sendKeys(giaBan);
        this.getBtnLuu().click();
        return new HangHoaPage();
    }

    //hàm kiểm tra form update close
    public boolean isUpdateFormClosed() {
        return Constant.WEBDRIVER.findElements(btnBoQua).isEmpty();
    }

    //hàm cập nhật số lượng hàng hóa về 0
    public boolean isAddFormClosed() {
        return Constant.WEBDRIVER.findElements(btnBoQua).isEmpty();
    }
    public HangHoaChiTietPage UpdateTonKho(String soLuong) {
        // 1. Đợi ô input và xử lý nếu nó bị disabled bằng JS
        WebElement inputTonKho = wait.until(ExpectedConditions.presenceOfElementLocated(txtTonKho));

        // Scroll và Click bằng JS để tránh bị che bởi header/footer
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", inputTonKho);
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", inputTonKho);

        inputTonKho.clear();
        inputTonKho.sendKeys(soLuong);

        // 2. Click Lưu
        WebElement nutLuu = wait.until(ExpectedConditions.elementToBeClickable(btnLuu));
        nutLuu.click();

        // 3. Đợi cho Form đóng hẳn (nút Bỏ qua biến mất)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(btnBoQua));

        // 4. Đợi Backdrop (lớp phủ đen) biến mất hoàn toàn
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'bg-black')]")));

        // 5. Quan trọng: Đợi Toast thông báo biến mất để không che nút Xóa ở bước sau
        // Thường Radix UI dùng li hoặc div có role='status'
        By toastLocator = By.xpath("//li[@data-state='open']");
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(toastLocator));
        } catch (Exception e) {
            // Nếu không có toast thì bỏ qua
        }

        return new HangHoaChiTietPage();
    }





}
