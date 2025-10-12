package model;

public class Author {
    private static int idCounter = 2000;
    private int authorId;
    private String name;
    private String nationality;
    
    public Author(String name, String nationality) {
        this.authorId = idCounter++;
        this.name = name;
        this.nationality = nationality;
    }
    
    public int getAuthorId() { return authorId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
}