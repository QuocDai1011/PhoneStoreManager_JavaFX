package org.uiemployee.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.uiemployee.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private Parent root;
    private Scene scene;
    private Stage stage;

    // Chuyển đổi giữa các cửa số menu
    public void home (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/org/uiemployee/home-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void employee (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/org/uiemployee/employee-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void manage (ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/org/uiemployee/manage-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void order (ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/org/uiemployee/order-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private FlowPane productContainer;

    public void initialize() {
        if (productContainer == null)
            return;

        List<Product> products = new ArrayList<>();
        products.add(new Product("iPhone 15", "Apple", "30,000,000 VNĐ", "/img/iphone15.png"));
        products.add(new Product("Samsung S23", "Samsung", "22,000,000 VNĐ", "/img/samsung_s23.png"));
        products.add(new Product("Xiaomi 13", "Xiaomi", "12,000,000 VNĐ", "/img/xiaomi_13.png"));
        products.add(new Product("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/img/iphone15.png"));
        products.add(new Product("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/img/iphone15.png"));
        products.add(new Product("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/img/iphone15.png"));

        productContainer.setHgap(10);
        productContainer.setVgap(10);

        for (Product product : products) {
            productContainer.getChildren().add(productController.createProductPane(product));
        }
    }

}