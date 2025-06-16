package org.phonestoremanager.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.phonestoremanager.models.BrandModel;
import org.phonestoremanager.models.ProductDetailModel;
import org.phonestoremanager.models.ProductViewModel;
import org.phonestoremanager.repositories.ColorRepository;
import org.phonestoremanager.repositories.ProductColorRepository;
import org.phonestoremanager.repositories.ProductDetailRepository;
import org.phonestoremanager.utils.CopyFileUtil;
import org.phonestoremanager.utils.InputValidator;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class AddProductDetailController {
    @FXML private Button btnAddProductDetail;
    @FXML private TextField txtColor;
    @FXML private TextField txtBattery;
    @FXML private TextField txtBrand;
    @FXML private TextField txtCamFront;
    @FXML private TextField txtCamRear;
    @FXML private TextField txtChip;
    @FXML private TextField txtImage;
    @FXML private TextField txtPrice;
    @FXML private TextField txtProductID;
    @FXML private TextField txtProductName;
    @FXML private TextField txtRam;
    @FXML private TextField txtRom;
    @FXML private TextField txtScanFreq;
    @FXML private TextField txtScreenParams;
    @FXML private TextField txtScreenSize;
    @FXML private TextField txtScreenTech;
    @FXML private TextField txtStock;
    @FXML private TextArea txtDescription;
    @FXML private TableView<String> listColorName;
    @FXML private TableColumn<String, String> colorColumn;

    private ProductViewModel product;


    @FXML
    public void initialize() {
        setInputValidator();
        color(); // xử lý màu sắc khi thêm sản phẩm

        btnAddProductDetail.setOnAction(event -> {
            insertProductDetail();
            showAlert("Thêm phân loại sản phẩm thành công!");
//            resetForm();
        });

        // Xử lý Enter để focus sang TextField tiếp theo
//        setupEnterKeyTraversal();
    }

    @FXML
    private void handleChooseImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh sản phẩm");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(txtImage.getScene().getWindow());
        if (selectedFile != null) {
            txtImage.setText(selectedFile.getAbsolutePath().trim());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void color() {
        colorColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        listColorName.setVisible(false);
        listColorName.setManaged(false);

        txtColor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                listColorName.setVisible(false);
                listColorName.setManaged(false);
            } else {
                listColorName.setVisible(true);
                listColorName.setManaged(true);
                loadColorName(newValue.trim());
            }
        });

        listColorName.setOnMouseClicked(event -> {
            String selected = listColorName.getSelectionModel().getSelectedItem();
            if (selected != null) {
                txtColor.setText(selected.trim());
                listColorName.setVisible(false);
                listColorName.setManaged(false);
            }
        });
    }

    private void resetForm() {
        txtRam.clear();
        txtRom.clear();
        txtChip.clear();
        txtBattery.clear();
        txtScreenParams.clear();
        txtScreenSize.clear();
        txtImage.clear();
        txtCamFront.clear();
        txtCamRear.clear();
        txtScreenTech.clear();
        txtScanFreq.clear();
        txtPrice.clear();
        txtStock.clear();
        txtColor.clear();
    }

    public void loadColorName(String keyword) {
        List<String> colors = ColorRepository.getInstance().getNameColorFromDB();
        List<String> filtered = colors.stream()
                .filter(color -> color.toLowerCase().contains(keyword.toLowerCase()))
                .toList();

        listColorName.getItems().setAll(filtered);
        autoResizeTableViewHeight();

        if (filtered.isEmpty()) {
            listColorName.setVisible(false);
            listColorName.setManaged(false);
        }
    }

    public void autoResizeTableViewHeight() {
        int rowCount = listColorName.getItems().size();
        double rowHeight = 26;
        double headHeight = 0;
        listColorName.setPrefHeight(rowCount * rowHeight + headHeight);
    }

    public void insertProductDetail() {
        ProductDetailModel detail = new ProductDetailModel();

        detail.setProductID(Integer.parseInt(txtProductID.getText().trim()));
        detail.setRam(Integer.parseInt(txtRam.getText().trim()));
        detail.setRom(Integer.parseInt(txtRom.getText().trim()));
        detail.setChip(txtChip.getText().trim());
        try {
            detail.setScreenSize(Float.parseFloat(txtScreenSize.getText().trim()));
        } catch (NumberFormatException e) {
            showAlert("Dữ liệu màng hình nhập sai");
        }
        detail.setScreenParameters(txtScreenParams.getText().trim());
        detail.setBatteryCapacity(Float.parseFloat(txtBattery.getText().trim()));

        changePath();
        txtImage.setText(txtBrand.getText().trim() + "/" + nameImage());

        detail.setImage(txtImage.getText().trim());
        detail.setDescription(txtDescription.getText().trim());
        detail.setPrice(Double.parseDouble(txtPrice.getText().trim()));
        detail.setStockQuantity(Integer.parseInt(txtStock.getText().trim()));
        detail.setCameraFront(txtCamFront.getText().trim());
        detail.setCameraRear(txtCamRear.getText().trim());
        detail.setScreenTechnology(txtScreenTech.getText().trim());
        detail.setScanFrequency(txtScanFreq.getText().trim());
        detail.setColorID(getSelectedColorID());

        if (!validateInputs())
            return;

        ProductDetailRepository.getInstance().insertProductDetailByProductDetailModel(detail);
    }

    public boolean validateInputs() {
        if (txtRam.getText().trim().isEmpty() || txtRom.getText().trim().isEmpty() ||
                txtChip.getText().trim().isEmpty() || txtBattery.getText().trim().isEmpty() ||
                txtImage.getText().trim().isEmpty() || txtPrice.getText().trim().isEmpty() ||
                txtStock.getText().trim().isEmpty() || txtColor.getText().trim().isEmpty()) {

            showAlert("Vui lòng điền đầy đủ thông tin bắt buộc!");
            return false;
        }

        if (Double.parseDouble(txtPrice.getText().trim()) <= 0) {
            showAlert("Giá phải lớn hơn 0");
            return false;
        }
        if (Integer.parseInt(txtStock.getText().trim()) < 0) {
            showAlert("Tồn kho không được âm");
            return false;
        }

        return true;
    }

    public int getSelectedColorID() {
        String colorName = txtColor.getText().trim();

        if (!colorName.matches("[\\p{L}\\p{N} ]+")) {
            showAlert("Tên màu không hợp lệ. Vui lòng chỉ nhập chữ, số và khoảng trắng.");
            return -1;
        }

        // nếu màu người dùng nhập không tồn tại trong database thì thêm nó vào
        if(!ColorRepository.getInstance().isColorExists(colorName)) {
            ColorRepository.getInstance().addColor(colorName);
        }

        return ProductColorRepository.getInstance().getColorIDByNameColor(txtColor.getText().trim());
    }

    public String nameImage() {
        String sourcePathStr = txtImage.getText().trim();
        return Paths.get(sourcePathStr).getFileName().toString();
    }

    public String changePath() {
        String sourcePathStr = txtImage.getText().trim();
        String brand = txtBrand.getText().trim();
        String fileName = Paths.get(sourcePathStr).getFileName().toString();

        String targetResourceDir = "target/classes/org/phonestoremanager/assets/image/" + brand + "/";
        String destinationPathStr = targetResourceDir + fileName;

        String success = CopyFileUtil.getInstance().copyImage(sourcePathStr, destinationPathStr);

        if (success != null) {
            return "/org/phonestoremanager/assets/image/" + brand + "/" + fileName;
        } else {
            return null;
        }
    }

    public void setProductInfo(int productID, String productName, String brandName, String productDescription) {
        txtProductID.setText(String.valueOf(productID));
        txtProductID.setFocusTraversable(false);
        txtProductName.setText(productName.trim());
        txtProductName.setFocusTraversable(false);
        txtBrand.setText(String.valueOf(brandName).trim());
        txtBrand.setFocusTraversable(false);
        txtDescription.setText(productDescription.trim());
    }

    public void setInputValidator() {
        InputValidator inputValidator = new InputValidator();

        inputValidator.applyLetterNumberFilter(txtColor);
        inputValidator.applyLetterNumberFilter(txtBrand);
        inputValidator.applyLetterNumberFilter(txtCamFront);
        inputValidator.applyLetterNumberFilter(txtCamRear);
        inputValidator.applyLetterNumberFilter(txtChip);
        inputValidator.applyLetterNumberFilter(txtProductName);
        inputValidator.applyLetterNumberFilter(txtScanFreq);
        inputValidator.applyLetterNumberFilter(txtScreenParams);
        inputValidator.applyLetterNumberFilter(txtScreenTech);

        inputValidator.applyIntegerFilter(txtProductID);
        inputValidator.applyIntegerFilter(txtRam);
        inputValidator.applyIntegerFilter(txtRom);
        inputValidator.applyIntegerFilter(txtStock);

        inputValidator.applyDecimalFilter(txtPrice);
        inputValidator.applyDecimalFilter(txtBattery);
        inputValidator.applyDecimalFilter(txtScreenSize);
    }

    public void setupEnterKeyTraversal() {
        TextField[] fields = {
                txtColor, txtRam, txtRom, txtChip, txtScreenSize,
                txtScreenParams, txtScreenTech, txtScanFreq,
                txtBattery, txtCamFront, txtCamRear,
                txtImage, txtPrice, txtStock
        };

        for(int i = 0; i < fields.length - 1; i++) {
            final int current = i;
            fields[i].setOnAction(e -> fields[current + 1].requestFocus());
        }

        fields[fields.length - 1].setOnAction(e -> btnAddProductDetail.requestFocus());
    }

    // Dùng để truyền dữ liệu từ stage này sang stage khác
}

