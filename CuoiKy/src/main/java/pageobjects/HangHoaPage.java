package pageobjects;

import Common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class HangHoaPage extends GeneralPage {
    //locators
    private final By btnTaoMoi = By.xpath("//button[text()='Tạo mới']");
    private final By tabHangHoa = By.xpath("//button[text()='Hàng hóa']");
    private WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
    private final By toastMessage = By.xpath("//div[contains(text(), 'Đã thêm hàng hóa mới')]");
    private final By _toastTitle = By.xpath("//div[text()='Thành công']");
    private final By toastErrorMessage = By.xpath("//div[div[text()='Lỗi']]/div[contains(@class, 'opacity-90')]");
    private final By txtTimKiemVai = By.xpath("//input[@placeholder='Nhập tên vải']");
    private final By txtTimKiemUnvalidVai = By.xpath("//td[contains(text(), 'Không tìm thấy hàng hóa')]");

    // Locator trỏ vào cell chứa tên của dòng đầu tiên trong bảng
    private final By firstProductLink = By.xpath("//table/tbody/tr[1]/td[2]");


    //Elements
    protected WebElement getBtnTaoMoi(){
//        return Constant.WEBDRIVER.findElement(btnTaoMoi);
        return  getWait().until(ExpectedConditions.elementToBeClickable(btnTaoMoi));
       }
    public WebElement getTXTTimKiemVai(){
        return Constant.WEBDRIVER.findElement(txtTimKiemVai);
    }
    public WebElement gettxtTimKiemUnvalidVai(){
        return Constant.WEBDRIVER.findElement(txtTimKiemUnvalidVai);
    }

    // lấy dòng dữ liệu đầu tiên của bảng
    public WebElement getFirstRowName() {
        return Constant.WEBDRIVER.findElement(firstProductLink);
    }



    //Methods
    public HangHoaCreateForm goToHangHoaForm(){
        this.getBtnTaoMoi().click();
        return new HangHoaCreateForm();
    }
    public String getToastContent() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        // Đợi cho đến khi message xuất hiện trên màn hình
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage));
        return messageElement.getText();
    }
    public String getToastErrorContent() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        // Đợi thông báo hiện ra
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(toastErrorMessage));
        return messageElement.getText();
    }

    //Hàm kiểm tra dòng dữ liệu trên cùng của bảng
    public boolean isProductNotDisplayed(String productName) {
        // Tìm tất cả các dòng trong bảng có chứa tên sản phẩm
        By productRow = By.xpath("//table//td[contains(text(), '" + productName + "')]");

        // Trả về true nếu danh sách tìm thấy bằng 1 (nghĩa là không có)
        return Constant.WEBDRIVER.findElements(productRow).size() == 1;
    }
    public String getFirstRowNameText() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(firstProductLink)).getText();
    }

   //
    public HangHoaChiTietPage goToChiTietPage(String tenHangHoa){
        By locatorTenHang = By.xpath("//table//td[contains(text(), '" + tenHangHoa + "')]");
        Constant.WEBDRIVER.findElement(locatorTenHang).click();
        return new HangHoaChiTietPage();
    }
    public HangHoaChiTietPage goToFirstChiTietHH(){
        getWait().until(ExpectedConditions.elementToBeClickable(firstProductLink)).click();
        return new HangHoaChiTietPage();
    }
    public boolean isProductExistInAllPages(String tenVai) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(15));

        // 🔥 CHỜ TABLE LOAD TRƯỚC
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table")));

        while (true) {
            // 🔥 CHỜ ROW XUẤT HIỆN
            List<WebElement> rows = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table//tbody/tr"))
            );

            for (WebElement row : rows) {
                String text = row.getText().trim();

                System.out.println("Row: " + text); // debug

                if (text.contains(tenVai.trim())) {
                    return true;
                }
            }

            // Next button
            List<WebElement> nextBtn = Constant.WEBDRIVER.findElements(
                    By.xpath("//button[contains(@class,'next')]")
            );

            if (nextBtn.isEmpty() || !nextBtn.get(0).isEnabled()) {
                break;
            }

            WebElement firstRow = rows.get(0);
            nextBtn.get(0).click();

            // 🔥 CHỜ PAGE LOAD LẠI
            wait.until(ExpectedConditions.stalenessOf(firstRow));
        }

        return false;
    }
}
