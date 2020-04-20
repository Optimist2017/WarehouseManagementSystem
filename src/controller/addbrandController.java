package controller;

import Utility.Utility;
import business.DBManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Brand;
import model.Log;
import model.Supplier;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class addbrandController {

    @FXML private ChoiceBox<Supplier> supplier;
    @FXML private TextField name;
    @FXML private TextArea description;

    private TableView<Brand> table;

    @FXML private void initialize(){
        supplier.setConverter(new StringConverter<Supplier>() {
            @Override
            public String toString(Supplier object) {
                if (object==null)return null;
                return object.getName();
            }

            @Override
            public Supplier fromString(String string) {
                return null;
            }
        });
        List<Supplier> suppliers = DBManager.listAll(Supplier.class);
        supplier.setItems(FXCollections.observableArrayList(suppliers));
        supplier.getSelectionModel().selectFirst();
    }

    @FXML private void save(){

        Supplier supplier = this.supplier.getValue();
        String name = this.name.getText();
        String description = this.description.getText();

        if (supplier.getName().isEmpty()|| name.isEmpty()||description.isEmpty()){
            Utility.alert(Alert.AlertType.WARNING,"Please check your inputs");
            return;
        }

        Brand brand = new Brand();
        brand.setSupplier(supplier);
        brand.setName(name);
        brand.setAddedby(loginController.getUser().getUsername());
        brand.setDescription(description);
        brand.setDatecreated(LocalDate.now());

        table.getItems().addAll(brand);

        DBManager.save(brand);

        ((Stage)this.name.getScene().getWindow()).close();

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("New brand name "+name+" added");
        DBManager.save(log);




    }

    public void setTable(TableView<Brand> table){
        this.table = table;

    }

}
