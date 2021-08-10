/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hartshorninventroysystem.View_Controller;

import hartshorninventroysystem.Model.Part;
import hartshorninventroysystem.Model.Product;
import static hartshorninventroysystem.View_Controller.MainScreenController.inventroy;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christina Joy Hartshorn
 */
public class ModifyProductController implements Initializable {

    @FXML
    private Button Cancel;
    @FXML
    private Button Save;
    
    Product product;
    @FXML
    private TextField ID;
    @FXML
    private TextField Name;
    @FXML
    private TextField Inv;
    @FXML
    private TextField Price;
    @FXML
    private TextField Max;
    @FXML
    private TextField Min;
    
    private ObservableList<Part> productParts;
    
    @FXML
    private TextField SearchText;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> PartsID;
    @FXML
    private TableColumn<Part, String> PartName;
    @FXML
    private TableColumn<Part, Integer> PartInventory;
    @FXML
    private TableColumn<Part, Double> PartPrice;
    @FXML
    private TableView<Part> partsTableProduct;
    @FXML
    private TableColumn<Part, Integer> PartsID2;
    @FXML
    private TableColumn<Part, String> PartName2;
    @FXML
    private TableColumn<Part, Integer> PartInventory2;
    @FXML
    private TableColumn<Part, Double> PartPrice2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        PartsID.setCellValueFactory(new PropertyValueFactory<>("PartID"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInventory.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(inventroy.ObsListPart());
        
        PartsID2.setCellValueFactory(new PropertyValueFactory<>("PartID"));
        PartName2.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInventory2.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        PartPrice2.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTableProduct.setItems(productParts);
    }    

    @FXML
    private void search(ActionEvent event) {
        String x=SearchText.getText();
        partsTable.getSelectionModel().select(inventroy.lookupPart(Integer.parseInt(x)));
        partsTableProduct.getSelectionModel().select(product.lookupAssociatedPart(Integer.parseInt(x)));
        //product.lookupAssociatedPart(0)
    }

    @FXML
    private void add(ActionEvent event) {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(part);
        productParts = product.ObsListPart();
        partsTableProduct.setItems(productParts);
        //System.out.println(productParts);
    }

    @FXML
    private void delete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Your are about to delete updating this product.");
        alert.setContentText("Is this ok?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            product.removeAssociatedPart(partsTableProduct.getSelectionModel().getSelectedItem().getPartID());
            productParts = product.ObsListPart();
            
            partsTableProduct.setItems(productParts);
        }
        //product.removeAssociatedPart(0)
        
       // product.removeAssociatedPart(partsTableProduct.getSelectionModel().getSelectedItem().getPartID());
        //productParts = product.ObsListPart();
            
        //partsTableProduct.setItems(productParts);
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Your are about to cancel the selected part for this product.");
        alert.setContentText("Is this ok?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage stage; 
            Parent root;       
            stage=(Stage) Cancel.getScene().getWindow();
            //load up OTHER FXML document
            FXMLLoader loader=new FXMLLoader(getClass().getResource(
                 "mainScreen.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        
        product.setName(Name.getText());
        product.setPrice(Double.parseDouble(Price.getText()));
        product.setInStock(Integer.parseInt(Inv.getText()));
        product.setMax(Integer.parseInt(Max.getText()));
        product.setMin(Integer.parseInt(Min.getText()));
        
        if(product.getMin()>product.getMax() || product.getMax()<product.getInStock() || product.getMin()>product.getInStock() ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Data Issue");
            alert.setHeaderText("Please double check the data");
            alert.setContentText("Something is wrong with you min, max or inventory");
            
            alert.showAndWait();
            return;
        }
        
        
        double partsPrice = 0.0;
        for(Part part : productParts) {
            partsPrice += part.getPrice();
            System.out.println(partsPrice);
        }
        if(partsPrice>Double.parseDouble(Price.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Price issues");
            alert.setHeaderText("The price of parts are greater then the price of the product");
            alert.setContentText("Please correct this issue to contiune");
            
            alert.showAndWait();
            return;
        }
        
        inventroy.updateProduct(Integer.parseInt(ID.getText()), product);
        
        Stage stage; 
        Parent root;       
        stage=(Stage) Save.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader=new FXMLLoader(getClass().getResource(
               "mainScreen.fxml"));
        root =loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void setPart(Product product) {
        this.product = product;
        
        

        ID.setText(Integer.toString(product.getProductID()));
        Name.setText(product.getName());
        Inv.setText(Integer.toString(product.getInStock()));
        Price.setText(Double.toString(product.getPrice()));
        Max.setText(Integer.toString(product.getMax())); //Min LabelOutIn
        Min.setText(Integer.toString(product.getMin()));
        productParts = product.ObsListPart();
        partsTableProduct.setItems(productParts);
        
     }
    
}
