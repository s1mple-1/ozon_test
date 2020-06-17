package ozon.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ozon.pages.BasePage;
import ozon.pages.ResultsPage;

import static ozon.steps.BaseSteps.basePage;

public class ResultsPageSteps {
    private ResultsPage resultsPage = new ResultsPage();

    @When("Страница {string} загружена")
    public void pageLoaded(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class clazz = Class.forName("ozon.pages." + name);
        basePage = (BasePage) clazz.newInstance();
        resultsPage.pageLoaded();
    }

    @Then("Выбираем поле {string} и высталвяем значение {string}")
    public void fillField(String fieldName, String value) {
        WebElement webElement = basePage.getFieldByName(fieldName);
        basePage.fillField(webElement, value);
        resultsPage.pageLoaded();
    }

    @And("Выбираем бренды")
    public void chooseBrands(DataTable fields) {
        WebElement lookALl = basePage.getFieldByName("Посмотреть все");
        basePage.clickToElement(lookALl);
        fields.asList(String.class).forEach(option -> {
            String brand = (String) option;
            WebElement findBrand = basePage.getFieldByName("Найти бренды");
            basePage.fillField(findBrand, brand);
            try {
                WebElement webElement = basePage.getFieldByName(brand);
                basePage.scrollIntoView(webElement);
                basePage.clickToElement(webElement);
                resultsPage.pageLoaded();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
            resultsPage.pageLoaded();
        });
    }

    @And("Отмечаем опции")
    public void chooseCheckBoxes(DataTable fields) {
        fields.asList(String.class).forEach(option -> {
            WebElement webElement = basePage.getFieldByName((String) option);
            basePage.clickToElement(webElement);
            resultsPage.pageLoaded();
        });
    }

    @And("Добавляем {string} товары в количестве {string} штук")
    public void buyEven(String even, String quantity) {
        boolean flag = false;
        if (even.equalsIgnoreCase("четные")) {
            flag = true;
        }
        resultsPage.addEvenCountToBasket(flag, Integer.parseInt(quantity));
    }

    @And("Добавляем все {string} товары")
    public void buyAllEven(String even) {
        boolean flag = false;
        if (even.equalsIgnoreCase("четные")) {
            flag = true;
        }
        resultsPage.addAllEvenToBasket(flag);
    }

    @And("Переходим на страницу {string}")
    public void goToBasket(String fieldName) {
        WebElement webElement = basePage.getFieldByName(fieldName);
        basePage.clickToElement(webElement);
    }
}
