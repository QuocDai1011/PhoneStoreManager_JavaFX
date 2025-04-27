package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import org.phonestoremanager.daos.ProductViewDAO;
import org.phonestoremanager.models.ProductViewModel;

import java.util.List;

public class ProductContentController {

    @FXML
    private FlowPane productContainer;

    @FXML
    public void initialize() {
        if (productContainer == null) {
            System.err.println("Error: productContainer is null");
        } else {
            loadHomeView();
        }
    }


    public void loadHomeView() {
        List<ProductViewModel> productViewModels = ProductViewDAO.getInstance().selectAll();

        productContainer.setVgap(30);
        productContainer.setHgap(20);

        for (ProductViewModel productViewModel : productViewModels)
            productContainer.getChildren().add(ProductController.createProductPane(productViewModel));
    }
}