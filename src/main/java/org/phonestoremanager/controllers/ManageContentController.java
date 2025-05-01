package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import org.phonestoremanager.models.ManageModel;

import java.util.ArrayList;
import java.util.List;

public class ManageContentController {

    @FXML
    private FlowPane manageContainer;

    @FXML
    public void initialize() {
        if (manageContainer != null)
            loadManeger();
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
