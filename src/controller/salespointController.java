package controller;

import business.DBManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class salespointController {
    @FXML private TextField search;
    @FXML private TableView<Sale> table;
    @FXML private TableColumn<Sale,Long> id;
    @FXML private TableColumn<Sale, List<CartItem>> productid;
    @FXML private TableColumn<Sale, Customer> customername;
    @FXML private TableColumn<Sale,Timestamp> solddate;
    @FXML private TableColumn<Sale,List<CartItem>> price;
    @FXML private TableColumn<Sale,List<CartItem>> quantity;
    @FXML private TableColumn<Sale,List<CartItem>> totalprice;
    @FXML private TableColumn<Sale, Employees> soldby;


    @FXML private void initialize(){

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        productid.setCellValueFactory(new PropertyValueFactory<>("items"));
        customername.setCellValueFactory(new PropertyValueFactory<>("customer"));
        solddate.setCellValueFactory(new PropertyValueFactory<>("time"));
        price.setCellValueFactory(new PropertyValueFactory<>("items"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("items"));
        totalprice.setCellValueFactory(new PropertyValueFactory<>("amount"));
        soldby.setCellValueFactory(new PropertyValueFactory<>("soldby"));

        productid.setCellFactory(cell -> new TableCell<Sale, List<CartItem>>() {
            @Override
            protected void updateItem(List<CartItem> cartItems, boolean empty) {
                super.updateItem(cartItems, empty);
                if (cartItems == null || empty) {
                    setText(null);
                    return;
                }

                StringBuilder builder = new StringBuilder();
                for (CartItem cartItem : cartItems) {
                    builder.append(cartItem.getProduct().getId()).append(";");
                }
                setText(builder.toString());
            }
        });
        price.setCellFactory(cell -> new TableCell<Sale,List<CartItem>>(){
            @Override
            protected void updateItem(List<CartItem> cartItems, boolean empty) {
                super.updateItem(cartItems, empty);
                if (cartItems==null|| empty){
                    setText(null);
                    return;
                }
                StringBuilder builder= new StringBuilder();
                for (CartItem cartItem:cartItems){
                    builder.append(cartItem.getPrice()).append(";");
                }
                setText(builder.toString());
            }
        });

        quantity.setCellFactory(cell -> new TableCell<Sale,List<CartItem>>(){
            @Override
            protected void updateItem(List<CartItem> item, boolean empty) {
                super.updateItem(item, empty);
                if (item==null|| empty)
                {
                    setText(null);
                    return;
                }
                StringBuilder builder = new StringBuilder();
                for (CartItem cartItem:item){
                    builder.append(cartItem.getQuantity()).append(";");
                }
                setText(builder.toString());
            }
        });

        customername.setCellFactory(cell -> new TableCell<Sale,Customer>(){
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                if (item==null|| empty){
                    setText(null);
                    return;
                }
                setText(item.getName());
            }
        });

        List<Sale> sales = DBManager.listAll(Sale.class);
        table.setItems(FXCollections.observableArrayList(sales));
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sale>() {
            @Override
            public void changed(ObservableValue<? extends Sale> observable, Sale oldValue, Sale newValue) {
                if (newValue==null) return;
            }
        });

        soldby.setCellFactory(new Callback<TableColumn<Sale, Employees>, TableCell<Sale, Employees>>() {
            @Override
            public TableCell<Sale, Employees> call(TableColumn<Sale, Employees> param) {
                return new TableCell<Sale,Employees>(){
                    @Override
                    protected void updateItem(Employees item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item==null|| empty) setText(null);
                        else setText(item.getFullname());
                    }
                };
            }
        });




    }




    @FXML private void addsales() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addsales.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        addsalesController controller =new addsalesController();
        controller.table(table);
        stage.setTitle("Add Sale");
        stage.setScene(new Scene(root));
        stage.show();



    }
}
