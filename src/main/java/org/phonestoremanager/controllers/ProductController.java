package org.phonestoremanager.controllers;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.phonestoremanager.models.ProductViewModel;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductController {
    public static Pane createProductPane (ProductViewModel productViewModel) {
        Pane pane = new Pane();
        pane.setPrefSize(230, 310);
        pane.setStyle("-fx-background-color: white; " +
                "-fx-border-color: white; " +
                "-fx-border-radius: 10px; " +
                "-fx-background-radius: 10px;" +
                "-fx-cursor: hand;");

        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));

        pane.setEffect(shadow);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setLayoutX(12);
        imageView.setLayoutY(14);

        try {
            if (productViewModel.getImage() != null) {
                String imagePath = "/org/phonestoremanager/assets/image/" + productViewModel.getImage();
                Image image = new Image(ProductController.class.getResourceAsStream(imagePath));
                if (image.isError()) {
                    throw new Exception("Lỗi tải ảnh: " + productViewModel.getImage());
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
        nameLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555; -fx-font-weight: bold;");

        Label priceLabel = new Label("Giá sản phẩm:");
        priceLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555; -fx-font-weight: bold;");

        Label nameValue = new Label(productViewModel.getName());
        nameValue.setStyle("-fx-font-size: 14px; -fx-text-fill: #222;");
        nameValue.setWrapText(true);
        nameValue.setMaxWidth(120);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        Label priceValue = new Label(currencyFormat.format(productViewModel.getPrice()));
        priceValue.setStyle("-fx-font-size: 14px; -fx-text-fill: #e53935; -fx-font-weight: bold;");

        gridPane.addRow(0, nameLabel, nameValue);
        gridPane.addRow(1, priceLabel, priceValue);

        pane.getChildren().addAll(imageView, gridPane);

        return pane;
    }
}
