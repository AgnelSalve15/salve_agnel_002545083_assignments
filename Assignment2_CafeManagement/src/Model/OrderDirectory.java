package model;

import java.util.ArrayList;

/**
 * OrderDirectory class for Assignment 2 - Coffee Shop Management
 * Manages the collection of all orders in the café system
 */
public class OrderDirectory {
    private ArrayList<Order> orderList;
    
    public OrderDirectory() {
        this.orderList = new ArrayList<>();
    }
    
    // Add order to directory
    public Order addOrder() {
        Order order = new Order();
        orderList.add(order);
        return order;
    }
    
    public void addOrder(Order order) {
        if (order != null) {
            orderList.add(order);
        }
    }
    
    // Remove order from directory
    public void removeOrder(Order order) {
        orderList.remove(order);
    }
    
    public void removeOrder(int index) {
        if (index >= 0 && index < orderList.size()) {
            orderList.remove(index);
        }
    }
    
    // Get order by index
    public Order getOrder(int index) {
        if (index >= 0 && index < orderList.size()) {
            return orderList.get(index);
        }
        return null;
    }
    
    // Get all orders
    public ArrayList<Order> getOrderList() {
        return orderList;
    }
    
    // Search orders by Order ID
    public Order searchOrderById(int orderId) {
        for (Order order : orderList) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }
    
    // Search orders by Customer ID
    public ArrayList<Order> searchOrdersByCustomerId(int customerId) {
        ArrayList<Order> results = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getCustomer() != null && order.getCustomer().getCustomerId() == customerId) {
                results.add(order);
            }
        }
        return results;
    }
    
    // Search orders by Customer Name
    public ArrayList<Order> searchOrdersByCustomerName(String customerName) {
        ArrayList<Order> results = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getCustomer() != null && 
                order.getCustomer().getFullName().toLowerCase().contains(customerName.toLowerCase())) {
                results.add(order);
            }
        }
        return results;
    }
    
    // Search orders by status
    public ArrayList<Order> searchOrdersByStatus(String status) {
        ArrayList<Order> results = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getOrderStatus().equalsIgnoreCase(status)) {
                results.add(order);
            }
        }
        return results;
    }
    
    // Get total number of orders
    public int getOrderCount() {
        return orderList.size();
    }
    
    // Check if directory is empty
    public boolean isEmpty() {
        return orderList.isEmpty();
    }
    
    // Calculate total revenue
    public double getTotalRevenue() {
        double revenue = 0.0;
        for (Order order : orderList) {
            if ("Completed".equals(order.getOrderStatus())) {
                revenue += order.getTotalAmount();
            }
        }
        return revenue;
    }
    
    // Get orders by status count
    public int getOrderCountByStatus(String status) {
        int count = 0;
        for (Order order : orderList) {
            if (order.getOrderStatus().equalsIgnoreCase(status)) {
                count++;
            }
        }
        return count;
    }
    
    // Get data for JTable display
    public Object[][] getTableData() {
        Object[][] data = new Object[orderList.size()][7];
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            data[i][0] = order.getOrderId();
            data[i][1] = order.getCustomerId();
            data[i][2] = order.getCustomerName();
            data[i][3] = order.getProductName();
            data[i][4] = order.getOrderType();
            data[i][5] = String.format("$%.2f", order.getTotalAmount());
            data[i][6] = order.getOrderStatus();
        }
        return data;
    }
    
    public String[] getTableColumns() {
        return new String[]{"Order ID", "Customer ID", "Customer", "Product", "Type", "Total", "Status"};
    }
}