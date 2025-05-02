package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import org.phonestoremanager.models.ManageModel;
import org.phonestoremanager.repositories.ManageRepository;
import org.phonestoremanager.repositories.ProductViewRepository;

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

    private void loadManeger () {
        List<ManageModel> manageModels = ManageRepository.getInstance().selectAll();

        manageContainer.setVgap(30);
        manageContainer.setHgap(20);

        for (ManageModel manageModel : manageModels) {
            manageContainer.getChildren().add(ManageController.createManagerPane(manageModel));
        }
    }
}
