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
import javafx.util.StringConverter;
import model.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class productsController {

    @FXML private TextField search;
    @FXML private ComboBox<Supplier> supplierSearch;
    @FXML private ComboBox<Category> categorySearch;
    @FXML private ComboBox<Brand> brandSearch;
    @FXML private TableView<Product> table;
    @FXML private TableColumn<Product,Long> idColumn;
    @FXML private TableColumn<Product,String> nameColumn;
    @FXML private TableColumn<Product,String> quantityColumn;
    @FXML private TableColumn<Product,Supplier> supplierColumn;
    @FXML private TableColumn<Product,Brand>  brandColumn;
    @FXML private TableColumn<Product, Category> categoryColumn;
    @FXML private TableColumn<Product,String> purchaseColumn;
    @FXML private TableColumn<Product,String> sellingColumn;

    @FXML private void initialize(){

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productname"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        purchaseColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseprice"));
        sellingColumn.setCellValueFactory(new PropertyValueFactory<>("salesprice"));



        List<Product> products = DBManager.listAll(Product.class);
        table.setItems(FXCollections.observableArrayList(products));
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                if (newValue==null)return;
            }
        });

        supplierColumn.setCellFactory(new Callback<TableColumn<Product, Supplier>, TableCell<Product, Supplier>>() {
            @Override
            public TableCell<Product, Supplier> call(TableColumn<Product, Supplier> param) {
                return new TableCell<Product,Supplier>(){
                    @Override
                    protected void updateItem(Supplier item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item==null||empty) setText(null);
                        else
                            setText(item.getName());
                    }
                };
            }
        });

        brandColumn.setCellFactory(new Callback<TableColumn<Product, Brand>, TableCell<Product, Brand>>() {
            @Override
            public TableCell<Product, Brand> call(TableColumn<Product, Brand> param) {
                return new TableCell<Product,Brand>(){
                    @Override
                    protected void updateItem(Brand item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item==null||empty) setText(null);
                        else
                            setText(item.getName());
                    }
                };
            }
        });

        categoryColumn.setCellFactory(new Callback<TableColumn<Product, Category>, TableCell<Product, Category>>() {
            @Override
            public TableCell<Product, Category> call(TableColumn<Product, Category> param) {
                return new TableCell<Product,Category>(){
                    @Override
                    protected void updateItem(Category item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item==null||empty) setText(null);
                        else
                            setText(item.getName());
                    }
                };
            }
        });


        List<Supplier> suppliers = DBManager.listAll(Supplier.class);
        supplierSearch.setItems(FXCollections.observableArrayList(suppliers));
        supplierSearch.setConverter(new StringConverter<Supplier>() {
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
        supplierSearch.getSelectionModel().selectFirst();

        List<Brand> brands = DBManager.listAll(Brand.class);
        brandSearch.setItems(FXCollections.observableArrayList(brands));
        brandSearch.setConverter(new StringConverter<Brand>() {
            @Override
            public String toString(Brand object) {
                if (object==null) return null;
                return object.getName();
            }

            @Override
            public Brand fromString(String string) {
                return null;
            }
        });
        brandSearch.getSelectionModel().selectFirst();

        List<Category> categories= DBManager.listAll(Category.class);
        categorySearch.setItems(FXCollections.observableArrayList(categories));
        categorySearch.setConverter(new StringConverter<Category>() {
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
        categorySearch.getSelectionModel().selectFirst();

    }






    @FXML private void Add() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addproduct.fxml"));
        Parent root = loader.load();
        addproductController controller = loader.getController();
        controller.setTable(table);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Product");
        stage.setScene(new Scene(root));
        stage.show();


    }

    @FXML private void update() throws IOException {

        if (table.getSelectionModel().isEmpty()){

            Utility.alert(Alert.AlertType.WARNING,"Please select a product to update");
            return;

        }

        Product product  = table.getSelectionModel().getSelectedItem();
        String name = product.getProductname();
        String quantity = product.getQuantity();
        String purchaseprice = product.getPurchaseprice();
        String salesprice = product.getSalesprice();
        String description = product.getDescription();
        Employees employees = product.getEmployees();
        LocalDate dateadded = product.getDateadded();
        Supplier supplier = product.getSupplier();
        Category category = product.getCategory();
        Brand brand = product.getBrand();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/updateproduct.fxml"));
        Parent root = loader.load();
        updateproductController controller = loader.getController();
        controller.setTable(table);
        controller.setItems(name,quantity,purchaseprice,salesprice,description,employees,dateadded,supplier,category,brand);

        Stage stage = new Stage();
        stage.setTitle("Update Product");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML private void delete(){

        if (table.getSelectionModel().isEmpty()){
            Utility.alert(Alert.AlertType.WARNING,"Please select a Product ");
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" Are you Sure ");
            alert.setContentText("Please are you very sure about deletting this category ? ");
            Optional<ButtonType> response =  alert.showAndWait();

            if(response !=null && response.isPresent() && response.get()==ButtonType.OK){

                Product product = table.getSelectionModel().getSelectedItem();
                DBManager.delete(product);
                table.refresh();

                Log log = new Log();
                log.setUser(loginController.getUser());
                log.setTime(Timestamp.valueOf(LocalDateTime.now()));
                log.setAction("Product name "+product.getProductname()+" removed");
                DBManager.save(log);

            }

        }



    }

    @FXML private void Search(){


        String search = this.search.getText();
        Long count = DBManager.queryForSingleResult(Long.class,"select count(c) from Product C where c.productname=?1",search);



        if (search.isEmpty() || count<1){

            Utility.alert(Alert.AlertType.ERROR,"Please enter the field or Item not found ");
            List<Product> products = DBManager.listAll(Product.class);
            table.setItems(FXCollections.observableArrayList(products));



            return;
        }else {
            Product product = DBManager.queryForSingleResult(Product.class, "select c from Product c where c.productname=?1", search);
            table.getItems().clear();
            table.getItems().add(product);
        }

    }

    @FXML private void SupplierSearch(){


        Supplier search = this.supplierSearch.getValue();
        Long count = DBManager.queryForSingleResult(Long.class,"select count(c) from Product C where c.supplier=?1",search);



        if (search.getName().isEmpty() || count<1){

            Utility.alert(Alert.AlertType.ERROR,"Please enter the field or Item not found ");
            List<Product> products = DBManager.listAll(Product.class);
            table.setItems(FXCollections.observableArrayList(products));



            return;
        }else {
            List<Product> product = DBManager.query(Product.class, "select c from Product c where c.supplier=?1", search);
            table.getItems().clear();
            table.setItems(FXCollections.observableArrayList(product));
        }

    }

    @FXML private void CategorySearch(){


        Category search = this.categorySearch.getValue();
        Long count = DBManager.queryForSingleResult(Long.class,"select count(c) from Product C where c.category=?1",search);



        if (search.getName().isEmpty() || count<1){

            Utility.alert(Alert.AlertType.ERROR,"Please enter the field or Item not found ");
            List<Product> products = DBManager.listAll(Product.class);
            table.setItems(FXCollections.observableArrayList(products));

            return;
        }else {
            List<Product> product = DBManager.query(Product.class, "select c from Product c where c.category=?1", search);
            table.getItems().clear();
            table.setItems(FXCollections.observableArrayList(product));
        }

    }

    @FXML private void BrandSearch(){


        Brand search = this.brandSearch.getValue();
        Long count = DBManager.queryForSingleResult(Long.class,"select count(c) from Product C where c.brand=?1",search);



        if (search.getName().isEmpty() || count<1){

            Utility.alert(Alert.AlertType.ERROR,"Please enter the field or Item not found ");
            List<Product> products = DBManager.listAll(Product.class);
            table.setItems(FXCollections.observableArrayList(products));



            return;
        }else {
            List<Product> product = DBManager.query(Product.class, "select c from Product c where c.brand=?1", search);
            table.getItems().clear();
            table.setItems(FXCollections.observableArrayList(product));
        }

    }


}
