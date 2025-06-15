package org.phonestoremanager.models;

public class ProductViewModel extends ProductModel{
    private String image;
    private double price;

    public ProductViewModel() {
    }

    public ProductViewModel(int productID, int brandID, String name, String description, String image, double price) {
        super(productID, brandID, name, description);
        this.image = image;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
