package org.phonestoremanager.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ProductView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/Product.fxml")); // Ensure correct path
            Parent root = loader.load(); // Load correctly

            Scene scene = new Scene(root);

            scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/style.css").toExternalForm()); // Ensure correct path
<<<<<<< Updated upstream
            primaryStage.setTitle("SignUp for Employee");
=======

>>>>>>> Stashed changes
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}