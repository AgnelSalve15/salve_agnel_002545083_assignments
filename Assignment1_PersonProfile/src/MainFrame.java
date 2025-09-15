import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JSplitPane splitPane;
    private JPanel buttonPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    // Panels for different views
    private PersonPanel personPanel;
    private BankAccountPanel bankAccountPanel;
    private AddressPanel addressPanel;
    
    // Data objects
    private Person currentPerson;
    private BankAccount currentBankAccount;
    private Address currentAddress;
    
    public MainFrame() {
        initializeData();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeData() {
        currentPerson = new Person();
        currentBankAccount = new BankAccount();
        currentAddress = new Address();
    }
    
    private void initializeComponents() {
        setTitle("Person Profile Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Create button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setPreferredSize(new Dimension(200, 0));
        
        // Create content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        
        // Create individual panels
        personPanel = new PersonPanel(currentPerson);
        bankAccountPanel = new BankAccountPanel(currentBankAccount);
        addressPanel = new AddressPanel(currentAddress);
        
        // Add panels to content panel
        contentPanel.add(personPanel, "PERSON");
        contentPanel.add(bankAccountPanel, "BANKACCOUNT");
        contentPanel.add(addressPanel, "ADDRESS");
        
        // Create split pane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buttonPanel, contentPanel);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.0); // Keep button panel fixed width
    }
    
    private void setupLayout() {
        // Create buttons
        JButton personButton = new JButton("Person Profile");
        JButton bankAccountButton = new JButton("Bank Account");
        JButton addressButton = new JButton("Address");
        JButton saveAllButton = new JButton("Save All Data");
        JButton clearAllButton = new JButton("Clear All Data");
        
        // Style buttons
        Dimension buttonSize = new Dimension(180, 40);
        personButton.setPreferredSize(buttonSize);
        bankAccountButton.setPreferredSize(buttonSize);
        addressButton.setPreferredSize(buttonSize);
        saveAllButton.setPreferredSize(buttonSize);
        clearAllButton.setPreferredSize(buttonSize);
        
        // Add buttons to panel
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(personButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(bankAccountButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(addressButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(new JSeparator());
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(saveAllButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(clearAllButton);
        buttonPanel.add(Box.createVerticalGlue());
        
        // Add split pane to frame
        add(splitPane, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        // Get buttons from button panel
        Component[] components = buttonPanel.getComponents();
        
        for (Component comp : components) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.addActionListener(new ButtonClickListener());
            }
        }
    }
    
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();
            
            switch (buttonText) {
                case "Person Profile":
                    cardLayout.show(contentPanel, "PERSON");
                    break;
                case "Bank Account":
                    cardLayout.show(contentPanel, "BANKACCOUNT");
                    break;
                case "Address":
                    cardLayout.show(contentPanel, "ADDRESS");
                    break;
                case "Save All Data":
                    saveAllData();
                    break;
                case "Clear All Data":
                    clearAllData();
                    break;
            }
        }
    }
    
    private void saveAllData() {
        // Update objects with current panel data
        personPanel.updatePersonData();
        bankAccountPanel.updateBankAccountData();
        addressPanel.updateAddressData();
        
        // Show confirmation
        JOptionPane.showMessageDialog(this, 
            "All data has been saved successfully!", 
            "Save Confirmation", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // Print data to console (for demonstration)
        System.out.println("=== SAVED DATA ===");
        System.out.println(currentPerson.toString());
        System.out.println(currentBankAccount.toString());
        System.out.println(currentAddress.toString());
    }
    
    private void clearAllData() {
        int result = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to clear all data?",
            "Clear Confirmation",
            JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            // Reset all objects
            currentPerson = new Person();
            currentBankAccount = new BankAccount();
            currentAddress = new Address();
            
            // Update panels with cleared data
            personPanel.setPerson(currentPerson);
            bankAccountPanel.setBankAccount(currentBankAccount);
            addressPanel.setAddress(currentAddress);
            
            JOptionPane.showMessageDialog(this,
                "All data has been cleared!",
                "Clear Confirmation",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new MainFrame().setVisible(true);
        });
    }
}