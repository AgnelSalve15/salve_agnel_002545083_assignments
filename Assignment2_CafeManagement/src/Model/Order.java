package model;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Order class for Assignment 2 - Coffee Shop Management
 * Each order has ONE product and belongs to ONE customer
 */
public class Order {
    private int orderId;             // int primitive
    private String orderDateTime;    // String reference type
    private String orderType;        // String reference type (Dine-in/Takeout/Pickup)
    private String paymentMethod;    // String reference type (Cash/Card/Mobile)
    private String orderStatus;      // String reference type (Pending/Preparing/Ready/Completed)
    private Product product;         // Product reference (ONE product per order)
    private Customer customer;       // Customer reference (ONE customer per order)
    private double totalAmount;      // double primitive
    
    private static int orderCounter = 0;
    
    // Default constructor
    public Order() {
        this.orderId = ++orderCounter;
        this.orderDateTime = getCurrentDateTime();
        this.orderType = "Dine-in";
        this.paymentMethod = "Cash";
        this.orderStatus = "Pending";
        this.product = null;
        this.customer = null;
        this.totalAmount = 0.0;
    }
    
    // Parameterized constructor
    public Order(Customer customer, Product product, String orderType, String paymentMethod) {
        this.orderId = ++orderCounter;
        this.orderDateTime = getCurrentDateTime();
        this.orderType = orderType;
        this.paymentMethod = paymentMethod;
        this.orderStatus = "Pending";
        this.product = product;
        this.customer = customer;
        calculateTotalAmount();
    }
    
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    
    private void calculateTotalAmount() {
        if (product != null) {
            this.totalAmount = product.getPrice();
        } else {
            this.totalAmount = 0.0;
        }
    }
    
    // Getters
    public int getOrderId() { return orderId; }
    public String getOrderDateTime() { return orderDateTime; }
    public String getOrderType() { return orderType; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getOrderStatus() { return orderStatus; }
    public Product getProduct() { return product; }
    public Customer getCustomer() { return customer; }
    public double getTotalAmount() { return totalAmount; }
    
    // Setters
    public void setOrderType(String orderType) { this.orderType = orderType; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    
    public void setProduct(Product product) { 
        this.product = product;
        calculateTotalAmount();
    }
    
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    // Validation method
    public boolean isValidOrder() {
        return customer != null && product != null &&
               orderType != null && !orderType.trim().isEmpty() &&
               paymentMethod != null && !paymentMethod.trim().isEmpty() &&
               orderStatus != null && !orderStatus.trim().isEmpty();
    }
    
    // Helper methods for display
    public String getCustomerName() {
        return customer != null ? customer.getFullName() : "Unknown Customer";
    }
    
    public String getProductName() {
        return product != null ? product.getProductName() : "No Product";
    }
    
    public int getCustomerId() {
        return customer != null ? customer.getCustomerId() : 0;
    }
    
    @Override
    public String toString() {
        return "Order #" + orderId + " - " + getCustomerName() + " - " + getProductName();
    }
}