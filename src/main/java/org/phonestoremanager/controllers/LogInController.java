package org.phonestoremanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.phonestoremanager.daos.AccountDAO;
import org.phonestoremanager.daos.RoleDAO;
import org.phonestoremanager.services.AccountService;
import org.phonestoremanager.utils.PasswordEncrypt;
import org.phonestoremanager.views.SignUpForCustomerView;

import java.io.IOException;
import java.sql.SQLException;

public class LogInController {
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordTextField;

    public void checkAccount() throws SQLException {
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();

        //kiem tra username va password khong duoc de trong
        if(userName.isEmpty() && password.isEmpty()) {
            showAlert("ERROR", "Tên đăng nhập và mật khẩu không được để trống!", Alert.AlertType.ERROR);
            return;
        }else if(userName.isEmpty()) {
            showAlert("ERROR", "Chưa nhập tên đăng nhập", Alert.AlertType.ERROR);
            return;
        }else if(password.isEmpty()) {
            showAlert("ERROR", "Chưa nhập mật khẩu", Alert.AlertType.ERROR);
            return;
        }

        AccountService accountService = new AccountService();
        boolean result = accountService.checkAccountWhenLogIn(userName, password);
        if(result) {
            int roleId = AccountDAO.getRoleIDByUsername(userName);
            String role = RoleDAO.getRoleNameByRoleID(roleId);
            showAlert("INFORMATION", "Đăng nhập thành công! " + role, Alert.AlertType.INFORMATION);
        }
        else {
            showAlert("ERROR", "Tên đăng nhập hoặc mật khẩu không đúng!", Alert.AlertType.ERROR);
        }
    }


    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // Không có tiêu đề phụ
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void checkAccountOnEnter(KeyEvent event) throws SQLException {
        if(event.getCode() == KeyCode.ENTER) {
            String userName = userNameTextField.getText();
            String password = passwordTextField.getText();

            //kiem tra username va password khong duoc de trong
            if(userName.isEmpty() && password.isEmpty()) {
                showAlert("ERROR", "Tên đăng nhập và mật khẩu không được để trống!", Alert.AlertType.ERROR);
                return;
            }else if(userName.isEmpty()) {
                showAlert("ERROR", "Chưa nhập tên đăng nhập", Alert.AlertType.ERROR);
                return;
            }else if(password.isEmpty()) {
                showAlert("ERROR", "Chưa nhập mật khẩu", Alert.AlertType.ERROR);
                return;
            }

            AccountService accountService = new AccountService();
            boolean result = accountService.checkAccountWhenLogIn(userName, password);
            if(result) {
                int roleId = AccountDAO.getRoleIDByUsername(userName);
                String role = RoleDAO.getRoleNameByRoleID(roleId);
                showAlert("INFORMATION", "Đăng nhập thành công! " + role, Alert.AlertType.INFORMATION);
            }
            else {
                showAlert("ERROR", "Tên đăng nhập hoặc mật khẩu không đúng!", Alert.AlertType.ERROR);
            }
        }
    }

    public void signUpPress (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/SignUpForCustomer.fxml"));
        Parent root = loader.load();

        Stage signUpStage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/SignUpForCustomer.css").toExternalForm());
        signUpStage.setScene(scene);
        signUpStage.setTitle("Sign Up");
        signUpStage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }


}
