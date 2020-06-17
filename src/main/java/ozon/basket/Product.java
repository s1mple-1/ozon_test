package ozon.basket;

/**
 * This class is virtual entity of product
 */
public class Product {
    private String name;
    private long price;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Продукт {" +
                "Название='" + name + '\'' +
                ", Цена=" + price +
                '}';
    }
}
