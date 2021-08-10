/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hartshorninventroysystem.Model;

/**
 *
 * @author Christina Joy Hartshorn
 */
public abstract class Part {
    protected int PartID;
    protected String Name;
    protected double price;
    protected int inStock;
    protected int min;
    protected int max;
    
    protected static int count = 1;

    
    public Part(String Name, double price, int inStock, int min, int max) {
        this.PartID = this.count;
        this.Name = Name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
        this.count++;
    }
    

    public int getPartID() {
        return PartID;
    }

    public void setPartID(int PartID) {
        this.PartID = PartID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
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
    
    public void correctCount(){
        this.count--;
    }
}


