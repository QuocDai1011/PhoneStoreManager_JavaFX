package org.phonestoremanager.models;

public class ColorDetailModel {
    int colorID;
    String nameColor;

    public ColorDetailModel(int colorID, String nameColor) {
        this.colorID = colorID;
        this.nameColor = nameColor;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }
}
