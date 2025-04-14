package org.phonestoremanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuController {

    @FXML
    private Pane most_inner_pane;
    @FXML
    private VBox contenVBox;
    @FXML
    private javafx.scene.control.Button home;
    @FXML
    private javafx.scene.control.Button btn_home10;
    @FXML
    private javafx.scene.control.Button btn_home11;
    @FXML
    private javafx.scene.control.Button btn_home12;

    private javafx.scene.control.Button currentSelectedButton;

    // Phương thức dùng để cập nhật class CSS
    private void setSelectedButton(javafx.scene.control.Button button) {
        if (currentSelectedButton != null) {
            currentSelectedButton.getStyleClass().remove("button-selected");
        }
        currentSelectedButton = button;
        if (!button.getStyleClass().contains("button-selected")) {
            button.getStyleClass().add("button-selected");
        }
    }

    // Cập nhật các hàm xử lý sự kiện menu
    public void home (ActionEvent event) throws IOException {
        setSelectedButton(home);
        Parent homeView = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/home-view.fxml"));
        contenVBox.getChildren().setAll(homeView);
    }

    public void employee (ActionEvent event) throws IOException {
        setSelectedButton(btn_home10);
        Parent employeeView = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/employee-view.fxml"));
        contenVBox.getChildren().setAll(employeeView);
    }

    public void manage (ActionEvent event) throws IOException {
        setSelectedButton(btn_home11);
        Parent manageView = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/manage-view.fxml"));
        contenVBox.getChildren().setAll(manageView);
    }

    public void order (ActionEvent event) throws IOException {
        setSelectedButton(btn_home12);
        Parent orderView = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/order-view.fxml"));
        contenVBox.getChildren().setAll(orderView);
    }


    @FXML
    public void initialize() {
        if (most_inner_pane != null)
            addContextMenuLogOut(most_inner_pane);
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
}