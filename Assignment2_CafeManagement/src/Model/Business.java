package model;

import java.util.ArrayList;

/**
 * Business class for Assignment 2 - Coffee Shop Management
 * Central class that manages ProductCatalog and OrderDirectory
 */
public class Business {
    private String businessName;
    private ProductCatalog productCatalog;
    private OrderDirectory orderDirectory;
    private ArrayList<Customer> customerList;
    
    public Business() {
        this.businessName = "Coffee Central";
        this.productCatalog = new ProductCatalog();
        this.orderDirectory = new OrderDirectory();
        this.customerList = new ArrayList<>();
        initializeSampleData();
    }
    
    public Business(String businessName) {
        this.businessName = businessName;
        this.productCatalog = new ProductCatalog();
        this.orderDirectory = new OrderDirectory();
        this.customerList = new ArrayList<>();
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Add 5+ sample products as required
        Product espresso = new Product("Espresso", "Coffee", 2.50, 50, 3);
        Product cappuccino = new Product("Cappuccino", "Coffee", 3.75, 40, 5);
        Product latte = new Product("Latte", "Coffee", 4.25, 35, 6);
        Product americano = new Product("Americano", "Coffee", 3.00, 45, 4); // Same name test
        Product croissant = new Product("Croissant", "Pastry", 2.95, 20, 2);
        Product bagel = new Product("Bagel", "Pastry", 1.95, 25, 3);
        Product sandwich = new Product("Sandwich", "Food", 6.95, 15, 8);
        
        productCatalog.addProduct(espresso);
        productCatalog.addProduct(cappuccino);
        productCatalog.addProduct(latte);
        productCatalog.addProduct(americano);
        productCatalog.addProduct(croissant);
        productCatalog.addProduct(bagel);
        productCatalog.addProduct(sandwich);
        
        // Add sample customers
        Customer customer1 = new Customer("John", "Doe", "555-1234");
        Customer customer2 = new Customer("Jane", "Smith", "555-5678");
        Customer customer3 = new Customer("Bob", "Johnson", "555-9012");
        Customer customer4 = new Customer("Alice", "Brown", "555-3456"); // For duplicate name testing
        
        addCustomer(customer1);
        addCustomer(customer2);
        addCustomer(customer3);
        addCustomer(customer4);
        
        // Create 3+ sample orders as required
        Order order1 = new Order(customer1, espresso, "Dine-in", "Card");
        order1.setOrderStatus("Completed");
        orderDirectory.addOrder(order1);
        customer1.addOrder(order1);
        
        Order order2 = new Order(customer2, cappuccino, "Takeout", "Cash");
        order2.setOrderStatus("Ready");
        orderDirectory.addOrder(order2);
        customer2.addOrder(order2);
        
        Order order3 = new Order(customer3, latte, "Pickup", "Mobile");
        order3.setOrderStatus("Preparing");
        orderDirectory.addOrder(order3);
        customer3.addOrder(order3);
        
        Order order4 = new Order(customer1, americano, "Dine-in", "Card");
        order4.setOrderStatus("Pending");
        orderDirectory.addOrder(order4);
        customer1.addOrder(order4);
    }
    
    // Getters
    public String getBusinessName() { return businessName; }
    public ProductCatalog getProductCatalog() { return productCatalog; }
    public OrderDirectory getOrderDirectory() { return orderDirectory; }
    public ArrayList<Customer> getCustomerList() { return customerList; }
    
    // Setters
    public void setBusinessName(String businessName) { this.businessName = businessName; }
    
    // Customer management
    public void addCustomer(Customer customer) {
        if (customer != null) {
            customerList.add(customer);
        }
    }
    
    public void removeCustomer(Customer customer) {
        customerList.remove(customer);
    }
    
    public void removeCustomer(int index) {
        if (index >= 0 && index < customerList.size()) {
            customerList.remove(index);
        }
    }
    
    // Search customers by ID
    public Customer searchCustomerById(int customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }
    
    // Search customers by name (returns all matches)
    public ArrayList<Customer> searchCustomersByName(String name) {
        ArrayList<Customer> results = new ArrayList<>();
        for (Customer customer : customerList) {
            if (customer.getFullName().toLowerCase().contains(name.toLowerCase()) ||
                customer.getCustomerFirstName().toLowerCase().contains(name.toLowerCase()) ||
                customer.getCustomerLastName().toLowerCase().contains(name.toLowerCase())) {
                results.add(customer);
            }
        }
        return results;
    }
    
    // Get customer by index
    public Customer getCustomer(int index) {
        if (index >= 0 && index < customerList.size()) {
            return customerList.get(index);
        }
        return null;
    }
    
    // Statistics methods
    public int getTotalCustomers() {
        return customerList.size();
    }
    
    public int getTotalProducts() {
        return productCatalog.getProductCount();
    }
    
    public int getTotalOrders() {
        return orderDirectory.getOrderCount();
    }
    
    public double getTotalRevenue() {
        return orderDirectory.getTotalRevenue();
    }
    
    public int getPendingOrdersCount() {
        return orderDirectory.getOrderCountByStatus("Pending");
    }
    
    public int getCompletedOrdersCount() {
        return orderDirectory.getOrderCountByStatus("Completed");
    }
    
    // Get data for customer table display
    public Object[][] getCustomerTableData() {
        Object[][] data = new Object[customerList.size()][5];
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            data[i][0] = customer.getCustomerId();
            data[i][1] = customer.getCustomerFirstName();
            data[i][2] = customer.getCustomerLastName();
            data[i][3] = customer.getContact();
            data[i][4] = customer.getTotalOrders();
        }
        return data;
    }
    
    public String[] getCustomerTableColumns() {
        return new String[]{"Customer ID", "First Name", "Last Name", "Contact", "Total Orders"};
    }
}