package pageobjects;

import Common.Constant; // Đã giữ nguyên Common viết hoa theo file của bạn kia
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class KhachHangPage extends GeneralPage {

    // ========================================================================
    // PHẦN 1: LOCATORS CỦA BẠN KIA (DÙNG CHO THÊM MỚI, TRA CỨU, CHECK QUYỀN)
    // ========================================================================
    private final By btnThemKH = By.xpath("(//button[text()='Khách hàng'])[2]");
    private final By txtTimKiem = By.xpath("//input[@placeholder='Nhập tên, số điện thoại']");
    private final By tableDanhSach = By.xpath("//table");
    private final By txtTenKHDauTien = By.xpath("//tbody/tr[1]/td[2]");
    private final By btnChinhSua = By.xpath("//button[text()=' Chỉnh sửa']");
    private final By btnXoa = By.xpath("//button[contains(text(),'Xóa')]");
    private final By txtTenKHForm = By.xpath("//input[@placeholder='Nhập tên khách hàng']");
    private final By txtSDTKHForm = By.xpath("//input[@placeholder='0123456789']");
    private final By btnLuu = By.xpath("//button[text()='Lưu']");
    private final By txtEmailForm = By.xpath("//input[@type='email']");
    private final By msgSuccess = By.xpath("//div[text()='Thành công']/following-sibling::div");
    private final By msgError = By.xpath("//div[text()='Lỗi']/following-sibling::div");
    private final By btnBanHang = By.xpath("//button[text()='Bán hàng']");
    private final By btnThemKHForm = By.xpath("//button[contains(@class, 'bg-white') and contains(@class, 'border-gray-300')]");
    private final By txtSDTForm = By.xpath("//input[@placeholder='Số điện thoại']");
    private final By txtTenKHForm2 = By.xpath("//input[@placeholder='Tên khách hàng']");
    private final By txtEmailForm2 = By.xpath("//input[@placeholder='Email']");
    private final By btnLuu2 = By.xpath("//button[text()='Lưu']");
    private final By btnBoQua = By.xpath("//button[contains(text(),'Bỏ qua')]");

    // ========================================================================
    // PHẦN 2: LOCATORS CỦA BẠN (DÙNG CHO SỬA, XÓA - ĐÃ FIX LỖI TC14 & TC19)
    // ========================================================================
    private final By menuCustomer     = By.xpath("//button[normalize-space()='Khách hàng']");
    private final By txtSearch        = By.xpath("//input[contains(@placeholder,'Nhập tên, số điện thoại')]");
    private final By customerRows     = By.xpath("//table//tbody//tr");

    // ĐÃ FIX: Đường dẫn Tên chuẩn xác để qua TC14
    private final By txtName          = By.xpath("//input[@placeholder='Nhập tên khách hàng'] | //label[contains(text(), 'Tên khách hàng')]/following-sibling::input");

    private final By txtPhone         = By.cssSelector("input[placeholder='0123456789']");
    private final By txtEmail         = By.cssSelector("input[type='email']");
    private final By btnEdit          = By.xpath("//button[contains(.,'Chỉnh sửa')]");
    private final By btnSave          = By.xpath("//button[normalize-space()='Lưu']");
    private final By btnCancel        = By.xpath("//button[normalize-space()='Bỏ qua']");
    private final By btnDelete        = By.xpath("//button[contains(.,'Xóa') and not(ancestor::div[@role='dialog'])]");
    private final By btnConfirmDelete = By.xpath("//div[@role='dialog']//button[contains(.,'Đồng ý')]");

    // ĐÃ FIX: Bổ sung bắt chữ "Hủy", "Cancel" để qua TC19
    private final By btnCancelDelete  = By.xpath("//div[@role='dialog' or @role='alertdialog']//button[contains(.,'Bỏ qua') or contains(.,'Hủy') or contains(.,'Cancel')]");

    private final By lblToast         = By.cssSelector("li[data-state='open']");

    // ========== JAVASCRIPT ĐẶC TRỊ REACTJS (DÀNH CHO CHỈNH SỬA) ==========
    private static final String REACT_CLEAR_JS =
            "var el = arguments[0];" +
                    "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype,'value').set;" +
                    "setter.call(el,'');" +
                    "el.dispatchEvent(new Event('input',{bubbles:true}));" +
                    "el.dispatchEvent(new Event('change',{bubbles:true}));" +
                    "el.dispatchEvent(new Event('blur',{bubbles:true}));";

    private static final String REACT_SET_JS =
            "var el=arguments[0]; var val=arguments[1];" +
                    "var setter=Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype,'value').set;" +
                    "setter.call(el,val);" +
                    "el.dispatchEvent(new Event('input',{bubbles:true}));" +
                    "el.dispatchEvent(new Event('change',{bubbles:true}));";

    // ========================================================================
    // PHẦN 3: HÀM TIỆN ÍCH DÙNG CHUNG
    // ========================================================================
//    private WebDriverWait getWait() {
//        return new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
//    }

    private void sleep(int ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }

    private void jsClick(WebElement el) {
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", el);
    }

    // ========================================================================
    // PHẦN 4: CÁC HÀM GET ELEMENT (CỦA BẠN KIA GIỮ NGUYÊN)
    // ========================================================================
    public WebElement getTenKHDauTien(){
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(txtTenKHDauTien));
    }
    public WebElement gettxtTenKHForm(){
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(txtTenKHForm));
    }
    public WebElement gettxtSDTKHForm(){
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(txtSDTKHForm));
    }
    public WebElement getBtnLuu(){
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(btnLuu));
    }
    public WebElement gettxtEmailKHForm(){
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(txtEmailForm));
    }
    public WebElement getBtnThemKHForm(){
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(btnThemKHForm));
    }
    public WebElement getBtnBanHang(){
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(btnBanHang));
    }
    public WebElement getMsgSucces(){
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(msgSuccess));
    }
    public WebElement getMsgError(){
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(msgError));
    }
    public WebElement getTxtTimKiem(){
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(txtTimKiem));
    }

    // ========================================================================
    // PHẦN 5: CÁC HÀM XỬ LÝ (BUSINESS METHODS)
    // ========================================================================

    // ----- A. CÁC HÀM CỦA BẠN KIA (THÊM MỚI, PHÂN QUYỀN) -----
    public void clickFirstCustomer() {
        getWait().until(ExpectedConditions.elementToBeClickable(txtTenKHDauTien)).click();
    }

    public boolean isKhachHangPageDisplayed() {
        try {
            return Constant.WEBDRIVER.findElement(btnThemKH).isDisplayed() &&
                    Constant.WEBDRIVER.findElement(txtTimKiem).isDisplayed() &&
                    Constant.WEBDRIVER.findElement(tableDanhSach).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areActionButtonsDisplayed() {
        try {
            boolean isEditVisible = Constant.WEBDRIVER.findElement(btnChinhSua).isDisplayed();
            boolean isDeleteVisible = Constant.WEBDRIVER.findElement(btnXoa).isDisplayed();
            return isEditVisible && isDeleteVisible;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDeleteButtonDisplayed() {
        try {
            return Constant.WEBDRIVER.findElement(btnXoa).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddButtonDisplayed() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(btnThemKH)).isDisplayed();
    }

    public boolean isEditButtonDisplayed() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(btnChinhSua)).isDisplayed();
    }

    public void addNewCustomer(String name, String phone, String email) {
        getWait().until(ExpectedConditions.elementToBeClickable(btnThemKH)).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(txtTenKHForm)).sendKeys(name);
        getWait().until(ExpectedConditions.visibilityOfElementLocated(txtEmailForm)).sendKeys(email);
        Constant.WEBDRIVER.findElement(txtSDTKHForm).sendKeys(phone);
        Constant.WEBDRIVER.findElement(btnLuu).click();
    }

    public void addNewCustomer_TC4(String name, String phone, String email) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(txtTenKHForm2)).sendKeys(name);
        getWait().until(ExpectedConditions.visibilityOfElementLocated(txtEmailForm2)).sendKeys(email);
        Constant.WEBDRIVER.findElement(txtSDTForm).sendKeys(phone);
        Constant.WEBDRIVER.findElement(btnLuu2).click();
    }

    public String getAddSuccessMsg() {
        By specificMsg = By.xpath("//div[contains(text(),'Đã thêm khách hàng mới')]");
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(specificMsg)).getText();
    }

    public void cancelAddCustomer(String ten, String sdt, String email) {
        getWait().until(ExpectedConditions.elementToBeClickable(btnThemKH)).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(txtTenKHForm)).sendKeys(ten);
        Constant.WEBDRIVER.findElement(txtSDTKHForm).sendKeys(sdt);
        Constant.WEBDRIVER.findElement(txtEmailForm).sendKeys(email);
        Constant.WEBDRIVER.findElement(btnBoQua).click();
    }

    public boolean isAddFormClosed() {
        try {
            return getWait().until(ExpectedConditions.invisibilityOfElementLocated(txtTenKHForm));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean  searchCustomer(String phone) {
//        WebElement input = getWait().until(ExpectedConditions.elementToBeClickable(txtTimKiem));
//        input.click();
//        input.clear();
//        for (char c : phone.toCharArray()) {
//            input.sendKeys(String.valueOf(c));
//        }
//        input.sendKeys(org.openqa.selenium.Keys.ENTER);
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

        while (true) {

            // đợi bảng load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table//tr")));

            List<WebElement> rows = Constant.WEBDRIVER.findElements(By.xpath("//table//tr"));

            // duyệt từng dòng
            for (WebElement row : rows) {
                if (row.getText().contains(phone)) {
                    return true; // tìm thấy
                }
            }

            // tìm nút next
            List<WebElement> nextBtn = Constant.WEBDRIVER.findElements(
                    By.xpath("//button[contains(@class,'next') or contains(text(),'Next')]")
            );

            // nếu không có hoặc bị disable => hết trang
            if (nextBtn.isEmpty() || !nextBtn.get(0).isEnabled()) {
                break;
            }

            WebElement oldFirstRow = rows.get(0);

            nextBtn.get(0).click();

            // chờ trang đổi (tránh stale + reload)
            wait.until(ExpectedConditions.stalenessOf(oldFirstRow));
        }

        return false;
    }

    // ----- B. CÁC HÀM CỦA BẠN (SỬA, XÓA) -----

    public void goToCustomerPage() {
        sleep(2000);
        try {
            jsClick(Constant.WEBDRIVER.findElement(menuCustomer));
        } catch (Exception e) {
            Constant.WEBDRIVER.get("http://localhost:3000/customers");
        }
        sleep(2000);
    }

    public void search(String keyword) {
        try {
            WebElement input = getWait().until(ExpectedConditions.elementToBeClickable(txtSearch));
            input.click();
            input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
            sleep(300);
            input.sendKeys(keyword);
            sleep(300);
            input.sendKeys(Keys.ENTER);
            sleep(2000);
        } catch (Exception e) {
            System.out.println("DEBUG search: " + e.getMessage());
        }
    }

    public void clickFirstRow() {
        try {
            WebElement row = getWait().until(ExpectedConditions.elementToBeClickable(customerRows));
            jsClick(row);
            sleep(1000);
        } catch (Exception e) {
            System.out.println("DEBUG clickFirstRow: " + e.getMessage());
        }
    }

    public int getResultCount() {
        try {
            List<WebElement> rows = Constant.WEBDRIVER.findElements(customerRows);
            if (rows.isEmpty()) return 0;
            if (rows.size() == 1) {
                String text = rows.get(0).getText().toLowerCase();
                if (text.contains("không tìm thấy") || text.contains("trống") || text.contains("no data") || text.isEmpty()) {
                    return 0;
                }
            }
            return rows.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickEdit() {
        try {
            WebElement btn = getWait().until(ExpectedConditions.elementToBeClickable(btnEdit));
            jsClick(btn);
            sleep(1000);
        } catch (Exception e) {
            System.out.println("DEBUG clickEdit: " + e.getMessage());
        }
    }

    private void setReactField(By locator, String value) {
        try {
            WebElement el = getWait().until(ExpectedConditions.elementToBeClickable(locator));
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript(REACT_CLEAR_JS, el);
            sleep(300);
            if (value != null && !value.isEmpty()) {
                ((JavascriptExecutor) Constant.WEBDRIVER).executeScript(REACT_SET_JS, el, value);
                el.sendKeys(Keys.END);
                sleep(100);
            }
            sleep(200);
        } catch (Exception e) {
            System.out.println("DEBUG setReactField [" + locator + "]: " + e.getMessage());
        }
    }

    public void updateCustomer(String name, String email, String phone) {
        try {
            getWait().until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(.,'Chỉnh sửa thông tin khách hàng')]")));
            sleep(500);
        } catch (Exception e) {}

        if (name  != null) setReactField(txtName,  name);
        if (phone != null) setReactField(txtPhone, phone);
        if (email != null) setReactField(txtEmail, email);
    }

    public void clickSave() {
        try {
            WebElement btn = getWait().until(ExpectedConditions.elementToBeClickable(btnSave));
            jsClick(btn);
            sleep(500);
        } catch (Exception e) {
            System.out.println("DEBUG clickSave: " + e.getMessage());
        }
    }

    public void clickCancel() {
        try {
            jsClick(Constant.WEBDRIVER.findElement(btnCancel));
        } catch (Exception e) {}
    }

    public void clickDelete() {
        try {
            jsClick(Constant.WEBDRIVER.findElement(btnDelete));
            sleep(500);
        } catch (Exception e) {}
    }

    public void confirmDelete() {
        try {
            WebElement btn = getWait().until(ExpectedConditions.elementToBeClickable(btnConfirmDelete));
            jsClick(btn);
            sleep(1000);
        } catch (Exception e) {}
    }

    public void cancelDelete() {
        try {
            WebElement btn = getWait().until(ExpectedConditions.elementToBeClickable(btnCancelDelete));
            jsClick(btn);
            sleep(500);
        } catch (Exception e) {}
    }

    public String getMessage() {
        try {
            WebElement toast = getWait().until(ExpectedConditions.visibilityOfElementLocated(lblToast));
            return toast.getText().trim();
        } catch (Exception e) {
            return Constant.WEBDRIVER.findElement(By.tagName("body")).getText();
        }
    }
}