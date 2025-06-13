package org.phonestoremanager.services;

import org.phonestoremanager.models.OrderDetailModel;
import org.phonestoremanager.models.OrdersModel;
import org.phonestoremanager.repositories.CustomerRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersService {
    public static OrdersModel ordersModel (ResultSet rs) throws SQLException {
        OrdersModel ordersModel = new OrdersModel();

        ordersModel.setOrderID(rs.getInt("OrderID"));
        ordersModel.setCustomerID(rs.getInt("CustomerID"));
        ordersModel.setOrderDate(rs.getString("OrderDate"));
        ordersModel.setTotalAmout(rs.getDouble("TotalAmount"));
        int status_raw = rs.getInt("Status");
        if(status_raw == 0) {
            ordersModel.setStatus("Chưa thanh toán");
        }else {
            ordersModel.setStatus("Đã thanh toán");
        }
        ordersModel.setShippingAddress(rs.getString("ShippingAddress"));
        ordersModel.setCustomerName(CustomerRepository.getNameByCustomerID(ordersModel.getCustomerID()));

        return ordersModel;
    }
}
