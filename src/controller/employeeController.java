package controller;

import business.DBManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Employees;
import model.Log;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class employeeController {



    @FXML private TextField search;
     public TableView<Employees> table;
     public TableColumn<Employees,String> employeeColumn;
    @FXML private TextField username;
    @FXML private TextField fullname;
    @FXML private TextField phonenumber;
    @FXML private TextField email;
    @FXML private TextField password;
    @FXML private TextField salary;
    @FXML private TextField dateregistered;
    @FXML private TextField registeredby;
    public ImageView picture;
    @FXML private ChoiceBox<String> status;
    @FXML private TextArea address;
    @FXML private Button addbtn;
    @FXML private Button removebtn;
    @FXML private Button updatebtn;


    private String image;


    @FXML  private void initialize(){

        employeeColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        this.dateregistered.setText(String.valueOf(LocalDate.now()));

        status.getItems().addAll("Active","Inactive");
        removebtn.setDisable(true);
        updatebtn.setDisable(true);

        List<Employees> employees = DBManager.listAll(Employees.class);
        table.setItems(FXCollections.observableArrayList(employees));
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employees>() {
            @Override
            public void changed(ObservableValue<? extends Employees> observable, Employees oldValue, Employees newValue) {
                if (newValue==null ){
                    picture.setImage(null);



                    return;

                }
                else {
                    String pictue = newValue.getImage();
                    if (pictue==null||pictue.isEmpty()){
                        username.setText(newValue.getUsername());
                        fullname.setText(newValue.getFullname());
                        phonenumber.setText(newValue.getPhonenumber());
                        email.setText(newValue.getEmail());
                        password.setText(newValue.getPassword());
                        salary.setText(newValue.getSalary());
                        address.setText(newValue.getAddress());
                        status.setValue(newValue.getStatus());
                        dateregistered.setText(String.valueOf(newValue.getDateregistered()));
                        registeredby.setText(newValue.getRegisteredby());
                        addbtn.setDisable(true);
                        updatebtn.setDisable(false);
                        removebtn.setDisable(false);
                        return;
                    }
                    byte[] bytes = Base64.decodeBase64(pictue);
                    picture.setImage(new Image(new ByteArrayInputStream(bytes)));
                    removebtn.setDisable(true);
                    updatebtn.setDisable(true);

                    username.setText(newValue.getUsername());
                    fullname.setText(newValue.getFullname());
                    phonenumber.setText(newValue.getPhonenumber());
                    email.setText(newValue.getEmail());
                    password.setText(newValue.getPassword());
                    salary.setText(newValue.getSalary());
                    address.setText(newValue.getAddress());
                    status.setValue(newValue.getStatus());
                    dateregistered.setText(String.valueOf(newValue.getDateregistered()));
                    registeredby.setText(newValue.getRegisteredby());



                }

                removebtn.setDisable(false);
                updatebtn.setDisable(false);

                username.setText(newValue.getUsername());
                fullname.setText(newValue.getFullname());
                phonenumber.setText(newValue.getPhonenumber());
                email.setText(newValue.getEmail());
                password.setText(newValue.getPassword());
                salary.setText(newValue.getSalary());
                address.setText(newValue.getAddress());
                status.setValue(newValue.getStatus());
                dateregistered.setText(String.valueOf(newValue.getDateregistered()));
                registeredby.setText(newValue.getRegisteredby());



            }


        });






    }


    @FXML public void selectimage() throws IOException {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Please Select Employee Image ");
        File file = chooser.showOpenDialog(username.getScene().getWindow());

        if (file!=null){
            byte[] bytes = FileUtils.readFileToByteArray(file);

            image = Base64.encodeBase64String(bytes);
            picture.setImage(new Image(new ByteArrayInputStream(bytes)));


        }


    }


    @FXML private void add(){

        String username = this.username.getText();
        String fullname = this.fullname.getText();
        String phonenumber = this.phonenumber.getText();
        String email = this.email.getText();
        String password =this.password.getText();
        String salary = this.salary.getText();
        String dateregistered = this.dateregistered.getText();
        String registeredby = this.registeredby.getText();
        String status = this.status.getValue();
        String address = this.address.getText();

        if (username.isEmpty()|| fullname.isEmpty()|| phonenumber.isEmpty()|| email.isEmpty()|| status.isEmpty()||address.isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Please check your inputs ");
            alert.setHeaderText("Inputs Error");
            alert.setContentText("Please check your inputs to make sure they are entered correctly ");
            alert.show();
            return;
        }

        Employees employees = new Employees();
        employees.setUsername(username);
        employees.setFullname(fullname);
        employees.setPhonenumber(phonenumber);
        employees.setEmail(email);
        employees.setPassword(password);
        employees.setSalary(salary);
        employees.setDateregistered(LocalDate.parse(dateregistered));
        employees.setRegisteredby(registeredby);
        employees.setImage(image);
        employees.setStatus(status);
        employees.setAddress(address);

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("New employee name "+username+" added");
        DBManager.save(log);



        DBManager.save(employees);
        table.getItems().add(employees);

        this.username.clear();
        this.fullname.clear();
        this.email.clear();
        this.phonenumber.clear();
        this.password.clear();
        this.salary.clear();
        this.dateregistered.clear();
        this.registeredby.clear();
        this.picture.setImage(null);
        this.status.setValue(null);
        this.address.clear();








    }

    @FXML private void remove(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletion");
        alert.setHeaderText("Delete");
        alert.setContentText("Are sure you want to delete the Employee from the list");
        Optional<ButtonType> response = alert.showAndWait();

        if(response != null && response.isPresent() && response.get()==ButtonType.OK){

            Employees employees = table.getSelectionModel().getSelectedItem();

            DBManager.delete(employees);
            table.getItems().remove(employees);

            Log log = new Log();
            log.setUser(loginController.getUser());
            log.setTime(Timestamp.valueOf(LocalDateTime.now()));
            log.setAction("Employee name "+employees.getUsername()+" removed");
            DBManager.save(log);


        }

    }

    @FXML private void update(){


        String username = this.username.getText();
        String fullname = this.fullname.getText();
        String phonenumber = this.phonenumber.getText();
        String email = this.email.getText();
        String password = this.password.getText();
        String salary = this.salary.getText();
        String dateregistered = this.dateregistered.getText();
        String registeredby = this.registeredby.getText();
        String status = this.status.getValue();
        String address = this.address.getText();

        Employees employees = table.getSelectionModel().getSelectedItem();
        employees.setUsername(username);
        employees.setFullname(fullname);
        employees.setPhonenumber(phonenumber);
        employees.setEmail(email);
        employees.setPassword(password);
        employees.setSalary(salary);
        employees.setDateregistered(LocalDate.parse(dateregistered));
        employees.setRegisteredby(registeredby);
        employees.setImage(image);
        employees.setStatus(status);
        employees.setAddress(address);

        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setAction("Employee name "+username+" record updated");
        DBManager.save(log);



        DBManager.merge(employees);
        table.refresh();

        this.username.clear();
        this.fullname.clear();
        this.email.clear();
        this.phonenumber.clear();
        this.password.clear();
        this.salary.clear();
        this.dateregistered.clear();
        this.registeredby.clear();
        this.picture.setImage(null);
        this.status.setValue(null);
        this.address.clear();



    }



}
