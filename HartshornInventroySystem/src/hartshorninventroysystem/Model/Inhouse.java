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
public class Inhouse extends Part{
    protected int machineID;


    
    public Inhouse(int machineID, String Name, double price, int inStock, int min, int max) {
        super(Name, price, inStock, min, max);
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
    
    

    
}
