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
import java.time.LocalDate;
import java.time.LocalDateTime;


public class addsupplierController {

    @FXML private TextField name ;
    @FXML private TextArea contact;
    @FXML private TextArea description;
    @FXML private TextArea address;



    @FXML private void save(){

        String name = this.name.getText();
        String contact = this.contact.getText();
        String address = this.address.getText();
        String description = this.description.getText();

        if (name.isEmpty()|| contact.isEmpty()||address.isEmpty()|| description.isEmpty()){

            Utility.alert(Alert.AlertType.WARNING,"Please enter your inpputs ");
            return;
        }

        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setContactnumber(contact);
        supplier.setAddress(address);
        supplier.setDescription(description);
        supplier.setStartdate(LocalDate.now());
        table.getItems().addAll(supplier);

        DBManager.save(supplier);

        ((Stage)this.description.getScene().getWindow()).close();

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("New supplier name "+name+" added");
        DBManager.save(log);


    }



    private TableView<Supplier> table;

    public  void setTable(TableView<Supplier> table){
        this.table = table;

    }
}
