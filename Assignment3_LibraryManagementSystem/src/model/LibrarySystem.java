package model;

import java.util.ArrayList;

public class LibrarySystem {
    private static LibrarySystem instance;
    private UserAccountDirectory userDirectory;
    private AuthorDirectory authorDirectory;
    private ArrayList<Branch> branches;
    
    private LibrarySystem() {
        this.userDirectory = new UserAccountDirectory();
        this.authorDirectory = new AuthorDirectory();
        this.branches = new ArrayList<>();
    }
    
    public static LibrarySystem getInstance() {
        if (instance == null) {
            instance = new LibrarySystem();
        }
        return instance;
    }
    
    public UserAccountDirectory getUserDirectory() { return userDirectory; }
    public AuthorDirectory getAuthorDirectory() { return authorDirectory; }
    public ArrayList<Branch> getBranches() { return branches; }
    
    public void addBranch(Branch branch) {
        branches.add(branch);
    }
    
    public void removeBranch(Branch branch) {
        branches.remove(branch);
    }
    
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> allBooks = new ArrayList<>();
        for (Branch branch : branches) {
            allBooks.addAll(branch.getLibrary().getBooks());
        }
        return allBooks;
    }
}
