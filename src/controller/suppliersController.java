package controller;

import Utility.Utility;
import business.DBManager;
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
import model.Category;
import model.Log;
import model.Supplier;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class suppliersController {

    @FXML private TextField search;
    @FXML private TableView<Supplier> table;
    @FXML private TableColumn<Supplier,String> nameColumn;
    @FXML private TableColumn<Supplier,String> contactnumberColumn;
    @FXML private TableColumn<Supplier,String> addressColumn;
    @FXML private TableColumn<Supplier,LocalDate> startdateColumn;
    @FXML private TableColumn<Supplier,String> descriptionColumn;


    @FXML private void initialize(){

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactnumberColumn.setCellValueFactory(new PropertyValueFactory<>("contactnumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        startdateColumn.setCellValueFactory(new PropertyValueFactory<>("startdate"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        List<Supplier> suppliers = DBManager.listAll(Supplier.class);
        table.setItems(FXCollections.observableArrayList(suppliers));
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Supplier>() {
            @Override
            public void changed(ObservableValue<? extends Supplier> observable, Supplier oldValue, Supplier newValue) {
                if (newValue==null){
                    return;
                }
            }
        });

    }



    @FXML private void Add() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addsupplier.fxml"));
        Parent root = loader.load();

        addsupplierController controller = loader.getController();
        controller.setTable(table);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Supplier");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML private void Update() throws IOException {

        if (table.getSelectionModel().isEmpty()){
            Utility.alert(Alert.AlertType.WARNING,"Please a supplier to update ");
            return;
        }
        Supplier supplier = table.getSelectionModel().getSelectedItem();
        String name = supplier.getName();
        String contact = supplier.getContactnumber();
        String address = supplier.getAddress();
        String description = supplier.getDescription();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/updatesupplier.fxml"));
        Parent root = loader.load();

        updatesupplierController controller = loader.getController();
        controller.setTable(table);
        controller.setItems(name,contact,address,description);

        Stage stage = new Stage();
        stage.setTitle("Update Supplier");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML private void Delete(){

        if (table.getSelectionModel().isEmpty()){
            Utility.alert(Alert.AlertType.ERROR,"Please select a supplier ");
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" Are you Sure ");
            alert.setContentText("Please are you very sure about deletting this supplier ? ");
            Optional<ButtonType> response =  alert.showAndWait();

            if(response !=null && response.isPresent() && response.get()==ButtonType.OK){

                Supplier supplier = table.getSelectionModel().getSelectedItem();
                DBManager.delete(supplier);
                table.refresh();

                Log log = new Log();
                log.setUser(loginController.getUser());
                log.setTime(Timestamp.valueOf(LocalDateTime.now()));
                log.setAction("Supplier name "+supplier.getName()+" removed");
                DBManager.save(log);

            }

        }




    }

    @FXML private void Search(){


        String search = this.search.getText();
        Long count = DBManager.queryForSingleResult(Long.class,"select count(c) from Supplier C where c.name=?1",search);



        if (search.isEmpty() || count<1){

            Utility.alert(Alert.AlertType.ERROR,"Please enter the field or Item not found ");
            table.getItems().clear();
            List<Supplier> suppliers = DBManager.listAll(Supplier.class);
            table.setItems(FXCollections.observableArrayList(suppliers));

            return;
        }else {
            Supplier supplier = DBManager.queryForSingleResult(Supplier.class, "select c from Supplier c where c.name=?1", search);
            table.getItems().clear();
            table.getItems().add(supplier);
        }

    }

}
