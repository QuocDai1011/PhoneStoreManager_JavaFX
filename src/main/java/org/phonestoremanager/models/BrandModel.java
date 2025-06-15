package org.phonestoremanager.models;

public class BrandModel {
    private int brandID;
    private String name;

    public BrandModel(int brandID, String name) {
        this.brandID = brandID;
        this.name = name;
    }

    public int getBrandID() {
        return brandID;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;  // Hiển thị trong ComboBox
    }
}
