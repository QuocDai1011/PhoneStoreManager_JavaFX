package org.phonestoremanager.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.phonestoremanager.models.BrandModel;
import org.phonestoremanager.models.ProductDetailModel;
import org.phonestoremanager.models.ProductViewModel;
import org.phonestoremanager.repositories.BrandRepository;
import org.phonestoremanager.repositories.ColorRepository;
import org.phonestoremanager.repositories.ProductColorRepository;
import org.phonestoremanager.repositories.ProductDetailRepository;
import org.phonestoremanager.utils.CopyFileUtil;
import org.phonestoremanager.utils.InputValidator;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class FixProductDetailController {

    @FXML private Button btnAddProductDetail;
    @FXML private TableColumn<String, String> colorColumn;
    @FXML private TableView<String> listColorName;
    @FXML private TextField txtBattery, txtBrand, txtCamFront, txtCamRear, txtChip, txtColor, txtImage,
            txtPrice, txtProductID, txtProductName, txtRam, txtRom, txtScanFreq, txtScreenParams,
            txtScreenSize, txtScreenTech, txtStock;
    @FXML private TextArea txtDescription;

    private ProductViewModel product;
    private ProductDetailModel productDetail;

    @FXML
    public void initialize() {
        setInputValidator();
        setupColorAutocomplete();
        btnAddProductDetail.setOnAction(event -> {
            updateProductDetail();
//            showAlert("Thêm phân loại sản phẩm thành công!");
//            resetForm();
        });
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

    private void setupColorAutocomplete() {
        colorColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        listColorName.setVisible(false);
        listColorName.setManaged(false);

        txtColor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                listColorName.setVisible(false);
                listColorName.setManaged(false);
            } else {
                loadColorName(newValue.trim());
                listColorName.setVisible(true);
                listColorName.setManaged(true);
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

    private void loadColorName(String keyword) {
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

    private void autoResizeTableViewHeight() {
        int rowCount = listColorName.getItems().size();
        double rowHeight = 26;
        listColorName.setPrefHeight(rowCount * rowHeight);
    }

    private void resetForm() {
        txtRam.clear(); txtRom.clear(); txtChip.clear(); txtBattery.clear();
        txtScreenParams.clear(); txtScreenSize.clear(); txtImage.clear();
        txtCamFront.clear(); txtCamRear.clear(); txtScreenTech.clear();
        txtScanFreq.clear(); txtPrice.clear(); txtStock.clear(); txtColor.clear();
    }

    private void updateProductDetail() {
        if (!validateInputs()) return;

        ProductDetailModel detail = new ProductDetailModel();
        detail.setProductDetailID(productDetail.getProductDetailID());
        try {
            detail.setProductID(Integer.parseInt(txtProductID.getText().trim()));
            detail.setRam(Integer.parseInt(txtRam.getText().trim()));
            detail.setRom(Integer.parseInt(txtRom.getText().trim()));
            detail.setChip(txtChip.getText().trim());
            detail.setScreenSize(Float.parseFloat(txtScreenSize.getText().trim()));
            detail.setScreenParameters(txtScreenParams.getText().trim());
            detail.setBatteryCapacity(Float.parseFloat(txtBattery.getText().trim()));
            String imagePath = changePath(); // gọi changePath và lưu kết quả
            if (imagePath == null) {
                showAlert("Không thể sao chép hình ảnh. Vui lòng kiểm tra lại đường dẫn.");
                return;
            }
            detail.setImage(imagePath); // ✅ Đây là giá trị sẽ lưu vào DB: IPhone/iphoneX.jpg
            txtImage.setText(imagePath);
            detail.setDescription(txtDescription.getText().trim());
            detail.setPrice(Double.parseDouble(txtPrice.getText().trim()));
            detail.setStockQuantity(Integer.parseInt(txtStock.getText().trim()));
            detail.setCameraFront(txtCamFront.getText().trim());
            detail.setCameraRear(txtCamRear.getText().trim());
            detail.setScreenTechnology(txtScreenTech.getText().trim());
            detail.setScanFrequency(txtScanFreq.getText().trim());
            detail.setColorID(getSelectedColorID());
        } catch (Exception e) {
            showAlert("Dữ liệu nhập không hợp lệ. Vui lòng kiểm tra lại.");
            return;
        }
        System.out.println(">> Gửi dữ liệu cập nhật cho ProductDetailID = " + detail.getProductDetailID());
        System.out.println(">> Image sẽ lưu là: " + detail.getImage());

        if (productDetail == null) {
            System.out.println("❌ productDetail đang null! Không thể cập nhật.");
            showAlert("Không thể cập nhật do thiếu dữ liệu chi tiết sản phẩm.");
            return;
        }
        System.out.println("✅ Đang cập nhật ProductDetailID = " + productDetail.getProductDetailID());

        ProductDetailRepository.getInstance().updateProductDetailByProductID(detail);
    }

    private boolean validateInputs() {
        if (txtRam.getText().isEmpty() || txtRom.getText().isEmpty() || txtChip.getText().isEmpty() ||
                txtBattery.getText().isEmpty() || txtImage.getText().isEmpty() ||
                txtPrice.getText().isEmpty() || txtStock.getText().isEmpty() ||
                txtColor.getText().isEmpty()) {
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

    private int getSelectedColorID() {
        String colorName = txtColor.getText().trim();
        if (!colorName.matches("[\\p{L}\\p{N} ]+")) {
            showAlert("Tên màu không hợp lệ. Vui lòng chỉ nhập chữ, số và khoảng trắng.");
            return -1;
        }
        if (!ColorRepository.getInstance().isColorExists(colorName)) {
            ColorRepository.getInstance().addColor(colorName);
        }
        return ProductColorRepository.getInstance().getColorIDByNameColor(colorName);
    }

    private String formatImagePath() {
        String sourcePath = txtImage.getText().trim();
        String brand = txtBrand.getText().trim();
        String fileName = Paths.get(sourcePath).getFileName().toString();
        String destPath = "target/classes/org/phonestoremanager/assets/image/" + brand + "/" + fileName;

        CopyFileUtil.getInstance().copyImage(sourcePath, destPath);
        return "/org/phonestoremanager/assets/image/" + brand + "/" + fileName;
    }

    public void setProductInfo(int productID, String productName, BrandModel brandName, String productDescription) {
        txtProductID.setText(String.valueOf(productID));
        txtProductID.setFocusTraversable(false);
        txtProductName.setText(productName.trim());
        txtProductName.setFocusTraversable(false);
        txtBrand.setText(String.valueOf(brandName).trim());
        txtBrand.setFocusTraversable(false);
        txtDescription.setText(productDescription.trim());
    }

    private void setInputValidator() {
        InputValidator v = new InputValidator();
        v.applyLetterNumberFilter(txtColor); v.applyLetterNumberFilter(txtBrand);
        v.applyLetterNumberFilter(txtCamFront);
//        v.applyLetterNumberFilter(txtCamRear);
        v.applyLetterNumberFilter(txtChip); v.applyLetterNumberFilter(txtProductName);
        v.applyLetterNumberFilter(txtScanFreq); v.applyLetterNumberFilter(txtScreenParams);
        v.applyLetterNumberFilter(txtScreenTech);

        v.applyIntegerFilter(txtProductID);
        v.applyIntegerFilter(txtRam);
        v.applyIntegerFilter(txtRom);
        v.applyIntegerFilter(txtStock);

//        v.applyDecimalFilter(txtPrice);
        v.applyDecimalFilter(txtBattery);
        v.applyDecimalFilter(txtScreenSize);
    }

    public void setProductDetail(ProductViewModel product, ProductDetailModel detail) {
        this.productDetail = detail;
        this.product = product;
        System.out.println(">> Đã gọi setProductDetail với ProductDetailID = " + detail.getProductDetailID());
        loadDetailToView(product, detail);
    }

    private void loadDetailToView(ProductViewModel product, ProductDetailModel detail) {
        txtProductID.setText(String.valueOf(product.getProductID()));
        txtProductName.setText(product.getName());
        txtBrand.setText(BrandRepository.getInstance().getBrandNameByID(product.getBrandID()));
        txtColor.setText(ColorRepository.getInstance().getColorNameByColorID(detail.getColorID()));
        txtRam.setText(String.valueOf(detail.getRam()));
        txtRom.setText(String.valueOf(detail.getRom()));
        txtChip.setText(detail.getChip());
        txtScreenSize.setText(String.valueOf(detail.getScreenSize()));
        txtScreenParams.setText(detail.getScreenParameters());
        txtScreenTech.setText(detail.getScreenTechnology());
        txtScanFreq.setText(detail.getScanFrequency());
        txtBattery.setText(String.valueOf(detail.getBatteryCapacity()));
        txtCamFront.setText(detail.getCameraFront());
        txtCamRear.setText(detail.getCameraRear());
        txtImage.setText(detail.getImage());
        txtDescription.setText(detail.getDescription());
        txtPrice.setText(String.valueOf(detail.getPrice()));
        txtStock.setText(String.valueOf(detail.getStockQuantity()));
    }

    public String changePath() {
        String sourcePathStr = txtImage.getText().trim();
        String brand = txtBrand.getText().trim();
        String fileName = Paths.get(sourcePathStr).getFileName().toString();

        String targetResourceDir = "target/classes/org/phonestoremanager/assets/image/" + brand + "/";
        String destinationPathStr = targetResourceDir + fileName;

        String success = CopyFileUtil.getInstance().copyImage(sourcePathStr, destinationPathStr);

        if (success != null) {
            return brand + "/" + fileName;  // ✅ Trả về đường dẫn rút gọn để lưu vào DB
        } else {
            return null;
        }
    }


    public String nameImage() {
        String sourcePathStr = txtImage.getText().trim();
        return Paths.get(sourcePathStr).getFileName().toString();
    }
}
