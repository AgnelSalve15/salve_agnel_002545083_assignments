public class BankAccount {
    // At least 5 String attributes including person's name
    private String personName;
    private String accountNumber;
    private String accountType;
    private String balance;
    private String bankName;
    private String branchCode;
    private String openingDate;
    
    // Default constructor
    public BankAccount() {
        this.personName = "";
        this.accountNumber = "";
        this.accountType = "";
        this.balance = "";
        this.bankName = "";
        this.branchCode = "";
        this.openingDate = "";
    }
    
    // Parameterized constructor
    public BankAccount(String personName, String accountNumber, String accountType,
                      String balance, String bankName, String branchCode, String openingDate) {
        this.personName = personName;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.bankName = bankName;
        this.branchCode = branchCode;
        this.openingDate = openingDate;
    }
    
    // Getters
    public String getPersonName() { return personName; }
    public String getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public String getBalance() { return balance; }
    public String getBankName() { return bankName; }
    public String getBranchCode() { return branchCode; }
    public String getOpeningDate() { return openingDate; }
    
    // Setters
    public void setPersonName(String personName) { this.personName = personName; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
    public void setBalance(String balance) { this.balance = balance; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }
    public void setOpeningDate(String openingDate) { this.openingDate = openingDate; }
    
    // Business methods
    public void deposit(String amount) {
        // In a real application, you'd convert to numbers and do math
        // For this assignment, we're keeping everything as strings
        System.out.println("Depositing " + amount + " to account " + accountNumber);
    }
    
    public void withdraw(String amount) {
        System.out.println("Withdrawing " + amount + " from account " + accountNumber);
    }
    
    @Override
    public String toString() {
        return "BankAccount{" +
                "personName='" + personName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", balance='" + balance + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", openingDate='" + openingDate + '\'' +
                '}';
    }
}