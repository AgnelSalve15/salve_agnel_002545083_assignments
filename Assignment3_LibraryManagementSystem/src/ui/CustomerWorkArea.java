package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import model.*;

public class CustomerWorkArea extends JFrame {
    private Customer customer;
    private LibrarySystem system;
    private JTabbedPane tabbedPane;
    
    public CustomerWorkArea(Customer customer, LibrarySystem system) {
        this.customer = customer;
        this.system = system;
        
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Customer Dashboard - " + customer.getName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Browse Books", createBrowseBooksPanel());
        tabbedPane.addTab("Return Books", createReturnBooksPanel());
        tabbedPane.addTab("Rental History", createRentalHistoryPanel());
        
        add(tabbedPane);
    }
    
    private JPanel createBrowseBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"Serial No", "Book Name", "Author", "Pages", "Language", "Branch", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        refreshBrowseTable(tableModel);
        
        JPanel buttonPanel = new JPanel();
        JButton rentButton = new JButton("Rent Selected Book");
        JButton refreshButton = new JButton("Refresh");
        
        rentButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String status = (String) tableModel.getValueAt(selectedRow, 6);
                if (status.equals("Available")) {
                    int serialNo = (int) tableModel.getValueAt(selectedRow, 0);
                    rentBook(serialNo);
                    refreshBrowseTable(tableModel);
                } else {
                    JOptionPane.showMessageDialog(this, "This book is currently rented out!", 
                        "Not Available", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to rent!", 
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        refreshButton.addActionListener(e -> refreshBrowseTable(tableModel));
        
        buttonPanel.add(rentButton);
        buttonPanel.add(refreshButton);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshBrowseTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        for (Branch branch : system.getBranches()) {
            for (Book book : branch.getLibrary().getBooks()) {
                tableModel.addRow(new Object[]{
                    book.getSerialNumber(),
                    book.getName(),
                    book.getAuthor().getName(),
                    book.getNumberOfPages(),
                    book.getLanguage(),
                    branch.getName(),
                    book.isRented() ? "Currently Rented Out" : "Available"
                });
            }
        }
    }
    
    private void rentBook(int serialNo) {
        for (Branch branch : system.getBranches()) {
            for (Book book : branch.getLibrary().getBooks()) {
                if (book.getSerialNumber() == serialNo && !book.isRented()) {
                    book.setRented(true);
                    RentalRecord record = new RentalRecord(book, customer, branch.getLibrary());
                    branch.getLibrary().addRentalRecord(record);
                    customer.addRentalRecord(record);
                    JOptionPane.showMessageDialog(this, 
                        "Book rented successfully!\n\nBook: " + book.getName() + 
                        "\nBranch: " + branch.getName(), 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }
    }
    
    private JPanel createReturnBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"Serial No", "Book Name", "Branch", "Rented At", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        refreshReturnTable(tableModel);
        
        JPanel buttonPanel = new JPanel();
        JButton returnButton = new JButton("Return Selected Book");
        JButton refreshButton = new JButton("Refresh");
        
        returnButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String status = (String) tableModel.getValueAt(selectedRow, 4);
                if (status.equals("RENTED")) {
                    int serialNo = (int) tableModel.getValueAt(selectedRow, 0);
                    returnBook(serialNo);
                    refreshReturnTable(tableModel);
                } else {
                    JOptionPane.showMessageDialog(this, "This book is already returned!", 
                        "Already Returned", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to return!", 
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        refreshButton.addActionListener(e -> refreshReturnTable(tableModel));
        
        buttonPanel.add(returnButton);
        buttonPanel.add(refreshButton);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshReturnTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        for (RentalRecord record : customer.getRentalHistory()) {
            if (record.getStatus() == RentalRecord.RentalStatus.RENTED) {
                tableModel.addRow(new Object[]{
                    record.getBook().getSerialNumber(),
                    record.getBook().getName(),
                    record.getLibrary().getBranch().getName(),
                    record.getRentedAt().toString(),
                    record.getStatus()
                });
            }
        }
    }
    
    private void returnBook(int serialNo) {
        for (RentalRecord record : customer.getRentalHistory()) {
            if (record.getBook().getSerialNumber() == serialNo && 
                record.getStatus() == RentalRecord.RentalStatus.RENTED) {
                record.getBook().setRented(false);
                record.setReturnedAt(LocalDateTime.now());
                record.setStatus(RentalRecord.RentalStatus.RETURNED);
                JOptionPane.showMessageDialog(this, 
                    "Book returned successfully!\n\nBook: " + record.getBook().getName() + 
                    "\nReturned at: " + record.getReturnedAt(), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }
    
    private JPanel createRentalHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"Book Name", "Branch", "Rented At", "Returned At", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        
        refreshHistoryTable(tableModel);
        
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        
        refreshButton.addActionListener(e -> refreshHistoryTable(tableModel));
        
        buttonPanel.add(refreshButton);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshHistoryTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        for (RentalRecord record : customer.getRentalHistory()) {
            String returnedAt = record.getReturnedAt() != null ? 
                record.getReturnedAt().toString() : "Not Returned Yet";
            tableModel.addRow(new Object[]{
                record.getBook().getName(),
                record.getLibrary().getBranch().getName(),
                record.getRentedAt().toString(),
                returnedAt,
                record.getStatus()
            });
        }
    }
}
