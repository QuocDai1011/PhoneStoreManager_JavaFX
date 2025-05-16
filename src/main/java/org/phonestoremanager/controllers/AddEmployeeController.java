package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class AddEmployeeController {

    @FXML private TextField txtLastName;
    @FXML private TextField txtFirstName;
    @FXML private ComboBox<String> cbGender;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private TextField txtAddress;
    @FXML private ComboBox<String> cbPosition;
    @FXML private TextField txtSalary;
    @FXML private ComboBox<String> cbRole;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnTogglePassword;
    @FXML private Button btnSave, btnReset, btnCancel;
    @FXML private TextField txtPasswordVisible;             // icon con mắt để đổi giữa ẩn/hiện


    private boolean passwordVisible = false;

    public void initialize() {
        cbGender.getSelectionModel().selectFirst();
        cbPosition.getSelectionModel().select("Nhân viên");
        btnTogglePassword.setOnAction(e -> togglePasswordVisibility());
        btnSave.setOnAction(e -> handleSave());
        btnReset.setOnAction(e -> resetForm());
        btnCancel.setOnAction(e -> btnCancel.getScene().getWindow().hide());
    }

    @FXML
    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Ẩn mật khẩu
            txtPassword.setText(txtPasswordVisible.getText());
            txtPassword.setVisible(true);
            txtPassword.setManaged(true);

            txtPasswordVisible.setVisible(false);
            txtPasswordVisible.setManaged(false);

            btnTogglePassword.setText("👁"); // Biểu tượng hiện mật khẩu
        } else {
            // Hiện mật khẩu
            txtPasswordVisible.setText(txtPassword.getText());
            txtPasswordVisible.setVisible(true);
            txtPasswordVisible.setManaged(true);

            txtPassword.setVisible(false);
            txtPassword.setManaged(false);

            btnTogglePassword.setText("🙈"); // Biểu tượng ẩn mật khẩu
        }
        passwordVisible = !passwordVisible;
    }

    private void handleSave() {
        if (validateForm()) {
            // TODO: Lưu dữ liệu vào database
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Thành công");
            success.setHeaderText(null);
            success.setContentText("Dữ liệu đã được lưu!");
            success.showAndWait();
        }
    }

    private void resetForm() {
        txtLastName.clear();
        txtFirstName.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtAddress.clear();
        txtSalary.clear();
        txtUsername.clear();
        txtPassword.clear();
        cbGender.getSelectionModel().selectFirst();
        cbPosition.getSelectionModel().select("Nhân viên");
        cbRole.getSelectionModel().select("Nhân viên");
    }

    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();

        if (txtLastName.getText().trim().isEmpty()) errors.append("Vui lòng nhập họ.");
        if (txtFirstName.getText().trim().isEmpty()) errors.append("Vui lòng nhập tên.");
        if (!txtEmail.getText().matches("^\\S+@\\S+\\.\\S+$")) errors.append("Email không hợp lệ.");
        if (!txtPhone.getText().matches("^0\\d{9}$")) errors.append("Số điện thoại không hợp lệ. ");
        if (txtSalary.getText().trim().isEmpty() || !txtSalary.getText().matches("\\d+(\\.\\d{1,2})?"))
            errors.append("Lương không hợp lệ. ");
        if (txtUsername.getText().trim().isEmpty()) errors.append("Vui lòng nhập username. ");
        if (!txtPassword.getText().matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$"))
            errors.append("Mật khẩu phải ít nhất 8 ký tự, gồm chữ và số. ");

        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi nhập liệu");
            alert.setHeaderText("Vui lòng kiểm tra lại:");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
