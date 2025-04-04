module org.uiemployee {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.uiemployee to javafx.fxml;
    exports org.uiemployee;
    exports org.uiemployee.controller;
    opens org.uiemployee.controller to javafx.fxml;
}