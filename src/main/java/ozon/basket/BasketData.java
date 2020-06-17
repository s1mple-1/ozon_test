package ozon.basket;

import io.qameta.allure.Attachment;

import java.util.ArrayList;

/**
 * This class is virtual entity of product Basket on website
 */
public class BasketData {
    private static ArrayList<Product> productList;

    private BasketData() {
    }

    @Attachment
    public static ArrayList<Product> getBasketData() {
        if (productList == null) {
            productList = new ArrayList<>();
        }
        return productList;
    }
}
