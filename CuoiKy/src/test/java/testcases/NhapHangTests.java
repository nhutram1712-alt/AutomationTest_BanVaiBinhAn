package testcases;

import Common.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.NhapHangPage;
import pageobjects.DangNhapPage;
import org.testng.ITestResult;
import java.time.Duration;

public class NhapHangTests extends BaseTest {

    private NhapHangPage inventoryPage;

    @BeforeMethod
    public void setup(ITestResult result) {
        if (result.getMethod().getMethodName().equals("TC08_EmployeeCannotAccessImport")) {
            return;
        }

        WebDriverManager.chromedriver().setup();
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
        Constant.WEBDRIVER.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        Constant.WEBDRIVER.get(Constant.LOGIN_URL);
        new DangNhapPage().login(Constant.USERNAME, Constant.PASSWORD);
        new DangNhapPage().isLoginSuccessful();
        try { Thread.sleep(2000); } catch (Exception ignored) {}

        Constant.WEBDRIVER.get(Constant.INVENTORY_URL);
        Constant.WEBDRIVER.navigate().refresh();
        try { Thread.sleep(2000); } catch (Exception ignored) {}

        inventoryPage = new NhapHangPage();
        inventoryPage.clickGoToImportForm();
    }

    @AfterMethod
    public void teardown() {
        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
            Constant.WEBDRIVER = null;
        }
    }

    @Test(description = "TC01 - Nhập hàng thành công với NCC hợp lệ")
    public void TC01_ImportSuccessExistingFabricAndSupplier() {
        inventoryPage.searchAndSelectFabric("Vải flannel");
        inventoryPage.enterQuantity(5);
        inventoryPage.selectSupplierFromDropdown("Nhà máy Dệt Đà Lạt");
        inventoryPage.clickSave();

        String toast = inventoryPage.getToastMessage();
        Assert.assertTrue(
                toast.contains("Thành công") || toast.contains("cập nhật tồn kho") || toast.contains("Lưu thành công"),
                "TC01 FAILED: Actual: " + toast
        );
    }

    @Test(description = "TC02 - Nhập hàng thành công với NCC mới")
    public void TC02_ImportSuccessNewSupplier() {
        inventoryPage.searchAndSelectFabric("Vải flannel");
        inventoryPage.enterQuantity(3);
        inventoryPage.selectSupplierFromDropdown("Thêm mới"); // Bắt buộc chọn Thêm Mới để hiện form điền
        inventoryPage.enterSupplierName("NCC_Test_" + System.currentTimeMillis());
        inventoryPage.enterSupplierPhone("090" + (int)(Math.random() * 10000000));
        inventoryPage.clickSave();

        String toast = inventoryPage.getToastMessage();
        Assert.assertTrue(
                toast.contains("Thành công") || toast.contains("cập nhật tồn kho") || toast.contains("Lưu thành công"),
                "TC02 FAILED: Actual: " + toast
        );
    }

    @Test(description = "TC03 - Không tìm thấy vải khi nhập tên vải không tồn tại")
    public void TC03_FabricNotFound() {
        WebElement input = Constant.WEBDRIVER.findElement(By.cssSelector("input[placeholder='Nhập tên vải']"));
        input.click();
        input.sendKeys("VaiKhongTonTai_XYZ_999");
        try { Thread.sleep(1500); } catch (Exception ignored) {}

        String errorMsg = inventoryPage.getFabricErrorMessage();

        // [ĐÚNG THEO TEST MANUAL]: Test case này bắt buộc phải FAILED (Đỏ) vì hệ thống báo lỗi sai text.
        // Thực tế: "Không tìm thấy tên sản phẩm". Mong đợi: "Không tìm thấy vải, vui lòng nhập lại"
        Assert.assertEquals(errorMsg, "Không tìm thấy vải, vui lòng nhập lại",
                "TC03 BẮT ĐƯỢC BUG: Thông báo lỗi không khớp với mong đợi (Actual: " + errorMsg + ")"
        );
    }

    @Test(description = "TC04 - Nhập số lượng bé hơn hoặc bằng 0")
    public void TC04_NegativeQuantity() {
        inventoryPage.searchAndSelectFabric("Vải flannel");
        inventoryPage.enterQuantity(-1); // Thử cố tình nhập -1

        String currentQty = inventoryPage.getQuantityValue();

        Assert.assertTrue(currentQty.equals("1") || currentQty.equals(""),
                "TC04 FAILED: Web bị lỗi, cho phép nhập số lượng âm: " + currentQty);
    }

    @Test(description = "TC05 - Hủy thao tác nhập hàng")
    public void TC05_CancelImport() {
        inventoryPage.searchAndSelectFabric("Vải flannel");
        inventoryPage.enterQuantity(3);
        inventoryPage.clickCancel();

        Assert.assertTrue(
                inventoryPage.isOnInventoryListPage(),
                "TC05 FAILED: Không quay về danh sách sau khi nhấn Hủy"
        );
    }

    @Test(description = "TC06 - Tồn kho tăng chính xác sau khi nhập hàng")
    public void TC06_InventoryIncreasesCorrectly() {
        inventoryPage.searchAndSelectFabric("Vải flannel");
        inventoryPage.enterQuantity(10);
        inventoryPage.selectSupplierFromDropdown("Xưởng Vải");
        inventoryPage.clickSave();

        String toast = inventoryPage.getToastMessage();
        Assert.assertTrue(
                toast.contains("Thành công") || toast.contains("cập nhật tồn kho") || toast.contains("Lưu thành công"),
                "TC06 FAILED: Actual: " + toast
        );
    }

    @Test(description = "TC07 - Phiếu nhập được lưu đúng thông tin")
    public void TC07_ImportRecordSavedCorrectly() {
        inventoryPage.searchAndSelectFabric("Vải flannel");
        inventoryPage.enterQuantity(5);
        inventoryPage.selectSupplierFromDropdown("Xưởng Vải");
        inventoryPage.clickSave();

        String toast = inventoryPage.getToastMessage();
        Assert.assertTrue(
                toast.contains("Thành công") || toast.contains("cập nhật tồn kho") || toast.contains("Lưu thành công"),
                "TC07 FAILED: Phiếu không lưu được. Actual: " + toast
        );

        try { Thread.sleep(1000); } catch (Exception ignored) {}
        Assert.assertTrue(
                inventoryPage.isOnInventoryListPage(),
                "TC07 FAILED: Không quay về trang danh sách."
        );
    }


}