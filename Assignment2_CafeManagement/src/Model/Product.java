package model;

public class Product {
    private int productId;           // int primitive
    private String productName;      // String reference type
    private String category;         // String reference type  
    private double price;            // double primitive
    private int number;              // int primitive (quantity/stock)
    private int preparationTime;     // int primitive (minutes)
    
    private static int productCounter = 0;
    
    // Default constructor
    public Product() {
        this.productId = ++productCounter;
        this.productName = "";
        this.category = "";
        this.price = 0.0;
        this.number = 0;
        this.preparationTime = 0;
    }
    
    // Parameterized constructor
    public Product(String productName, String category, double price, int number, int preparationTime) {
        this.productId = ++productCounter;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.number = number;
        this.preparationTime = preparationTime;
    }
    
    // Getters
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getNumber() { return number; }
    public int getPreparationTime() { return preparationTime; }
    
    // Setters
    public void setProductName(String productName) { this.productName = productName; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }
    public void setNumber(int number) { this.number = number; }
    public void setPreparationTime(int preparationTime) { this.preparationTime = preparationTime; }
    
    // Validation methods
    public boolean isValidProduct() {
        return productName != null && !productName.trim().isEmpty() &&
               category != null && !category.trim().isEmpty() &&
               price > 0 && number >= 0 && preparationTime > 0;
    }
    
    @Override
    public String toString() {
        return productName + " (" + category + ") - $" + String.format("%.2f", price);
    }
}