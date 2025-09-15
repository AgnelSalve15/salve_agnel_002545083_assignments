import javax.swing.*;
import java.awt.*;

public class PersonPanel extends JPanel {
    private Person person;
    
    // Text fields for all person attributes
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField dobField;
    private JTextField occupationField;
    private JTextField nationalityField;
    
    public PersonPanel(Person person) {
        this.person = person;
        initializeComponents();
        setupLayout();
        loadPersonData();
    }
    
    private void initializeComponents() {
        // Initialize text fields
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        dobField = new JTextField(20);
        occupationField = new JTextField(20);
        nationalityField = new JTextField(20);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Person Information"));
        
        // Create main panel with form layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Add form fields
        int row = 0;
        
        // First Name
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(firstNameField, gbc);
        
        // Last Name
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(lastNameField, gbc);
        
        // Email
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);
        
        // Phone
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);
        
        // Date of Birth
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        formPanel.add(dobField, gbc);
        
        // Occupation
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Occupation:"), gbc);
        gbc.gridx = 1;
        formPanel.add(occupationField, gbc);
        
        // Nationality
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Nationality:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nationalityField, gbc);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateButton = new JButton("Update Person");
        JButton clearButton = new JButton("Clear Fields");
        
        updateButton.addActionListener(e -> updatePersonData());
        clearButton.addActionListener(e -> clearFields());
        
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);
        
        // Add components to main panel
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add some padding
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Person Information"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }
    
    private void loadPersonData() {
        if (person != null) {
            firstNameField.setText(person.getFirstName());
            lastNameField.setText(person.getLastName());
            emailField.setText(person.getEmail());
            phoneField.setText(person.getPhoneNumber());
            dobField.setText(person.getDateOfBirth());
            occupationField.setText(person.getOccupation());
            nationalityField.setText(person.getNationality());
        }
    }
    
    public void updatePersonData() {
        if (person != null) {
            person.setFirstName(firstNameField.getText().trim());
            person.setLastName(lastNameField.getText().trim());
            person.setEmail(emailField.getText().trim());
            person.setPhoneNumber(phoneField.getText().trim());
            person.setDateOfBirth(dobField.getText().trim());
            person.setOccupation(occupationField.getText().trim());
            person.setNationality(nationalityField.getText().trim());
            
            JOptionPane.showMessageDialog(this, 
                "Person data updated successfully!", 
                "Update Confirmation", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        dobField.setText("");
        occupationField.setText("");
        nationalityField.setText("");
    }
    
    public void setPerson(Person person) {
        this.person = person;
        loadPersonData();
    }
    
    public Person getPerson() {
        updatePersonData();
        return person;
    }
}