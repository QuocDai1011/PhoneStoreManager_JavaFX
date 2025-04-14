package org.phonestoremanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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


    @FXML
    private VBox contenVBox;
    // Chuyển đổi giữa các cửa số menu
    public void home (ActionEvent event) throws IOException {
        Parent homeView = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/home-view.fxml"));
        contenVBox.getChildren().setAll(homeView);
    }

    public void employee (ActionEvent event) throws IOException {
        Parent employeeView = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/employee-view.fxml"));
        contenVBox.getChildren().setAll(employeeView);
    }

    public void manage (ActionEvent event) throws IOException{
        Parent manageView = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/manage-view.fxml"));
        contenVBox.getChildren().setAll(manageView);
    }

    public void order (ActionEvent event) throws IOException{
        Parent orderView = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/order-view.fxml"));
        contenVBox.getChildren().setAll(orderView);
    }

    @FXML
    private FlowPane productContainer;

    @FXML
    private FlowPane manageContainer;

    @FXML
    private Pane most_inner_pane;

    @FXML
    public void initialize() {
        if (most_inner_pane != null)
            addContextMenuLogOut(most_inner_pane);
        if (productContainer != null)
            loadProduct();
        if(manageContainer != null)
            loadManeger();
    }

    private void addContextMenuLogOut (Pane pane) {
        Image profileImage = new Image(getClass().getResource("/org/phonestoremanager/assets/image/user.png").toString());
        Image logoutImage = new Image(getClass().getResource("/org/phonestoremanager/assets/image/logout_icon.png").toString());

        ImageView proifileIcon = new ImageView(profileImage);
        proifileIcon.setFitHeight(16);
        proifileIcon.setFitWidth(16);

        ImageView logoutIcon = new ImageView(logoutImage);
        logoutIcon.setFitWidth(16);
        logoutIcon.setFitHeight(16);


        ContextMenu menu = new ContextMenu();
        menu.getStyleClass().add("context-menu");
        MenuItem Profile = new MenuItem("Profile", proifileIcon);
        MenuItem LogOut = new MenuItem("Log out", logoutIcon);
        Profile.getStyleClass().add("profile-item");
        LogOut.getStyleClass().add("logout-item");

        menu.getItems().addAll(Profile, LogOut);

        pane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                menu.show(pane, e.getScreenX(), e.getScreenY());
            }
        });
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