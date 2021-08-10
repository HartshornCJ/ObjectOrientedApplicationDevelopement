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
public class Product {
    protected ArrayList<Part> associatedParts;
    protected int productID;
    protected String name;
    protected double price;
    protected int inStock;
    protected int min;
    protected int max;
    protected static int count = 1;


    
    public Product(ArrayList<Part> associatedParts, String name, double price, int inStock, int min, int max) {
        this.associatedParts = associatedParts;
        this.productID = this.count;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
        this.count++;
    }
    
    
    public Product(String name, double price, int inStock, int min, int max) {
        this.associatedParts = new ArrayList<>();
        this.productID = this.count;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
        this.count++;
    }
    

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);        
    }
    
    public boolean removeAssociatedPart(int partID) {
        //associatedParts.removeIf(filter)
        //associatedParts.remove(min)
        return associatedParts.removeIf(n -> (n.getPartID()== partID));
        //return true;
    }
    
    public Part lookupAssociatedPart(int partID) {
        //associatedParts.indexOf(min)
        
        //associatedParts.
        for(Part part : associatedParts) {
            if(part.getPartID() == partID) {
                return part;
            }
        }
        
        return null;
    }
    
    public ObservableList<Part> ObsListPart() {
        return FXCollections.observableArrayList(associatedParts);
    }
    
    public void correctCount(){
        this.count--;
    }
    
}
