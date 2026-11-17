package testcases;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import Common.Constant;
import org.testng.annotations.Test;
import pageobjects.*;
import org.testng.Assert;

import org.openqa.selenium.chrome.ChromeOptions;
public class HangHoaTests extends BaseTest{
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); // ← chạy ẩn danh, không có password manager

        Constant.WEBDRIVER = new ChromeDriver(options);
        Constant.WEBDRIVER.manage().window().maximize();
//        System.out.println("Pre-condition");
//        Constant.WEBDRIVER = new ChromeDriver();
//        Constant.WEBDRIVER.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();
    }

    @Test
    public void TC01() {
        System.out.println("TC01-Thêm vải thành công với tên vải và giá bán hợp lệ");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang Hàng hóa → mở form → thêm hàng hóa
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        HangHoaCreateForm hangHoaForm = hangHoaPage.goToHangHoaForm();
        String tenVai = "Vái cotton mềm " + System.currentTimeMillis();
        hangHoaForm.ThemHH(tenVai, "50000", "10");

        // Step 3: Kiểm tra thông báo
        String actualToast = hangHoaPage.getToastContent();
        Assert.assertEquals(actualToast, "Thêm sản phẩm mới thành công");
    }

    @Test
    public void TC02() {
        System.out.println("TC02-Không cho thêm vải khi tên sản phẩm đã tồn tại");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang Hàng hóa → mở form → thêm hàng hóa
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        HangHoaCreateForm hangHoaForm = hangHoaPage.goToHangHoaForm();
        hangHoaForm.ThemHH("Vải nilon 2000", "50000", "10");

        // Step 3: Kiểm tra thông báo
        String actualToast = hangHoaPage.getToastErrorContent();
        Assert.assertEquals(actualToast, "Tên sản phẩm đã tồn tại trên hệ thống.");
    }

    @Test
    public void TC03() {
        System.out.println("TC03-Không cho thêm vải khi giá bán nhỏ hơn 0");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang Hàng hóa → mở form → thêm hàng hóa
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        HangHoaCreateForm hangHoaForm = hangHoaPage.goToHangHoaForm();
        hangHoaForm.ThemHH("Vải nilon 200000", "-50000", "10");

        // Step 3: Kiểm tra thông báo
        String actualToast = hangHoaPage.getToastErrorContent();
        Assert.assertEquals(actualToast, "price must not be less than 0");
    }

    @Test
    public void TC04() {
        System.out.println("TC04-Hủy thao tác thêm vải");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang Hàng hóa → mở form → thêm thông tin tên, giá bán và click Bỏ qua
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        HangHoaCreateForm hangHoaForm = hangHoaPage.goToHangHoaForm();
        String tenVai = "Vải Test Hủy " + System.currentTimeMillis();
        String giaBan = "50000";
        hangHoaForm.getTxtTenHH().sendKeys(tenVai);
        hangHoaForm.getTxtGiaBan().sendKeys(giaBan);
        hangHoaPage = hangHoaForm.clickBoQua();

        // Step 3: Kiểm tra thông báo
        Assert.assertTrue(hangHoaForm.isAddFormClosed());
        Assert.assertFalse(hangHoaPage.isProductNotDisplayed(tenVai));
    }

    @Test
    public void TC05() {
        System.out.println("TC05-Tìm kiếm vải theo tên và xem thông tin chi tiết");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang Hàng hóa → nhập tên vải đã tồn tại vào ô tìm kiếm và lấy dòng đầu tiên
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        String tenVai = "Vải nilon 20000";
        hangHoaPage.getTXTTimKiemVai().sendKeys(tenVai);

        HangHoaChiTietPage ct = hangHoaPage.goToChiTietPage(hangHoaPage.getFirstRowNameText());
        // Step 3: Kiểm tra vải đã xuất hiện trong bảng
//        Assert.assertTrue(ct.isDetailPageDisplayed(), "Trang chi tiết chưa hiện!");
//        Assert.assertTrue(ct.getProductNameDetailText().contains(tenVai), "Tên không khớp!");
        Assert.assertTrue(ct.isDetailPageDisplayed());
        Assert.assertTrue(ct.getProductNameDetailText().contains(tenVai));

    }


    @Test
    public void TC06() {
        System.out.println("TC06-Không tìm thấy sản phẩm khi nhập tên vải không tồn tại");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang Hàng hóa → nhập tên vải không tồn tại vào ô tìm kiếm
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        String tenVai = "Vải Test Tìm kiếm  ";
        hangHoaPage.getTXTTimKiemVai().sendKeys(tenVai);

        // Step 3: Kiểm tra vải không xuất hiện trong bảng
        String actualtMSG = hangHoaPage.gettxtTimKiemUnvalidVai().getText();

        Assert.assertEquals(actualtMSG, "Sản phẩm không tồn tại");
    }

    @Test
    public void TC07() {
        System.out.println("TC07-Cập nhật thông tin vải thành công với dữ liệu hợp lệ");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang Hàng hóa → nhập tên vải đã tồn tại vào ô tìm kiếm và lấy dòng đầu tiên để thực hiện chỉnh sửa
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        HangHoaChiTietPage detail = hangHoaPage.goToFirstChiTietHH();
        HangHoaUpdateForm update = detail.gotoHHUpdateForm();

        String tenVai = "Vái cotton mềm " + System.currentTimeMillis();
        update.UpdateHH(tenVai, "55000");

        // Step 3: Kiểm tra vải không xuất hiện trong bảng
        String actualtMSG = detail.getToastSuccessText();

        Assert.assertEquals(actualtMSG, "Đã cập nhật thông tin hàng hóa");
    }

    @Test
    public void TC08() {
        System.out.println("TC08-Không cho cập nhật vải khi trùng tên vải");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang Hàng hóa → nhập tên vải đã tồn tại vào ô tìm kiếm và lấy dòng đầu tiên để thực hiện chỉnh sửa
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        HangHoaChiTietPage detail = hangHoaPage.goToFirstChiTietHH();
        HangHoaUpdateForm update = detail.gotoHHUpdateForm();

        String tenVai = "HH22";
        update.UpdateTenHH(tenVai);

        // Step 3: Kiểm tra vải không xuất hiện trong bảng
        String actualtMSG = detail.getNameMsgError();

        Assert.assertEquals(actualtMSG, "Tên sản phẩm đã tồn tại trên hệ thống.");
    }

    @Test
    public void TC09() {
        System.out.println("TC09-Không cho cập nhật cải khi giá bán nhỏ hơn hoặc bằng 0");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang Hàng hóa → chọn hàng hóa đầu tiên trong bảng
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        HangHoaChiTietPage detail = hangHoaPage.goToFirstChiTietHH();
        HangHoaUpdateForm update = detail.gotoHHUpdateForm();

        String giaBan = "-50000";
        update.UpdateGiaHH(giaBan);

        // Step 3: Kiểm tra vải không xuất hiện trong bảng
        String actualtMSG = detail.getPriceMsgError();

        Assert.assertEquals(actualtMSG, "price must not be less than 0");
    }

    @Test
    public void TC10() {
        System.out.println("TC10-Hủy thao tác cập nhật vải");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        // Step 2: Vào trang Hàng hóa → chọn hàng hóa đầu tiên trong bảng
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        HangHoaChiTietPage detail = hangHoaPage.goToFirstChiTietHH();
        HangHoaUpdateForm update = detail.gotoHHUpdateForm();
        //Thay đổi tên
        String tenVai = "HH22";
        update.UpdateTenHH(tenVai);
        //chọn Bỏ qua
        update.getBtnBoQua().click();
        //Kiểm tra
        Assert.assertTrue(update.isUpdateFormClosed());
        Assert.assertFalse(hangHoaPage.isProductNotDisplayed(tenVai));
    }

    @Test
    public void TC11() {
        System.out.println("TC11-Xóa vải thành công khi tồn kho bằng 0");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        // Step 2: Vào trang Hàng hóa → chọn hàng hóa đầu tiên trong bảng
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        HangHoaChiTietPage detail = hangHoaPage.goToFirstChiTietHH();
        HangHoaUpdateForm update = detail.gotoHHUpdateForm();
        //Thay đổi số lượng tồn kho
        update.UpdateTonKho("0");
        detail.deleteProduct();
        String actualMsg = detail.getDeleteCompleteText();
        //Kiểm tra
        Assert.assertEquals(actualMsg, "Đã xóa sản phẩm thành công");
    }

    @Test
    public void TC12() {
        System.out.println("TC12-Không cho xóa vải khi tồn kho lớn hơn 0");
        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);
        // Step 2: Vào trang Hàng hóa → chọn hàng hóa đầu tiên trong bảng
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        HangHoaChiTietPage detail = hangHoaPage.goToFirstChiTietHH();
        detail.getBtnXoa().click();
        String actualMsg = detail.getErrorTonKhoText();
        //Kiểm tra
        Assert.assertEquals( actualMsg, "Không thể xóa sản phẩm.");
    }
    @Test
    public void TC13() {
        System.out.println("TC13-Hủy thao tác xóa vải");

        // Step 1: Login
        DangNhapPage loginPage = new DangNhapPage();
        loginPage.open();
        BaoCaoPage_OwnerHomePage baoCaoPage = loginPage.loginOwner(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Vào trang Hàng hóa → Chọn hàng hóa đầu tiên
        HangHoaPage hangHoaPage = baoCaoPage.gotoHangHoaPage();
        // Lấy tên sản phẩm từ bảng để verify
        String tenSP = hangHoaPage.getFirstRowNameText();
        HangHoaChiTietPage detail = hangHoaPage.goToFirstChiTietHH();

        // Step 3: Thay đổi tồn kho về 0
        HangHoaUpdateForm update = detail.gotoHHUpdateForm();
        update.UpdateTonKho("0");

        // Step 4: Thực hiện click Xóa (Gọi hàm click an toàn để tránh bị toast che)
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", detail.getBtnXoa());

        // Step 5: Nhấn Hủy trên Dialog xác nhận
        detail.getBtnHuy().click();

        // Kiểm tra đóng form (Sử dụng hàm isDialogClosed bạn đã viết)
        Assert.assertTrue(detail.isDialogClosed());

        // Kiểm tra sản phẩm vẫn tồn tại (Sử dụng hàm isProductStillExists bạn đã viết)
        Assert.assertTrue(detail.isProductStillExists(tenSP), "Lỗi: Sản phẩm '" + tenSP + "' không tìm thấy!");
    }
}