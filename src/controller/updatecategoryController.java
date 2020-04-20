package controller;

import business.DBManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Category;
import model.Log;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class updatecategoryController {

    @FXML private TextField name;
    @FXML private TextArea description;

    private TableView<Category> table;

    @FXML private void update(){

        String name = this.name.getText();
        String description = this.description.getText();

        Category category = table.getSelectionModel().getSelectedItem();
        category.setName(name);
        category.setDescription(description);

        DBManager.merge(category);
        table.refresh();

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("Category name "+category.getName()+" updated");
        DBManager.save(log);

        ((Stage)this.name.getScene().getWindow()).close();



    }




    public void setTable(TableView<Category> table){
        this.table = table;
    }

    public void setItems(String name , String description){

        this.name.setText(name);
        this.description.setText(description);

    }


}
