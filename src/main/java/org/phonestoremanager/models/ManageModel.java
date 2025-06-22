package org.phonestoremanager.models;

public class ManageModel {
    private final int productID; // ✅ Thêm
    private final String name;
    private final double price;
    private final String imageUrl;
    private final int inventory;
    private ProductViewModel productViewModel;

    // ✅ Constructor có productID
    public ManageModel(int productID, String name, double price, String imageUrl, int inventory) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.inventory = inventory;
    }

    public int getProductID() {
        return productID;
    }

    public ProductViewModel getProductViewModel() {
        return productViewModel;
    }

    public void setProductViewModel(ProductViewModel productViewModel) {
        this.productViewModel = productViewModel;
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
