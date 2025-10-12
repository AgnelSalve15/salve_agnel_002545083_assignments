package model;

import java.time.LocalDateTime;

public class RentalRecord {
    private static int idCounter = 7000;
    private int id;
    private Book book;
    private Customer customer;
    private Library library;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private RentalStatus status;
    
    public enum RentalStatus {
        RENTED, RETURNED
    }
    
    public RentalRecord(Book book, Customer customer, Library library) {
        this.id = idCounter++;
        this.book = book;
        this.customer = customer;
        this.library = library;
        this.rentedAt = LocalDateTime.now();
        this.status = RentalStatus.RENTED;
    }
    
    
    public int getId() { return id; }
    public Book getBook() { return book; }
    public Customer getCustomer() { return customer; }
    public Library getLibrary() { return library; }
    public LocalDateTime getRentedAt() { return rentedAt; }
    public LocalDateTime getReturnedAt() { return returnedAt; }
    public void setReturnedAt(LocalDateTime returnedAt) { this.returnedAt = returnedAt; }
    public RentalStatus getStatus() { return status; }
    public void setStatus(RentalStatus status) { this.status = status; }
}