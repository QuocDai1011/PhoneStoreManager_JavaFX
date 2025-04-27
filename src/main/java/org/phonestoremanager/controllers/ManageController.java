package org.phonestoremanager.controllers;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.phonestoremanager.models.ManageModel;

public class ManageController {
        public static Pane createManagePane (ManageModel manageModel) {
        Pane pane = new Pane();
        pane.setPrefSize(216, 300);
        pane.setStyle("-fx-background-color: white; " +
                "-fx-border-color: white; " +
                "-fx-border-radius: 10px; " +
                "-fx-background-radius: 10px;" +
                "-fx-cursor: hand;");

        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        shadow.setColor(Color.rgb(0, 0, 0, 0.2)); // màu đen nhạt, độ mờ 20%

        pane.setEffect(shadow);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(130);
        imageView.setLayoutX(12);
        imageView.setLayoutY(14);

        try {
            if (manageModel.getImageUrl() != null) {
                Image image = new Image(ProductController.class.getResourceAsStream(manageModel.getImageUrl()));
                if (image.isError()) {
                    throw new Exception("Lỗi tải ảnh: " + manageModel.getImageUrl());
                }
                imageView.setImage(image);
            }
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh: " + e.getMessage());
        }

        GridPane gridPane = new GridPane();
        gridPane.setLayoutX(15);
        gridPane.setLayoutY(180);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Label nameLabel = new Label("Tên sản phẩm:");
        Label brandLabel = new Label("Hãng điện thoại:");
        Label priceLabel = new Label("Giá sản phẩm:");
        Label inventoryLabel = new Label("Tồn kho");
        Label nameValue = new Label(manageModel.getName());
        Label brandValue = new Label(manageModel.getBrand());
        Label priceValue = new Label(manageModel.getPrice());
        Label inventoryValue = new Label(manageModel.getInventory());

        gridPane.addRow(0, nameLabel, nameValue);
        gridPane.addRow(1, brandLabel, brandValue);
        gridPane.addRow(2, priceLabel, priceValue);
        gridPane.addRow(3, inventoryLabel, inventoryValue);

        pane.getChildren().addAll(imageView, gridPane);

        return pane;
    }
}
