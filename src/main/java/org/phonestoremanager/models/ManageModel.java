package org.phonestoremanager.models;

public class ManageModel {
    private final String name;
    private final String brand;
    private final String price;
    private final String imageUrl;
    private final String inventory;

    public ManageModel(String name, String brand, String price, String imageUrl, String iventory) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
        this.inventory = iventory;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInventory() {
        return inventory;
    }
}
