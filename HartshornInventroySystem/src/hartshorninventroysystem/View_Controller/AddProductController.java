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
import javafx.collections.FXCollections;
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
 * FXML Controller Christina Joy Hartshorn
 *
 * @author Elicea
 */
public class AddProductController implements Initializable {

    @FXML
    private TextField addProductID;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductMin;
    @FXML
    private TextField addProductSearchText;
    @FXML
    private Button Search;
    @FXML
    private Button Add;
    @FXML
    private Button Delete;
    @FXML
    private Button Cancel;
    @FXML
    private Button Save;
    @FXML
    private TableColumn<Part, Integer> PartsID;
    @FXML
    private TableColumn<Part, String> PartName;
    @FXML
    private TableColumn<Part, Integer> PartInventory;
    @FXML
    private TableColumn<Part, Double> PartPrice;
    @FXML
    private TableColumn<Part, Integer> PartsID2;
    @FXML
    private TableColumn<Part, String> PartName2;
    @FXML
    private TableColumn<Part, Integer> PartInventory2;
    @FXML
    private TableColumn<Part, Double> PartPrice2;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableView<Part> partsTableProduct;
    
    private ArrayList<Part> productParts;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        productParts = new ArrayList<>();
        
        PartsID.setCellValueFactory(new PropertyValueFactory<>("PartID"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInventory.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(inventroy.ObsListPart());
        
        
        PartsID2.setCellValueFactory(new PropertyValueFactory<>("PartID"));
        PartName2.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInventory2.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        PartPrice2.setCellValueFactory(new PropertyValueFactory<>("price"));
        //partsTable.setItems(inventroy.ObsListPart());
        partsTableProduct.setItems(FXCollections.observableArrayList(productParts));
        //ObservableList<Part> parts = FXCollections.observableArrayList(productParts);
        //partsTableProduct.setItems(parts);
    }    

    @FXML
    private void search(ActionEvent event) {
        String x=addProductSearchText.getText();
        partsTable.getSelectionModel().select(inventroy.lookupPart(Integer.parseInt(x)));
        
        productParts.stream().filter((part) -> (part.getPartID() == Integer.parseInt(x))).forEachOrdered((part) -> {
            partsTableProduct.getSelectionModel().select(part);
        });
    }

    @FXML
    private void add(ActionEvent event) {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        productParts.add(part);
        partsTableProduct.setItems(FXCollections.observableArrayList(productParts));
    }

    @FXML
    private void delete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Your are about to delete adding the selected part for this product.");
        alert.setContentText("Is this ok?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            productParts.removeIf(n -> (n == partsTableProduct.getSelectionModel().getSelectedItem()));
            partsTableProduct.setItems(FXCollections.observableArrayList(productParts));
        }
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Your are about to Cancel adding the new product.");
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
        String name = addProductName.getText();
        Double price = Double.parseDouble(addProductPrice.getText());
        int inStock = 0;
        inStock = Integer.parseInt(addProductInv.getText());
        int max = Integer.parseInt(addProductMax.getText());
        int min = Integer.parseInt(addProductMin.getText());
        
        if(min>max || max<inStock || min>inStock || name.isEmpty() || price.isNaN()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Data Issue");
            alert.setHeaderText("Please double check the data");
            alert.setContentText("Something is wrong with the values");
            
            alert.showAndWait();
            return;
        }
        
        if(productParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Part Needed");
            alert.setHeaderText("To Finish Creating this Product please add a Part");
            alert.setContentText("Click on a part in the table above and click add.");
            
            alert.showAndWait();
            return;
        }
        
        double partsPrice = 0.0;
        for(Part part : productParts) {
            partsPrice += part.getPrice();
            System.out.println(partsPrice);
        }
        if(partsPrice>price){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Price issues");
            alert.setHeaderText("The price of parts are greater then the price of the product");
            alert.setContentText("Please correct this issue to contiune");
            
            alert.showAndWait();
            return;
        }
        
        inventroy.addProduct(new Product(productParts, name, price, inStock, max, min));
        
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
    
}
