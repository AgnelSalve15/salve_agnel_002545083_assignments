package model;

public class BranchManager extends Employee {
    private Library library;
    
    public BranchManager(String name, String username, String password, int experience) {
        super(name, username, password, experience);
    }
 
    
    @Override
    public String getRole() {
        return "BranchManager";
    }
    
    public Library getLibrary() { return library; }
    public void setLibrary(Library library) { this.library = library; }
}