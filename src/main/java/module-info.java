module org.phonestoremanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.desktop;
    requires itextpdf;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires jdk.httpserver;
    requires json;
    requires java.naming;

    opens org.phonestoremanager to javafx.fxml;
    exports org.phonestoremanager;

    opens org.phonestoremanager.views to javafx.graphics;
    opens org.phonestoremanager.controllers to javafx.fxml;
    opens org.phonestoremanager.models to javafx.base;
    opens org.phonestoremanager.utils to javafx.graphics;
}