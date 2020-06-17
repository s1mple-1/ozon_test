package ozon.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebElement;
import ozon.basket.BasketData;
import ozon.pages.BasePage;
import ozon.pages.BasketPage;

import static ozon.steps.BaseSteps.basePage;

public class BasketPageSteps {
    private BasketPage basketPage = new BasketPage();

    @When("{string} загружена")
    public void pageLoaded(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class clazz = Class.forName("ozon.pages." + name);
        basePage = (BasePage) clazz.newInstance();
    }

    @Then("Проверяем, что товары в корзине соотвествуют добавленным и их количетсво равно {string}")
    public void check(String quantity) {
        basketPage.checkQuantityInBasket(quantity);
        basketPage.checkProducts();
    }

    @Then("Проверяем, что товары в корзине соотвествуют добавленным")
    public void check() {
        basketPage.checkProducts();
    }

    @And("Удаляем все товары и проверяем что корзина пуста")
    public void deleteAllProducts() {
        WebElement selected = basePage.getFieldByName("Удалить выбранные");
        basePage.clickToElement(selected);
        WebElement confirm = basePage.getFieldByName("Подтвердить удаление");
        basePage.clickToElement(confirm);
        basketPage.checkEmpty();
    }


    @Then("Прикладываем вложение с товарами добалвенными в корзину")
    public void allProducts() {
        BasketData.getBasketData().forEach(product -> {
            Allure.addAttachment(product.getName(), product.toString());
        });

    }

    @And("Вложение с наиболее дорогим товаром")
    public void maxPriceProduct() {
        Allure.addAttachment("Товар с максимальной ценой", basketPage.getMaxPrice().toString());

    }
}
