package org.phonestoremanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.phonestoremanager.repositories.AccountRepository;
import org.phonestoremanager.repositories.CustomerRepository;
import org.phonestoremanager.models.AccountModel;
import org.phonestoremanager.models.CustomerModel;
import org.phonestoremanager.services.AccountService;
import org.phonestoremanager.services.CustomerService;

import java.io.IOException;

public class SignUpForCustomerController {

    @FXML
    private TextField firstNameText, lastNameText, addressText, phoneNumberText;
    @FXML
    private TextField emailText, usernameText, passwordText, confirmPasswordText;

    public void submit () {
        //get cac gia tri tu TextField
        String firstNameValaue = firstNameText.getText();
        String lastNameValue = lastNameText.getText();
        String addressValue = addressText.getText();
        String phoneNumberValue = phoneNumberText.getText();
        String emailValue = emailText.getText();
        String usernameValue = usernameText.getText();
        String passwordValue = passwordText.getText();
        String confirmPasswordValue = confirmPasswordText.getText();

        //kiem tra username da ton tai hay chưa
        if(AccountRepository.checkUsername(usernameValue)) {
            showAlert("ERROR", "Tên đăng nhập đã tồn tại!", Alert.AlertType.ERROR);
            return;
        }

        //kiem tra username va password khong duoc de trong
        if(usernameValue.isEmpty() && passwordValue.isEmpty() && confirmPasswordValue.isEmpty()) {
            showAlert("ERROR", "Tên đăng nhập và mật khẩu trống!", Alert.AlertType.ERROR);
            return;
        }else if(usernameValue.isEmpty()) {
            showAlert("EROR", "Bạn chưa nhập tên đăng nhập", Alert.AlertType.ERROR);
            return;
        }else if(passwordValue.isEmpty()) {
            showAlert("ERROR", "Bạn chưa nhập mật khẩu", Alert.AlertType.ERROR);
            return;
        }else if (confirmPasswordValue.isEmpty()) {
            showAlert("ERROR", "Xác nhận mật khẩu chưa được nhập", Alert.AlertType.ERROR);
            return;
        }

        //tao doi tuong account
        AccountService accountService = new AccountService();
        AccountModel newAccount = new AccountModel();
        String messageAccount = accountService.createNewAccount("KH", usernameValue, passwordValue,
                confirmPasswordValue, newAccount);

        if(!messageAccount.equals("success")) {
            showAlert("ERROR", "Lỗi! Không thể tạo tài khoản.", Alert.AlertType.ERROR);
            return;
        }


        //tao doi tuong customer
        String resultCreateNewCustomer = "";
        CustomerModel newCustomer = new CustomerModel();
        CustomerService customerService = new CustomerService();
        resultCreateNewCustomer = customerService.createNewCustomer(firstNameValaue, lastNameValue, addressValue, phoneNumberValue,
                emailValue, newCustomer);
        if(!resultCreateNewCustomer.equals("success")) {
            showAlert("ERROR", "Lỗi! Không thể thêm dữ liệu khách hàng.", Alert.AlertType.ERROR);
            return;
        }

        int rowAccount = 0, rowCustomer = 0;
        try {
            rowAccount = AccountRepository.insert(newAccount);
            rowCustomer = CustomerRepository.insert(newCustomer, usernameValue);
            if(rowAccount > 0 && rowCustomer > 0) {
                showAlert("INFORMATION", "Tạo tài khoản thành công!\n Thêm dữ liệu khách hàng thành công!", Alert.AlertType.INFORMATION);
            }else {
                showAlert("ERROR", "Lỗi trong quá trình thêm dữ liệu vào database!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("ERROR", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // Không có tiêu đề phụ
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void submitByKeyEnter(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            submit();
        }
    }

    public void logInPress(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/LogIn.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/LogIn.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Đăng nhập!");
        stage.show();
    }
}
