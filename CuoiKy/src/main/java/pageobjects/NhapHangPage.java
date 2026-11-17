package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class NhapHangPage extends GeneralPage {

    private final By inputFabricSearch = By.cssSelector("input[placeholder='Nhập tên vải']");
    private final By lblFabricErrorBy  = By.xpath("//p[text()='Không tìm thấy tên sản phẩm']");
    private final By inputQuantity     = By.xpath("//tbody//input[@type='number']");
    private final By ddlSupplierBy     = By.cssSelector("select.w-full");

    // Tuyệt chiêu ép React nhận diện dữ liệu nhập vào (Xóa sạch mọi lỗi gõ phím xịt)
    private static final String REACT_SET_JS =
            "var el = arguments[0]; var val = arguments[1];" +
                    "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype,'value').set;" +
                    "setter.call(el, val);" +
                    "el.dispatchEvent(new Event('input', {bubbles: true}));" +
                    "el.dispatchEvent(new Event('change', {bubbles: true}));";

    public NhapHangPage() {
        super();
    }

    public void clickGoToImportForm() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.tracking-tight")));
        try { Thread.sleep(500); } catch (Exception ignored) {}
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(.,'Nhập hàng')])[last()]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputFabricSearch));
    }

    public void searchAndSelectFabric(String fabricName) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputFabricSearch));

        // BƯỚC 1: Xóa trắng ô nhập liệu bằng React Setter
        ((JavascriptExecutor) driver).executeScript(REACT_SET_JS, input, "");
        try { Thread.sleep(300); } catch (Exception ignored) {}

        // BƯỚC 2: Điền tên vải bằng React Setter
        ((JavascriptExecutor) driver).executeScript(REACT_SET_JS, input, fabricName);

        // BƯỚC 3: Gõ thêm 1 phím Space rồi xóa đi để "đánh thức" dropdown của React
        input.sendKeys(" ");
        input.sendKeys(Keys.BACK_SPACE);

        try { Thread.sleep(2000); } catch (Exception ignored) {}

        // Tìm dropdown và click
        try {
            By optionLocator = By.xpath("//*[contains(@class, 'absolute')]//*[contains(text(), '" + fabricName + "')]");
            WebElement option = new WebDriverWait(driver, Duration.ofSeconds(4))
                    .until(ExpectedConditions.elementToBeClickable(optionLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
        } catch (Exception e) {
            return; // Trả về im lặng để TC03 bắt lỗi
        }

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(inputQuantity));
        } catch (Exception ignored) {}
    }

    public void enterQuantity(int quantity) {
        WebElement qtyInput = wait.until(ExpectedConditions.elementToBeClickable(inputQuantity));

        // Dùng React Setter để gõ số lượng cực chuẩn
        ((JavascriptExecutor) driver).executeScript(REACT_SET_JS, qtyInput, String.valueOf(quantity));
        qtyInput.sendKeys(Keys.TAB);
    }

    public String getQuantityValue() {
        try {
            WebElement qtyInput = wait.until(ExpectedConditions.presenceOfElementLocated(inputQuantity));
            return qtyInput.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    public void selectSupplierFromDropdown(String supplierName) {
        WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(ddlSupplierBy));

        // Logic cải tiến: Quét tất cả option, nếu thấy chữ "Thêm" hoặc "Mới" thì cũng chọn luôn (Bắt bao chuẩn TC02)
        String jsCode =
                "var s = arguments[0]; var name = arguments[1].toLowerCase();" +
                        "for(var i = 0; i < s.options.length; i++) {" +
                        "  var optText = s.options[i].text.toLowerCase();" +
                        "  if(optText.includes(name) || (name.includes('thêm') && optText.includes('thêm')) ) {" +
                        "    s.selectedIndex = i;" +
                        "    s.dispatchEvent(new Event('change', {bubbles: true}));" +
                        "    return;" +
                        "  }" +
                        "}";
        ((JavascriptExecutor) driver).executeScript(jsCode, selectElement, supplierName);

        try { Thread.sleep(500); } catch (Exception ignored) {}
    }

    public void enterSupplierName(String name) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Nhập tên nhà cung cấp']")));
        ((JavascriptExecutor) driver).executeScript(REACT_SET_JS, input, name);
    }

    public void enterSupplierPhone(String phone) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Nhập số điện thoại']")));
        ((JavascriptExecutor) driver).executeScript(REACT_SET_JS, input, phone);
    }

    public void clickSave() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'LƯU HÓA ĐƠN') or contains(.,'Lưu hóa đơn')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        try { Thread.sleep(1500); } catch (Exception ignored) {}
    }

    public void clickCancel() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'BỎ QUA') or contains(.,'Bỏ qua') or contains(.,'HỦY') or contains(.,'Hủy')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        try { Thread.sleep(1000); } catch (Exception ignored) {}
    }

    public String getToastMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[data-state='open']")));
            return driver.findElement(By.cssSelector("li[data-state='open']")).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getFabricErrorMessage() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(lblFabricErrorBy));
            return driver.findElement(lblFabricErrorBy).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isOnInventoryListPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.tracking-tight")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUrlContaining(String path) {
        return driver.getCurrentUrl().contains(path);
    }
}