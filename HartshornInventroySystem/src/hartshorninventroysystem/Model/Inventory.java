/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hartshorninventroysystem.Model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Christina Joy Hartshorn
 */
public class Inventory {
    protected ArrayList<Part> allParts;
    protected ArrayList<Product> products;

    public Inventory() {
        allParts = new ArrayList<>();
        products = new ArrayList<>();
    }
    
    public void addProduct(Product product) {
        products.add(product);
    }
    
    public boolean removeProduct(int productID) {
        //return true;
        return products.removeIf(n -> (n.getProductID() == productID));
    }
    
    public Product lookupProduct(int productID) {
        //return products.get(productID);
        
        for(Product product : products) {
            if(product.getProductID() == productID) {
                return product;
            }
        }
        return null;
    }
    
    public void updateProduct(int productID, Product updatedProduct) {
        
        removeProduct(productID);
        updatedProduct.setProductID(productID);
        //updatedProduct.correctCount();
        addProduct(updatedProduct);
        
    }
    
    public ObservableList<Product> ObsListProduct() {
        return FXCollections.observableArrayList(products);
    }
    
    public void addPart(Part part) {
        allParts.add(part); 
    }
    
    public boolean deletePart(Part part) {
        //return true;
        return allParts.removeIf(n -> (n == part));
    }
    
    
    public Part lookupPart(int partID) {
        //return allParts.get(partID);
        
        for(Part part : allParts) {
            if(part.getPartID() == partID) {
                return part;
            }
        }
        return null;
    }
    
    public void updatePart(int partID, Part updatedPart) {
        //Searchs though all parts and selects the one with the correct partID 
        //Deletes the old part
        //replaces old part with the updated part
        for(Part part : allParts) {
            if(part.getPartID() == partID) {
                deletePart(part);
                updatedPart.setPartID(partID);
                updatedPart.correctCount();
            }
        }
        addPart(updatedPart);
    }
    
    public ObservableList<Part> ObsListPart() {
        return FXCollections.observableArrayList(allParts);
    }
    //ObservableList<Part> parts = FXCollections.observableArrayList(inv.allParts);
}
