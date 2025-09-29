package model;

import java.util.ArrayList;

/**
 * Customer class for Assignment 2 - Coffee Shop Management
 * Represents customers with their information and order history
 */
public class Customer {
    private int customerId;          // int primitive
    private String customerFirstName; // String reference type
    private String customerLastName;  // String reference type
    private String contact;          // String reference type
    private boolean isLoyaltyMember; // boolean primitive
    private ArrayList<Order> orderHistory;
    
    private static int customerCounter = 0;
    
    // Default constructor
    public Customer() {
        this.customerId = ++customerCounter;
        this.customerFirstName = "";
        this.customerLastName = "";
        this.contact = "";
        this.isLoyaltyMember = false;
        this.orderHistory = new ArrayList<>();
    }
    
    // Parameterized constructor
    public Customer(String firstName, String lastName, String contact) {
        this.customerId = ++customerCounter;
        this.customerFirstName = firstName;
        this.customerLastName = lastName;
        this.contact = contact;
        this.isLoyaltyMember = false;
        this.orderHistory = new ArrayList<>();
    }
    
    // Getters
    public int getCustomerId() { return customerId; }
    public String getCustomerFirstName() { return customerFirstName; }
    public String getCustomerLastName() { return customerLastName; }
    public String getContact() { return contact; }
    public boolean isLoyaltyMember() { return isLoyaltyMember; }
    public ArrayList<Order> getOrderHistory() { return orderHistory; }
    
    // Setters
    public void setCustomerFirstName(String firstName) { this.customerFirstName = firstName; }
    public void setCustomerLastName(String lastName) { this.customerLastName = lastName; }
    public void setContact(String contact) { this.contact = contact; }
    public void setLoyaltyMember(boolean loyaltyMember) { this.isLoyaltyMember = loyaltyMember; }
    
    // Helper methods
    public String getFullName() {
        return customerFirstName + " " + customerLastName;
    }
    
    public void addOrder(Order order) {
        orderHistory.add(order);
    }
    
    public double getTotalSpent() {
        double total = 0.0;
        for (Order order : orderHistory) {
            if ("Completed".equals(order.getOrderStatus())) {
                total += order.getProduct().getPrice();
            }
        }
        return total;
    }
    
    public int getTotalOrders() {
        return orderHistory.size();
    }
    
    // Validation method
    public boolean isValidCustomer() {
        return customerFirstName != null && !customerFirstName.trim().isEmpty() &&
               customerLastName != null && !customerLastName.trim().isEmpty() &&
               contact != null && !contact.trim().isEmpty();
    }
    
    @Override
    public String toString() {
        return getFullName() + " (ID: " + customerId + ")";
    }
}