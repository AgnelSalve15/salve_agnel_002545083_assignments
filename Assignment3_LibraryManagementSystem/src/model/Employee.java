package model;

public abstract class Employee extends Person {
    private static int empIdCounter = 5000;
    private int employeeId;
    private int experience;
    
    public Employee(String name, String username, String password, int experience) {
        super(name, username, password);
        this.employeeId = empIdCounter++;
        this.experience = experience;
    }
    
    public int getEmployeeId() { return employeeId; }
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
}