package Web;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.List;

@Listeners(AutomationListeners.class)
public class TableMain {

    //WebDriver
    private WebDriver driver;
    private TablePage table;
    private final int expectedColSize = 31;
    private final double maxBalance=250000;
    private final String url = "https://www.mortgagecalculator.org/";

    @BeforeClass
    public void start() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        table = PageFactory.initElements(driver, TablePage.class);

    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        try {
            MonteScreenRecorder.startRecord(method.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Step("Testing The number of cols")
    @Test
    public void test01_verifyNumberOfCols() {

        Assert.assertEquals(table.getNumberOfCols(), expectedColSize);
    }

    @Step("Printing Cols Details")
    @Test
    public void test02_printDetails() {

        table.printDetails();
        List<Double> list=table.getBalances();
        SoftAssert soft = new SoftAssert();

        for (double balance : list) {
            soft.assertTrue(balance <maxBalance);
        }
       soft.assertAll();

    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
    }
}
