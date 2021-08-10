/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hartshorninventroysystem.View_Controller;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import hartshorninventroysystem.Model.Part;


import hartshorninventroysystem.Model.Outsourced;
import hartshorninventroysystem.Model.Inhouse;


import hartshorninventroysystem.Model.Product;

import hartshorninventroysystem.Model.Inventory;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
//import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author Christina Joy Hartshorn
 */
public class MainScreenController implements Initializable {

    @FXML
    private TableView<Part> partTable;
    @FXML
    private Button AddPart;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private Button AddProdcut;
    @FXML
    private TextField SearchFieldPart;
    @FXML
    private TableColumn<Part, Integer> PartsID;
    @FXML
    private TableColumn<Part, String> PartName;
    @FXML
    private TableColumn<Part, Integer> PartInventory;
    @FXML
    private TableColumn<Part, Double> PartPrice;
    @FXML
    private Button DeletePart;
    @FXML
    private Button ModifyPart;
    @FXML
    private TextField SearchFieldProdcut;
    @FXML
    private TableColumn<Product, Integer> ProductID;
    @FXML
    private TableColumn<Product, String> ProductName;
    @FXML
    private TableColumn<Product, Integer> ProductInventory;
    @FXML
    private TableColumn<Product, Double> ProductPrice;
    @FXML
    private Button DeleteProduct;
    @FXML
    private Button ModifyProdcut;
    @FXML
    private Button Exit;
    
    
    static boolean entered;
    static Inventory inventroy;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        //inv = new Inventory();
        //Create test cases for Parts and Product table
        if(!entered){
            inventroy = new Inventory();
            inventroy.addPart(new Outsourced("Company A", "Part 1", 5.0, 5, 5, 300));
            inventroy.addPart(new Inhouse(1, "Part 2", 10.0, 10, 2, 150));
            inventroy.addPart(new Outsourced("Company B", "Part 3", 15.0, 12, 1, 100));
            
            inventroy.addProduct(new Product("Product 1", 5.0, 5, 5, 300));
            inventroy.addProduct(new Product("Product 2", 10.0, 10, 2, 150));
            inventroy.addProduct(new Product("Product 3", 15.0, 12, 1, 100));
            entered=true;
        }
        
        PartsID.setCellValueFactory(new PropertyValueFactory<>("PartID"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInventory.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        //ObservableList<Part> parts = FXCollections.observableArrayList(inv.allParts);
        partTable.setItems(inventroy.ObsListPart());
        
        ProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventory.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        //ObservableList<Part> parts = FXCollections.observableArrayList(inv.allParts);
        productTable.setItems(inventroy.ObsListProduct());
    }    

    @FXML
    private void searchPart(ActionEvent event) {
        String x=SearchFieldPart.getText();
        partTable.getSelectionModel().select(inventroy.lookupPart(Integer.parseInt(x)));
    }

    @FXML
    private void deletePart(ActionEvent event) {
        //inventroy.removePart(partTable.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete Part");
        alert.setHeaderText("Your are about to Delete the selected part.");
        alert.setContentText("Is this ok?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            inventroy.deletePart(partTable.getSelectionModel().getSelectedItem());
            partTable.setItems(inventroy.ObsListPart());
        }
    }

    @FXML
    private void modifyPart(ActionEvent event) throws IOException {
        
        Part part=partTable.getSelectionModel().getSelectedItem();
        
        if(part!=null){
            Stage stage; 
            Parent root;       
            stage=(Stage) ModifyPart.getScene().getWindow();
            //load up OTHER FXML document
            FXMLLoader loader=new FXMLLoader(getClass().getResource(
                "modifyPart.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            ModifyPartController controller = loader.getController();
            controller.setPart(part);
        }
        
        
        /**
        ModifyPartController controller = loader.getController();
        Part part=partTable.getSelectionModel().getSelectedItem();
        controller.setPart(part); **/
        
        
        //ModifyPartController controller = loader.getController();
        //Part part=partTable.getSelectionModel().getSelectedItem();
        //if(part!=null){
            //controller.setPart(part);
        //}
        //controller.setPart(part);
    }

    
    @FXML
    private void addPart(ActionEvent event) throws IOException {
        Stage stage; 
        Parent root;       
        stage=(Stage) AddPart.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader=new FXMLLoader(getClass().getResource(
               "addPart.fxml"));
        root =loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void searchProdcut(ActionEvent event) {
        String x=SearchFieldProdcut.getText();
        productTable.getSelectionModel().select(inventroy.lookupProduct(Integer.parseInt(x)));
    }

    @FXML
    private void deleteProdcut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete Prodcut");
        alert.setHeaderText("Your are about to Delete the selected product.");
        alert.setContentText("Is this ok?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Product temp = productTable.getSelectionModel().getSelectedItem();
            if(temp.ObsListPart().isEmpty()){
                inventroy.removeProduct(temp.getProductID());
                productTable.setItems(inventroy.ObsListProduct());
            }
            else{
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("This product still has parts incluced");
                alert.setContentText("please remove these to continue");
            
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void modifyProdcut(ActionEvent event) throws IOException {
        
        Product product=productTable.getSelectionModel().getSelectedItem();
        
        if(product!=null){
            Stage stage; 
            Parent root;       
            stage=(Stage) ModifyProdcut.getScene().getWindow();
            //load up OTHER FXML document
            FXMLLoader loader=new FXMLLoader(getClass().getResource(
                "modifyProduct.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            //ModifyProductController controller = loader.getController();
            //Product product=table.getSelectionModel().getSelectedItem();
            //controller.;
            //Product product=productTable.getSelectionModel().getSelectedItem();
            ModifyProductController controller = loader.getController();
            controller.setPart(product);
        }
    }

    @FXML
    private void addProdcut(ActionEvent event) throws IOException {
        Stage stage; 
        Parent root;       
        stage=(Stage) AddProdcut.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader=new FXMLLoader(getClass().getResource(
               "addProduct.fxml"));
        root =loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void exitHandler(ActionEvent event) {
        Stage stage;
        stage=(Stage) Exit.getScene().getWindow();
        stage.close();
    }
    
  
    
}
