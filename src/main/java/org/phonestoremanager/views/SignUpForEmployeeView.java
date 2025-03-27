package org.phonestoremanager.views;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpForEmployeeView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/SignUpForEmployee.fxml"));
        Parent root = loader.load(); // Load đúng cách

        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/SignUpForEmployeeStyle.css").toExternalForm()); // Đảm bảo đúng đường dẫn
        primaryStage.setTitle("SignUp for Employee");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
