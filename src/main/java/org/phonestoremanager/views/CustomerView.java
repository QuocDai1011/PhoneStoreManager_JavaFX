package org.phonestoremanager.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/Customer.fxml")); // Đảm bảo đúng đường dẫn
        Parent root = loader.load(); // Load đúng cách

        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/style.css").toExternalForm()); // Đảm bảo đúng đường dẫn
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
