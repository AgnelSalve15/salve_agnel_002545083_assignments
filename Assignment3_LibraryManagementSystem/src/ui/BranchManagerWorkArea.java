package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import model.*;

public class BranchManagerWorkArea extends JFrame {
    private BranchManager manager;
    private LibrarySystem system;
    private JTabbedPane tabbedPane;
    
    public BranchManagerWorkArea(BranchManager manager, LibrarySystem system) {
        this.manager = manager;
        this.system = system;
        
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Branch Manager Dashboard - " + manager.getName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Manage Books", createBookPanel());
        tabbedPane.addTab("Manage Authors", createAuthorPanel());
        tabbedPane.addTab("Rental Records", createRentalRecordPanel());
        
        add(tabbedPane);
    }
    
    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"Serial No", "Name", "Pages", "Language", "Author", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        
        refreshBookTable(tableModel);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Book");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> {
            if (system.getAuthorDirectory().getAuthors().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Add authors first!");
                return;
            }
            
            String name = JOptionPane.showInputDialog(this, "Book Name:");
            if (name == null || name.trim().isEmpty()) return;
            
            String pagesStr = JOptionPane.showInputDialog(this, "Number of Pages:");
            if (pagesStr == null || pagesStr.trim().isEmpty()) return;
            
            String language = JOptionPane.showInputDialog(this, "Language:");
            if (language == null || language.trim().isEmpty()) return;
            
            String[] authorNames = system.getAuthorDirectory().getAuthors().stream()
                .map(Author::getName).toArray(String[]::new);
            String selectedAuthor = (String) JOptionPane.showInputDialog(this, "Select Author:",
                "Author Selection", JOptionPane.QUESTION_MESSAGE, null, authorNames, authorNames[0]);
            
            if (selectedAuthor != null) {
                try {
                    int pages = Integer.parseInt(pagesStr);
                    Author author = null;
                    for (Author a : system.getAuthorDirectory().getAuthors()) {
                        if (a.getName().equals(selectedAuthor)) {
                            author = a;
                            break;
                        }
                    }
                    
                    Book book = new Book(name, pages, language, author);
                    manager.getLibrary().addBook(book);
                    refreshBookTable(tableModel);
                    JOptionPane.showMessageDialog(this, "Book added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid page number!");
                }
            }
        });
        
        refreshButton.addActionListener(e -> refreshBookTable(tableModel));
        
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshBookTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        if (manager.getLibrary() != null) {
            for (Book book : manager.getLibrary().getBooks()) {
                tableModel.addRow(new Object[]{
                    book.getSerialNumber(),
                    book.getName(),
                    book.getNumberOfPages(),
                    book.getLanguage(),
                    book.getAuthor().getName(),
                    book.isRented() ? "Rented" : "Available"
                });
            }
        }
    }
    
    private JPanel createAuthorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"Author ID", "Name", "Nationality"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        
        refreshAuthorTable(tableModel);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Author");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Author Name:");
            if (name == null || name.trim().isEmpty()) return;
            
            String nationality = JOptionPane.showInputDialog(this, "Nationality:");
            if (nationality == null || nationality.trim().isEmpty()) return;
            
            Author author = new Author(name, nationality);
            system.getAuthorDirectory().addAuthor(author);
            refreshAuthorTable(tableModel);
            JOptionPane.showMessageDialog(this, "Author added successfully!");
        });
        
        refreshButton.addActionListener(e -> refreshAuthorTable(tableModel));
        
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshAuthorTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        for (Author author : system.getAuthorDirectory().getAuthors()) {
            tableModel.addRow(new Object[]{
                author.getAuthorId(),
                author.getName(),
                author.getNationality()
            });
        }
    }
    
    private JPanel createRentalRecordPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"Record ID", "Book", "Customer", "Rented At", "Returned At", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        
        refreshRentalRecordTable(tableModel);
        
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        
        refreshButton.addActionListener(e -> refreshRentalRecordTable(tableModel));
        
        buttonPanel.add(refreshButton);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshRentalRecordTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        if (manager.getLibrary() != null) {
            for (RentalRecord record : manager.getLibrary().getRentalRecords()) {
                String returnedAt = record.getReturnedAt() != null ? 
                    record.getReturnedAt().toString() : "Not Returned";
                tableModel.addRow(new Object[]{
                    record.getId(),
                    record.getBook().getName(),
                    record.getCustomer().getName(),
                    record.getRentedAt().toString(),
                    returnedAt,
                    record.getStatus()
                });
            }
        }
    }
}
