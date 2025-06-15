package org.phonestoremanager.models;

public class ProductDetailModel extends ProductViewModel{
        private int productDetailID;
        private int productID;
        private int ram;
        private int rom;
        private String chip;
        private float screenSize;
        private String screenParameters;
        private float batteryCapacity;
        private String image;
        private String description;
        private double price;
        private int stockQuantity;
        private String cameraFront;
        private String cameraRear;
        private String screenTechnology;
        private String scanFrequency;
        private int colorID;


    public ProductDetailModel() {
    }

    public ProductDetailModel(int productDetailID, int productID, int ram, int rom, String chip, float screenSize, String screenParameters, float batteryCapacity, int colorID, String image, String description, double price, int stockQuantity, String cameraFront, String cameraRear, String screenTechnology, String scanFrequency) {
        this.productDetailID = productDetailID;
        this.productID = productID;
        this.ram = ram;
        this.rom = rom;
        this.chip = chip;
        this.screenSize = screenSize;
        this.screenParameters = screenParameters;
        this.batteryCapacity = batteryCapacity;
        this.colorID = colorID;
        this.image = image;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.cameraFront = cameraFront;
        this.cameraRear = cameraRear;
        this.screenTechnology = screenTechnology;
        this.scanFrequency = scanFrequency;
    }

    public int getProductDetailID() {
        return productDetailID;
    }

    public void setProductDetailID(int productDetailID) {
        this.productDetailID = productDetailID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public float getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(float screenSize) {
        this.screenSize = screenSize;
    }

    public String getScreenParameters() {
        return screenParameters;
    }

    public void setScreenParameters(String screenParameters) {
        this.screenParameters = screenParameters;
    }

    public float getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(float batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCameraFront() {
        return cameraFront;
    }

    public void setCameraFront(String cameraFront) {
        this.cameraFront = cameraFront;
    }

    public String getCameraRear() {
        return cameraRear;
    }

    public void setCameraRear(String cameraRear) {
        this.cameraRear = cameraRear;
    }

    public String getScreenTechnology() {
        return screenTechnology;
    }

    public void setScreenTechnology(String screenTechnology) {
        this.screenTechnology = screenTechnology;
    }

    public String getScanFrequency() {
        return scanFrequency;
    }

    public void setScanFrequency(String scanFrequency) {
        this.scanFrequency = scanFrequency;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }
}
