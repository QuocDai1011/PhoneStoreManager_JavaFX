package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import org.phonestoremanager.models.ManageModel;
import org.phonestoremanager.models.ProductViewModel;
import org.phonestoremanager.repositories.ManageRepository;
import org.phonestoremanager.repositories.ProductViewRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageContentController {

    @FXML
    private FlowPane manageContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField searchField;


    @FXML
    public void initialize() {
        if (manageContainer != null)
            loadManeger();

        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                performSearch(newValue.trim());
            });
        } else {
            System.err.println("Error: searchField is null");
        }
    }

    private void performSearch(String value) {
        manageContainer.getChildren().clear();

        List<ManageModel> manageModels;
        if (value.isEmpty()) {
            manageModels = ManageRepository.getInstance().selectAll();
        } else {
            manageModels = ManageRepository.getInstance().searchByName(value);
        }

        for (ManageModel manageModel : manageModels) {
            var productPane = ManageController.createManagerPane(manageModel);

            manageContainer.getChildren().add(productPane);
        }
    }

    private void openProductDetail(ProductViewModel product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/product-details-view.fxml"));
            Parent root = loader.load();

            ProductDetailsContentController controller = loader.getController();
            controller.setProduct(product);  // vẫn dùng ProductViewModel

            Scene currentScene = manageContainer.getScene(); // dùng manageContainer
            if (currentScene != null) {
                currentScene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/product-detail.css").toExternalForm());
                currentScene.setRoot(root);
            } else {
                System.err.println("Error: Scene is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadManeger() {
        List<ManageModel> manageModels = ManageRepository.getInstance().selectAll();

        manageContainer.setVgap(30);
        manageContainer.setHgap(20);

        for (ManageModel manageModel : manageModels) {
            var managerPane = ManageController.createManagerPane(manageModel);

            managerPane.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    // Lấy ProductViewModel từ ManageModel (giả sử có hàm getProductViewModel)
                    ProductViewModel productView = manageModel.getProductViewModel();
                    openProductDetail(productView);
                }
            });

            manageContainer.getChildren().add(managerPane);
        }
    }
}
