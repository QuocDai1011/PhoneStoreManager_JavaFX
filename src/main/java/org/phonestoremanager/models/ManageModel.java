package org.phonestoremanager.models;

public class ManageModel {
    private final String name;
    private final double price;
    private final String imageUrl;
    private final int inventory;

    public ManageModel(String name, double price, String imageUrl, int iventory) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.inventory = iventory;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getInventory() {
        return inventory;
    }
}
