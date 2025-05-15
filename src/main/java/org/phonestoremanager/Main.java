package org.phonestoremanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/phonestoremanager/viewsfxml/Welcome.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/phonestoremanager/viewsfxml/AddEmployeeForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/add_employee_style.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}