package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.phonestoremanager.models.ProductDetailModel;
import org.phonestoremanager.repositories.*;
import org.phonestoremanager.models.ProductViewModel;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

public class ProductDetailsContentController {
    @FXML
    private Button btn_back;

    @FXML
    private FlowPane colorFlowPane;

    @FXML
    private VBox detailVBox;

    @FXML
    private VBox imageVBox;

    @FXML
    private FlowPane romFlowPane;

    @FXML
    public void initialize() {
        btnDeleteDetail.setOnAction(event -> deleteProductDetail(productDetail, product));
    }


    Button btnFixProduct = new Button("Sửa");

    Button btnDeleteDetail = new Button("Xóa");

    private ProductViewModel product;

    private ProductDetailModel productDetail;

    private int selectedColorID;
    private String selectedNameColor = null;
    private String selectedROM = null;

    private List<Button> colorButtons = new ArrayList<>();
    private List<Button> romButtons = new ArrayList<>();

    public void goBack() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/main-view.fxml"));
            Parent parent = loader.load();

            MenuController menuController = loader.getController();
            menuController.home(null);

            Scene scene = btn_back.getScene();
            scene.setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNameColorProduct(ProductViewModel product) {
        List<String> nameColors = ProductColorRepository.getInstance().getNameColorByProductID(product.getProductID());

        colorFlowPane.getChildren().clear();
        colorButtons.clear();

        // Sử dụng Set để lưu trữ tên màu đã gặp
        Set<String> uniqueNameColors = new HashSet<>();

        for (String nameColor : nameColors) {
            // Kiểm tra xem màu này đã tồn tại trong Set chưa
            if (uniqueNameColors.contains(nameColor)) {
                continue;  // Nếu đã có, bỏ qua màu này
            }

            // Nếu chưa có, thêm màu vào Set và tạo button
            uniqueNameColors.add(nameColor);

            Button colorButton = new Button(nameColor);  // Gán tên màu vào text của button
            colorButton.setPrefSize(80, 30);  // Cài đặt kích thước của nút

            colorButton.getStyleClass().add("color-button");

            colorButton.setOnAction(e -> {
                selectedNameColor = nameColor;

                for(Button b : colorButtons) {
                    b.getStyleClass().remove("selected-button");
                }

                colorButton.getStyleClass().add("selected-button");

                updateImageDisplay();
            });

            colorFlowPane.getChildren().add(colorButton);
            colorButtons.add(colorButton);
        }

        colorFlowPane.setHgap(40);  // Khoảng cách ngang giữa các button
        colorFlowPane.setVgap(20);  // Khoảng cách dọc giữa các button

        if (!colorButtons.isEmpty()) {
            colorButtons.get(0).fire();  // Gọi sự kiện click cho nút đầu tiên
        }
    }

    public void setROMProduct(ProductViewModel product) {
        List<String> roms = ProductROMRepository.getInstance().getROMByProductID(product.getProductID());

        romFlowPane.getChildren().clear();
        romButtons.clear();

        Set<String> uniqueRoms = new HashSet<>();

        for(String rom : roms) {
            if(uniqueRoms.contains(rom))
                continue;
            uniqueRoms.add(rom);

            Button romButton = new Button(rom);  // Gán tên màu vào text của button
            romButton.setPrefSize(70, 30);  // Cài đặt kích thước của nút

            romButton.getStyleClass().add("color-button");

            romButton.setOnAction(e -> {
                selectedROM = rom;

                for(Button b : romButtons) {
                    b.getStyleClass().remove("selected-button");
                }

                romButton.getStyleClass().add("selected-button");

                updateImageDisplay();

                displayProductOverview(product, Integer.parseInt(selectedROM));
            });



            romFlowPane.getChildren().add(romButton);
            romButtons.add(romButton);
        }

        romFlowPane.setHgap(40);  // Khoảng cách ngang giữa các button
        romFlowPane.setVgap(20);  // Khoảng cách dọc giữa các button

        if (!romButtons.isEmpty()) {
            romButtons.get(0).fire();  // Gọi sự kiện click cho nút đầu tiên
        }
    }

    private void updateImageDisplay() {
        // Truy xuất ColorID từ NameColor
        selectedColorID = ProductColorRepository.getInstance().getColorIDByProductIDAndNameColor(product.getProductID(), selectedNameColor);
        if (selectedNameColor != null && selectedROM != null) {
            List<String> imageList = ImageProductRepository.getInstance()
                    .getImageByProductIDColorIDAndROM(product.getProductID(), selectedColorID, selectedROM);

            imageVBox.getChildren().clear();

            for (String imagePath : imageList) {
                URL imageUrl = getClass().getResource("/org/phonestoremanager/assets/image/" + imagePath);

                if (imageUrl != null) {
                    Image image = new Image(imageUrl.toExternalForm());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(300);
                    imageView.setPreserveRatio(true);
                    imageView.setSmooth(true);

                    StackPane stackPane = new StackPane(imageView);
                    stackPane.getStyleClass().add("image-wrapper");

                    imageVBox.getChildren().add(stackPane);

                    System.out.println(imageUrl);
                } else {
                    System.err.println("Không tìm thấy ảnh: " + imagePath);
                }
            }
        }
    }

    public void displayProductOverview(ProductViewModel product, int selectedRom) {
        ProductDetailModel spec = ProductDetailRepository.getInstance().getDetailByProductIDAndROM(product.getProductID(), selectedRom);

        Label nameChip = new Label("Tên chíp:");
        Label nameROMRAM = new Label("RAM + ROM:");
        Label nameScreen = new Label("Màn hình:");
        Label nameCamera = new Label("Camera:");
        Label namePin = new Label("Pin:");
        Label namePrice = new Label("Giá bán:");
//        Thiết lập css
        nameChip.getStyleClass().add("grid-label");
        nameROMRAM.getStyleClass().add("grid-label");
        nameScreen.getStyleClass().add("grid-label");
        nameCamera.getStyleClass().add("grid-label");
        namePin.getStyleClass().add("grid-label");
        namePrice.getStyleClass().add("grid-label");


        Label valueChip = new Label(spec.getChip());
        Label valueRamRom = new Label(spec.getRam()+ "GB" + " + " + spec.getRom()+ "GB");
        Label valueScreen = new Label(spec.getScreenSize()+ "inch" + " - " + spec.getScanFrequency());
        Label valueCamera = new Label("Trước: " + spec.getCameraFront() + " / Sau: " + spec.getCameraRear());
        Label valuePin = new Label(spec.getBatteryCapacity() + " mAh");
        double price = spec.getPrice(); // đảm bảo là kiểu double
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = formatter.format(price);
        Label valuePrice = new Label(formattedPrice);
        valuePrice.getStyleClass().addAll("grid-value", "price-label");

//        Thiết lập css
        valueChip.getStyleClass().add("grid-value");
        valueRamRom.getStyleClass().add("grid-value");
        valueScreen.getStyleClass().add("grid-value");
        valueCamera.getStyleClass().add("grid-value");
        valuePin.getStyleClass().add("grid-value");
        valuePrice.getStyleClass().addAll("grid-value", "price-label");

        for (Label label : new Label[]{valueChip, valueRamRom, valueScreen, valueCamera, valuePin, valuePrice}) {
            label.setWrapText(true);
            label.setMaxWidth(250);
        }

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(20);

        grid.add(nameChip, 0, 0);
        grid.add(valueChip, 1, 0);

        grid.add(nameROMRAM, 0, 1);
        grid.add(valueRamRom, 1, 1);

        grid.add(nameScreen, 0, 2);
        grid.add(valueScreen, 1, 2);

        grid.add(nameCamera, 0, 3);
        grid.add(valueCamera, 1, 3);

        grid.add(namePin, 0, 4);
        grid.add(valuePin, 1, 4);

        grid.add(namePrice, 0, 5);
        grid.add(valuePrice, 1, 5);

        GridPane.setColumnSpan(btnFixProduct, 2);
        grid.add(btnFixProduct, 0, 6);
        btnFixProduct.getStyleClass().add("fixProduct-btn");

        GridPane.setColumnSpan(btnDeleteDetail, 2);
        GridPane.setHalignment(btnDeleteDetail, HPos.RIGHT);
        grid.add(btnDeleteDetail, 1, 6);
        btnDeleteDetail.getStyleClass().add("fixProduct-btn");

        detailVBox.getChildren().clear();
        detailVBox.getChildren().add(grid);

        productDetail = ProductDetailRepository.getInstance().getDetailByProductIDAndROM(product.getProductID(), selectedRom);
        btnFixProduct.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/fix-product-detail.fxml"));
                Parent root = loader.load();

                FixProductDetailController controller = loader.getController();
                controller.setProductDetail(product, productDetail);

                System.out.println("ProductDetailID đã truyền sang: " + productDetail.getProductDetailID());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }

    public void deleteProductDetail(ProductDetailModel pd, ProductViewModel p) {
        if (pd == null) {
            System.out.println("pd đang null! Không thể xóa.");
            return;
        }

        int selected = ProductDetailRepository.getInstance().getProductDetailIDByProductIDAndColorIDAndRom(p.getProductID(), selectedColorID, Integer.parseInt(selectedROM));

        if (selected == -1) {
                // Báo lỗi hoặc hiển thị thông báo người dùng
                System.out.println("Không tìm thấy chi tiết sản phẩm để xóa!");
                return;
        }

        System.out.println(p.getProductID());
        System.out.println(selected);
        System.out.println(selectedColorID);
        System.out.println(selectedROM);

        ProductDetailRepository.getInstance().deleteProductDetailByProductDetailID(selected);

        boolean isEmpty = ProductDetailRepository.getInstance().isEmptyProductDetail(p.getProductID());

        if (isEmpty) {
            System.out.println(0);
            // Xóa luôn sản phẩm nếu không còn chi tiết nào
            ProductRepository.getInstance().deleteProduct(p);

            // Quay về trang chính (main-view.fxml)
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/main-view.fxml"));
                Parent parent = loader.load();

                MenuController menuController = loader.getController();
                menuController.home(null); // nếu bạn có hàm home() để reset lại giao diện chính

                Scene scene = btn_back.getScene();
                scene.setRoot(parent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Nếu sản phẩm vẫn còn chi tiết → làm mới lại giao diện hiện tại
            refreshCurrentView();
        }
    }

    private void refreshCurrentView() {
        // Gọi lại các hàm hiển thị để cập nhật giao diện
        updateImageDisplay(); // cập nhật ảnh mới
        setNameColorProduct(product); // cập nhật nút màu
        setROMProduct(product); // cập nhật nút ROM

        // Nếu selectedROM đã bị xóa, bạn nên kiểm tra lại selectedROM còn hợp lệ không
        // Tạm thời gọi lại displayProductOverview với ROM đầu tiên
        List<String> romList = ProductROMRepository.getInstance().getROMByProductID(product.getProductID());
        if (!romList.isEmpty()) {
            selectedROM = romList.get(0); // gán lại ROM đầu tiên
            displayProductOverview(product, Integer.parseInt(selectedROM));
        } else {
            // Xử lý khi không còn ROM nào: xóa toàn bộ chi tiết
            detailVBox.getChildren().clear();
            imageVBox.getChildren().clear();
        }
    }

    public void setProduct(ProductViewModel p) {
        this.product = p;
        showProductDetails();
    }

    private void showProductDetails() {
        // Hiển thị dữ liệu product vào các label/textfield
        updateImageDisplay();
        setNameColorProduct(product);
        setROMProduct(product);
        displayProductOverview(product, Integer.parseInt(selectedROM));
    }
}
