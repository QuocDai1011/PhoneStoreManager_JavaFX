package org.phonestoremanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.phonestoremanager.models.OrdersModel;
import org.phonestoremanager.repositories.OrdersRepository;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderID_cl.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        customerID_cl.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName_cl.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        orderDate_cl.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        status_cl.setCellValueFactory(new PropertyValueFactory<>("status"));
        totalAmout_cl.setCellValueFactory(new PropertyValueFactory<>("totalAmout"));
        shippingAddress_cl.setCellValueFactory(new PropertyValueFactory<>("shippingAddress"));

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
