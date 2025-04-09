package org.phonestoremanager.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/Home.css").toExternalForm());

        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/ContextMenu.css").toExternalForm());

        stage.setTitle("Employee");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}