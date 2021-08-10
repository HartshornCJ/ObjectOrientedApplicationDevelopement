/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hartshorninventroysystem.View_Controller;

import hartshorninventroysystem.Model.Inhouse;
import hartshorninventroysystem.Model.Outsourced;
import hartshorninventroysystem.Model.Part;
import static hartshorninventroysystem.View_Controller.MainScreenController.inventroy;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christina Joy Hartshorn
 */
public class ModifyPartController implements Initializable {

    @FXML
    private Label LabelOutIn;
    @FXML
    private TextField TextInOut;
    @FXML
    private TextField Price;
    @FXML
    private TextField Inv;
    @FXML
    private TextField Name;
    @FXML
    private TextField ID;
    @FXML
    private TextField Max;
    @FXML
    private TextField Min;
    @FXML
    private Button Cancel;
    @FXML
    private Button Save;
    @FXML
    private RadioButton OutsourcedToggle;
    @FXML
    private RadioButton InHouseToggle;
    
    private ToggleGroup partGroup;
    

    Part part;
    //Inhouse partInhouse;
    //Outsourced partOutsourced;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        //configuring the RadioButtons 
        partGroup = new ToggleGroup();
        this.InHouseToggle.setToggleGroup(partGroup);
        this.OutsourcedToggle.setToggleGroup(partGroup);
    }    

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Your are about to cancel updating this part.");
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
        String name = Name.getText();
        Double price = Double.parseDouble(Price.getText());
        int inStock = Integer.parseInt(Inv.getText());
        int max = Integer.parseInt(Max.getText());
        int min = Integer.parseInt(Min.getText());
        
        if(min>max || max<inStock || min>inStock ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Data Issue");
            alert.setHeaderText("Please double check the data");
            alert.setContentText("Something is wrong with you min, max or inventory");
            
            alert.showAndWait();
            return;
        }
        
        
        if(this.partGroup.getSelectedToggle().equals(this.InHouseToggle)) {
            int machineID = Integer.parseInt(TextInOut.getText());
            inventroy.updatePart(part.getPartID() ,new Inhouse(machineID, name, price, inStock, max, min));
        }
        if(this.partGroup.getSelectedToggle().equals(this.OutsourcedToggle)) {
            String companyName = TextInOut.getText();
            inventroy.updatePart(part.getPartID() ,new Outsourced(companyName, name, price, inStock, max, min));
        }
        
        
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

    @FXML
    private void partStateToggle(ActionEvent event) {
        if(this.partGroup.getSelectedToggle().equals(this.InHouseToggle)) {
            LabelOutIn.setText("Machine ID");
            TextInOut.setPromptText("Mach ID");
        }
        
        if(this.partGroup.getSelectedToggle().equals(this.OutsourcedToggle)) {
            LabelOutIn.setText("Company Name");
            TextInOut.setPromptText("Comp Nm");
        }
    }
    
    
    public void setPart(Part part) {
        this.part = part;
        
        

        ID.setText(Integer.toString(part.getPartID()));
        Name.setText(part.getName());
        Inv.setText(Integer.toString(part.getInStock()));
        Price.setText(Double.toString(part.getPrice()));
        Max.setText(Integer.toString(part.getMax())); //Min LabelOutIn
        Min.setText(Integer.toString(part.getMin()));
        if(part instanceof Inhouse){
            //this.partInhouse = part;
            LabelOutIn.setText("Machine ID");
            //TextInOut.setPromptText("Mach ID");
            TextInOut.setText(Integer.toString(((Inhouse) part).getMachineID()));
            this.partGroup.selectToggle(this.InHouseToggle);
        }
        else{
            LabelOutIn.setText("Company Name");
            //TextInOut.setPromptText("Comp Nm");
            TextInOut.setText(((Outsourced) part).getCompanyName());
            this.partGroup.selectToggle(this.OutsourcedToggle);
        }
     }
    
}
