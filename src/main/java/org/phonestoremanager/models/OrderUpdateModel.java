package org.phonestoremanager.models;

public class OrderUpdateModel {
    private String productName;
    private int ram;
    private int rom;
    private String color;
    private int quantity;
    private String unitPrice;
    private double unitPriceNumber;

    public OrderUpdateModel() {
    }

    public OrderUpdateModel(String productName, int ram, int rom, String color, int quantity, String unitPrice) {
        this.productName = productName;
        this.ram = ram;
        this.rom = rom;
        this.color = color;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getUnitPriceNumber() {
        return unitPriceNumber;
    }

    public void setUnitPriceNumber(double unitPriceNumber) {
        this.unitPriceNumber = unitPriceNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getRom() {
        return rom;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }


}
