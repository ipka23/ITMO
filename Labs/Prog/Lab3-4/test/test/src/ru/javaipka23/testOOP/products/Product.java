package ru.javaipka23.testOOP.products;

public class Product {
    private int price;
    private String name;

    public Product() {
    }

    public Product(String name, int price) {
        this.price = price;
        this.setName(name);
    }

    public Product(int price) {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
