package org.phonestoremanager.views;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.phonestoremanager.Main;

import java.io.IOException;

public class AddBrandView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/phonestoremanager/viewsfxml/add-brand.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Add Brand!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
