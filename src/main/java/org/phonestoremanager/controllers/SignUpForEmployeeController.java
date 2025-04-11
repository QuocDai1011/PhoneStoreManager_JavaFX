package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.phonestoremanager.daos.AccountDAO;
import org.phonestoremanager.daos.EmployeeDAO;
import org.phonestoremanager.daos.RoleDAO;
import org.phonestoremanager.exeptions.PasswordValidation;
import org.phonestoremanager.exeptions.PhoneNumberValidation;
import org.phonestoremanager.exeptions.StringValidation;
import org.phonestoremanager.models.AccountModel;
import org.phonestoremanager.models.EmployeeModel;
import org.phonestoremanager.services.AccountService;
import org.phonestoremanager.services.EmployeeService;

public class SignUpForEmployeeController {

    @FXML
    private TextField firstName, lastName, email, phoneNumber, address, username, salary;

    @FXML
    private PasswordField password, confirmPassword;

    @FXML
    private ToggleGroup gender;
    @FXML
    private ToggleGroup position;


    public void Submit() {


        //lay gia tri nhap vao tu view
        String firstNameValue = firstName.getText();
        String lastNameValue = lastName.getText();
        String emailValue = email.getText();
        String phoneNumberValue = phoneNumber.getText();
        String addressValue = address.getText();
        String usernameValue = username.getText();
        String passwordValue = password.getText();
        String confirmPasswordValue = confirmPassword.getText();
        String salaryValue_raw = salary.getText();
        //gender
        RadioButton radioButton = (RadioButton) gender.getSelectedToggle();
        String genderValue = radioButton.getText();
        //position
        RadioButton radioButton1 = (RadioButton) position.getSelectedToggle();
        String positionValue = radioButton1.getText();


        //check cac ngoai le kiem tra du lieu dau vao
        String message = "";
        //kiem tra ho va ten phai la chu cai
        try {
            StringValidation.validateString(firstNameValue);
            StringValidation.validateString(lastNameValue);
        }catch (IllegalArgumentException e) {
            message = "Họ và tên chỉ chứa các kí tự chữ cái!";
            showAlert("ERROR", message, Alert.AlertType.ERROR);
            return;
        }

        //kiem tra so dien thoai phai la
        try {
            PhoneNumberValidation.validatePhoneNumber(phoneNumberValue);
        }catch (IllegalArgumentException e) {
            message = e.getMessage();
            showAlert("ERROR", message, Alert.AlertType.ERROR);
            return;
        }

        //kiem tra mat khau phai co 1 chu cai va 1 chu so
        try {
            PasswordValidation.validate(passwordValue);
        }catch (IllegalArgumentException e) {
            message = e.getMessage();
            showAlert("ERROR", message, Alert.AlertType.ERROR);
            return;
        }


        //tao doi tuong account
        AccountService accountService = new AccountService();
        AccountModel newAccount = new AccountModel();
        String messageAccount = accountService.createNewAccount(positionValue, usernameValue, passwordValue,
                confirmPasswordValue, newAccount);

        //tao doi tuong employee
        EmployeeService employeeService = new EmployeeService();
        EmployeeModel newEmployee = new EmployeeModel();
        String messageEmployee = employeeService.createNewEmployee(firstNameValue, lastNameValue,
                emailValue, phoneNumberValue, addressValue, positionValue, genderValue, salaryValue_raw, newEmployee);

        //kiem tra co tao doi tuong thanh cong khong
        if(!messageEmployee.equals("success") && !messageAccount.equals("success")) {
            message = "Dữ liệu nhân viên và dữ liệu tạo tài khoản không hợp lệ!";
            showAlert("ERROR", message, Alert.AlertType.ERROR);
        }
        else if(!messageEmployee.equals("success")) {
            message = messageEmployee;
            showAlert("ERROR", message, Alert.AlertType.ERROR);
        }else if(!messageAccount.equals("success")) {
            message = messageAccount;
            showAlert("ERROR", message, Alert.AlertType.ERROR);
        }else {
            int rowAccount = AccountDAO.insert(newAccount);
            int rowEmployee = EmployeeDAO.insert(newEmployee, usernameValue);
            if(rowAccount > 0 && rowEmployee > 0) {
                message = "Tạo tài khoản thành công!\n" + "Thêm dữ liệu nhân viên thành công!";
            }else {
                message = "Lỗi thêm dữ liệu vào cơ sở dữ liệu!";
            }
            showAlert("INFORMATION", message, Alert.AlertType.INFORMATION);
        }
        return;
    }

    public static void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // Không có tiêu đề phụ
        alert.setContentText(message);
        alert.showAndWait();
    }
}
