package model;

import java.util.ArrayList;

public class UserAccountDirectory {
    private ArrayList<Person> users;
    
    public UserAccountDirectory() {
        this.users = new ArrayList<>();
    }
    
    public ArrayList<Person> getUsers() { return users; }
    
    public void addUser(Person user) {
        users.add(user);
    }
    
    public void removeUser(Person user) {
        users.remove(user);
    }
    
    public Person authenticate(String username, String password) {
        for (Person user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}