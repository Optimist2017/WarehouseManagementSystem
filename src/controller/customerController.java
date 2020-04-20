package controller;

import Utility.Utility;
import business.DBManager;
import com.sun.deploy.util.FXLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;
import model.Employees;
import model.Log;
import model.Product;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class customerController {


    @FXML private TextField search;
    @FXML private TableView<Customer> table;
    @FXML private TableColumn<Customer,String> nameColumn;
    @FXML private TableColumn<Customer,String> contactColumn;
    @FXML private TableColumn<Customer,String> addressColumn;
    @FXML private TableColumn<Customer,LocalDate> startdateColumn;
    @FXML private TableColumn<Customer, String> addedbyColumn;
    @FXML private TableColumn<Customer,Double> purchaseColumn;


    @FXML private void initialize(){

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        startdateColumn.setCellValueFactory(new PropertyValueFactory<>("startdate"));
        addedbyColumn.setCellValueFactory(new PropertyValueFactory<>("addedby"));
        purchaseColumn.setCellValueFactory(new PropertyValueFactory<>("totalpurchase"));



        List<Customer> customers = DBManager.listAll(Customer.class);
        table.setItems(FXCollections.observableArrayList(customers));
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
                if (newValue==null) return;
            }
        });





    }




    @FXML private void addcustomer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addcustomer.fxml"));
        Parent root = loader.load();
        addcustomerController controller = loader.getController();
        controller.setTable(table);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML private void update() throws IOException {

        if (table.getSelectionModel().isEmpty()){

            Utility.alert(Alert.AlertType.WARNING,"Please select the customer to update");
            return;
        }

        Customer customer = table.getSelectionModel().getSelectedItem();
        String name = customer.getName();
        String contact = customer.getContact();
        String address = customer.getAddress();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/updatecustomer.fxml"));
        Parent root = loader.load();
        updatecustomerController controller = loader.getController();
        controller.setTable(table);
        controller.setItems(name,contact,address);


        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Update Customer");
        stage.setScene(new Scene(root));
        stage.show();


    }


    @FXML private void delete(){


        if (table.getSelectionModel().isEmpty()){
            Utility.alert(Alert.AlertType.WARNING,"Please select a customer ");
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" Are you Sure ");
            alert.setContentText("Please are you very sure about deletting this customer  ? ");
            Optional<ButtonType> response =  alert.showAndWait();

            if(response !=null && response.isPresent() && response.get()==ButtonType.OK){

                Customer customer = table.getSelectionModel().getSelectedItem();
                DBManager.delete(customer);
                table.refresh();

                Log log = new Log();
                log.setUser(loginController.getUser());
                log.setTime(Timestamp.valueOf(LocalDateTime.now()));
                log.setAction("Customer name "+customer.getName()+" removed");
                DBManager.save(log);

            }

        }




    }

    @FXML private void Search(){


        String search = this.search.getText();
        Long count = DBManager.queryForSingleResult(Long.class,"select count(c) from Customer C where c.name=?1",search);



        if (search.isEmpty() || count<1){

            Utility.alert(Alert.AlertType.ERROR,"Please enter the field or Item not found ");
            initialize();


            return;
        }else {
            List<Customer> customers = DBManager.query(Customer.class, "select c from Customer c where c.name=?1", search);
            table.getItems().clear();
            table.setItems(FXCollections.observableArrayList(customers));
        }

    }


}
