package org.phonestoremanager.controllers;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.phonestoremanager.models.ProductModel;

public class ProductController {
    public static Pane createProductPane (ProductModel productModel) {
        Pane pane = new Pane();
        pane.setPrefSize(216, 287);
        pane.setStyle("-fx-border-color: black; -fx-border-radius: 10px;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setLayoutX(12);
        imageView.setLayoutY(14);

        try {
            if (productModel.getImageUrl() != null) {
                Image image = new Image(ProductController.class.getResourceAsStream(productModel.getImageUrl()));
                if (image.isError()) {
                    throw new Exception("Lỗi tải ảnh: " + productModel.getImageUrl());
                }
                imageView.setImage(image);
            }
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh: " + e.getMessage());
        }

        GridPane gridPane = new GridPane();
        gridPane.setLayoutX(15);
        gridPane.setLayoutY(200);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Label nameLabel = new Label("Tên sản phẩm:");
        Label brandLabel = new Label("Hãng điện thoại:");
        Label priceLabel = new Label("Giá sản phẩm:");
        Label nameValue = new Label(productModel.getName());
        Label brandValue = new Label(productModel.getBrand());
        Label priceValue = new Label(productModel.getPrice());

        gridPane.addRow(0, nameLabel, nameValue);
        gridPane.addRow(1, brandLabel, brandValue);
        gridPane.addRow(2, priceLabel, priceValue);

        pane.getChildren().addAll(imageView, gridPane);

        return pane;
    }
}
