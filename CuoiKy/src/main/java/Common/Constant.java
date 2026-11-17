package Common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Constant {

    public static WebDriver WEBDRIVER;

    public static final String WEBAPP_URL = "http://localhost:3000/login";
    public static final String USERNAME = "owner";
    public static final String PASSWORD = "123456";
    public static final String USER_NHANVIEN = "staff2";
    public static final String PASS_NHANVIEN = "123456";
//-------------------------------------------------------------
public static final String BASE_URL        = "http://localhost:3000";
    public static final String LOGIN_URL       = BASE_URL + "/login";
    public static final String POS_URL         = BASE_URL + "/pos";
    public static final String PROTECTED_PATH  = "/dashboard";

    // Tài khoản đăng nhập
    public static final String VALID_USERNAME = "manager";
    public static final String VALID_PASSWORD = "123456";

    // Sản phẩm test
    public static final String PRODUCT_1_NAME    = "Vải modal";
    public static final String PRODUCT_1_QTY     = "1";
    public static final String PRODUCT_2_NAME    = "Vải tricot";
    public static final String PRODUCT_2_QTY     = "1";
    public static final String PRODUCT_CHEAP_NAME = "Vải tricot";
    public static final String PRODUCT_NOT_EXIST  = "Vải_ABC_XYZ_999";

    // Số lượng đặc biệt
    public static final String OVERSTOCK_QTY   = "99999";
    public static final String NEGATIVE_QTY    = "-1";
    public static final String QTY_OVER_100K   = "1";
    public static final String QTY_UNDER_100K  = "1";

    // Khách hàng
    public static final String CUSTOMER_PHONE           = "0912345027"; // Đỗ Minh Châu - < 10 điểm
    public static final String CUSTOMER_10_POINTS_PHONE = "0123456789"; // Kiều Thanh Nam - >= 10 điểm

    // Khách hàng mới
    public static final String NEW_CUSTOMER_NAME  = "Nguyễn A";
    public static final String NEW_CUSTOMER_PHONE = "0999888777";
    public static final String NEW_CUSTOMER_EMAIL = "vana@email.com";
    public static final String NEW_CUSTOMER_ADDR  = "123 Đường Lê Lợi";

    // Thanh toán
    public static final String PAYMENT_AMOUNT       = "2000000";
    public static final String PAYMENT_AMOUNT_SMALL = "50000";

    // Tìm kiếm
    public static final String ORDER_NOT_EXIST = "HDB1111111";
    public static final String ORDER_EXIST = "HDB0053";

    public static void initDriver() {
        org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordCheck,SafeBrowsingEnhancedProtection");
        options.addArguments("--password-store=basic");
        options.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {{
            put("credentials_enable_service", false);
            put("profile.password_manager_enabled", false);
            put("profile.password_manager_leak_detection", false);
        }});
        WEBDRIVER = new ChromeDriver(options);
        WEBDRIVER.manage().window().maximize();
        WEBDRIVER.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(1));
    }

    public static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void quitDriver() {
        if (WEBDRIVER != null) WEBDRIVER.quit();
    }
    //--------------------------
    public static final String INVENTORY_URL = BASE_URL + "/inventory";

}
