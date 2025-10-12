package model;

public class Branch {
    private static int idCounter = 4000;
    private int branchId;
    private String name;
    private Library library;
    
    public Branch(String name) {
        this.branchId = idCounter++;
        this.name = name;
        this.library = new Library(this);
    }
    
    public int getBranchId() { return branchId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Library getLibrary() { return library; }
    public void setLibrary(Library library) { this.library = library; }
}