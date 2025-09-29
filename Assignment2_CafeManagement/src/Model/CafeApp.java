package model;

import ui.MainJPanel;
import javax.swing.*;
import java.awt.Dimension;

public class CafeApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Set system look and feel for better appearance
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
                } catch (Exception e) {
                    // Use default if system L&F fails
                    System.out.println("Using default look and feel");
                }
                
                // Create main application window
                JFrame frame = new JFrame("Coffee Shop Management System - Assignment 2");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 700);
                frame.setLocationRelativeTo(null);
                frame.setMinimumSize(new Dimension(800, 600));
                
                // Create and add main panel
                MainJPanel mainPanel = new MainJPanel();
                frame.add(mainPanel);
                
                // Show the application
                frame.setVisible(true);
                
                // Console output for verification
                System.out.println("Coffee Shop Management System started successfully!");
                System.out.println("Assignment 2 - Ready for testing");
                System.out.println("Sample data loaded:");
                System.out.println("- 7 Products available");
                System.out.println("- 4 Sample customers created");
                System.out.println("- 4 Sample orders created");
                System.out.println("Ready for CRUD operations testing!");
            }
        });
    }
}