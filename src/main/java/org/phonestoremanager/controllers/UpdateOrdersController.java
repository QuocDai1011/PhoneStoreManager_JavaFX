package org.phonestoremanager.controllers;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.phonestoremanager.models.*;
import org.phonestoremanager.repositories.*;

import javafx.event.ActionEvent;
import org.phonestoremanager.utils.ParseVietNamCurrencyToDouble;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class UpdateOrdersController implements Initializable {
    @FXML
    private TextField inputInfoCustomer;

    @FXML
    private TextField fullName;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField address;

    @FXML
    private DatePicker orderDatePicker;

    @FXML
    private ComboBox<String> productComboBox;

    @FXML
    private ComboBox<Integer> ramComboBox;

    @FXML
    private ComboBox<Integer> romComboBox;

    @FXML
    private ComboBox<String> colorComboBox;

    @FXML
    private TextField quantityField;

    @FXML
    private Button addProductButton;

    @FXML
    private TableView<OrderUpdateModel> productTable;

    @FXML
    private TableColumn<OrderUpdateModel, String> nameProduct_cl;

    @FXML
    private TableColumn<OrderUpdateModel, Integer> ramProduct_cl;

    @FXML
    private TableColumn<OrderUpdateModel, Integer> romProduct_cl;

    @FXML
    private TableColumn<OrderUpdateModel, String> colorProduct_cl;

    @FXML
    private TableColumn<OrderUpdateModel, Integer> quantityProduct_cl;

    @FXML
    private TableColumn<OrderUpdateModel, String> unitPriceProduct_cl;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Label totalAmountLabel;

    @FXML
    private Button createOrder_btn;

    @FXML
    private Button cancel_btn;


    @FXML
    private ProgressIndicator loadingIndicator;

    private PauseTransition pause;
    private CustomerModel customerModel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Tạo PauseTransition với thời gian delay 700ms
        pause = new PauseTransition(Duration.millis(700));

        // Đặt hành động sau khi chờ 700ms
        pause.setOnFinished(event -> {
            String keyword = inputInfoCustomer.getText().trim();
            if (!keyword.isEmpty()) {
                performSearch(keyword);
            }
        });

        // Gắn listener vào text field
        inputInfoCustomer.textProperty().addListener((observable, oldValue, newValue) -> {
            // Mỗi lần người dùng nhập, reset lại thời gian chờ
            loadingIndicator.setVisible(true);
            pause.playFromStart();
        });

        //Gán hành động đóng cửa sổ cho btn Hủy
        cancel_btn.setOnAction(this::closeWindow);

        //SetItems cho productsCombobox
        List<String> productList = ProductViewRepository.getAllNameProducts();
        ObservableList<String> productNames = FXCollections.observableArrayList(productList);
        productComboBox.setItems(productNames);
        productComboBox.setItems(productNames);

        //Lấy giá trị của productCombobox và set các Item cho Ram Rom và Color combobox
        productComboBox.setOnAction(event -> {
            //reset cac combobox
            ramComboBox.getSelectionModel().clearSelection();
            romComboBox.getSelectionModel().clearSelection();
            colorComboBox.getSelectionModel().clearSelection();
            quantityField.setText("");

            String selected = productComboBox.getValue();

            // setItems cho ramCombobox
            ramComboBox.setItems(ProductViewRepository.getRamByProductName(selected));

            //setItems cho romCombobox
            romComboBox.setItems(ProductViewRepository.getRomByProductName(selected));

            //setItems cho colorCombobox
            colorComboBox.setItems(ProductViewRepository.getColorByProductName(selected));
        });

        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            String quantityText = quantityField.getText();
            int quantityInt = 0;
            try {
                quantityInt = Integer.parseInt(quantityText);
            } catch (Exception e) {
                Platform.runLater(() -> quantityField.setText(""));
            }
        });

        //khởi tạo table view
        initTableView();

        //render dữ liệu vào table view
        List<OrderUpdateModel> list = new ArrayList<>();
        addProductButton.setOnAction(event -> {
            if(getValueModel() == null) return;
            list.add(getValueModel());
            renderTableView(list);
            updateTotalAmout(list);
        });

        createOrder_btn.setOnAction(event -> {
            OrderUpdateModel orderUpdateModel = getValueModel();
            handleCreateOrder(orderUpdateModel);
        });

    }

    private void handleCreateOrder(OrderUpdateModel orderUpdateModel) {
        //check khách hàng khác null
        if(!checkCustomer()) {
            return;
        }

        System.out.println(totalAmountLabel.getText());

        //insert dữ liệu vào bảng Order
        int rowOrder  = OrdersRepository.insert(customerModel, getDateSelected(),
                statusComboBox.getValue(), ParseVietNamCurrencyToDouble.parseVietnamCurrency(totalAmountLabel.getText()));

        if(rowOrder == 0) {
            alertError("Thêm dữ liệu vào bảng Orders bị lỗi!");
            return;
        }

        // TODO: insert dữ liệu vào bảng OrderDetail
        int rowOrderDetail = OrderDetailRepository.insert(
                OrdersRepository.getOrderIDByInfomation(customerModel.getCustomerID(), getDateSelected(),
                        ParseVietNamCurrencyToDouble.parseVietnamCurrency(totalAmountLabel.getText())),
                ProductDetailRepository.getInstance().getProductDetailIDByInfomation(orderUpdateModel),
                Integer.parseInt(quantityField.getText()),
                orderUpdateModel.getUnitPriceNumber()
        );

        if(rowOrderDetail == 0) {
            alertError("Thêm dữ liệu vào bảng Orders bị lỗi!");
            return;
        }

        // Alert thông báo thêm đơn hàng thành công
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Thêm đơn hàng thành công!");
        alert.showAndWait();

        //tắt màn hình khi tạo đơn hàng thành coong
        cancel_btn.getScene().getWindow().hide();
    }

    private void initTableView() {
        nameProduct_cl.setCellValueFactory(new PropertyValueFactory<>("productName"));
        ramProduct_cl.setCellValueFactory(new PropertyValueFactory<>("ram"));
        romProduct_cl.setCellValueFactory(new PropertyValueFactory<>("rom"));
        colorProduct_cl.setCellValueFactory(new PropertyValueFactory<>("color"));
        quantityProduct_cl.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceProduct_cl.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    private void renderTableView(List<OrderUpdateModel> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Không thể lấy dữ liệu đơn hàng hoặc dữ liệu rỗng");
            productTable.setItems(FXCollections.observableArrayList()); // xóa dữ liệu cũ
            return;
        }

        ObservableList<OrderUpdateModel> listObser = FXCollections.observableArrayList(list);
        productTable.setItems(listObser);
    }

    // Hàm xử lý tìm kiếm
    private void performSearch(String keyword) {
        // Thực hiện tìm kiếm trong danh sách, cơ sở dữ liệu, hoặc API
        int raw;
        try {
            raw = Integer.parseInt(keyword);
            customerModel = CustomerRepository.getAllByID(raw);
            if(customerModel.getFirstName() != null) {
                setTextField(customerModel);
            }else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Lỗi tìm kiếm");
                    alert.setContentText("Không tìm thấy khách hàng có ID: " + keyword);
                    alert.showAndWait();
                });
                setEditTableTrue();
            }
        } catch (Exception e) {
            customerModel = CustomerRepository.getAllByIEmail(keyword);
            if(customerModel.getFirstName() != null) {
                setTextField(customerModel);
            }else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Lỗi tìm kiếm");
                    alert.setContentText("Không tìm thấy khách hàng có Email: " + keyword);
                    alert.showAndWait();
                });
                setEditTableTrue();
            }
        }
        Platform.runLater(() -> loadingIndicator.setVisible(false));

    }

    private void setTextField(CustomerModel customerModel) {
        fullName.setText(customerModel.getLastName() + " " + customerModel.getFirstName());
        fullName.setEditable(false);
        email.setText(customerModel.getEmail());
        email.setEditable(false);
        phoneNumber.setText(customerModel.getPhoneNumber());
        phoneNumber.setEditable(false);
        address.setText(customerModel.getAddress());
        address.setEditable(false);
    }

    private void setEditTableTrue() {
        fullName.setEditable(true);
        email.setEditable(true);
        phoneNumber.setEditable(true);
        address.setEditable(true);
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private LocalDate getDateSelected() {
        return orderDatePicker.getValue();
    }

    private OrderUpdateModel getValueModel() {
        // check thông tin sản phẩm khác null
        if(!checkOrder()) {
            return null;
        }

        // lấy thông tin sản phẩm
        OrderUpdateModel orderUpdateModel = new OrderUpdateModel();
        orderUpdateModel.setProductName(productComboBox.getValue());
        orderUpdateModel.setRam(ramComboBox.getValue());
        orderUpdateModel.setRom(romComboBox.getValue());
        orderUpdateModel.setColor(colorComboBox.getValue());
        orderUpdateModel.setQuantity(Integer.parseInt(quantityField.getText()));
        double unitPrice = ProductViewRepository.getUnitPriceByProductName(
                productComboBox.getValue(), ramComboBox.getValue(), romComboBox.getValue()
        );
        orderUpdateModel.setUnitPriceNumber(unitPrice);

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String formattedPrice = currencyVN.format(unitPrice);
        orderUpdateModel.setUnitPrice(formattedPrice);
        return orderUpdateModel;
    }

    private void updateTotalAmout(List<OrderUpdateModel> list) {
        double price = 0;
        for (int i = 0; i < list.size(); i++) {
            price += (list.get(i).getUnitPriceNumber() * list.get(i).getQuantity());
        }
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String formattedPrice = currencyVN.format(price);
        totalAmountLabel.setText(formattedPrice);
    }

    private boolean checkCustomer() {
        if(fullName.getText().isEmpty() || email.getText().isEmpty() || phoneNumber.getText().isEmpty()
        || address.getText().isEmpty() || inputInfoCustomer.getText().isEmpty()) {
            alertError("Dữ liệu khách hàng còn trống!");
            return false;
        }
        return true;
    }

    private boolean checkOrder() {
        LocalDate localDate = null;
        localDate = orderDatePicker.getValue();
        if(localDate == null || productComboBox.getValue() == null || ramComboBox.getValue() == null
            || romComboBox.getValue() == null || colorComboBox.getValue() == null
                || quantityField.getText().isEmpty() || orderDatePicker.getValue() == null) {
            alertError("Dữ liệu sản phẩm không được để trống!");
            return false;
        }

        return true;
    }

    private void alertError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi!");
        alert.setHeaderText(text);
        alert.showAndWait();
    }

}
