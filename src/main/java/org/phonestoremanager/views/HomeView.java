package org.phonestoremanager.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/Home.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/ContextMenu.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/menu-button.css").toExternalForm());

        stage.setTitle("Manager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}