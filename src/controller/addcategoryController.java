package controller;

import business.DBManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Category;
import model.Log;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class addcategoryController {

    @FXML private TextField name;
    @FXML private TextArea description;

    private TableView<Category> table;

    @FXML private void save(){

        String name = this.name.getText();
        String description = this.description.getText();

        if (name.isEmpty()|| description.isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(" Inputs Error ");
            alert.setHeaderText("Please check your inputs ");
            alert.setContentText(" Inputs fields empty of wrong inputs entered ");
            alert.show();
            return;


        }

        Category category = new Category();

        category.setName(name);
        category.setDescription(description);
        category.setDate_created(LocalDate.now());
        category.setCreated_by(loginController.getUser().getUsername());
        table.getItems().addAll(category);

        DBManager.save(category);

        ((Stage) this.description.getScene().getWindow()).close();

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("New category name "+name+" added");
        DBManager.save(log);
    }



    public void setTable(TableView<Category> table){
        this.table = table;
    }



}
