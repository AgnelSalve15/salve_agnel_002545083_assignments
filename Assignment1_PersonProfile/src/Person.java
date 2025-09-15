public class Person {
    // At least 5 String attributes as required
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String occupation;
    private String nationality;
    
    // Default constructor
    public Person() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phoneNumber = "";
        this.dateOfBirth = "";
        this.occupation = "";
        this.nationality = "";
    }
    
    // Parameterized constructor
    public Person(String firstName, String lastName, String email, 
                  String phoneNumber, String dateOfBirth, String occupation, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.occupation = occupation;
        this.nationality = nationality;
    }
    
    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getOccupation() { return occupation; }
    public String getNationality() { return nationality; }
    
    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setOccupation(String occupation) { this.occupation = occupation; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    
    // Get full name method
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    // toString method for easy display
    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", occupation='" + occupation + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}