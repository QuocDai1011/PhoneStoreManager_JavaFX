package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.phonestoremanager.models.BrandModel;
import org.phonestoremanager.repositories.BrandRepository;
import org.phonestoremanager.repositories.ProductDetailRepository;
import org.phonestoremanager.repositories.ProductRepository;
import org.phonestoremanager.utils.InputValidator;

import java.util.ArrayList;


public class AddProductController {
    @FXML
    private ComboBox<BrandModel> comboBrand;

    @FXML
    private Button btnAddProduct;

    @FXML
    private TextField txtProductID;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextArea txtDescription;

    InputValidator inputValidator = new InputValidator();

    @FXML
    public void initialize() {
        loadBrands();

        inputValidator.applyLetterNumberFilter(txtProductName);

        int newProductID = ProductRepository.getInstance().getMaxProductID() + 1;
        txtProductID.setText(String.valueOf(newProductID));
        txtProductID.setEditable(false);
        txtProductID.setFocusTraversable(false);

        if (btnAddProduct != null) {
            btnAddProduct.setOnAction(event -> {
                BrandModel selectedBrand = comboBrand.getSelectionModel().getSelectedItem();
                if (selectedBrand == null) {
                    System.out.println("Vui lòng chọn hãng (Brand)");
                    return;
                }

                String name = txtProductName.getText();
                if (name.trim().isEmpty()) {
                    System.out.println("Vui lòng nhập tên sản phẩm!!!");
                    return;
                }

                String description = txtDescription.getText();

                int productID = Integer.parseInt(txtProductID.getText());

                System.out.println("Hiển bị bản add product detail");
                ProductRepository.getInstance().insertProduct(productID, selectedBrand, name, description);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/add-product-detail-view.fxml"));
                    Parent root = loader.load();

                    AddProductDetailController controller = loader.getController();
                    controller.setProductInfo(productID, name, selectedBrand, description);

                    Stage stage = new Stage();
                    stage.setTitle("Phân Loạt Sản Phẩm");
                    stage.setScene(new Scene(root));
                    stage.setOnCloseRequest((WindowEvent e) -> {
                        System.out.println("Cửa sổ đang được đóng!");
                        if(ProductDetailRepository.getInstance().isEmptyProductDetail(productID)) {
                            ProductDetailRepository.getInstance().deleteProduct(productID);
                        }
                    });
                    stage.show();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } else
            System.out.println("Cái nút có bị null má ơi");
    }

    private void loadBrands() {
        ArrayList<BrandModel> brandModelArrayList = BrandRepository.getInstance().selectAll();
        comboBrand.getItems().addAll(brandModelArrayList);
    }
}
