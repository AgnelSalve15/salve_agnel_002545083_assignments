package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import model.*;

public class SystemAdminWorkArea extends JFrame {
    private SystemAdmin admin;
    private LibrarySystem system;
    private JTabbedPane tabbedPane;
    
    public SystemAdminWorkArea(SystemAdmin admin, LibrarySystem system) {
        this.admin = admin;
        this.system = system;
        
        initComponents();
    }
    
    private void initComponents() {
        setTitle("System Admin Dashboard - " + admin.getName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Manage Branches", createBranchPanel());
        tabbedPane.addTab("Manage Employees", createEmployeePanel());
        tabbedPane.addTab("Manage Customers", createCustomerPanel());
        
        add(tabbedPane);
    }
    
    private JPanel createBranchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"Branch ID", "Name", "Building No", "Manager"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        refreshBranchTable(tableModel);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Branch");
        JButton deleteButton = new JButton("Delete Branch");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Branch Name:");
            if (name != null && !name.trim().isEmpty()) {
                Branch branch = new Branch(name);
                String buildingNo = JOptionPane.showInputDialog(this, "Building No:");
                branch.getLibrary().setBuildingNo(buildingNo != null ? buildingNo : "");
                system.addBranch(branch);
                refreshBranchTable(tableModel);
                JOptionPane.showMessageDialog(this, "Branch added successfully!");
            }
        });
        
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int branchId = (int) tableModel.getValueAt(selectedRow, 0);
                Branch toRemove = null;
                for (Branch branch : system.getBranches()) {
                    if (branch.getBranchId() == branchId) {
                        toRemove = branch;
                        break;
                    }
                }
                if (toRemove != null) {
                    int confirm = JOptionPane.showConfirmDialog(this, 
                        "Are you sure you want to delete this branch?", 
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        system.removeBranch(toRemove);
                        refreshBranchTable(tableModel);
                        JOptionPane.showMessageDialog(this, "Branch deleted successfully!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a branch to delete!");
            }
        });
        
        refreshButton.addActionListener(e -> refreshBranchTable(tableModel));
        
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshBranchTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        for (Branch branch : system.getBranches()) {
            String managerName = branch.getLibrary().getBranchManager() != null ? 
                branch.getLibrary().getBranchManager().getName() : "None";
            tableModel.addRow(new Object[]{
                branch.getBranchId(),
                branch.getName(),
                branch.getLibrary().getBuildingNo(),
                managerName
            });
        }
    }
    
    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"Employee ID", "Name", "Username", "Experience", "Branch"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        
        refreshEmployeeTable(tableModel);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Employee");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> {
            if (system.getBranches().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Create branches first!");
                return;
            }
            
            String name = JOptionPane.showInputDialog(this, "Employee Name:");
            if (name == null || name.trim().isEmpty()) return;
            
            String username = JOptionPane.showInputDialog(this, "Username:");
            if (username == null || username.trim().isEmpty()) return;
            
            String password = JOptionPane.showInputDialog(this, "Password:");
            if (password == null || password.trim().isEmpty()) return;
            
            String expStr = JOptionPane.showInputDialog(this, "Experience (years):");
            if (expStr == null || expStr.trim().isEmpty()) return;
            
            String[] branchNames = system.getBranches().stream()
                .map(Branch::getName).toArray(String[]::new);
            String selectedBranch = (String) JOptionPane.showInputDialog(this, "Select Branch:",
                "Branch Selection", JOptionPane.QUESTION_MESSAGE, null, branchNames, branchNames[0]);
            
            if (selectedBranch != null) {
                try {
                    int experience = Integer.parseInt(expStr);
                    BranchManager manager = new BranchManager(name, username, password, experience);
                    
                    for (Branch branch : system.getBranches()) {
                        if (branch.getName().equals(selectedBranch)) {
                            branch.getLibrary().setBranchManager(manager);
                            break;
                        }
                    }
                    
                    system.getUserDirectory().addUser(manager);
                    refreshEmployeeTable(tableModel);
                    JOptionPane.showMessageDialog(this, "Employee added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid experience value!");
                }
            }
        });
        
        refreshButton.addActionListener(e -> refreshEmployeeTable(tableModel));
        
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshEmployeeTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        for (Person user : system.getUserDirectory().getUsers()) {
            if (user instanceof BranchManager) {
                BranchManager manager = (BranchManager) user;
                String branchName = manager.getLibrary() != null ? 
                    manager.getLibrary().getBranch().getName() : "Unassigned";
                tableModel.addRow(new Object[]{
                    manager.getEmployeeId(),
                    manager.getName(),
                    manager.getUsername(),
                    manager.getExperience(),
                    branchName
                });
            }
        }
    }
    
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"Customer ID", "Name", "Username"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        
        refreshCustomerTable(tableModel);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Register Customer");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Customer Name:");
            if (name == null || name.trim().isEmpty()) return;
            
            String username = JOptionPane.showInputDialog(this, "Username:");
            if (username == null || username.trim().isEmpty()) return;
            
            String password = JOptionPane.showInputDialog(this, "Password:");
            if (password == null || password.trim().isEmpty()) return;
            
            Customer customer = new Customer(name, username, password);
            system.getUserDirectory().addUser(customer);
            refreshCustomerTable(tableModel);
            JOptionPane.showMessageDialog(this, "Customer registered successfully!");
        });
        
        refreshButton.addActionListener(e -> refreshCustomerTable(tableModel));
        
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshCustomerTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        for (Person user : system.getUserDirectory().getUsers()) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                tableModel.addRow(new Object[]{
                    customer.getId(),
                    customer.getName(),
                    customer.getUsername()
                });
            }
        }
    }
}