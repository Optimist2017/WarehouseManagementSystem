package controller;

import Utility.Utility;
import business.DBManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class addproductController {
    @FXML private TextField name;
    @FXML private TextField quantity;
    @FXML private TextField purchaseprice;
    @FXML private TextField salesprice;
    @FXML private TextArea description;
    @FXML private TextField user;
    @FXML private ComboBox<Supplier> suppliedby;
    @FXML private ComboBox<Category> category;
    @FXML private ComboBox<Brand> brand;
    @FXML private DatePicker dateadded;

    private TableView<Product> table;

    @FXML private void initialize(){

        List<Supplier> suppliers = DBManager.listAll(Supplier.class);
        suppliedby.setItems(FXCollections.observableArrayList(suppliers));
        suppliedby.setConverter(new StringConverter<Supplier>() {
            @Override
            public String toString(Supplier object) {
                if (object==null) return null;
                return object.getName();
            }

            @Override
            public Supplier fromString(String string) {
                return null;
            }
        });
        suppliedby.getSelectionModel().selectFirst();


        List<Category> categories = DBManager.listAll(Category.class);
        category.setItems(FXCollections.observableArrayList(categories));
        category.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category object) {
                if (object==null) return null;
                return object.getName();
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        });

        category.getSelectionModel().selectFirst();


        List<Brand> brands = DBManager.listAll(Brand.class);
        brand.setItems(FXCollections.observableArrayList(brands));
        brand.setConverter(new StringConverter<Brand>() {
            @Override
            public String toString(Brand object) {
                if (object==null)return null;
                return object.getName();
            }

            @Override
            public Brand fromString(String string) {
                return null;
            }
        });

        brand.getSelectionModel().selectFirst();

        dateadded.setValue(LocalDate.now());

        user.setText(loginController.getUser().getFullname());

    }



    @FXML private void save(){

        if (name.getText().isEmpty()|| quantity.getText().isEmpty()||purchaseprice.getText().isEmpty()||salesprice.getText().isEmpty()||description.getText().isEmpty()
                ||user.getText().isEmpty()||dateadded.getConverter().toString().isEmpty()||suppliedby.getValue().getName().isEmpty()||brand.getValue().getName().isEmpty()
                ||category.getValue().getName().isEmpty()){
            Utility.alert(Alert.AlertType.WARNING,"Please check your inputs");
            return;
        }

        String name = this.name.getText();
        String quantity = this.quantity.getText();
        String purchaseprice = this.purchaseprice.getText();
        String salesprice = this.salesprice.getText();
        String description = this.description.getText();
        LocalDate dateadded = LocalDate.now();
        Supplier supplier = this.suppliedby.getValue();
        Category category = this.category.getValue();
        Brand brand = this.brand.getValue();

        Product product = new Product();
        product.setProductname(name);
        product.setQuantity(quantity);
        product.setPurchaseprice(purchaseprice);
        product.setSalesprice(salesprice);
        product.setDescription(description);
        product.setEmployees(loginController.getUser());
        product.setDateadded(dateadded);
        product.setSupplier(supplier);
        product.setCategory(category);
        product.setBrand(brand);


        table.getItems().addAll(product);
        DBManager.save(product);
        ((Stage)this.name.getScene().getWindow()).close();

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("New product name "+name+" added");
        DBManager.save(log);


    }

    public void setTable(TableView<Product> table){
        this.table = table;
    }

}
