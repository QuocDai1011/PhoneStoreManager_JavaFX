package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.phonestoremanager.repositories.ProductViewRepository;
import org.phonestoremanager.models.ProductViewModel;

import java.io.IOException;
import java.util.List;

public class ProductContentController {
    @FXML
    private MenuButton filterMenuButton;

    @FXML
    private FlowPane productContainer;

    @FXML
    private javafx.scene.control.TextField searchField;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button fixedButton;


    @FXML
    public void initialize() {
        if (productContainer == null) {
            System.err.println("Error: productContainer is null");
        } else {
            loadHomeView();  // load ban đầu
        }

        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                performSearch(newValue.trim());
            });
        } else {
            System.err.println("Error: searchField is null");
        }

        for (MenuItem menuItem : filterMenuButton.getItems()) {
            menuItem.setOnAction(event -> filterByBrand(menuItem.getText()));
        }

        if (fixedButton != null) {
            fixedButton.toFront(); // Đưa nút lên trên cùng giao diện
            fixedButton.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/add-product-view.fxml"));
                    Parent root = loader.load();

                    // Tạo một Stage mới hoặc sử dụng Stage hiện tại
                    Stage stage = new Stage();
                    stage.setTitle("Thêm Dòng Sản Phẩm Mới");
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    // Bạn có thể dùng Alert để thông báo lỗi
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Không thể mở giao diện Thêm Sản Phẩm");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        } else {
            System.err.println("Nút này nó bị null");
        }

    }

    private void filterByBrand(String brand) {
        // Xóa sản phẩm cũ
        productContainer.getChildren().clear();

        // Lấy danh sách sản phẩm theo nhãn hàng
        List<ProductViewModel> productViewModels = ProductViewRepository.getInstance().selectByBrand(brand);

        // Hiển thị sản phẩm đã lọc
        for (ProductViewModel productViewModel : productViewModels) {
            var productPane = ProductController.createProductPane(productViewModel);

            productPane.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    openProductDetail(productViewModel);
                }
            });

            productContainer.getChildren().add(productPane);
        }
    }


    private void performSearch(String keyword) {
        productContainer.getChildren().clear(); // Xóa sản phẩm cũ

        List<ProductViewModel> productViewModels;

        if (keyword.isEmpty()) {
            productViewModels = ProductViewRepository.getInstance().selectAll();
        } else {
            productViewModels = ProductViewRepository.getInstance().searchByName(keyword);
        }

        for (ProductViewModel productViewModel : productViewModels) {
            var productPane = ProductController.createProductPane(productViewModel);

            productPane.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    openProductDetail(productViewModel);
                }
            });

            productContainer.getChildren().add(productPane);
        }
    }


    private void openProductDetail(ProductViewModel product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/product-details-view.fxml"));
            Parent root = loader.load();

            // Truyền dữ liệu cho controller chi tiết
            ProductDetailsContentController controller = loader.getController();
            controller.setProduct(product);


            // Hiển thị giao diện chi tiết
            Scene currentScene = productContainer.getScene();
            if (currentScene != null) {
                currentScene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/product-detail.css").toExternalForm());
                currentScene.setRoot(root);
            } else {
                System.err.println("Error: Scene is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHomeView() {
        List<ProductViewModel> productViewModels = ProductViewRepository.getInstance().selectAll();

        productContainer.setVgap(30);
        productContainer.setHgap(20);

        for (ProductViewModel productViewModel : productViewModels) {
            // Tạo pane sản phẩm
            var productPane = ProductController.createProductPane(productViewModel);

            // Thêm sự kiện click
            productPane.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    // Nếu click 2 lần -> Mở chi tiết sản phẩm
                    openProductDetail(productViewModel);
                }
            });

            productContainer.getChildren().add(productPane);
        }
    }
}