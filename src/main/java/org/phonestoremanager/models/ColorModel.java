package org.phonestoremanager.models;

public class ColorModel {
    int colorID;
    String nameColor;

    public ColorModel(int colorID, String nameColor) {
        this.colorID = colorID;
        this.nameColor = nameColor;
    }

    public int getColorID() {
        return colorID;
    }

    public String getNameColor() {
        return nameColor;
    }

    @Override
    public String toString() {
        return nameColor;
    }
}
