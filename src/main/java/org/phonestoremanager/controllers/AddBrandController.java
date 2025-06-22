package org.phonestoremanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.phonestoremanager.repositories.BrandRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBrandController implements Initializable {
    @FXML
    private TextField txtBrandName;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set Action cho btnCancel
        btnCancel.setOnAction(event -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close(); // Đóng cửa sổ
        });

        //set Action cho btnSave
        btnSave.setOnAction(this::handle);
    }

    private void handle(ActionEvent event) {
        String txtBrandNameValue = txtBrandName.getText();
        if (txtBrandNameValue.isEmpty()) {
            alert(Alert.AlertType.ERROR, "ERROR", "Tên Brand không được để trống!");
        }else {
            // kiem tra xem da co Brand do hay chua
            if(BrandRepository.getInstance().checkBrandNameExist(txtBrandNameValue)) {
                alert(Alert.AlertType.ERROR, "ERROR", "Brand này đã tồn tại!");
                txtBrandName.setText("");
                txtBrandName.requestFocus();
                return;
            }

            int row = BrandRepository.getInstance().insert(txtBrandNameValue);

            // sau khi lưu hoàn tất thì đóng cửa sổ
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close(); // Đóng cửa sổ

            if(row == 0) {
                alert(Alert.AlertType.ERROR, "ERROR", "Thêm Brand thất bại!");
            }else {
                alert(Alert.AlertType.CONFIRMATION, "INFORMATION", "Thêm Brand thành công!");
            }
        }
    }

    private void alert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Không có tiêu đề phụ
        alert.setContentText(content);
        alert.showAndWait();
    }
}
