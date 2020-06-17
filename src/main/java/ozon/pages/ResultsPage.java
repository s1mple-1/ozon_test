package ozon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ozon.annotation.ElementName;
import ozon.basket.BasketData;
import ozon.basket.Product;

import java.util.List;

public class ResultsPage extends BasePage {
    @FindBy(xpath = "//div[@data-widget='megaPaginator']")
    @ElementName(name = "Загрузка страницы")
    private WebElement pageLoaded;

    @FindBy(xpath = "(//input[@qa-id='range-to'])[1]")
    @ElementName(name = "Максимальная цена")
    public WebElement maxPriceField;

    @FindBy(xpath = "//div[@value='Высокий рейтинг']//div")
    @ElementName(name = "Высокий рейтинг")
    public WebElement highRatingCheckBox;

    @FindBy(xpath = "//span[text()='NFC']")
    @ElementName(name = "NFC")
    public WebElement nfcCheckBox;

    @FindBy(xpath = "//a[@data-widget='cart']")
    @ElementName(name = "Корзина")
    public WebElement basket;

    @FindBy(xpath = "//span[text()='Samsung']")
    @ElementName(name = "Samsung")
    public WebElement samsung;

    @FindBy(xpath = "//span[text()='Beats']")
    @ElementName(name = "Beats")
    public WebElement beats;

    @FindBy(xpath = "(//span[text()='Xiaomi'])[2]")
    @ElementName(name = "Xiaomi")
    public WebElement xiaomi;

    @FindBy(xpath = "(//span[contains(text(), 'Посмотреть все')])[1]")
    @ElementName(name = "Посмотреть все")
    public WebElement lookAll;

    @FindBy(xpath = "(//input[@class='ui-av9 ui-av4'])[1]")
    @ElementName(name = "Найти бренды")
    public WebElement findBrands;

    private String productCard = "//div[@style='grid-column-start: span 12;']";

    public void pageLoaded() {
        waitVisibilityAllElements(By.xpath("//div[@style='grid-template-columns: repeat(12, 1fr);']"));
        hardWait(2000);
    }

    public void addEvenCountToBasket(Boolean even, int quantity) {
        List<WebElement> list = findElements(By.xpath(productCard));
        if (even) {
            addSomeProductsToBasket(list, 1, quantity);
        } else {
            addSomeProductsToBasket(list, 0, quantity);
        }
    }

    public void addAllEvenToBasket(Boolean even) {
        List<WebElement> list = findElements(By.xpath(productCard));
        if (even) {
            addAllProductsToBasket(list, 1);
        } else {
            addAllProductsToBasket(list, 0);
        }
    }

    private void addAllProductsToBasket(List<WebElement> productList, int startIndex) {
        for (int i = startIndex; i < productList.size(); i += 2) {
            try {
                addProduct(productList, i);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
    }

    private void addSomeProductsToBasket(List<WebElement> productList, int start, int quantity) {
        for (int i = start; i < productList.size(); i += 2) {
            try {
                addProduct(productList, i);
                quantity--;
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
            if (quantity == 0) {
                break;
            }
        }
    }

    private void addProduct(List<WebElement> productList, int index) throws NoSuchElementException {
        productList.get(index).findElement(By.xpath(".//div[text()='В корзину']")).click();
        String productName = productList.get(index).findElement(By.xpath("(.//a)[2]")).getText();
        String productPrice = productList.get(index).findElement(By.xpath("(.//span[contains(text(), '\u20BD')])")).getText().replaceAll("[^0-9]", "");
        BasketData.getBasketData().add(new Product(productName, Long.parseLong(productPrice)));
        hardWait(1000);
    }

    @Override
    public WebElement getFieldByName(String name) {
        return getFieldByClassAndName(name, "ozon.pages.ResultsPage");
    }
}
