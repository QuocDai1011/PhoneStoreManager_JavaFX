package org.phonestoremanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.phonestoremanager.repositories.*;
import org.phonestoremanager.models.ProductSpecificationModel;
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

    private ProductViewModel product;

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
        // Truy xuất ColorID từ NameColor để hiển thị hình ảnh
        selectedColorID = ProductColorRepository.getInstance().getColorIDByProductIDAndNameColor(product.getProductID(), selectedNameColor);
        if (selectedNameColor != null && selectedROM != null) {
            List<String> imageList = ImageProductRepository.getInstance()
                    .getImageByProductIDColorIDAndROM(product.getProductID(), selectedColorID, selectedROM);

            imageVBox.getChildren().clear();

//            System.out.println("ProductID: " + product.getProductID());
//            System.out.println("ColorID: " + selectedColorID);
//            System.out.println("ROM: " + selectedROM);
//            System.out.println("Số ảnh tìm được: " + imageList.size());

            for (String imagePath : imageList) {
                URL imageUrl = getClass().getResource("/org/phonestoremanager/assets/image/" + imagePath);

//                System.out.println("selectedNameColor = " + selectedNameColor + ", selectedROM = " + selectedROM);

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
        ProductSpecificationModel spec = ProductDetailRepository.getInstance().getDetailByProductIDAndROM(product.getProductID(), Integer.parseInt(selectedROM));

        Label nameChip = new Label("Tên chíp:");
        Label nameROMRAM = new Label("RAM + ROM:");
        Label nameScreen = new Label("Màn hình:");
        Label nameCamera = new Label("Camera:");
        Label namePin = new Label("Pin:");
        Label namePrice = new Label("Giá bán:");
        Button btnBuy = new Button("Mua ngay");
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
        double price = Double.parseDouble(spec.getPrice()); // đảm bảo là kiểu double
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

//        GridPane.setColumnSpan(btnBuy, 2);
//        GridPane.setHalignment(btnBuy, HPos.CENTER);
//        grid.add(btnBuy, 0, 6);
//        btnBuy.getStyleClass().add("buy-button");

        detailVBox.getChildren().clear();
        detailVBox.getChildren().add(grid);
    }

    public void setProduct(ProductViewModel product) {
        this.product = product;
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
