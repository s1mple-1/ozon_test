package ozon.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ozon.utils.DriverManager;
import ozon.annotation.ElementName;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


public abstract class BasePage {

    public BasePage() {
        PageFactory.initElements(DriverManager.getWebDriver(), this);
    }

    /**
     * This method has an internal waiting, when element to be clickable
     *
     * @param webElement
     */
    public void clickToElement(WebElement webElement) {
        DriverManager.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();
        hardWait(1000);
    }


    public void doubleClick(WebElement webElement) {
        DriverManager.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(webElement));
        DriverManager.getActions().doubleClick(webElement).perform();
    }

    public void waitVisibility(WebElement webElement) {
        DriverManager.getWebDriverWait().until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitVisibilityAllElements(By by) {
        DriverManager.getWebDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public WebElement findElement(By by) {
        return DriverManager.getWebDriver().findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return DriverManager.getWebDriver().findElements(by);
    }

    public void moveToElement(WebElement webElement) {
        DriverManager.getActions().moveToElement(webElement).perform();
    }


    public void scrollIntoView(WebElement webElement) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) DriverManager.getWebDriver();
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(false)", webElement);
    }


    /**
     * Method for waiting, when dynamic element will stop change its value
     *
     * @param webElement
     */
    public void waitElementRefreshing(WebElement webElement) {
        DriverManager.getWebDriverWait().until(ExpectedConditions.visibilityOf(webElement));
        WebDriverWait webDriverWait = new WebDriverWait(DriverManager.getWebDriver(), 10);
        webDriverWait.until(webDriver -> {
            String oldValue = webElement.getText();//берем старое значение целевого поля
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String newValue = webElement.getText();//получаем новое значение целевого поля
            return newValue.equals(oldValue);//если значение не равны ждем
        });
    }


    public void fillField(WebElement webElement, String value) {
        doubleClick(webElement);
        webElement.sendKeys(value);
        webElement.sendKeys(Keys.ENTER);
        hardWait(1000);
    }

    public void sendEnterKey(WebElement webElement) {
        webElement.sendKeys(Keys.ENTER);
    }


    /**
     * Method for for forced waiting with param in milliseconds
     * example: hardWait(1000)
     *
     * @param ms
     */
    void hardWait(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    WebElement getFieldByClassAndName(String elementName, String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert clazz != null;
        List<Field> fields = Arrays.asList(clazz.getFields());
        for (Field field : fields) {
            if (field.getAnnotation(ElementName.class).name().equalsIgnoreCase(elementName)) {
                return findElement(By.xpath(field.getAnnotation(FindBy.class).xpath()));
            }
        }
        Assert.fail("Element " + elementName + " not found");
        return null;
    }

    public abstract WebElement getFieldByName(String name);
}
