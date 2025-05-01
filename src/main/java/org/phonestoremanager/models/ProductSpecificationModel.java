package org.phonestoremanager.models;

public class ProductSpecificationModel {
    private String chip;
    private String ram;
    private String rom;
    private String screenSize;
    private String scanFrequency;
    private String cameraFront;
    private String cameraRear;
    private String batteryCapacity;
    private String price;

    public ProductSpecificationModel() {
    }

    public ProductSpecificationModel(String chip, String ram, String rom, String screenSize, String scanFrequency, String cameraFront, String cameraRear, String batteryCapacity, String price) {
        this.chip = chip;
        this.ram = ram;
        this.rom = rom;
        this.screenSize = screenSize;
        this.scanFrequency = scanFrequency;
        this.cameraFront = cameraFront;
        this.cameraRear = cameraRear;
        this.batteryCapacity = batteryCapacity;
        this.price = price;
    }

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getScanFrequency() {
        return scanFrequency;
    }

    public void setScanFrequency(String scanFrequency) {
        this.scanFrequency = scanFrequency;
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

    public String getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(String batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

