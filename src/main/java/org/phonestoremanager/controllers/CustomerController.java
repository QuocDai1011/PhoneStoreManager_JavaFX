package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerController {
    @FXML
    private TableView<?> tableView;
    @FXML
    private TableColumn<?, ?> colMaKH;
    @FXML
    private TableColumn<?, ?> colHo;
    @FXML
    private TableColumn<?, ?> colTen;
    @FXML
    private TableColumn<?, ?>colSDT;
    @FXML
    private TableColumn<?, ?> colDiaChi;
    @FXML
    private TableColumn<?, ?> colDT;
    @FXML
    private TableColumn<?, ?> colGiaTien;
    @FXML
    private TableColumn<?, ?> colNgayM;
    @FXML
    private TextField txtMaKH;
    @FXML
    private TextField txtHo;
    @FXML
    private TextField txtTen;
    @FXML
    private TextField txtSDT;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private TextField txtDT;
    @FXML
    private TextField txtGiaTien;
    @FXML
    private DatePicker dpNgayM;

    @FXML
    private void handleThem() {
        // Handle the "Thêm" button action
    }
    @FXML
    private void handleSua() {
        // Handle the "Sửa" button action
    }
    @FXML
    private void handleXoa() {
        // Handle the "Xóa" button action
    }
    @FXML
    private void handleXoaTrang() {
        // Handle the "Xóa Trắng" button action
    }
    @FXML
    private void handleQuayLai() {
        // Handle the "Quay Lại" button action
    }
}
