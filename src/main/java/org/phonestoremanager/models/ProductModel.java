package org.phonestoremanager.models;

public class ProductModel {
    private int productID;
    private int brandID;
    private String name;
    private String description;

    public ProductModel() {
    }

    public ProductModel(int productID, int brandID, String name, String description) {
        this.productID = productID;
        this.brandID = brandID;
        this.name = name;
        this.description = description;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
