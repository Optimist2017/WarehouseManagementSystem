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
import java.time.LocalDateTime;
import java.util.List;

public class updatebrandController {

    @FXML private ChoiceBox<Supplier> supplier;
    @FXML private TextField  name;
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


    @FXML private void update(){
        Supplier supplier = this.supplier.getValue();
        String name = this.name.getText();
        String description = this.description.getText();

        if (supplier.getName().isEmpty()||name.isEmpty()||description.isEmpty()){
            Utility.alert(Alert.AlertType.WARNING,"Please check your inputs ");
            return;
        }
        Brand brand = table.getSelectionModel().getSelectedItem();
        brand.setSupplier(supplier);
        brand.setName(name);
        brand.setDescription(description);
        DBManager.merge(brand);
        table.refresh();

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("Brand name "+brand.getName()+" updated");
        DBManager.save(log);

        ((Stage)this.name.getScene().getWindow()).close();


    }

    public void setTable(TableView<Brand> table){

        this.table = table;

    }

    public void setItems(Supplier supplier,String name,String description){

        this.supplier.setValue(supplier);
        this.name.setText(name);
        this.description.setText(description);

    }



}
