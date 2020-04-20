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

public class updateproductController {

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

        user.setText("Developer");

    }


    @FXML private void update(){

        if (name.getText().isEmpty()|| quantity.getText().isEmpty()||purchaseprice.getText().isEmpty()||salesprice.getText().isEmpty()||description.getText().isEmpty()
                ||user.getText().isEmpty()||dateadded.getConverter().toString().isEmpty()||suppliedby.getValue().getName().isEmpty()||brand.getValue().getName().isEmpty()
                ||category.getValue().getName().isEmpty()){
            Utility.alert(Alert.AlertType.WARNING,"Please chech your inputs");
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

        Product product = table.getSelectionModel().getSelectedItem();
        product.setProductname(name);
        product.setQuantity(quantity);
        product.setPurchaseprice(purchaseprice);
        product.setSalesprice(salesprice);
        product.setDescription(description);
        product.setEmployees(null);
        product.setDateadded(dateadded);
        product.setSupplier(supplier);
        product.setCategory(category);
        product.setBrand(brand);

        DBManager.merge(product);
        table.refresh();

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("Product name "+product.getProductname()+" updated");
        DBManager.save(log);

        ((Stage)this.name.getScene().getWindow()).close();

    }

    public void setTable(TableView<Product> table){

        this.table=table;

    }

    public void setItems(String productname, String quantity, String purchaseprice, String salesprice, String description, Employees employees
    ,LocalDate dateadded , Supplier supplier,Category category,Brand brand){

        this.name.setText(productname);
        this.quantity.setText(quantity);
        this.purchaseprice.setText(purchaseprice);
        this.salesprice.setText(salesprice);
        this.description.setText(description);
        this.user.setText(String.valueOf(employees));
        this.dateadded.setValue(dateadded);
        this.suppliedby.setValue(supplier);
        this.category.setValue(category);
        this.brand.setValue(brand);

    }






}
