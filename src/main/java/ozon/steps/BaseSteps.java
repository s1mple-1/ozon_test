package ozon.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import ozon.basket.BasketData;
import ozon.pages.BasePage;
import ozon.utils.DriverManager;
import ozon.utils.TestProperties;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseSteps {
    private static Properties properties = TestProperties.getInstance().getProperties();

    static BasePage basePage;

    @Before
    public static void setUp() {
        WebDriver webDriver = DriverManager.getWebDriver();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        webDriver.get(properties.getProperty("url"));
    }

    @After
    public static void tearDown() {
        DriverManager.quitWebDriver();
        BasketData.getBasketData().clear();
    }
}
