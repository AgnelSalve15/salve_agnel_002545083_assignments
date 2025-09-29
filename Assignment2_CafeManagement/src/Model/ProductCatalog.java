package model;

import java.util.ArrayList;

/**
 * ProductCatalog class for Assignment 2 - Coffee Shop Management
 * Manages the collection of all products in the café
 */
public class ProductCatalog {
    private ArrayList<Product> productList;
    
    public ProductCatalog() {
        this.productList = new ArrayList<>();
    }
    
    // Add product to catalog
    public Product addProduct() {
        Product product = new Product();
        productList.add(product);
        return product;
    }
    
    public void addProduct(Product product) {
        if (product != null) {
            productList.add(product);
        }
    }
    
    // Remove product from catalog
    public void removeProduct(Product product) {
        productList.remove(product);
    }
    
    public void removeProduct(int index) {
        if (index >= 0 && index < productList.size()) {
            productList.remove(index);
        }
    }
    
    // Get product by index
    public Product getProduct(int index) {
        if (index >= 0 && index < productList.size()) {
            return productList.get(index);
        }
        return null;
    }
    
    // Get all products
    public ArrayList<Product> getProductList() {
        return productList;
    }
    
    // Search products by ID
    public Product searchProductById(int productId) {
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }
    
    // Search products by name
    public ArrayList<Product> searchProductsByName(String name) {
        ArrayList<Product> results = new ArrayList<>();
        for (Product product : productList) {
            if (product.getProductName().toLowerCase().contains(name.toLowerCase())) {
                results.add(product);
            }
        }
        return results;
    }
    
    // Search products by category
    public ArrayList<Product> searchProductsByCategory(String category) {
        ArrayList<Product> results = new ArrayList<>();
        for (Product product : productList) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                results.add(product);
            }
        }
        return results;
    }
    
    // Get total number of products
    public int getProductCount() {
        return productList.size();
    }
    
    // Check if catalog is empty
    public boolean isEmpty() {
        return productList.isEmpty();
    }
    
    // Get available categories
    public ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (Product product : productList) {
            if (!categories.contains(product.getCategory())) {
                categories.add(product.getCategory());
            }
        }
        return categories;
    }
    
    // Get data for JTable display
    public Object[][] getTableData() {
        Object[][] data = new Object[productList.size()][6];
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            data[i][0] = product.getProductId();
            data[i][1] = product.getProductName();
            data[i][2] = product.getCategory();
            data[i][3] = String.format("$%.2f", product.getPrice());
            data[i][4] = product.getNumber();
            data[i][5] = product.getPreparationTime() + " min";
        }
        return data;
    }
    
    public String[] getTableColumns() {
        return new String[]{"Product ID", "Name", "Category", "Price", "Stock", "Prep Time"};
    }
}