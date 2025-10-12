package ui;

import javax.swing.*;
import model.*;
import config.ConfigureTheBusiness;

public class MainFrame extends JFrame {
    private LibrarySystem system;
    private Person loggedInUser;
    
    public MainFrame() {
        ConfigureTheBusiness.configure();
        system = LibrarySystem.getInstance();
        
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Library Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        showLoginPanel();
    }
    
    private void showLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel titleLabel = new JLabel("Library Management System");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        
        JTextField usernameField = new JTextField(20);
        usernameField.setMaximumSize(new java.awt.Dimension(200, 25));
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new java.awt.Dimension(200, 25));
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            Person user = system.getUserDirectory().authenticate(username, password);
            
            if (user != null) {
                loggedInUser = user;
                openWorkArea();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(loginButton);
        
        panel.add(loginPanel);
        setContentPane(panel);
        revalidate();
    }
    
    private void openWorkArea() {
        if (loggedInUser instanceof SystemAdmin) {
            new SystemAdminWorkArea((SystemAdmin) loggedInUser, system).setVisible(true);
        } else if (loggedInUser instanceof BranchManager) {
            new BranchManagerWorkArea((BranchManager) loggedInUser, system).setVisible(true);
        } else if (loggedInUser instanceof Customer) {
            new CustomerWorkArea((Customer) loggedInUser, system).setVisible(true);
        }
        dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
