package controller;

import Utility.Utility;
import business.DBManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Log;
import model.Supplier;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class updatesupplierController {

    @FXML private TextField name;
    @FXML private TextArea contact;
    @FXML private TextArea address;
    @FXML private TextArea description;

    private TableView<Supplier> table;


    @FXML private void update(){


        String name = this.name.getText();
        String contact = this.contact.getText();
        String address = this.address.getText();
        String description = this.description.getText();

        if (name.isEmpty()||contact.isEmpty()||address.isEmpty()||description.isEmpty()){
            Utility.alert(Alert.AlertType.WARNING,"Please check your inputs");
            return;

        }

        Supplier supplier = table.getSelectionModel().getSelectedItem();
        supplier.setName(name);
        supplier.setAddress(address);
        supplier.setDescription(description);
        supplier.setContactnumber(contact);
        table.getItems().addAll(supplier);

        DBManager.merge(supplier);

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("Supplier name "+supplier.getName()+" removed");
        DBManager.save(log);

        ((Stage)this.name.getScene().getWindow()).close();



    }



    public void setTable(TableView<Supplier> table){
        this.table = table;
    }

    public void setItems(String name,String contact,String address,String description){
        this.name.setText(name);
        this.contact.setText(contact);
        this.address.setText(address);
        this.description.setText(description);
    }



}
