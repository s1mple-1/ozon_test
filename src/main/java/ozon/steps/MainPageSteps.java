package ozon.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import ozon.pages.BasePage;

import static ozon.steps.BaseSteps.basePage;

public class MainPageSteps {
    @When("Загружена страница {string}")
    public void pageLoaded(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class clazz = Class.forName("ozon.pages." + name);
        basePage = (BasePage) clazz.newInstance();
    }

    @Then("Выбираем поле {string} и вполняем поиск {string}")
    public void search(String fieldName, String searchText) {
        WebElement webElement = basePage.getFieldByName(fieldName);
        basePage.fillField(webElement, searchText);
    }
}
