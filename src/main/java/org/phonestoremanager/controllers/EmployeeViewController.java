package org.phonestoremanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.phonestoremanager.repositories.EmployeeRepository;
import org.phonestoremanager.models.EmployeeModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {
    @FXML
    TableView<EmployeeModel> tableView;

    @FXML
    TableColumn<EmployeeModel, Integer> columnID;

    @FXML
    TableColumn<EmployeeModel, String> columnFirstname;

    @FXML
    TableColumn<EmployeeModel, String> columnLastname;

    @FXML
    TableColumn<EmployeeModel, String> columnGender;

    @FXML
    TableColumn<EmployeeModel, String> columnEmail;

    @FXML
    TableColumn<EmployeeModel, String> columnPhonenumber;

    @FXML
    TableColumn<EmployeeModel, String> columnAddress;

    @FXML
    TableColumn<EmployeeModel, String> columnPosition;

    @FXML
    TableColumn<EmployeeModel, Double> columnSalary;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        columnFirstname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnLastname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("genderText"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPhonenumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        render(EmployeeRepository.getAll());
    }

    public void render(List<EmployeeModel> list) {
        ObservableList<EmployeeModel> listObser = FXCollections.observableArrayList();
        if (list != null) {
            listObser.addAll(list);
        }else {
            System.out.println("Khong the lay du lieu nhan vien");
            return;
        }
        tableView.setItems(listObser);
    }
}
