package org.phonestoremanager.models;

public class OrdersModel {
    private int orderID;
    private int customerID;
    private String orderDate;
    private Double totalAmout;
    private String status;
    private String shippingAddress;
    private String customerName;

    public OrdersModel() {
    }

    public OrdersModel(int orderID, int customerID, String orderDate, Double totalAmout, String status, String shippingAddress) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.totalAmout = totalAmout;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    public OrdersModel(int orderID, int customerID, String orderDate, Double totalAmout, String status, String shippingAddress, String customerName) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.totalAmout = totalAmout;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.customerName = customerName;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(Double totalAmout) {
        this.totalAmout = totalAmout;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "orderID=" + orderID +
                ", customerID=" + customerID +
                ", orderDate='" + orderDate + '\'' +
                ", totalAmout=" + totalAmout +
                ", status='" + status + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                '}';
    }
}
