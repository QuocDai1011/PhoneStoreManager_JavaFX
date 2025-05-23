package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.phonestoremanager.exeptions.EmailValidation;
import org.phonestoremanager.exeptions.PasswordValidation;
import org.phonestoremanager.exeptions.PhoneNumberValidation;
import org.phonestoremanager.exeptions.StringValidation;
import org.phonestoremanager.models.AccountModel;
import org.phonestoremanager.models.EmployeeModel;
import org.phonestoremanager.repositories.AccountRepository;
import org.phonestoremanager.repositories.EmployeeRepository;
import org.phonestoremanager.services.AccountService;
import org.phonestoremanager.services.EmployeeService;

import java.awt.event.ActionEvent;
import java.util.List;

public class AddEmployeeController {

    @FXML private TextField txtLastName;
    @FXML private TextField txtFirstName;
    @FXML private ComboBox<String> cbGender;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private TextField txtAddress;
    @FXML private ComboBox<String> cbPosition;
    @FXML private TextField txtSalary;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnTogglePassword;
    @FXML private Button btnSave, btnReset, btnCancel;
    @FXML private TextField txtPasswordVisible;             // icon con mắt để đổi giữa ẩn/hiện

    private boolean passwordVisible = false;
    private boolean isEditMode = false; //false là create, true là update

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
        if(isEditMode) {
            String resultAccount;
            AccountService accountService = new AccountService();
            AccountModel accountModel = new AccountModel();
            resultAccount = accountService.createNewAccount(
                    cbPosition.getValue(), txtUsername.getText(),
                    txtPassword.getText(), txtPassword.getText(), accountModel
            );

            String resultEmmployee;
            EmployeeService employeeService = new EmployeeService();
            EmployeeModel employeeModel = new EmployeeModel();
            resultEmmployee = employeeService.createNewEmployee(
                    txtFirstName.getText(), txtLastName.getText(),
                    txtEmail.getText(), txtPhone.getText(),
                    txtAddress.getText(), cbPosition.getValue(),
                    cbGender.getValue(), txtSalary.getText(), employeeModel
            );

            int rowAcc = 0, rowEmp = 0;
            if(resultEmmployee.equals("success") && resultAccount.equals("success")){
                rowAcc = AccountRepository.update(accountModel);
                rowEmp = EmployeeRepository.update(employeeModel, txtUsername.getText());
            }

            if(rowAcc != 0 && rowEmp != 0) {
                btnCancel.getScene().getWindow().hide();
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Thành công");
                success.setHeaderText(null);
                success.setContentText("Dữ liệu đã được lưu!");
                success.showAndWait();
            }else {
                Alert success = new Alert(Alert.AlertType.ERROR);
                success.setTitle("Lỗi!");
                success.setHeaderText(null);
                success.setContentText("Dữ liệu cập nhật thất bại!");
                success.showAndWait();
            }
            return;
        }

        if (validateForm()) {
            // TODO: Lưu dữ liệu vào database
            // tạo account model
            String resultAccount;
            AccountService accountService = new AccountService();
            AccountModel accountModel = new AccountModel();
            resultAccount = accountService.createNewAccount(
                    cbPosition.getValue(), txtUsername.getText(),
                    txtPassword.getText(), txtPassword.getText(), accountModel
            );
//            System.out.println("Account: " +resultAccount);

            String resultEmmployee;
            EmployeeService employeeService = new EmployeeService();
            EmployeeModel employeeModel = new EmployeeModel();
            resultEmmployee = employeeService.createNewEmployee(
                    txtFirstName.getText(), txtLastName.getText(),
                    txtEmail.getText(), txtPhone.getText(),
                    txtAddress.getText(), cbPosition.getValue(),
                    cbGender.getValue(), txtSalary.getText(), employeeModel
            );
//            System.out.println("Employee: " + resultEmmployee);

            if(resultEmmployee.equals("success") && resultAccount.equals("success")){
                AccountRepository.insert(accountModel);
                System.out.println("Thêm thành công account nhân viên");
                EmployeeRepository.insert(employeeModel, txtUsername.getText());
                System.out.println("Them thanh cong thong tin nhan vien");
            }

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Thành công");
            success.setHeaderText(null);
            success.setContentText("Dữ liệu đã được lưu!");
            success.showAndWait();
            btnCancel.getScene().getWindow().hide();
        }

        EmployeeViewController employeeViewController = new EmployeeViewController();
        employeeViewController.render(EmployeeRepository.getAll());
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
        txtPasswordVisible.clear();
        cbGender.getSelectionModel().selectFirst();
        cbPosition.getSelectionModel().select("Nhân viên");

    }

    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();

        //check họ và tên đêệm
        if (txtLastName.getText().trim().isEmpty()) {
            errors.append("Vui lòng nhập họ và tên đệm. ");
        }
        try {
            StringValidation.validateString(txtLastName.getText().trim());
        } catch (Exception e) {
            if(errors.isEmpty()) errors.append(e.getMessage());
        }

        //check tên
        if (txtFirstName.getText().trim().isEmpty()) {
            if(errors.isEmpty()){
                errors.append("Vui lòng nhập tên.");
            }
        }
        try {
            StringValidation.validateString(txtFirstName.getText().trim());
        } catch (Exception e) {
            if(errors.isEmpty()) errors.append(e.getMessage());
        }

        //check email
        if (txtEmail.getText().isEmpty()) {
            if(errors.isEmpty()) errors.append("Email không được để trống.");
        }else {
            try {
                EmailValidation.validation(txtEmail.getText().trim());
            }catch (Exception e) {
                if(errors.isEmpty()) errors.append(e.getMessage());
            }
        }

        //check số điện thoại
        try {
            PhoneNumberValidation.validatePhoneNumber(txtPhone.getText());
        } catch (Exception e) {
            if(errors.isEmpty()) errors.append(e.getMessage());
        }

        //check địa chỉ
        if(txtAddress.getText().trim().isEmpty()) {
            if(errors.isEmpty()) errors.append("Địa chỉ đang để trống.");
        }

        //check số lương
        if (txtSalary.getText().trim().isEmpty() || !txtSalary.getText().matches("\\d+(\\.\\d{1,2})?")) {
            if(errors.isEmpty()) errors.append("Lương không hợp lệ. ");
        }

        //check username
        if (txtUsername.getText().trim().isEmpty()) {
            if(errors.isEmpty()) errors.append("Vui lòng nhập username. ");
        }

        //check password
        if(txtPassword.getText().trim().length() < 8) {
            if(errors.isEmpty()) errors.append("Mật khẩu phải ít nhất 8 kí tự, gồm chữ cái và số.");
        }else {
            try {
                PasswordValidation.validate(txtPassword.getText().trim());
            } catch (Exception e) {
                if(errors.isEmpty()) errors.append(e.getMessage());
            }
        }

        if(AccountRepository.checkUsername(txtUsername.getText())) {
            System.out.println("Kết quả check: " + AccountRepository.checkUsername(txtUsername.getText()));
            if(errors.isEmpty()) errors.append("Username đã tồn tại! Vui lòng nhập Username khác.");
        }

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

    public void setEmployeeData(EmployeeModel employee) {
        if(employee == null) {
            Stage stage = (Stage)(btnCancel.getScene().getWindow());
            stage.close();
        }
        assert employee != null;
        List<String> list = EmployeeRepository.getEmployeeIDByInformation(employee.getEmail(), employee.getPhoneNumber(),
                employee.getGenderText(), employee.getSalary());
        isEditMode = true;

        txtLastName.setText(employee.getLastName());
        txtFirstName.setText(employee.getFirstName());
        cbGender.setValue(employee.getGenderText());
        txtEmail.setText(employee.getEmail());
        txtPhone.setText(employee.getPhoneNumber());
        txtAddress.setText(employee.getAddress());
        cbPosition.setValue(employee.getPosition());
        txtSalary.setText(employee.getSalary() + "");
        txtUsername.setText(list.get(0));
        txtUsername.setEditable(false);
        txtPassword.setText(list.get(1));
    }

    public void deleteEmployee() {
        System.out.println(txtUsername.getText());
    }
}
