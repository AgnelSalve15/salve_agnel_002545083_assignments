package model;

import java.util.ArrayList;

public class Library {
    private static int idCounter = 6000;
    private int libraryId;
    private String buildingNo;
    private Branch branch;
    private BranchManager branchManager;
    private ArrayList<Book> books;
    private ArrayList<RentalRecord> rentalRecords;
    
    public Library(Branch branch) {
        this.libraryId = idCounter++;
        this.branch = branch;
        this.books = new ArrayList<>();
        this.rentalRecords = new ArrayList<>();
    }
    
    public int getLibraryId() { return libraryId; }
    public String getBuildingNo() { return buildingNo; }
    public void setBuildingNo(String buildingNo) { this.buildingNo = buildingNo; }
    public Branch getBranch() { return branch; }
    public BranchManager getBranchManager() { return branchManager; }
    public void setBranchManager(BranchManager manager) { 
        this.branchManager = manager;
        if (manager != null) {
            manager.setLibrary(this);
        }
    }
    public ArrayList<Book> getBooks() { return books; }
    public ArrayList<RentalRecord> getRentalRecords() { return rentalRecords; }
    
    public void addBook(Book book) {
        books.add(book);
    }
    
    public void addRentalRecord(RentalRecord record) {
        rentalRecords.add(record);
    }
}