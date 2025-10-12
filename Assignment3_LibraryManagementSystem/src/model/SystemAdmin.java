package model;

public class SystemAdmin extends Person {
    
    public SystemAdmin(String name, String username, String password) {
        super(name, username, password);
    }
    
    @Override
    public String getRole() {
        return "SystemAdmin";
    }
}