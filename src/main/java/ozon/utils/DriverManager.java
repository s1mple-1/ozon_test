package ozon.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;

public class DriverManager {
    private static Properties properties = TestProperties.getInstance().getProperties();
    private static WebDriver webDriver = null;
    private static WebDriverWait webDriverWait = null;
    private static Actions actions;

    public static WebDriver getWebDriver() {
        if (webDriver == null) {
            webDriver = createWebDriver();
        }
        return webDriver;
    }

    public static WebDriverWait getWebDriverWait() {
        if (webDriverWait == null) {
            webDriverWait = new WebDriverWait(getWebDriver(), 10);
        }
        return webDriverWait;
    }

    public static Actions getActions() {
        if (actions == null) {
            actions = new Actions(getWebDriver());
        }
        return actions;
    }

    private static WebDriver createWebDriver() {
        switch (properties.getProperty("browser")) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", properties.getProperty("driver"));
                webDriver = new ChromeDriver();
                break;
            case "opera":
                System.setProperty("webdriver.opera.driver", properties.getProperty("driver"));
                webDriver = new OperaDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", properties.getProperty("driver"));
                webDriver = new FirefoxDriver();
                break;
        }
        return webDriver;
    }

    public static void quitWebDriver() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
            webDriverWait = null;
            actions = null;
        }
    }

}
