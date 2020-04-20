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
import javafx.util.Callback;
import model.Brand;
import model.Log;
import model.Supplier;


import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class brandController {

    @FXML private TextField search;
    @FXML private TableView<Brand> table;
    @FXML private TableColumn<Brand,String> brandColumn;
    @FXML private TableColumn<Brand, Supplier> supplierColumn;
    @FXML private TableColumn<Brand,String> descriptionColumn;
    @FXML private TableColumn<Brand,String> addedbyColumn;
    @FXML private TableColumn<Brand,LocalDate> datecreatedColumn;


    @FXML private void initialize(){

        brandColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        addedbyColumn.setCellValueFactory(new PropertyValueFactory<>("addedby"));
        datecreatedColumn.setCellValueFactory(new PropertyValueFactory<>("datecreated"));

        List<Brand> brands = DBManager.listAll(Brand.class);
        table.setItems(FXCollections.observableArrayList(brands));
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Brand>() {
            @Override
            public void changed(ObservableValue<? extends Brand> observable, Brand oldValue, Brand newValue) {
                if (newValue==null){return;}
            }
        });

        supplierColumn.setCellFactory(new Callback<TableColumn<Brand, Supplier>, TableCell<Brand, Supplier>>() {
            @Override
            public TableCell<Brand, Supplier> call(TableColumn<Brand, Supplier> param) {
                return new TableCell<Brand,Supplier>(){
                    @Override
                    protected void updateItem(Supplier item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item==null||empty)setText(null);
                        else
                            setText(item.getName());
                    }
                };
            }
        });

    }







    @FXML private void add() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addbrand.fxml"));
        Parent root = loader.load();

        addbrandController controller = loader.getController();
        controller.setTable(table);

        Stage stage = new Stage();
        stage.setTitle("Add Brand");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML private void update() throws IOException {

        if (table.getSelectionModel().isEmpty()){
            Utility.alert(Alert.AlertType.WARNING,"Please select a brand to update");
            return;
        }

        Brand brand = table.getSelectionModel().getSelectedItem();
        Supplier supplier = brand.getSupplier();
        String name = brand.getName();
        String description = brand.getDescription();

        if (supplier.getName().isEmpty()|| name.isEmpty()|| description.isEmpty()){
            Utility.alert(Alert.AlertType.WARNING,"Please check your inputs ");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/updatebrand.fxml"));
        Parent root = loader.load();

        updatebrandController controller = loader.getController();
        controller.setTable(table);
        controller.setItems(supplier,name,description);

        Stage stage = new Stage();
        stage.setTitle("Update Brand");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML private void delete(){


        if (table.getSelectionModel().isEmpty()){
            Utility.alert(Alert.AlertType.ERROR,"Please select a brand ");
            return;
         }
          else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" Are you Sure ");
            alert.setContentText("Please are you very sure about deletting this brand ? ");
            Optional<ButtonType> response = alert.showAndWait();

            if (response != null && response.isPresent() && response.get() == ButtonType.OK) {

                Brand brand = table.getSelectionModel().getSelectedItem();
                DBManager.delete(brand);
                table.refresh();

                Log log = new Log();
                log.setUser(loginController.getUser());
                log.setTime(Timestamp.valueOf(LocalDateTime.now()));
                log.setAction("New brand name "+brand.getName()+" removed");
                DBManager.save(log);

            }

          }

    }

    @FXML private void Search(){


        String search = this.search.getText();
        Long count = DBManager.queryForSingleResult(Long.class,"select count(c) from Brand C where c.name=?1",search);



        if (search.isEmpty() || count<1){

            Utility.alert(Alert.AlertType.ERROR,"Please enter the field or Item not found ");
            initialize();

            return;
        }else {
            Brand brand = DBManager.queryForSingleResult(Brand.class, "select c from Brand c where c.name=?1", search);
            table.getItems().clear();
            table.getItems().add(brand);
        }

    }


}
