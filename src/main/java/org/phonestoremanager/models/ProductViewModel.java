package org.phonestoremanager.models;

public class ProductViewModel {
    private String image;
    private String name;
    private double price;

    public ProductViewModel() {
    }

    public ProductViewModel(String image, String name, double price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
