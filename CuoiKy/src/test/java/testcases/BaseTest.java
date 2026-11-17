//package testcases;
//
//import Common.Constant;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import org.testng.ITestResult;
//import org.testng.annotations.*;
//import java.lang.reflect.Method;
//
//public class BaseTest {
//    // static giúp biến này sống suốt quá trình chạy 7 chức năng
//    protected static ExtentReports extent;
//    protected static ExtentTest test;
//    protected static ExtentTest parentTest;
//
//    @BeforeSuite
//    public void initReport() {
//        if (extent == null) {
//            ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReport.html");
//            extent = new ExtentReports();
//            extent.attachReporter(spark);
//        }
//    }
//
//    @BeforeClass
//    public void setupClass() {
//        // Tạo một "Tab" lớn dựa trên tên Class (ví dụ: LoginTest, StaffTest)
//        parentTest = extent.createTest(this.getClass().getSimpleName());
//    }
//
//    @BeforeMethod
//    public void setupReport(Method method) {
//        // Tạo test case con nằm bên trong Tab cha
//        test = parentTest.createNode(method.getName());
//    }
//
//    @AfterMethod
//    public void recordResult(ITestResult result) {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            test.log(Status.FAIL, "Thất bại: " + result.getThrowable());
//        } else if (result.getStatus() == ITestResult.SUCCESS) {
//            test.log(Status.PASS, "Thành công");
//        }
//
//        if (Constant.WEBDRIVER != null) {
//            Constant.WEBDRIVER.quit();
//            Constant.WEBDRIVER = null;
//        }
//    }
//
//    @AfterSuite
//    public void cleanUp() {
//        if (extent != null) {
//            extent.flush();
//        }
////    }}
//package testcases;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import org.testng.ITestResult;
//import org.testng.annotations.*;
//import java.lang.reflect.Method;
//
//    public class BaseTest {
//        // Mỗi Class sẽ có một đối tượng báo cáo riêng
//        protected ExtentReports extent;
//        protected ExtentTest test;
//
//        @BeforeClass
//        public void setupClass() {
//            // Lấy tên Class làm tên file báo cáo (VD: LoginTest.html)
//            String reportName = this.getClass().getSimpleName() + ".html";
//            ExtentSparkReporter spark = new ExtentSparkReporter(reportName);
//
//            extent = new ExtentReports();
//            extent.attachReporter(spark);
//
//            System.out.println("=== Khởi tạo báo cáo: " + reportName + " ===");
//        }
//
//        @BeforeMethod
//        public void setupReport(Method method) {
//            // Tạo một mục test mới trong file report trước khi chạy mỗi method
//            test = extent.createTest(method.getName());
//        }
//
//        @AfterMethod
//        public void recordResult(ITestResult result) {
//            // Ghi lại kết quả Pass/Fail vào báo cáo
//            if (result.getStatus() == ITestResult.FAILURE) {
//                test.log(Status.FAIL, "Thất bại: " + result.getThrowable());
//            } else if (result.getStatus() == ITestResult.SUCCESS) {
//                test.log(Status.PASS, "Thành công");
//            }
//        }
//
//        @AfterClass
//        public void tearDownClass() {
//            if (extent != null) {
//                // Xuất file ngay sau khi chạy xong toàn bộ test trong Class đó
//                extent.flush();
//                System.out.println("=== Đã lưu file báo cáo riêng cho Class này ===");
//            }
//        }
//    }
package testcases;

import Common.Constant;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {
    // PHẢI dùng static để biến sống suốt quá trình chạy Suite (nhiều Class)
    protected static ExtentReports extent;
    protected static ExtentTest parentTest;
    protected static ExtentTest test;

    @BeforeSuite
    public void initReport() {
        if (extent == null) {
            // Tên file tổng hợp duy nhất
            ExtentSparkReporter spark = new ExtentSparkReporter("FullTestReport.html");

            // Cấu hình giao diện (tùy chọn để giống ảnh hơn)
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Automation Test Report");
            spark.config().setReportName("Hệ Thống Báo Cáo Tổng Hợp");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
    }

    @BeforeClass
    public void setupClass() {
        // Tạo "Tab" cha bên trái menu (LoginTest, HangHoaTest...)
        parentTest = extent.createTest(this.getClass().getSimpleName());
    }

    @BeforeMethod
    public void setup(Method method) {
        // Tạo các "Node" con (TC01, TC02...) nằm trong Class cha
        test = parentTest.createNode(method.getName());

        // Khởi tạo trình duyệt cho mỗi case
////        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--incognito");
//        options.addArguments("--remote-allow-origins=*");

//        Constant.WEBDRIVER = new ChromeDriver(options);
//        Constant.WEBDRIVER.manage().window().maximize();
//        Constant.WEBDRIVER.manage().timeouts().implicidddddtlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void recordResult(ITestResult result) {
        // Ghi log kết quả vào báo cáo
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Thất bại: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Thành công");
        } else {
            test.log(Status.SKIP, "Bị bỏ qua");
        }

        // Đóng trình duyệt sau mỗi case
        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
            Constant.WEBDRIVER = null;
        }
    }

    @AfterSuite
    public void cleanUp() {
        if (extent != null) {
            // Lệnh quan trọng nhất để xuất file
            extent.flush();
        }
    }}