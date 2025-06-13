package org.phonestoremanager.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class updateOrdersView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/UpdateOrders.fxml"));

        Parent root = loader.load(); // Load đúng cách


        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/UpdateOrders.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
