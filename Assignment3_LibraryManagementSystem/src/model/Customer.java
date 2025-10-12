package model;

import java.util.ArrayList;

public class Customer extends Person {
    private ArrayList<RentalRecord> rentalHistory;
    
    public Customer(String name, String username, String password) {
        super(name, username, password);
        this.rentalHistory = new ArrayList<>();
    }
    
    @Override
    public String getRole() {
        return "Customer";
    }
    
    public ArrayList<RentalRecord> getRentalHistory() { return rentalHistory; }
    
    public void addRentalRecord(RentalRecord record) {
        rentalHistory.add(record);
    }
}