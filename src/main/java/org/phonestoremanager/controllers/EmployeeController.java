package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EmployeeController {
    @FXML
    private TableView<?> tableView;
    @FXML
    private TableColumn<?, ?> colSTT;
    @FXML
    private TableColumn<?, ?> colMaNV;
    @FXML
    private TableColumn<?, ?> colTenNV;
    @FXML
    private TableColumn<?, ?> colNgaySinh;
    @FXML
    private TableColumn<?, ?> colNgayVL;
    @FXML
    private TableColumn<?, ?> colChucVu;
    @FXML
    private TableColumn<?, ?> colDiaChi;
    @FXML
    private TableColumn<?, ?> colGioiTinh;
    @FXML
    private TableColumn<?, ?> colSoDT;
    @FXML
    private TableColumn<?, ?> colGhiChu;
    @FXML
    private TextField txtMaNV;
    @FXML
    private TextField txtTenNV;
    @FXML
    private DatePicker dpNgaySinh;
    @FXML
    private ToggleGroup genderGroup; // Ensure this declaration is present
    @FXML
    private RadioButton rbNam;
    @FXML
    private RadioButton rbNu;
    @FXML
    private DatePicker dpNgayVaoLam;
    @FXML
    private ComboBox<?> cbChucVu;
    @FXML
    private TextField txtSoDT;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private TextArea txtGhiChu;

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
<<<<<<< Updated upstream
=======
    @FXML
    private void handleQuayLai() {
        // Handle the "Quay Lại" button action
    }
>>>>>>> Stashed changes

}
