package org.phonestoremanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.phonestoremanager.models.ManageModel;
import org.phonestoremanager.models.ProductModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeController {
    private Parent root;
    private Scene scene;
    private Stage stage;

    // Chuyển đổi giữa các cửa số menu
    public void home (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/home-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void employee (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/employee-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void manage (ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/manage-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void order (ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/order-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private FlowPane productContainer;

    @FXML
    private FlowPane manageContainer;

    @FXML
    public void initialize() {
        if (productContainer != null)
            loadProduct();
        if(manageContainer != null)
            loadManeger();
    }

    private void loadProduct () {
        List<ProductModel> productModels = new ArrayList<>();
        productModels.add(new ProductModel("iPhone 15", "Apple", "30,000,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));
        productModels.add(new ProductModel("Samsung S23", "Samsung", "22,000,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));
        productModels.add(new ProductModel("Xiaomi 13", "Xiaomi", "12,000,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));
        productModels.add(new ProductModel("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));
        productModels.add(new ProductModel("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));
        productModels.add(new ProductModel("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));

        productContainer.setHgap(20);
        productContainer.setVgap(30);

        for (ProductModel productModel : productModels) {
            productContainer.getChildren().add(ProductController.createProductPane(productModel));
        }
    }

    private void loadManeger () {
        List<ManageModel> manageModels = new ArrayList<>();
        manageModels.add(new ManageModel("iPhone 15", "Apple", "30,000,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png", "10"));
        manageModels.add(new ManageModel("Samsung S23", "Samsung", "22,000,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png", "20"));
        manageModels.add(new ManageModel("Xiaomi 13", "Xiaomi", "12,000,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png", "30"));
        manageModels.add(new ManageModel("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png", "40"));
        manageModels.add(new ManageModel("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png", "50"));
        manageModels.add(new ManageModel("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png", "60"));

        manageContainer.setHgap(20);
        manageContainer.setVgap(30);

        for (ManageModel manageModel : manageModels) {
            manageContainer.getChildren().add(ManageController.createManagePane(manageModel));
        }
    }
}