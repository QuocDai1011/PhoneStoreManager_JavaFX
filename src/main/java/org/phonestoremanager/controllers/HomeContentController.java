package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import org.phonestoremanager.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class HomeContentController {

    @FXML
    private FlowPane productContainer;

    @FXML
    public void initialize() {
        if (productContainer != null)
            loadHomeView();
    }

    public void loadHomeView() {
        List<ProductModel> productModelList = new ArrayList<>();
        productModelList.add(new ProductModel("iPhone 15", "Apple", "30,000,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));
        productModelList.add(new ProductModel("Samsung S23", "Samsung", "22,000,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));
        productModelList.add(new ProductModel("Xiaomi 13", "Xiaomi", "12,000,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));
        productModelList.add(new ProductModel("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));
        productModelList.add(new ProductModel("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));
        productModelList.add(new ProductModel("Nubia NEO 2", "nubia", "4,900,000 VNĐ", "/org/phonestoremanager/assets/image/iphone15.png"));

        productContainer.setVgap(30);
        productContainer.setHgap(20);

        for (ProductModel productModel : productModelList)
            productContainer.getChildren().add(ProductController.createProductPane(productModel));
    }

}