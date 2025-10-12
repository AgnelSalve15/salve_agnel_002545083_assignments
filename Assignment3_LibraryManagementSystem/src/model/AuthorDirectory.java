package model;

import java.util.ArrayList;

public class AuthorDirectory {
    private ArrayList<Author> authors;
    
    public AuthorDirectory() {
        this.authors = new ArrayList<>();
    }
    
    public ArrayList<Author> getAuthors() { return authors; }
    
    public void addAuthor(Author author) {
        authors.add(author);
    }
}