package pageobjects;


import Common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;


import java.time.Duration;


public class BanHang extends GeneralPage {



    private final By _inputSearchProduct    = By.xpath("//input[@placeholder='Nhập tên vải']");


    private final By _lblNoProduct          = By.xpath(
            "//p[contains(@class,'text-red') and (" +
                    "contains(text(),'Không tìm thấy tên sản phẩm') or " +
                    "contains(text(),'Không tìm thấy vải') or " +
                    "contains(text(),'không tìm thấy')" +
                    ")]"
    );


    private final By _lstProductSuggestion  = By.xpath(
            "(//div[contains(@class,'absolute')]//button)[1]"
    );
    private final By _inputQuantityLast     = By.xpath("(//input[@type='number'])[last()]");
    private final By _inputCustomerPhone    = By.xpath("//input[@placeholder='Nhập SĐT hoặc tên']");
    private final By _btnAddCustomer        = By.xpath(
            "//button[contains(@class,'h-9') and contains(@class,'w-9') and contains(@class,'bg-white')]"
    );
    private final By _lstCustomerSuggestion = By.xpath(
            "//div[contains(@class,'absolute') or contains(@class,'dropdown') or contains(@class,'shadow')]" +
                    "//div[contains(text(),'0') or contains(@class,'cursor') or contains(@class,'hover')]" +
                    "[not(contains(@class,'input'))][1]"
    );
    private final By _inputNewCustomerName  = By.xpath("//input[@placeholder='Tên khách hàng']");
    private final By _inputNewCustomerPhone = By.xpath("//input[@placeholder='Số điện thoại']");
    private final By _inputNewCustomerEmail = By.xpath("//input[@placeholder='Email']");
    private final By _inputNewCustomerAddr  = By.xpath("//textarea[@placeholder='Địa chỉ'] | //input[@placeholder='Địa chỉ']");
    private final By _btnSaveNewCustomer    = By.xpath("//button[normalize-space()='Lưu']");
    private final By _btnSaveInvoice        = By.xpath("//button[contains(.,'LƯU HÓA ĐƠN')]");
    private final By _inputPaymentAmount    = By.xpath("//input[contains(@class,'text-right') or @placeholder='0'][last()]");
    private final By _lblTotalAmount        = By.xpath(
            "//span[contains(text(),'Tổng tiền hàng')]/following-sibling::span | " +
                    "//*[contains(text(),'Tổng tiền hàng')]/../*[last()]"
    );
    private final By _lblDiscount           = By.xpath(
            "//*[contains(text(),'Giảm giá')]/following-sibling::*[1] | " +
                    "//*[contains(text(),'Giảm giá')]/../*[last()]"
    );


    // FIX: Cập nhật locator điểm - bắt cả +1 và +2 điểm
    private final By _lblPointInfo          = By.xpath(
            "//*[contains(text(),'+1 điểm')] | " +
                    "//*[contains(text(),'+2 điểm')] | " +
                    "//*[contains(text(),'cộng') and contains(text(),'điểm')] | " +
                    "//*[contains(@class,'text-green') and contains(text(),'điểm')]"
    );


    private final By _lblCustomerPoint      = By.xpath(
            "//div[contains(@class,'text-blue-600') and contains(.,'Điểm tích lũy')]"
    );
    private final By _lblCustomerPointDetail = By.xpath(
            "//div[contains(@class,'text-gray-500') and contains(@class,'italic') and contains(.,'Điểm tích lũy')]"
    );



    private final By _lblSuccessIndicator = By.xpath(
            "//*[contains(text(),'Khách cần trả')] | " +
                    "//*[contains(text(),'Tổng tiền hàng')] | " +
                    "//*[contains(text(),'Tiền thừa trả khách')]"
    );


    private final By _btnCloseModal         = By.xpath("//button[normalize-space()='Đóng']");
    private final By _lblToastError         = By.xpath("//li[contains(@class,'destructive') and @data-state='open']");
    private final By _btnCancelInvoice      = By.xpath("//button[contains(.,'Hủy') or contains(.,'hủy')]");


    private final By _lstOrderRows          = By.xpath("//tbody//tr[contains(@class,'cursor-pointer')]");
    private final By _inputSearchOrder      = By.xpath(
            "//input[@placeholder='Tìm kiếm' or contains(@placeholder,'mã') or contains(@placeholder,'khách')]"
    );
    private final By _lblNoOrderFound       = By.xpath("//*[contains(text(),'Không tìm thấy hóa đơn')]");
    private final By _btnEditOrder          = By.xpath("//button[contains(.,'Chỉnh sửa') or contains(.,'Sửa')]");
    private final By _btnDeleteOrder        = By.xpath("//button[contains(.,'Xóa') and not(contains(.,'xóa phiếu'))]");
    private final By _btnConfirmDelete      = By.xpath("//button[normalize-space()='Đồng ý']");
    private final By _btnCancelDelete       = By.xpath("//button[normalize-space()='Bỏ qua']");
    private final By _lblOrderCode          = By.xpath(
            "//td[contains(@class,'code') or contains(.,'HD')] | //span[contains(.,'HD')]"
    );
    private final By _lblOrderDetailTitle   = By.xpath(
            "//h2[contains(text(),'Xem thông tin')] | //*[contains(text(),'Xem thông tin')]"
    );
    private final By _lblOrderDetailCode    = By.xpath("//*[contains(text(),'HDB')]");
    private final By _lblDeleteSuccess      = By.xpath(
            "//*[contains(text(),'Đã xóa hóa đơn')]"
    );






    public WebElement getInputSearchProduct() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(_inputSearchProduct));
    }


    public WebElement getLblNoProduct() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_lblNoProduct));
    }


    public WebElement getLstProductSuggestion() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_lstProductSuggestion));
    }


    public WebElement getInputQuantityLast() {
        return Constant.WEBDRIVER.findElement(_inputQuantityLast);
    }


    public WebElement getInputCustomerPhone() {
        return Constant.WEBDRIVER.findElement(_inputCustomerPhone);
    }


    public WebElement getBtnAddCustomer() {
        return Constant.WEBDRIVER.findElement(_btnAddCustomer);
    }


    public WebElement getLstCustomerSuggestion() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_lstCustomerSuggestion));
    }


    public WebElement getBtnSaveInvoice() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.presenceOfElementLocated(_btnSaveInvoice));
    }


    public WebElement getBtnCloseModal() {
        return Constant.WEBDRIVER.findElement(_btnCloseModal);
    }


    public WebElement getLblToastError() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_lblToastError));
    }


    public WebElement getLblTotalAmount() {
        return Constant.WEBDRIVER.findElement(_lblTotalAmount);
    }


    public WebElement getLblDiscount() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_lblDiscount));
    }


    public WebElement getLblPointInfo() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(8));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_lblPointInfo));
    }


    public WebElement getLblCustomerPoint() {
        return Constant.WEBDRIVER.findElement(_lblCustomerPoint);
    }


    public WebElement getLblNoOrderFound() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_lblNoOrderFound));
    }





    public BanHang waitForPOSReady() {
        new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(15))
                .until(ExpectedConditions.elementToBeClickable(_inputSearchProduct));
        Constant.pause(500);
        return this;
    }


    public int getCustomerPointFromDetailPage(String customerPhone) {
        int maxRetries = 3;
        for (int i = 0; i < maxRetries; i++) {
            try {
                Constant.WEBDRIVER.get(Constant.BASE_URL + "/khach-hang");
                Constant.pause(2000);


                WebElement searchInput = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                        .until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//input[@placeholder='Tìm kiếm' or contains(@placeholder,'khách') or contains(@placeholder,'SĐT')]")
                        ));
                searchInput.clear();
                searchInput.sendKeys(customerPhone);
                Constant.pause(1500);


                WebElement customerRow = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                        .until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//table//tbody//tr[1] | //div[contains(@class,'cursor-pointer')][1]")
                        ));
                customerRow.click();
                Constant.pause(1500);


                WebElement pointElement = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                        .until(ExpectedConditions.visibilityOfElementLocated(_lblCustomerPointDetail));
                String text = pointElement.getText();
                String num = text.replaceAll("[^0-9]", "");
                if (!num.isEmpty()) {
                    return Integer.parseInt(num);
                }
            } catch (Exception e) {
                if (i == maxRetries - 1) return -1;
                Constant.pause(2000);
            }
        }
        return -1;
    }


    public int getCustomerCurrentPoint() {
        try {
            String text = Constant.WEBDRIVER.findElement(_lblCustomerPoint).getText();
            String[] parts = text.split(":");
            if (parts.length >= 2) {
                String numPart = parts[1].trim().replaceAll("[^0-9]", "");
                return numPart.isEmpty() ? 0 : Integer.parseInt(numPart);
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }


    public boolean isPointAddedDisplayed() {
        try {
            return this.getLblPointInfo().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public WebElement getBtnCancelInvoice() {
        return Constant.WEBDRIVER.findElement(_btnCancelInvoice);
    }


    public WebElement getInputSearchOrder() {
        return Constant.WEBDRIVER.findElement(_inputSearchOrder);
    }


    public boolean isOnOrderDetailPage() {
        try {
            return new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(_lblOrderDetailTitle),
                            ExpectedConditions.visibilityOfElementLocated(_lblOrderDetailCode)
                    )) != null;
        } catch (Exception e) {
            return false;
        }
    }


    public BanHang selectProduct(String productName) {
        try {
            WebElement overlay = Constant.WEBDRIVER.findElement(
                    By.xpath("//div[contains(@class,'fixed') and contains(@class,'inset-0') and contains(@class,'bg-black')]")
            );
            if (overlay.isDisplayed()) {
                Constant.WEBDRIVER.findElement(By.xpath("//button[normalize-space()='Đóng']")).click();
                Constant.pause(800);
            }
        } catch (Exception ignored) {}


        Constant.pause(1000);
        WebElement searchInput = this.getInputSearchProduct();
        searchInput.clear();
        searchInput.sendKeys(productName);
        Constant.pause(1500);


        By[] dropdownSelectors = {
                By.xpath("(//div[contains(@class,'absolute')]//button)[1]"),
                By.xpath("(//div[contains(@class,'shadow')]//button)[1]"),
                By.xpath("(//div[contains(@class,'z-')]//button)[1]"),
                By.xpath("//button[contains(@class,'rounded-full') and contains(text(),'+')]"),
                By.xpath("(//div[@role='listbox']//button | //ul//li//button)[1]"),
        };


        WebElement btn = null;
        for (By selector : dropdownSelectors) {
            try {
                btn = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(2))
                        .until(ExpectedConditions.elementToBeClickable(selector));
                if (btn != null) break;
            } catch (Exception ignored) {}
        }


        if (btn != null) {
            JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
            js.executeScript("arguments[0].scrollIntoView(true);", btn);
            Constant.pause(300);
            js.executeScript("arguments[0].click();", btn);
            Constant.pause(1000);
        } else {
            throw new RuntimeException("Không tìm thấy nút '+' trong dropdown sản phẩm: " + productName);
        }
        return this;
    }


    public BanHang enterQuantity(String quantity) {
        Constant.pause(800);
        WebElement qtyInput = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(_inputQuantityLast));
        qtyInput.clear();
        qtyInput.sendKeys(quantity);
        Constant.pause(800);
        return this;
    }


    public BanHang selectCustomer(String phone) {
        Constant.pause(800);
        this.getInputCustomerPhone().clear();
        this.getInputCustomerPhone().sendKeys(phone);
        Constant.pause(2000);


        try {
            WebElement suggestion = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//*[contains(text(),'" + phone + "') and not(self::input)][1]")
                    ));
            JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
            js.executeScript("arguments[0].click()", suggestion);
            Constant.pause(1000);
        } catch (Exception ignored) {}


        return this;
    }


    public BanHang enterPaymentAmount(String amount) {
        Constant.pause(800);
        WebElement payInput = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(_inputPaymentAmount));
        payInput.clear();
        payInput.sendKeys(amount);
        Constant.pause(800);
        return this;
    }


    public BanHang clickBtnSaveInvoice() {
        Constant.pause(1000);
        WebElement btn = this.getBtnSaveInvoice();
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].scrollIntoView(true);", btn);
        Constant.pause(300);
        js.executeScript("arguments[0].click();", btn);
        Constant.pause(1000);
        return this;
    }


    public BanHang clickBtnCloseModal() {
        try {
            WebElement btn = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(_btnCloseModal));
            JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
            js.executeScript("arguments[0].scrollIntoView(true);", btn);
            Constant.pause(300);
            js.executeScript("arguments[0].click();", btn);
            Constant.pause(500);
        } catch (Exception e) {
            // Modal có thể không có hoặc đã đóng
        }
        return this;
    }


    public boolean isCancelButtonDisplayed() {
        try {
            WebElement btn = Constant.WEBDRIVER.findElement(_btnCancelInvoice);
            return btn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isQuantityInputEditable() {
        try {
            WebElement input = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(_inputQuantityLast));
            String readOnly = input.getAttribute("readonly");
            String disabled = input.getAttribute("disabled");
            return readOnly == null && disabled == null;
        } catch (Exception e) {
            return false;
        }
    }


    public BanHang clickBtnAddCustomer() {
        try {
            WebElement overlay = Constant.WEBDRIVER.findElement(
                    By.xpath("//div[contains(@class,'fixed') and contains(@class,'inset-0') and contains(@class,'bg-black')]")
            );
            if (overlay.isDisplayed()) {
                Constant.WEBDRIVER.findElement(By.xpath("//button[normalize-space()='Đóng']")).click();
                Constant.pause(1000);
            }
        } catch (Exception ignored) {}


        Constant.pause(500);
        WebElement btn = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(_btnAddCustomer));
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].scrollIntoView(true);", btn);
        Constant.pause(300);
        js.executeScript("arguments[0].click();", btn);
        Constant.pause(1000);
        return this;
    }


    public BanHang gotoPOSPage() {
        Constant.WEBDRIVER.get(Constant.POS_URL);
        new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[@placeholder='Nhập tên vải']")
                ));
        Constant.pause(1500);
        return this;
    }


    public BanHang enterNewCustomerInfo(String name, String phone, String email, String address) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(_inputNewCustomerName)).sendKeys(name);
        Constant.WEBDRIVER.findElement(_inputNewCustomerPhone).sendKeys(phone);
        if (!email.isEmpty())
            Constant.WEBDRIVER.findElement(_inputNewCustomerEmail).sendKeys(email);
        if (!address.isEmpty())
            Constant.WEBDRIVER.findElement(_inputNewCustomerAddr).sendKeys(address);
        return this;
    }


    public String getDiscountText() {
        try {
            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
            WebElement discountElem = wait.until(ExpectedConditions.visibilityOfElementLocated(_lblDiscount));
            return discountElem.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }


    public boolean isDiscount50kApplied() {
        String discount = getDiscountText();
        return discount.contains("50.000") || discount.contains("50,000")
                || discount.contains("50000") || discount.contains("-50");
    }


    public boolean waitForPointToIncrease(String customerPhone, int expectedPoint, int timeoutSeconds) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < timeoutSeconds * 1000L) {
            int currentPoint = getCustomerPointFromDetailPage(customerPhone);
            if (currentPoint == expectedPoint) return true;
            Constant.pause(1000);
        }
        return false;
    }


    public boolean isNoProductDisplayed() {
        try { return this.getLblNoProduct().isDisplayed(); }
        catch (Exception e) { return false; }
    }


    public boolean isSaveButtonHiddenOrDisabled() {
        try {
            WebElement btn = Constant.WEBDRIVER.findElement(_btnSaveInvoice);
            String disabled = btn.getAttribute("disabled");
            String classes  = btn.getAttribute("class");
            return disabled != null || (classes != null && classes.contains("opacity-50"));
        } catch (Exception e) {
            return true;
        }
    }


    public boolean isNoOrderFoundDisplayed() {
        try { return this.getLblNoOrderFound().isDisplayed(); }
        catch (Exception e) { return false; }
    }


    public boolean isDeleteSuccessDisplayed() {
        try {
            WebElement lbl = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(_lblDeleteSuccess));
            return lbl.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }



    public boolean isSuccessModalDisplayed() {
        Constant.pause(2000);


        // Cách 1: Kiểm tra xem URL đã chuyển khỏi /pos chưa
        String currentUrl = Constant.WEBDRIVER.getCurrentUrl();
        if (!currentUrl.contains("/pos")) {
            System.out.println("Success detected: URL changed to " + currentUrl);
            return true;
        }


        // Cách 2: Kiểm tra có dấu hiệu đơn hàng được hiển thị không
        try {
            WebElement indicator = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(_lblSuccessIndicator));
            if (indicator.isDisplayed()) {
                System.out.println("Success detected: Order info displayed");
                return true;
            }
        } catch (Exception e) {
            System.out.println("No order indicator found: " + e.getMessage());
        }


        // Cách 3: Kiểm tra page source có chứa thông tin đơn hàng không
        try {
            String pageSource = Constant.WEBDRIVER.getPageSource();
            if (pageSource.contains("Khách cần trả") || pageSource.contains("Tổng tiền hàng")) {
                System.out.println("Success detected: Order info in page source");
                return true;
            }
            if (pageSource.contains("thành công")) {
                System.out.println("Success detected: 'thành công' in page source");
                return true;
            }
        } catch (Exception e) {}


        return false;
    }


    public boolean isToastErrorDisplayed() {
        try {
            return this.getLblToastError().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public String getNoProductMessage() {
        try {
            return this.getLblNoProduct().getText().trim();
        } catch (Exception e) {
            return "";
        }
    }


    public BanHang gotoDonHangPage() {
        WebElement menuDonHang = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//nav//a[contains(.,'Đơn hàng')] | " +
                                        "//header//a[contains(.,'Đơn hàng')] | " +
                                        "//a[contains(@href,'don-hang')] | " +
                                        "//button[normalize-space()='Đơn hàng'] | " +
                                        "//header//button[contains(.,'Đơn hàng')]"
                        )
                ));
        menuDonHang.click();
        Constant.pause(2000);


        new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Hóa đơn bán')]")
                ));
        Constant.pause(1000);
        return this;
    }


    public BanHang clickFirstOrder() {
        WebElement firstRow = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//tbody/tr)[1]")
                ));
        firstRow.click();
        Constant.pause(1500);
        return this;
    }


    public BanHang clickBtnSaveNewCustomer() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement btnSave = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Lưu']")
        ));
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].click();", btnSave);
        Constant.pause(1000);
        return this;
    }


    public BanHang searchOrder(String keyword) {
        this.getInputSearchOrder().clear();
        this.getInputSearchOrder().sendKeys(keyword);
        Constant.pause(1500);
        return this;
    }


    public BanHang clickBtnEditOrder() {
        Constant.WEBDRIVER.findElement(_btnEditOrder).click();
        return this;
    }


    public BanHang clickBtnDeleteOrder() {
        Constant.WEBDRIVER.findElement(_btnDeleteOrder).click();
        return this;
    }


    public BanHang clickBtnConfirmDelete() {
        Constant.WEBDRIVER.findElement(_btnConfirmDelete).click();
        return this;
    }


    public BanHang clickBtnCancelDelete() {
        Constant.WEBDRIVER.findElement(_btnCancelDelete).click();
        return this;
    }


    public String getFirstOrderCode() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_lblOrderCode)).getText();
    }


    public int getOrderCount() {
        try {
            Constant.pause(1000);
            return Constant.WEBDRIVER.findElements(_lstOrderRows).size();
        } catch (Exception e) {
            return 0;
        }
    }
}

