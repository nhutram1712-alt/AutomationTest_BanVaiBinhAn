//package testcases;
//
//import Common.Constant;
//import org.openqa.selenium.By;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//import pageobjects.DangXuat;
//import pageobjects.GeneralPage;
//
//import java.time.Duration;
//
//public class DangXuatTests extends BaseTest {
//
//    GeneralPage generalPage = new GeneralPage();
//    DangXuat    dangXuat    = new DangXuat();
//
//    @BeforeClass
//    public void setUp() {
//        Constant.initDriver();
//        generalPage.gotoLoginPage();
//        generalPage.login(Constant.VALID_USERNAME, Constant.VALID_PASSWORD);
//        new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(20))
//                .until(driver -> !driver.getCurrentUrl().contains("/login"));
//    }
//
//    @BeforeMethod
//    public void ensureLoggedIn() {
//        if (Constant.WEBDRIVER.getCurrentUrl().contains("/login")) {
//            generalPage.login(Constant.VALID_USERNAME, Constant.VALID_PASSWORD);
//            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(20))
//                    .until(driver -> !driver.getCurrentUrl().contains("/login"));
//        }
//        Constant.WEBDRIVER.get(Constant.BASE_URL);
//        Constant.pause(1000);
//    }
////@BeforeMethod
////public void setupTest() {
////    // 1. Khởi tạo trình duyệt mới
////    Constant.initDriver();
////
////    // 2. Thực hiện đăng nhập chuẩn bị cho Test Case
////    generalPage.gotoLoginPage();
////    generalPage.login(Constant.VALID_USERNAME, Constant.VALID_PASSWORD);
////
////    // 3. Đợi đăng nhập thành công
////    new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(20))
////            .until(driver -> !driver.getCurrentUrl().contains("/login"));
////
////    // 4. Đảm bảo ở trang chủ
////    Constant.WEBDRIVER.get(Constant.BASE_URL);
////    Constant.pause(1000);
////}

package testcases;

import Common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.DangXuat;
import pageobjects.GeneralPage;

import java.time.Duration;

public class DangXuatTests extends BaseTest {

    GeneralPage generalPage = new GeneralPage();
    DangXuat    dangXuat    = new DangXuat();

    @BeforeMethod
    public void setupTest() {
        // 1. Mở Trình duyệt mới tinh cho mỗi Test Case (đảm bảo không bao giờ bị Null)
        Constant.initDriver();

        // 2. Tự động Đăng nhập để tạo tiền đề cho việc Đăng xuất
        generalPage.gotoLoginPage();
        generalPage.login(Constant.VALID_USERNAME, Constant.VALID_PASSWORD);

        // 3. Đợi đến khi Đăng nhập thành công (mất chữ /login trên URL)
        new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(20))
                .until(driver -> !driver.getCurrentUrl().contains("/login"));

        // 4. Chắc chắn đang ở trang chủ trước khi bắt đầu test
        Constant.WEBDRIVER.get(Constant.BASE_URL);
        Constant.pause(1000);
    }

    @AfterMethod
    public void tearDown() {
        // 5. Chạy xong test nào là dọn dẹp sạch sẽ test đó
        Constant.quitDriver();
    }

    @Test
    public void TC01_DangXuat_ThanhCong() {
        dangXuat.clickUserAvatar();

        dangXuat.clickMenuDangXuat();

        Assert.assertTrue(
                dangXuat.isConfirmDialogDisplayed(),
                "TC01 FAILED: Hộp thoại 'Bạn có chắc chắn muốn đăng xuất?' phải hiển thị"
        );

        dangXuat.clickDongY();

        Assert.assertTrue(
                dangXuat.isOnLoginPage(),
                "TC01 FAILED: Sau khi đăng xuất phải chuyển về trang đăng nhập"
        );
    }

    @Test
    public void TC02_HuyDangXuat() {
        String urlBeforeLogout = Constant.WEBDRIVER.getCurrentUrl();

        dangXuat.clickUserAvatar();

        dangXuat.clickMenuDangXuat();

        Assert.assertTrue(
                dangXuat.isConfirmDialogDisplayed(),
                "TC02 FAILED: Hộp thoại xác nhận phải hiển thị trước khi hủy"
        );

        dangXuat.clickBoQua();
        Constant.pause(500);

        Assert.assertTrue(
                dangXuat.isStillLoggedIn(),
                "TC02 FAILED: Sau khi bỏ qua phải vẫn còn phiên đăng nhập, không chuyển về trang login"
        );

        Assert.assertFalse(
                dangXuat.isConfirmDialogDisplayed(),
                "TC02 FAILED: Hộp thoại phải đóng sau khi nhấn Bỏ qua"
        );
    }

    @Test
    public void TC03_SauDangXuat_KhongTruyCap_TrangNoiBo() {
        dangXuat.performLogout();
        Assert.assertTrue(
                dangXuat.isOnLoginPage(),
                "TC03 FAILED (setup): Phải đăng xuất thành công trước"
        );

        Constant.WEBDRIVER.get(Constant.BASE_URL + Constant.PROTECTED_PATH);
        Constant.pause(1500);

        Assert.assertTrue(
                dangXuat.isOnLoginPage(),
                "TC03 FAILED: Sau khi đăng xuất, truy cập trang nội bộ phải bị chuyển về trang đăng nhập"
        );

        Constant.WEBDRIVER.navigate().back();
        Constant.pause(1500);

        Assert.assertTrue(
                dangXuat.isOnLoginPage(),
                "TC03 FAILED: Sau khi nhấn Back, hệ thống vẫn phải ở trang đăng nhập, không cho truy cập lại"
        );
    }

}