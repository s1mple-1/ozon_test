package ozon.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ozon.annotation.ElementName;

public class MainPage extends BasePage {
    @FindBy(xpath = "//input[@name='search']")
    @ElementName(name = "Поиск")
    public WebElement searchBar;

    @Override
    public WebElement getFieldByName(String name) {
        return getFieldByClassAndName(name, "ozon.pages.MainPage");
    }
}
