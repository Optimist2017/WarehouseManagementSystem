package controller;

import Utility.Utility;
import business.DBManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.Employees;
import model.Log;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class addcustomerController {

    @FXML private TextField name;
    @FXML private TextField contact;
    @FXML private TextArea address;

    private TableView<Customer> table;


    @FXML private void save(){
        if (this.name.getText().isEmpty()|| this.contact.getText().isEmpty()){
            Utility.alert(Alert.AlertType.WARNING,"Please make sure you enter into the fields ");
            return;
        }

        String name = this.name.getText();
        String contact = this.contact.getText();
        String address = this.address.getText();
        Double total = 1d;



        Customer customer = new Customer();
        customer.setName(name);
        customer.setContact(contact);
        customer.setAddress(address);
        customer.setAddedby(loginController.getUser().getUsername());
        customer.setStartdate(LocalDate.now());
        customer.setTotalpurhase(total);
        table.getItems().addAll(customer);
        DBManager.save(customer);
        ((Stage)this.name.getScene().getWindow()).close();

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("New Customer name "+name+" added");
        DBManager.save(log);


    }

    public void setTable(TableView<Customer> table)
    {
        this.table=table;
    }
}
