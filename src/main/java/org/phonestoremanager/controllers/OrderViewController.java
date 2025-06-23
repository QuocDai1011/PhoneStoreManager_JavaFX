package org.phonestoremanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.phonestoremanager.models.OrdersModel;
import org.phonestoremanager.repositories.OrderDetailRepository;
import org.phonestoremanager.repositories.OrdersRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderViewController implements Initializable {
    @FXML
    TableView<OrdersModel> tableView;

    @FXML
    TableColumn<OrdersModel, Integer> orderID_cl;

    @FXML
    TableColumn<OrdersModel, Integer> customerID_cl;

    @FXML
    TableColumn<OrdersModel, String> customerName_cl;

    @FXML
    TableColumn<OrdersModel, String> orderDate_cl;

    @FXML
    TableColumn<OrdersModel, String> status_cl;

    @FXML
    TableColumn<OrdersModel, Double> totalAmout_cl;

    @FXML
    TableColumn<OrdersModel, String> shippingAddress_cl;

    @FXML private Button fixedButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderID_cl.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        customerID_cl.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName_cl.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        orderDate_cl.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        status_cl.setCellValueFactory(new PropertyValueFactory<>("status"));
        totalAmout_cl.setCellValueFactory(new PropertyValueFactory<>("totalAmout"));
        shippingAddress_cl.setCellValueFactory(new PropertyValueFactory<>("shippingAddress"));

        if (fixedButton != null) {
            fixedButton.toFront(); // Đưa nút lên trên cùng giao diện
            fixedButton.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/UpdateOrders.fxml"));
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

        // Bổ sung: Xóa dòng khi click chuột phải
        tableView.setRowFactory(tv -> {
            TableRow<OrdersModel> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem("Xóa đơn hàng");

            deleteItem.setOnAction(e -> {
                OrdersModel selectedOrder = row.getItem();
                if (selectedOrder != null) {
                    // Xác nhận trước khi xóa
                    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmAlert.setTitle("Xác nhận xóa");
                    confirmAlert.setHeaderText("Bạn có chắc muốn xóa đơn hàng #" + selectedOrder.getOrderID() + " không?");
                    confirmAlert.showAndWait().ifPresent(response -> {
                        if (response.getButtonData().isDefaultButton()) {
                            // Xóa OrderDetail trước rồi mới xóa trong Orders
                            OrderDetailRepository.delete(selectedOrder.getOrderID());

                            // Xóa trong database
                            OrdersRepository.delete(selectedOrder.getOrderID());

                            // Xóa trong TableView
                            tableView.getItems().remove(selectedOrder);
                        }
                    });
                }
            });

            contextMenu.getItems().add(deleteItem);

            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }
            });

            return row;
        });

        render(OrdersRepository.getAll());

    }

    public void render(List<OrdersModel> list) {
        ObservableList<OrdersModel> listObser = FXCollections.observableArrayList();
        if (list != null) {
            listObser.addAll(list);
        }else {
            System.out.println("Khong the lay du lieu nhan vien");
            return;
        }
        tableView.setItems(listObser);
    }
}
