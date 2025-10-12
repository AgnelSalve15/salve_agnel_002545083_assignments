package config;

import model.*;

public class ConfigureTheBusiness {
    
    public static void configure() {
        LibrarySystem system = LibrarySystem.getInstance();
        
        // Create System Admin
        SystemAdmin admin = new SystemAdmin("Admin User", "admin", "admin123");
        system.getUserDirectory().addUser(admin);
        
        // Create 2 Branches with Branch Managers
        Branch branch1 = new Branch("Downtown Branch");
        branch1.getLibrary().setBuildingNo("Building A-101");
        BranchManager manager1 = new BranchManager("John Smith", "john.smith", "pass123", 5);
        branch1.getLibrary().setBranchManager(manager1);
        system.addBranch(branch1);
        system.getUserDirectory().addUser(manager1);
        
        Branch branch2 = new Branch("Uptown Branch");
        branch2.getLibrary().setBuildingNo("Building B-202");
        BranchManager manager2 = new BranchManager("Sarah Johnson", "sarah.johnson", "pass456", 8);
        branch2.getLibrary().setBranchManager(manager2);
        system.addBranch(branch2);
        system.getUserDirectory().addUser(manager2);
        
        // Create Authors
        Author author1 = new Author("J.K. Rowling", "British");
        Author author2 = new Author("George Orwell", "British");
        Author author3 = new Author("Harper Lee", "American");
        Author author4 = new Author("F. Scott Fitzgerald", "American");
        Author author5 = new Author("Jane Austen", "British");
        system.getAuthorDirectory().addAuthor(author1);
        system.getAuthorDirectory().addAuthor(author2);
        system.getAuthorDirectory().addAuthor(author3);
        system.getAuthorDirectory().addAuthor(author4);
        system.getAuthorDirectory().addAuthor(author5);
        
        // Add Books to Branch 1
        Book book1 = new Book("Harry Potter and the Sorcerer's Stone", 320, "English", author1);
        Book book2 = new Book("1984", 328, "English", author2);
        Book book3 = new Book("Animal Farm", 112, "English", author2);
        branch1.getLibrary().addBook(book1);
        branch1.getLibrary().addBook(book2);
        branch1.getLibrary().addBook(book3);
        
        // Add Books to Branch 2
        Book book4 = new Book("To Kill a Mockingbird", 336, "English", author3);
        Book book5 = new Book("The Great Gatsby", 180, "English", author4);
        Book book6 = new Book("Pride and Prejudice", 432, "English", author5);
        branch2.getLibrary().addBook(book4);
        branch2.getLibrary().addBook(book5);
        branch2.getLibrary().addBook(book6);
        
        // Create 5 Customers
        Customer customer1 = new Customer("Alice Brown", "alice", "cust123");
        Customer customer2 = new Customer("Bob Wilson", "bob", "cust456");
        Customer customer3 = new Customer("Carol Davis", "carol", "cust789");
        Customer customer4 = new Customer("David Miller", "david", "cust101");
        Customer customer5 = new Customer("Eve Martinez", "eve", "cust202");
        
        system.getUserDirectory().addUser(customer1);
        system.getUserDirectory().addUser(customer2);
        system.getUserDirectory().addUser(customer3);
        system.getUserDirectory().addUser(customer4);
        system.getUserDirectory().addUser(customer5);
    }
}