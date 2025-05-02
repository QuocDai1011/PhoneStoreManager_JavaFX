module org.phonestoremanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens org.phonestoremanager to javafx.fxml;
    exports org.phonestoremanager;

    opens org.phonestoremanager.views to javafx.graphics;
    opens org.phonestoremanager.controllers to javafx.fxml;
    opens org.phonestoremanager.models to javafx.base;
}