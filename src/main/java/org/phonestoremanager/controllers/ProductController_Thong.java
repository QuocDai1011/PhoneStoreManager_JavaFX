package org.phonestoremanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ProductController_Thong {
    @FXML
    private TableView<?> tableView;
    @FXML
    private Label someLabel; // Example label, replace with actual label names
    @FXML
    private TableColumn<?, ?> colSTT;
    @FXML
    private TableColumn<?, ?> colHa;
    @FXML
    private TableColumn<?, ?> colMaDT;
    @FXML
    private TableColumn<?, ?> colTenDT;
    @FXML
    private TableColumn<?, ?> colHangDT;
    @FXML
    private TableColumn<?, ?> colGiaDT;
    @FXML
    private TableColumn<?, ?> colSoLH;
    @FXML
    private ImageView imgHa; // ImageView để hiển thị ảnh

    @FXML
    private TextField txtMaDT;
    @FXML
    private TextField txtTenDT;
    @FXML
    private TextField txtHangDT;
    @FXML
    private TextField txtGiaDT;
    @FXML
    private TextField txtSoLH;

    @FXML
    private void handleThem() {
        // Handle the "Thêm" button action
        String maDT = txtMaDT.getText();
        String tenDT = txtTenDT.getText();
        String hangDT = txtHangDT.getText();
        String giaDT = txtGiaDT.getText();
        String soLH = txtSoLH.getText();

        // Thêm thông tin sản phẩm vào database (cập nhật theo nhu cầu của bạn)
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
    @FXML
    private void handleChonHinh(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(imgHa.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Đường dẫn thư mục images trong project
                Path imageDir = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "org", "phonestoremanager", "assets", "image");

                // Tạo thư mục nếu chưa tồn tại
                if (!Files.exists(imageDir)) {
                    Files.createDirectories(imageDir);
                }

                // Lấy tên file
                String fileName = selectedFile.getName();
                Path destination = imageDir.resolve(fileName);

                // Sao chép nếu file chưa tồn tại
                if (!Files.exists(destination)) {
                    Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Ảnh đã được sao chép vào: " + destination.toAbsolutePath());
                }

                // Hiển thị ảnh trong ImageView
                Image image = new Image(destination.toUri().toString());
                imgHa.setImage(image);
                imgHa.setFitWidth(132);
                imgHa.setFitHeight(151);
                imgHa.setPreserveRatio(true);
                imgHa.setSmooth(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
