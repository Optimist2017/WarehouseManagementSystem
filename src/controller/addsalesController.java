package controller;

import business.DBManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class addsalesController {

    @FXML private ComboBox<Customer> customername;
    @FXML private ChoiceBox<Product> product;
    @FXML private TextField productname;
    @FXML private TextField quantity;
    @FXML private Text stock;
    @FXML private TextField salesprice;
    @FXML private Text purchaseprice;
    @FXML private TextField suppliedby;
    @FXML private TextField brand;
    @FXML private TextField category;
    @FXML private TextField date;
    @FXML private TableView<CartItem> table;
    @FXML private TableColumn<CartItem,Product> productColumn;
    @FXML private TableColumn<CartItem,Integer> quantityColumn;
    @FXML private TableColumn<CartItem,Double> priceColumn;
    @FXML private TableColumn<CartItem,Double> totalColumn;
    @FXML private TextField saletotal;

    private double amountValue;

    private TableView<Sale> table1;

    @FXML private void initialize(){

        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        productColumn.setCellFactory(new Callback<TableColumn<CartItem, Product>, TableCell<CartItem, Product>>() {
            @Override
            public TableCell<CartItem, Product> call(TableColumn<CartItem, Product> param) {
                return new TableCell<CartItem,Product>(){
                    @Override
                    protected void updateItem(Product item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item==null|| empty) setText(null);
                        else setText(item.getProductname());
                    }
                };
            }
        });


        List<CartItem> cartItems = DBManager.listAll(CartItem.class);
        table.setItems(FXCollections.observableArrayList(cartItems));

        table.getItems().clear();
        table.getItems().addListener((ListChangeListener.Change<? extends CartItem> c) -> {
            total();
            calculatetotal();

        });

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CartItem>() {
            @Override
            public void changed(ObservableValue<? extends CartItem> observable, CartItem oldValue, CartItem newValue) {
                if (newValue==null) return;
            }
        });


        List<Customer> customers = DBManager.listAll(Customer.class);
        customername.setItems(FXCollections.observableArrayList(customers));
        customername.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer object) {
                if (object==null) return null;
                return object.getName();
            }

            @Override
            public Customer fromString(String string) {
                return null;
            }
        });
        customername.getSelectionModel().selectFirst();

        List<Product> products = DBManager.listAll(Product.class);
        product.setItems(FXCollections.observableArrayList(products));
        product.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product object) {
                if (object==null) return null;
                return object.getProductname();
            }

            @Override
            public Product fromString(String string) {
                return null;
            }
        });
        product.getSelectionModel().selectFirst();

        Product product1 = product.getSelectionModel().getSelectedItem();

        productname.setText(product1.getProductname());
        stock.setText(product1.getQuantity());
        purchaseprice.setText(product1.getPurchaseprice());
        salesprice.setText(product1.getSalesprice());
        suppliedby.setText(String.valueOf(product1.getSupplier().getName()));
        brand.setText(String.valueOf(product1.getBrand().getName()));
        category.setText(String.valueOf(product1.getCategory().getName()));
        date.setText(String.valueOf(LocalDate.now()));
        quantity.setText(String.valueOf(1));





    }

    @FXML private void productinfor(){

        Product product1 = product.getSelectionModel().getSelectedItem();

        productname.setText(product1.getProductname());
        stock.setText(product1.getQuantity());
        purchaseprice.setText(product1.getPurchaseprice());
        salesprice.setText(product1.getSalesprice());
        suppliedby.setText(String.valueOf(product1.getSupplier().getName()));
        brand.setText(String.valueOf(product1.getBrand().getName()));
        category.setText(String.valueOf(product1.getCategory().getName()));
        date.setText(String.valueOf(LocalDate.now()));
        quantity.setText(String.valueOf(1));




    }


    public double total(){

        Product product = this.product.getValue();
        String quantity = this.quantity.getText().trim();
        quantity= quantity.isEmpty()? "0" : quantity;

        if (product==null ){
            return 0.0 ;
        }

        double total = Double.parseDouble(product.getSalesprice())* Double.parseDouble(quantity);

        return total;

    }
    private void calculatetotal(){
        amountValue = 0;
        for(CartItem cartItem: table.getItems()){

            amountValue += cartItem.getTotal();


        }

        saletotal.setText(String.valueOf(amountValue));

    }



    @FXML private void addcart(){

        Product product = this.product.getValue();
        String price = this.salesprice.getText();
        String quantity = this.quantity.getText().trim();


        CartItem item = new CartItem();
        item.setProduct(product);
        item.setPrice(Double.parseDouble(product.getSalesprice()));
        item.setQuantity(Integer.parseInt(quantity));
        item.setTotal(total());
        item.setPrice(Double.valueOf(price));

        table.getItems().add(item);
        total();



    }

    @FXML private void removecart(){

        Alert alert =new  Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Remove Cart Item");
        alert.setContentText("Do you really want to remove the item from the cart ?");

        Optional<ButtonType> buttonType = alert.showAndWait();

        if(buttonType!=null && buttonType.isPresent() && buttonType.get()==ButtonType.OK){
            CartItem item = table.getSelectionModel().getSelectedItem();

            table.getItems().remove(item);


        }



    }

    @FXML private void completesale(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Checkout");
        alert.setHeaderText("Confirm Items");
        alert.setContentText("Please confirm the items entered are correct and click enter");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType != null && buttonType.isPresent() && buttonType.get()== ButtonType.OK){

            Sale sale = new Sale();
            sale.setAmount(amountValue);
            sale.setSoldby(loginController.getUser());
            sale.setTime(Timestamp.valueOf(LocalDateTime.now()));
            sale.setCustomer(customername.getValue());


            DBManager.save(sale);

            for (CartItem item : table.getItems()) {

                item.setSale(sale);
                DBManager.save(item);
                Product product = DBManager.queryForSingleResult(Product.class, "select i from Product i where i.productname =?1", item.getProduct().getProductname());

                product.setQuantity(String.valueOf(Integer.parseInt(product.getQuantity())-item.getQuantity()));
                DBManager.merge(product);
            }

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Sale Completed");
            alert.setContentText("The sale have been saved successfully");
            alert.show();

            table.getItems().clear();

        }

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction(" Sale completed ");
        DBManager.save(log);


    }
    public void table(TableView<Sale> table){
        this.table1=table;

    }
}
