package controller;

import business.DBManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.Log;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class updatecustomerController {

    @FXML private TextField name;
    @FXML private TextField contact;
    @FXML private TextArea address;

    private TableView<Customer> table;

    @FXML private void update(){

        String name = this.name.getText();
        String contact= this.contact.getText();
        String address = this.address.getText();

        Customer customer = table.getSelectionModel().getSelectedItem();

        customer.setName(name);
        customer.setContact(contact);
        customer.setAddress(address);

        DBManager.merge(customer);
        table.refresh();

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("Customer name "+customer.getName()+" updated");
        DBManager.save(log);

        ((Stage) this.name.getScene().getWindow()).close();


    }

    public void setTable(TableView<Customer> table)
    {
        this.table=table;
    }

    public void setItems(String name,String contact,String address){

        this.name.setText(name);
        this.contact.setText(contact);
        this.address.setText(address);
    }

}
