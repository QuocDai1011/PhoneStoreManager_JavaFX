package org.phonestoremanager.models;

public class ProductModel {
    private int productID;
    private int categoryID;
    private int brandID;
    private String name;
    private String description;

    public ProductModel() {
    }

    public ProductModel(int productID, int categoryID, int brandID, String name, String description) {
        this.productID = productID;
        this.categoryID = categoryID;
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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
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
