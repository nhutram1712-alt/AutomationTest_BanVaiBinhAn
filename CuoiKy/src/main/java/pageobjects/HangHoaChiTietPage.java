package pageobjects;

import Common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class HangHoaChiTietPage extends HangHoaPage{
    //locator
    private final By lblProductNameDetail = By.xpath("//h2[contains(@class, 'text-2xl')]");
    private final By btnChinhSua = By.xpath("//button[text()='Chỉnh sửa']");
    private final By toastSuccessUpdate = By.xpath("//div[text()='Thành công']/following-sibling::div");
    private WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
    private final By msgError = By.xpath("//div[contains(text(),'Tên sản phẩm đã tồn tại')]");
    private final By msgPriceError = By.xpath("//div[contains(text(),'price must not be less than 0')]");
    private final By btnXoa = By.xpath("//button[text()='Xóa']");
    private final By btnXacNhanXoa = By.xpath("(//button[text()=\"Xóa\"])[2]");
    private final By msgXoaComplete = By.xpath("//div[contains(text(),'Đã xóa hàng hóa')]");
    private final By msgLoiTonKho = By.xpath("//p[contains(., 'Vui lòng thử lại sau khi tồn kho bằng 0!')]");
    private final By btnHuy = By.xpath("//button[text()='Hủy']");

    //method
    public String getProductNameDetailText() {
        return Constant.WEBDRIVER.findElement(lblProductNameDetail).getText();
    }

    public boolean isDetailPageDisplayed() {
        return !Constant.WEBDRIVER.findElements(lblProductNameDetail).isEmpty();
    }
    public WebElement getBtnXoa() {
        return Constant.WEBDRIVER.findElement(btnXoa);
    }
    public WebElement getBtnHuy(){
        return Constant.WEBDRIVER.findElement(btnHuy);
    }
    public WebElement getBtnXacNhanXoa() {
        return Constant.WEBDRIVER.findElement(btnXoa);
    }



    public HangHoaUpdateForm gotoHHUpdateForm() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

        // 1. Đợi toast (Radix UI) biến mất
        wait.until(driver ->
                driver.findElements(By.xpath("//li[@data-state='open']")).isEmpty()
        );

        // 2. Đợi nút "Chỉnh sửa" clickable
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(btnChinhSua)
        );

        // 3. Click (ưu tiên click thường, fallback JS nếu fail)
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Constant.WEBDRIVER)
                    .executeScript("arguments[0].click();", element);
        }

        return new HangHoaUpdateForm();
    }

    public String getToastSuccessText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(toastSuccessUpdate)).getText();
    }

    public String getNameMsgError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(msgError)).getText();
    }
    public String getPriceMsgError(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(msgPriceError)).getText();
    }
    public HangHoaPage deleteProduct() {
        // 1. Đợi và click nút Xóa chính
        WebElement xoaBtn = wait.until(ExpectedConditions.elementToBeClickable(btnXoa));
        xoaBtn.click();

        // 2. Đợi Dialog xác nhận hiện lên và click nút Xóa trên Dialog
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(btnXacNhanXoa));
        confirmBtn.click();

        // Trả về trang danh sách hàng hóa
        return new HangHoaPage();
    }

    // Hàm lấy text thông báo xóa thành công để Assert
    public String getDeleteCompleteText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(msgXoaComplete)).getText();
    }
    // Hàm kiểm tra text lỗi tồn kho
    public String getErrorTonKhoText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(msgLoiTonKho)).getText();
    }
    public boolean isDialogClosed() {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(btnHuy));

    }
    public boolean isProductStillExists(String tenSP) {
        // Tìm tất cả các phần tử có chứa text là tên sản phẩm
        // Nếu danh sách trả về không rỗng (isEmpty == false) nghĩa là sản phẩm vẫn còn
        return !Constant.WEBDRIVER.findElements(By.xpath("//*[contains(text(),'" + tenSP + "')]")).isEmpty();
    }
    public void clickElement(By locator) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

        WebElement el = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].scrollIntoView(true);", el);

        el.click();
    }

}
