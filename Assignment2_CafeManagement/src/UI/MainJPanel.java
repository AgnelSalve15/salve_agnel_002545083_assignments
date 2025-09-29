package ui;

import model.Business;
import model.Order;
import model.Customer;
import model.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MainJPanel extends JPanel {
    private Business business;
    private CreateOrderJPanel createOrderPanel;
    private JPanel viewOrdersPanel;
    private JPanel searchPanel;
    private JPanel manageProductsPanel;
    
    // Navigation buttons
    private JButton createOrderButton;
    private JButton viewOrdersButton;
    private JButton searchCustomerButton;
    private JButton manageProductsButton;
    
    // CardLayout components
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    // Orders table components
    private JTable ordersTable;
    private DefaultTableModel ordersTableModel;
    
    // Search components
    private JTextField searchIdField;
    private JTextField searchNameField;
    private JTable customersTable;
    private DefaultTableModel customersTableModel;
    
    // Products table components
    private JTable productsTable;
    private DefaultTableModel productsTableModel;
    
    public MainJPanel() {
        business = new Business();
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Left panel with navigation buttons (green like Vital Signs)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(200, 255, 200)); // Light green
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(180, 0));
        
        createOrderButton = new JButton("Create Order");
        viewOrdersButton = new JButton("View Orders");
        searchCustomerButton = new JButton("Search Customer");
        manageProductsButton = new JButton("Manage Products");
        
        // Style buttons
        Dimension buttonSize = new Dimension(160, 35);
        createOrderButton.setMaximumSize(buttonSize);
        viewOrdersButton.setMaximumSize(buttonSize);
        searchCustomerButton.setMaximumSize(buttonSize);
        manageProductsButton.setMaximumSize(buttonSize);
        
        createOrderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewOrdersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchCustomerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageProductsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(createOrderButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(viewOrdersButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(searchCustomerButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(manageProductsButton);
        leftPanel.add(Box.createVerticalGlue());
        
        // Content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        
        // Create panels
        createOrderPanel = new CreateOrderJPanel(business);
        viewOrdersPanel = createViewOrdersPanel();
        searchPanel = createSearchPanel();
        manageProductsPanel = createManageProductsPanel();
        
        // Add panels to card layout
        contentPanel.add(createOrderPanel, "CREATE_ORDER");
        contentPanel.add(viewOrdersPanel, "VIEW_ORDERS");
        contentPanel.add(searchPanel, "SEARCH_CUSTOMER");
        contentPanel.add(manageProductsPanel, "MANAGE_PRODUCTS");
        
        // Add components to main panel
        add(leftPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        
        // Add action listeners for navigation
        createOrderButton.addActionListener(e -> {
            createOrderPanel.refreshData();
            cardLayout.show(contentPanel, "CREATE_ORDER");
        });
        
        viewOrdersButton.addActionListener(e -> {
            refreshOrdersTable();
            cardLayout.show(contentPanel, "VIEW_ORDERS");
        });
        
        searchCustomerButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "SEARCH_CUSTOMER");
        });
        
        manageProductsButton.addActionListener(e -> {
            refreshProductsTable();
            cardLayout.show(contentPanel, "MANAGE_PRODUCTS");
        });
        
        // Show create order panel by default
        cardLayout.show(contentPanel, "CREATE_ORDER");
    }
    
    private JPanel createViewOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Order Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Orders table setup
        String[] orderColumns = business.getOrderDirectory().getTableColumns();
        ordersTableModel = new DefaultTableModel(orderColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only table
            }
        };
        
        ordersTable = new JTable(ordersTableModel);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordersTable.setRowHeight(25);
        
        JScrollPane ordersScrollPane = new JScrollPane(ordersTable);
        ordersScrollPane.setBorder(BorderFactory.createTitledBorder("All Orders"));
        panel.add(ordersScrollPane, BorderLayout.CENTER);
        
        // Orders button panel
        JPanel ordersButtonPanel = new JPanel(new FlowLayout());
        JButton viewOrderDetailsButton = new JButton("View Details");
        JButton updateOrderStatusButton = new JButton("Update Status");
        JButton deleteOrderButton = new JButton("Delete Order");
        
        viewOrderDetailsButton.addActionListener(new ViewOrderDetailsListener());
        updateOrderStatusButton.addActionListener(new UpdateOrderStatusListener());
        deleteOrderButton.addActionListener(new DeleteOrderListener());
        
        ordersButtonPanel.add(viewOrderDetailsButton);
        ordersButtonPanel.add(updateOrderStatusButton);
        ordersButtonPanel.add(deleteOrderButton);
        panel.add(ordersButtonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Search Customers", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Search form
        JPanel searchFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Search by ID
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        searchFormPanel.add(new JLabel("Search by Customer ID:"), gbc);
        searchIdField = new JTextField(15);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        searchFormPanel.add(searchIdField, gbc);
        
        JButton searchByIdButton = new JButton("Search by ID");
        searchByIdButton.addActionListener(new SearchByIdListener());
        gbc.gridx = 2;
        searchFormPanel.add(searchByIdButton, gbc);
        
        // Search by Name
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        searchFormPanel.add(new JLabel("Search by Name:"), gbc);
        searchNameField = new JTextField(15);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        searchFormPanel.add(searchNameField, gbc);
        
        JButton searchByNameButton = new JButton("Search by Name");
        searchByNameButton.addActionListener(new SearchByNameListener());
        gbc.gridx = 2;
        searchFormPanel.add(searchByNameButton, gbc);
        
        panel.add(searchFormPanel, BorderLayout.NORTH);
        
        // Customers table
        String[] customerColumns = business.getCustomerTableColumns();
        customersTableModel = new DefaultTableModel(customerColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        customersTable = new JTable(customersTableModel);
        customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customersTable.setRowHeight(25);
        
        JScrollPane customersScrollPane = new JScrollPane(customersTable);
        customersScrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));
        panel.add(customersScrollPane, BorderLayout.CENTER);
        
        // Customer management buttons
        JPanel customerButtonPanel = new JPanel(new FlowLayout());
        JButton viewCustomerDetailsButton = new JButton("View Customer Details");
        JButton deleteCustomerButton = new JButton("Delete Customer");
        JButton showAllCustomersButton = new JButton("Show All Customers");
        
        viewCustomerDetailsButton.addActionListener(new ViewCustomerDetailsListener());
        deleteCustomerButton.addActionListener(new DeleteCustomerListener());
        showAllCustomersButton.addActionListener(e -> refreshCustomersTable());
        
        customerButtonPanel.add(viewCustomerDetailsButton);
        customerButtonPanel.add(deleteCustomerButton);
        customerButtonPanel.add(showAllCustomersButton);
        panel.add(customerButtonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createManageProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Product Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Products table
        String[] productColumns = business.getProductCatalog().getTableColumns();
        productsTableModel = new DefaultTableModel(productColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        productsTable = new JTable(productsTableModel);
        productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productsTable.setRowHeight(25);
        
        JScrollPane productsScrollPane = new JScrollPane(productsTable);
        productsScrollPane.setBorder(BorderFactory.createTitledBorder("Product Catalog"));
        panel.add(productsScrollPane, BorderLayout.CENTER);
        
        // Product management buttons
        JPanel productButtonPanel = new JPanel(new FlowLayout());
        JButton addProductButton = new JButton("Add Product");
        JButton updateProductButton = new JButton("Update Product");
        JButton deleteProductButton = new JButton("Delete Product");
        
        addProductButton.addActionListener(new AddProductListener());
        updateProductButton.addActionListener(new UpdateProductListener());
        deleteProductButton.addActionListener(new DeleteProductListener());
        
        productButtonPanel.add(addProductButton);
        productButtonPanel.add(updateProductButton);
        productButtonPanel.add(deleteProductButton);
        panel.add(productButtonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Order management listeners
    private class ViewOrderDetailsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = ordersTable.getSelectedRow();
            if (selectedRow >= 0) {
                int orderId = (int) ordersTable.getValueAt(selectedRow, 0);
                Order order = business.getOrderDirectory().searchOrderById(orderId);
                
                if (order != null) {
                    showOrderDetails(order);
                }
            } else {
                JOptionPane.showMessageDialog(MainJPanel.this, 
                    "Please select an order to view details", 
                    "No Selection", 
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private class UpdateOrderStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = ordersTable.getSelectedRow();
            if (selectedRow >= 0) {
                int orderId = (int) ordersTable.getValueAt(selectedRow, 0);
                Order order = business.getOrderDirectory().searchOrderById(orderId);
                
                if (order != null) {
                    String[] statusOptions = {"Pending", "Preparing", "Ready", "Completed"};
                    String newStatus = (String) JOptionPane.showInputDialog(
                        MainJPanel.this,
                        "Select new order status:",
                        "Update Order Status",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        statusOptions,
                        order.getOrderStatus()
                    );
                    
                    if (newStatus != null) {
                        order.setOrderStatus(newStatus);
                        refreshOrdersTable();
                        JOptionPane.showMessageDialog(MainJPanel.this, 
                            "Order status updated to: " + newStatus);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(MainJPanel.this, 
                    "Please select an order to update");
            }
        }
    }
    
    private class DeleteOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = ordersTable.getSelectedRow();
            if (selectedRow >= 0) {
                int result = JOptionPane.showConfirmDialog(MainJPanel.this, 
                    "Are you sure you want to delete this order?", 
                    "Confirm Delete", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (result == JOptionPane.YES_OPTION) {
                    int orderId = (int) ordersTable.getValueAt(selectedRow, 0);
                    Order order = business.getOrderDirectory().searchOrderById(orderId);
                    
                    if (order != null) {
                        business.getOrderDirectory().removeOrder(order);
                        refreshOrdersTable();
                        JOptionPane.showMessageDialog(MainJPanel.this, 
                            "Order deleted successfully!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(MainJPanel.this, 
                    "Please select an order to delete");
            }
        }
    }
    
    // Customer search listeners
    private class SearchByIdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idText = searchIdField.getText().trim();
            
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(MainJPanel.this, 
                    "Please enter a Customer ID", 
                    "Input Required", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                int customerId = Integer.parseInt(idText);
                Customer customer = business.searchCustomerById(customerId);
                
                if (customer != null) {
                    displayCustomerSearchResults(java.util.Arrays.asList(customer));
                    showCustomerProfile(customer);
                } else {
                    JOptionPane.showMessageDialog(MainJPanel.this, 
                        "No customer found with ID: " + customerId, 
                        "Customer Not Found", 
                        JOptionPane.INFORMATION_MESSAGE);
                    customersTableModel.setRowCount(0);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainJPanel.this, 
                    "Please enter a valid numeric Customer ID", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private class SearchByNameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = searchNameField.getText().trim();
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(MainJPanel.this, 
                    "Please enter a customer name", 
                    "Input Required", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            ArrayList<Customer> results = business.searchCustomersByName(name);
            
            if (!results.isEmpty()) {
                displayCustomerSearchResults(results);
                
                if (results.size() == 1) {
                    showCustomerProfile(results.get(0));
                } else {
                    JOptionPane.showMessageDialog(MainJPanel.this, 
                        "Found " + results.size() + " customers matching '" + name + "'.\n" +
                        "Select a customer from the table to view details.", 
                        "Multiple Results", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(MainJPanel.this, 
                    "No customers found with name containing: " + name, 
                    "No Results", 
                    JOptionPane.INFORMATION_MESSAGE);
                customersTableModel.setRowCount(0);
            }
        }
    }
    
    private class ViewCustomerDetailsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = customersTable.getSelectedRow();
            if (selectedRow >= 0) {
                int customerId = (int) customersTable.getValueAt(selectedRow, 0);
                Customer customer = business.searchCustomerById(customerId);
                
                if (customer != null) {
                    showCustomerProfile(customer);
                }
            } else {
                JOptionPane.showMessageDialog(MainJPanel.this, 
                    "Please select a customer to view details");
            }
        }
    }
    
    private class DeleteCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = customersTable.getSelectedRow();
            if (selectedRow >= 0) {
                int result = JOptionPane.showConfirmDialog(MainJPanel.this, 
                    "Are you sure you want to delete this customer?\n" +
                    "This will also delete all their orders.", 
                    "Confirm Delete", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (result == JOptionPane.YES_OPTION) {
                    int customerId = (int) customersTable.getValueAt(selectedRow, 0);
                    Customer customer = business.searchCustomerById(customerId);
                    
                    if (customer != null) {
                        // Remove customer's orders first
                        ArrayList<Order> customerOrders = business.getOrderDirectory().searchOrdersByCustomerId(customerId);
                        for (Order order : customerOrders) {
                            business.getOrderDirectory().removeOrder(order);
                        }
                        
                        // Remove customer
                        business.removeCustomer(customer);
                        refreshCustomersTable();
                        
                        JOptionPane.showMessageDialog(MainJPanel.this, 
                            "Customer and their orders deleted successfully!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(MainJPanel.this, 
                    "Please select a customer to delete");
            }
        }
    }
    
    // Product management listeners
    private class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showAddProductDialog();
        }
    }
    
    private class UpdateProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = productsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int productId = (int) productsTable.getValueAt(selectedRow, 0);
                Product product = business.getProductCatalog().searchProductById(productId);
                
                if (product != null) {
                    showUpdateProductDialog(product);
                }
            } else {
                JOptionPane.showMessageDialog(MainJPanel.this, 
                    "Please select a product to update");
            }
        }
    }
    
    private class DeleteProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = productsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int result = JOptionPane.showConfirmDialog(MainJPanel.this, 
                    "Are you sure you want to delete this product?", 
                    "Confirm Delete", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (result == JOptionPane.YES_OPTION) {
                    int productId = (int) productsTable.getValueAt(selectedRow, 0);
                    Product product = business.getProductCatalog().searchProductById(productId);
                    
                    if (product != null) {
                        business.getProductCatalog().removeProduct(product);
                        refreshProductsTable();
                        createOrderPanel.refreshData(); // Update product combo in create order
                        
                        JOptionPane.showMessageDialog(MainJPanel.this, 
                            "Product deleted successfully!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(MainJPanel.this, 
                    "Please select a product to delete");
            }
        }
    }
    
    // Helper methods
    private void refreshOrdersTable() {
        ordersTableModel.setRowCount(0);
        Object[][] data = business.getOrderDirectory().getTableData();
        for (Object[] row : data) {
            ordersTableModel.addRow(row);
        }
    }
    
    private void refreshCustomersTable() {
        customersTableModel.setRowCount(0);
        Object[][] data = business.getCustomerTableData();
        for (Object[] row : data) {
            customersTableModel.addRow(row);
        }
    }
    
    private void refreshProductsTable() {
        productsTableModel.setRowCount(0);
        Object[][] data = business.getProductCatalog().getTableData();
        for (Object[] row : data) {
            productsTableModel.addRow(row);
        }
    }
    
    private void displayCustomerSearchResults(java.util.List<Customer> customers) {
        customersTableModel.setRowCount(0);
        for (Customer customer : customers) {
            Object[] row = {
                customer.getCustomerId(),
                customer.getCustomerFirstName(),
                customer.getCustomerLastName(),
                customer.getContact(),
                customer.getTotalOrders()
            };
            customersTableModel.addRow(row);
        }
    }
    
    private void showCustomerProfile(Customer customer) {
        StringBuilder profile = new StringBuilder();
        profile.append("Customer Profile:\n\n");
        profile.append("ID: ").append(customer.getCustomerId()).append("\n");
        profile.append("Name: ").append(customer.getFullName()).append("\n");
        profile.append("Contact: ").append(customer.getContact()).append("\n");
        profile.append("Loyalty Member: ").append(customer.isLoyaltyMember() ? "Yes" : "No").append("\n");
        profile.append("Total Orders: ").append(customer.getTotalOrders()).append("\n");
        profile.append("Total Spent: $").append(String.format("%.2f", customer.getTotalSpent())).append("\n\n");
        
        profile.append("Order History:\n");
        for (Order order : customer.getOrderHistory()) {
            profile.append("- Order #").append(order.getOrderId())
                   .append(" (").append(order.getProductName()).append(") - ")
                   .append(order.getOrderStatus()).append("\n");
        }
        
        JTextArea textArea = new JTextArea(profile.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(MainJPanel.this, 
            scrollPane, 
            "Customer Profile - " + customer.getFullName(), 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showOrderDetails(Order order) {
        StringBuilder details = new StringBuilder();
        details.append("Order Details:\n\n");
        details.append("Order ID: ").append(order.getOrderId()).append("\n");
        details.append("Date/Time: ").append(order.getOrderDateTime()).append("\n");
        details.append("Customer: ").append(order.getCustomerName()).append("\n");
        details.append("Product: ").append(order.getProductName()).append("\n");
        details.append("Order Type: ").append(order.getOrderType()).append("\n");
        details.append("Payment Method: ").append(order.getPaymentMethod()).append("\n");
        details.append("Status: ").append(order.getOrderStatus()).append("\n");
        details.append("Total Amount: $").append(String.format("%.2f", order.getTotalAmount()));
        
        JOptionPane.showMessageDialog(MainJPanel.this, 
            details.toString(), 
            "Order Details", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showAddProductDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Add Product", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Product form fields
        JTextField nameField = new JTextField(15);
        JTextField categoryField = new JTextField(15);
        JTextField priceField = new JTextField(15);
        JTextField stockField = new JTextField(15);
        JTextField prepTimeField = new JTextField(15);
        
        // Add form components
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(categoryField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(priceField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(stockField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("Prep Time (min):"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(prepTimeField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    String category = categoryField.getText().trim();
                    double price = Double.parseDouble(priceField.getText().trim());
                    int stock = Integer.parseInt(stockField.getText().trim());
                    int prepTime = Integer.parseInt(prepTimeField.getText().trim());
                    
                    if (name.isEmpty() || category.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Name and Category are required", 
                            "Validation Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (price <= 0 || stock < 0 || prepTime <= 0) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Price and Prep Time must be positive, Stock cannot be negative", 
                            "Validation Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    Product product = new Product(name, category, price, stock, prepTime);
                    business.getProductCatalog().addProduct(product);
                    refreshProductsTable();
                    createOrderPanel.refreshData();
                    
                    JOptionPane.showMessageDialog(dialog, "Product added successfully!");
                    dialog.dispose();
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Please enter valid numbers for Price, Stock, and Prep Time", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void showUpdateProductDialog(Product product) {
        // Similar to add dialog but pre-populated with current values
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Update Product", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Pre-populate fields
        JTextField nameField = new JTextField(product.getProductName(), 15);
        JTextField categoryField = new JTextField(product.getCategory(), 15);
        JTextField priceField = new JTextField(String.valueOf(product.getPrice()), 15);
        JTextField stockField = new JTextField(String.valueOf(product.getNumber()), 15);
        JTextField prepTimeField = new JTextField(String.valueOf(product.getPreparationTime()), 15);
        
        // Add form components (same layout as add dialog)
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(categoryField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(priceField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(stockField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("Prep Time (min):"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(prepTimeField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    String category = categoryField.getText().trim();
                    double price = Double.parseDouble(priceField.getText().trim());
                    int stock = Integer.parseInt(stockField.getText().trim());
                    int prepTime = Integer.parseInt(prepTimeField.getText().trim());
                    
                    if (name.isEmpty() || category.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Name and Category are required", 
                            "Validation Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (price <= 0 || stock < 0 || prepTime <= 0) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Price and Prep Time must be positive, Stock cannot be negative", 
                            "Validation Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Update product
                    product.setProductName(name);
                    product.setCategory(category);
                    product.setPrice(price);
                    product.setNumber(stock);
                    product.setPreparationTime(prepTime);
                    
                    refreshProductsTable();
                    createOrderPanel.refreshData();
                    
                    JOptionPane.showMessageDialog(dialog, "Product updated successfully!");
                    dialog.dispose();
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Please enter valid numbers for Price, Stock, and Prep Time", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    public Business getBusiness() {
        return business;
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
}
