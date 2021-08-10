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
public class Outsourced extends Part{
    protected String companyName;


    public Outsourced(String companyName, String Name, double price, int inStock, int min, int max) {
        super(Name, price, inStock, min, max);
        this.companyName = companyName;
    }
    

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
