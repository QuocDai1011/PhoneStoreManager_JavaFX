package org.phonestoremanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {
    public void goToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/LogIn.fxml"));
        Parent root = loader.load();

        Stage stageLogin = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/LogIn.css").toExternalForm());
        stageLogin.setScene(scene);
        stageLogin.setTitle("Đăng Nhập");
        stageLogin.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void goToSignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/SignUpForCustomer.fxml"));
        Parent root = loader.load();

        Stage stageSignUp = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/SignUpForCustomer.css").toExternalForm());
        stageSignUp.setScene(scene);
        stageSignUp.setTitle("Đăng Ký");
        stageSignUp.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
