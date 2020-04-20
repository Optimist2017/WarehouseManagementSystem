package controller;

import business.DBManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Product;


public class homeController {

    @FXML private TextField stock;
    @FXML private TextField supplier;
    @FXML private TextField customer;
    @FXML private TextField employees;

    @FXML  private void initialize(){

        Long count = DBManager.queryForSingleResult(Long.class,"select count (p) from Product p ");
        Long count1 = DBManager.queryForSingleResult(Long.class,"select count (p) from Supplier p ");
        Long count2 = DBManager.queryForSingleResult(Long.class,"select count (p) from Employees p ");
        Long count3 = DBManager.queryForSingleResult(Long.class,"select count (p) from Customer p ");


        stock.setText(String.valueOf(count));
        supplier.setText(String.valueOf(count1));
        customer.setText(String.valueOf(count3));
        employees.setText(String.valueOf(count2));


    }











}
