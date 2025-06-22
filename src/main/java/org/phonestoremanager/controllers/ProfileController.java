package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.phonestoremanager.models.AccountModel;
import org.phonestoremanager.repositories.EmployeeRepository;

public class ProfileController {
    @FXML private Label accountIdLabel, addressLabel, createAtLabel, dobLabel, emailLabel, employeeIdLabel,
            genderLabel, noteLabel, phoneLabel, positionLabel, salaryLabel,
            startDateLabel, updateAtLabel, firstNameLabel, lastNameLabel;
    @FXML private TextField firstNameField, lastNameField, emailField, phoneField, addressField, noteField;
    @FXML private ImageView avatarImageView, editIcon, tickIcon, cancelIcon;

    private String originalFirstName;
    private String originalLastName;
    private String originalEmail;
    private String originalPhone;
    private String originalAddress;
    private String originalNote;

    private AccountModel accountModel;

    @FXML
    public void initialize() {
        editIcon.setImage(new Image(getClass().getResource("/org/phonestoremanager/assets/image/edit-icon.png").toExternalForm()));
        editIcon.setOnMouseClicked(mouseEvent -> editProfile());
    }

    public void editProfile() {
        originalFirstName = firstNameLabel.getText();
        originalLastName = lastNameLabel.getText();
        originalEmail = emailLabel.getText();
        originalPhone = phoneLabel.getText();
        originalAddress = addressLabel.getText();
        originalNote = noteLabel.getText();

        firstNameField.setText(firstNameLabel.getText());
        firstNameLabel.setVisible(false);
        firstNameField.setVisible(true);

        lastNameField.setText(lastNameLabel.getText());
        lastNameLabel.setVisible(false);
        lastNameField.setVisible(true);

        emailField.setText(emailLabel.getText());
        emailLabel.setVisible(false);
        emailField.setVisible(true);

        phoneField.setText(phoneLabel.getText());
        phoneLabel.setVisible(false);
        phoneField.setVisible(true);

        addressField.setText(addressLabel.getText());
        addressLabel.setVisible(false);
        addressField.setVisible(true);

        noteField.setText(noteLabel.getText());
        noteLabel.setVisible(false);
        noteField.setVisible(true);

        tickIcon.setImage(new Image(getClass().getResource("/org/phonestoremanager/assets/image/tick-icon.png").toExternalForm()));
        tickIcon.setVisible(true);
        tickIcon.setOnMouseClicked(mouseEvent -> saveProfile());

        cancelIcon.setImage(new Image(getClass().getResource("/org/phonestoremanager/assets/image/cancelIcon.png").toExternalForm()));
        cancelIcon.setVisible(true);
        cancelIcon.setOnMouseClicked(mouseEvent -> cancelEdit());
    }

    public void saveProfile() {
        // Nếu người dùng bỏ trống, giữ lại giá trị gốc
        String newFirstName = firstNameField.getText().isEmpty() ? originalFirstName : firstNameField.getText();
        String newLastName  = lastNameField.getText().isEmpty() ? originalLastName  : lastNameField.getText();
        String newEmail     = emailField.getText().isEmpty()     ? originalEmail     : emailField.getText();
        String newPhone     = phoneField.getText().isEmpty()     ? originalPhone     : phoneField.getText();
        String newAddress   = addressField.getText().isEmpty()   ? originalAddress   : addressField.getText();
        String newNote      = noteField.getText().isEmpty()      ? originalNote      : noteField.getText();

        // Cập nhật vào cơ sở dữ liệu
        EmployeeRepository.updateEmployeeProfile(
                accountModel.getAccountID(),
                newFirstName,
                newLastName,
                newEmail,
                newPhone,
                newAddress,
                newNote
        );

        firstNameLabel.setText(firstNameField.getText());
        firstNameField.setVisible(false);
        firstNameLabel.setVisible(true);

        lastNameLabel.setText(lastNameField.getText());
        lastNameField.setVisible(false);
        lastNameLabel.setVisible(true);

        emailLabel.setText(emailField.getText());
        emailField.setVisible(false);
        emailLabel.setVisible(true);

        phoneLabel.setText(phoneField.getText());
        phoneField.setVisible(false);
        phoneLabel.setVisible(true);

        addressLabel.setText(addressField.getText());
        addressField.setVisible(false);
        addressLabel.setVisible(true);

        noteLabel.setText(noteField.getText());
        noteField.setVisible(false);
        noteLabel.setVisible(true);

        tickIcon.setVisible(false);
        cancelIcon.setVisible(false);
    }

    public void cancelEdit() {
        firstNameField.setVisible(false);
        firstNameLabel.setText(originalFirstName);
        firstNameLabel.setVisible(true);

        lastNameField.setVisible(false);
        lastNameLabel.setText(originalLastName);
        lastNameLabel.setVisible(true);

        emailField.setVisible(false);
        emailLabel.setText(originalEmail);
        emailLabel.setVisible(true);

        phoneField.setVisible(false);
        phoneLabel.setText(originalPhone);
        phoneLabel.setVisible(true);

        addressField.setVisible(false);
        addressLabel.setText(originalAddress);
        addressLabel.setVisible(true);

        noteField.setVisible(false);
        noteLabel.setText(originalNote);
        noteLabel.setVisible(true);

        tickIcon.setVisible(false);
        cancelIcon.setVisible(false);
    }

    public void showProfile(AccountModel accountModel) {
        if (accountModel == null) return;
        var employee = EmployeeRepository.getEmployeeProfileByAccountID(accountModel.getAccountID());
        if (employee == null) return;

        accountIdLabel.setText(String.valueOf(employee.getAccountID()));
        employeeIdLabel.setText(String.valueOf(employee.getEmployeeID()));
        firstNameLabel.setText(employee.getFirstName());
        lastNameLabel.setText(employee.getLastName());
        genderLabel.setText(employee.getGender() == 1 ? "Nam" : "Nữ");
        emailLabel.setText(employee.getEmail());
        phoneLabel.setText(employee.getPhoneNumber());
        addressLabel.setText(employee.getAddress());
        positionLabel.setText(employee.getPosition());
        salaryLabel.setText(String.format("%.0f VNĐ", employee.getSalary()));

        dobLabel.setText(employee.getDateOfBirth() != null ? employee.getDateOfBirth().toString() : "");
        startDateLabel.setText(employee.getStartDate() != null ? employee.getStartDate().toString() : "");
        createAtLabel.setText(employee.getCreateAt() != null ? employee.getCreateAt().toString() : "");
        updateAtLabel.setText(employee.getUpdateAt() != null ? employee.getUpdateAt().toString() : "");

        noteLabel.setText(employee.getNote() != null ? employee.getNote() : "");
        avatarImageView.setImage(new Image(getClass().getResource("/org/phonestoremanager/assets/image/user_icon.png").toExternalForm()));
    }

    public void setAccount(AccountModel accountModel) {
        this.accountModel = accountModel;
        System.out.println("Tài khoản hiện tại là: " + accountModel.getAccountID());
        showProfile(accountModel);
    }
}
