package org.phonestoremanager.models;

public class ProductViewModel extends ProductModel {
    private String image;
    private double price;

    public ProductViewModel() {
    }

    public ProductViewModel(int productID, String image, String name, double price) {
        super(productID, 0, 0, name, null); // Giá trị mặc định
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
