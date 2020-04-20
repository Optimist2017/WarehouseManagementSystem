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
import model.Category;
import model.Log;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class categoryController {

    @FXML private TextField search;
    @FXML private TableView<Category> table;
    @FXML private TableColumn<Category,String> categoryname;
    @FXML private TableColumn<Category,String> description;
    @FXML private TableColumn<Category,String> createdby;
    @FXML private TableColumn<Category,String> datecreated;


    @FXML private void initialize(){

        categoryname.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        createdby.setCellValueFactory(new PropertyValueFactory<>("created_by"));
        datecreated.setCellValueFactory(new PropertyValueFactory<>("date_created"));

        List<Category> categoryList = DBManager.listAll(Category.class);
        table.setItems(FXCollections.observableArrayList(categoryList));
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Category>() {
            @Override
            public void changed(ObservableValue<? extends Category> observable, Category oldValue, Category newValue) {
                if(newValue==null){
                    return;
                }
            }
        });


    }









    @FXML private void addnew() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addcategory.fxml"));
        Parent root = loader.load();
        addcategoryController controller = loader.getController();
        controller.setTable(table);

        Stage stage = new Stage();
        stage.setTitle("Add Category");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML private void update() throws IOException {

        if (table.getSelectionModel().isEmpty()){
            Utility.alert(Alert.AlertType.ERROR," Select a category to Update  ");
            return;
        }

        Category category = table.getSelectionModel().getSelectedItem();
        String name = category.getName();
        String descriptionn = category.getDescription();
        String createdby = category.getCreated_by();
        LocalDate datecreated = category.getDate_created();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/updatecategory.fxml"));
        Parent root = loader.load();


        updatecategoryController controller= loader.getController();
        controller.setTable(table);
        controller.setItems(name,descriptionn);
        Stage stage = new Stage();
        stage.setTitle(" Update Category ");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();


    }

    @FXML private void delete(){

        if (table.getSelectionModel().isEmpty()){
            Utility.alert(Alert.AlertType.ERROR,"Please select a category ");
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" Are you Sure ");
            alert.setContentText("Please are you very sure about deletting this category ? ");
            Optional<ButtonType> response =  alert.showAndWait();

            if(response !=null && response.isPresent() && response.get()==ButtonType.OK){

                Category category = table.getSelectionModel().getSelectedItem();
                DBManager.delete(category);
                table.refresh();

                Log log = new Log();
                log.setUser(loginController.getUser());
                log.setTime(Timestamp.valueOf(LocalDateTime.now()));
                log.setAction("Category name "+category.getName()+" remove");
                DBManager.save(log);

            }

        }


    }

    @FXML private void Search(){


        String search = this.search.getText();
        Long count = DBManager.queryForSingleResult(Long.class,"select count(c) from Category C where c.name=?1",search);



        if (search.isEmpty() || count<1){

            Utility.alert(Alert.AlertType.ERROR,"Please enter the field or Item not found ");
            initialize();


            return;
        }else {
            Category category = DBManager.queryForSingleResult(Category.class, "select c from Category c where c.name=?1", search);
            table.getItems().clear();
            table.getItems().add(category);
        }

    }

}
