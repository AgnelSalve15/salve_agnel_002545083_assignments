package ui;

import model.Business;
import model.Order;
import model.Customer;
import model.Product;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CreateOrderJPanel for creating new café orders
 * Similar to CreateJPanel from Vital Signs application
 */
public class CreateOrderJPanel extends JPanel {
    private Business business;
    
    // Customer fields
    private JTextField customerFirstNameField;
    private JTextField customerLastNameField;
    private JTextField contactField;
    private JCheckBox loyaltyMemberBox;
    
    // Order fields
    private JComboBox<String> orderTypeCombo;
    private JComboBox<String> paymentMethodCombo;
    private JComboBox<Product> productCombo;
    
    // Buttons
    private JButton createOrderButton;
    private JButton clearButton;
    private JButton addCustomerButton;
    
    public CreateOrderJPanel(Business business) {
        this.business = business;
        initComponents();
    }
    
    private void initComponents() {
        setBackground(new Color(255, 200, 200)); // Pink background like Vital Signs
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Title
        JLabel titleLabel = new JLabel("Create New Order", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        // Customer Information Section
        JLabel customerSectionLabel = new JLabel("Customer Information:");
        customerSectionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        add(customerSectionLabel, gbc);
        
        // Customer First Name
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("First Name:"), gbc);
        customerFirstNameField = new JTextField(15);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(customerFirstNameField, gbc);
        
        // Customer Last Name
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Last Name:"), gbc);
        customerLastNameField = new JTextField(15);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(customerLastNameField, gbc);
        
        // Contact
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Contact:"), gbc);
        contactField = new JTextField(15);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(contactField, gbc);
        
        // Loyalty Member Checkbox
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Loyalty Member:"), gbc);
        loyaltyMemberBox = new JCheckBox();
        loyaltyMemberBox.setBackground(new Color(255, 200, 200));
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(loyaltyMemberBox, gbc);
        
        // Order Information Section
        JLabel orderSectionLabel = new JLabel("Order Information:");
        orderSectionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        add(orderSectionLabel, gbc);
        
        // Order Type
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 7; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Order Type:"), gbc);
        orderTypeCombo = new JComboBox<>(new String[]{"Dine-in", "Takeout", "Pickup"});
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(orderTypeCombo, gbc);
        
        // Payment Method
        gbc.gridx = 0; gbc.gridy = 8; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Payment Method:"), gbc);
        paymentMethodCombo = new JComboBox<>(new String[]{"Cash", "Card", "Mobile"});
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(paymentMethodCombo, gbc);
        
        // Product Selection
        gbc.gridx = 0; gbc.gridy = 9; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Select Product:"), gbc);
        productCombo = new JComboBox<>();
        refreshProductCombo();
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(productCombo, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        createOrderButton = new JButton("Create Order");
        clearButton = new JButton("Clear Form");
        addCustomerButton = new JButton("Add Customer Only");
        
        // Style buttons
        createOrderButton.setBackground(new Color(46, 125, 50));
        createOrderButton.setForeground(Color.WHITE);
        createOrderButton.setFocusPainted(false);
        
        createOrderButton.addActionListener(new CreateOrderListener());
        clearButton.addActionListener(e -> clearForm());
        addCustomerButton.addActionListener(new AddCustomerListener());
        
        buttonPanel.add(createOrderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(addCustomerButton);
        buttonPanel.setBackground(new Color(255, 200, 200));
        
        gbc.gridx = 0; gbc.gridy = 10; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);
    }
    
    private void refreshProductCombo() {
        productCombo.removeAllItems();
        for (Product product : business.getProductCatalog().getProductList()) {
            productCombo.addItem(product);
        }
    }
    
    private class CreateOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!validateInput()) {
                return;
            }
            
            try {
                // Create customer
                Customer customer = new Customer(
                    customerFirstNameField.getText().trim(),
                    customerLastNameField.getText().trim(),
                    contactField.getText().trim()
                );
                customer.setLoyaltyMember(loyaltyMemberBox.isSelected());
                business.addCustomer(customer);
                
                // Create order
                Product selectedProduct = (Product) productCombo.getSelectedItem();
                String orderType = (String) orderTypeCombo.getSelectedItem();
                String paymentMethod = (String) paymentMethodCombo.getSelectedItem();
                
                Order order = new Order(customer, selectedProduct, orderType, paymentMethod);
                business.getOrderDirectory().addOrder(order);
                customer.addOrder(order);
                
                JOptionPane.showMessageDialog(CreateOrderJPanel.this, 
                    "Order created successfully!\n\n" +
                    "Order ID: " + order.getOrderId() + "\n" +
                    "Customer: " + customer.getFullName() + "\n" +
                    "Product: " + selectedProduct.getProductName() + "\n" +
                    "Total: $" + String.format("%.2f", order.getTotalAmount()), 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                clearForm();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(CreateOrderJPanel.this, 
                    "Error creating order: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private class AddCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!validateCustomerInput()) {
                return;
            }
            
            try {
                // Create customer only (no order)
                Customer customer = new Customer(
                    customerFirstNameField.getText().trim(),
                    customerLastNameField.getText().trim(),
                    contactField.getText().trim()
                );
                customer.setLoyaltyMember(loyaltyMemberBox.isSelected());
                business.addCustomer(customer);
                
                JOptionPane.showMessageDialog(CreateOrderJPanel.this, 
                    "Customer added successfully!\n\n" +
                    "Customer ID: " + customer.getCustomerId() + "\n" +
                    "Name: " + customer.getFullName() + "\n" +
                    "Contact: " + customer.getContact(), 
                    "Customer Added", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                clearForm();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(CreateOrderJPanel.this, 
                    "Error adding customer: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean validateInput() {
        // Validate customer information first
        if (!validateCustomerInput()) {
            return false;
        }
        
        // Validate product selection
        if (productCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select a product for the order", 
                "Product Required", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private boolean validateCustomerInput() {
        // Validate first name
        if (customerFirstNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Customer First Name is required", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            customerFirstNameField.requestFocus();
            return false;
        }
        
        // Validate last name
        if (customerLastNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Customer Last Name is required", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            customerLastNameField.requestFocus();
            return false;
        }
        
        // Validate contact
        String contact = contactField.getText().trim();
        if (contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Customer Contact is required", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            contactField.requestFocus();
            return false;
        }
        
        // Validate contact format (basic phone number validation)
        if (!contact.matches("\\d{3}-\\d{4}") && !contact.matches("\\d{10}") && !contact.matches("\\d{3}-\\d{3}-\\d{4}")) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid contact number\n" +
                "Accepted formats: 555-1234, 5551234567, 555-123-4567", 
                "Invalid Contact Format", 
                JOptionPane.ERROR_MESSAGE);
            contactField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        customerFirstNameField.setText("");
        customerLastNameField.setText("");
        contactField.setText("");
        loyaltyMemberBox.setSelected(false);
        orderTypeCombo.setSelectedIndex(0);
        paymentMethodCombo.setSelectedIndex(0);
        productCombo.setSelectedIndex(0);
        customerFirstNameField.requestFocus();
    }
    
    public void refreshData() {
        refreshProductCombo();
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

