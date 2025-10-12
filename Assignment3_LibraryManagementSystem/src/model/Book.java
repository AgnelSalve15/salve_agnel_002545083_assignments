package model;

import java.time.LocalDate;

public class Book {
    private static int serialCounter = 3000;
    private int serialNumber;
    private String name;
    private LocalDate registeredDate;
    private int numberOfPages;
    private String language;
    private boolean isRented;
    private Author author;
    
    public Book(String name, int numberOfPages, String language, Author author) {
        this.serialNumber = serialCounter++;
        this.name = name;
        this.registeredDate = LocalDate.now();
        this.numberOfPages = numberOfPages;
        this.language = language;
        this.isRented = false;
        this.author = author;
    }
    
    public int getSerialNumber() { return serialNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getRegisteredDate() { return registeredDate; }
    public int getNumberOfPages() { return numberOfPages; }
    public void setNumberOfPages(int numberOfPages) { this.numberOfPages = numberOfPages; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public boolean isRented() { return isRented; }
    public void setRented(boolean rented) { isRented = rented; }
    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}