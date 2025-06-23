package org.phonestoremanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.phonestoremanager.models.AccountModel;

import java.io.IOException;
import java.util.Optional;

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
    @FXML
    private Pane avatarPane;
    @FXML
    private ImageView avatar;
    @FXML
    private ImageView helloView;

    private javafx.scene.control.Button currentSelectedButton;

    private AccountModel accountModel;

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
    public void home(ActionEvent event) throws IOException {
        setSelectedButton(home);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/home-view.fxml"));
        Parent homeView = loader.load(); // <-- load() trả về Parent và controller đã được gán
        contenVBox.getChildren().setAll(homeView);
    }

    public void employee(ActionEvent event) throws IOException {
        setSelectedButton(btn_home10);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/employee-view.fxml"));
        Parent employeeView = loader.load();
        contenVBox.getChildren().setAll(employeeView);
    }

    public void manage(ActionEvent event) throws IOException {
        setSelectedButton(btn_home11);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/manage-view.fxml"));
        Parent manageView = loader.load();
        contenVBox.getChildren().setAll(manageView);
    }

    public void order(ActionEvent event) throws IOException {
        setSelectedButton(btn_home12);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/order-view.fxml"));
        Parent orderView = loader.load();
        contenVBox.getChildren().setAll(orderView);
    }

    @FXML
    public void initialize() {
        helloView.setImage(new Image(getClass().getResource("/org/phonestoremanager/assets/image/Hello.png").toExternalForm()));
        avatar.setImage(new Image(getClass().getResource("/org/phonestoremanager/assets/image/user_icon.png").toExternalForm()));
        if (most_inner_pane != null)
            addContextMenuLogOut(most_inner_pane);
        avatarPane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                openProfile();
            }
        });
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

        Profile.setOnAction(event -> openProfile());

        LogOut.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận đăng xuất");
            alert.setHeaderText("Bạn có chắc chắn muốn đăng xuất?");
            alert.setContentText("Hành động này sẽ đưa bạn về màn hình đăng nhập.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // TODO: Chuyển về màn hình đăng nhập hoặc thoát ứng dụng
                System.out.println("Đăng xuất thành công");

                // Ví dụ: load lại giao diện đăng nhập
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/org/phonestoremanager/viewsfxml/Welcome.fxml"));
                    Stage stage = (Stage) pane.getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/Welcome.css").toExternalForm());
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void openProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/profile-view.fxml"));
            Parent root = loader.load();

            ProfileController controller = loader.getController();
            controller.setAccount(accountModel);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setAcount(AccountModel accountModel) {
        this.accountModel = accountModel;
        afterAccountIDSet(); // <-- Gọi hàm xử lý sau khi gán xong
    }

    private void afterAccountIDSet() {
        System.out.println("Tài khoản hiện tại: " + accountModel.getAccountID());

        if (most_inner_pane != null)
            addContextMenuLogOut(most_inner_pane);
    }
}