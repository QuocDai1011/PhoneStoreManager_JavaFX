package org.uiemployee.model;

public class Product {
    private final String name;
    private final String brand;
    private final String price;
    private final String imageUrl;

    public Product(String name, String brand, String price, String imageUrl) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
}
