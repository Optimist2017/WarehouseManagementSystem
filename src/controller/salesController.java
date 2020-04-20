package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class salesController {

    @FXML private VBox container;

    public void initialize() throws IOException {

        loadsalespoint();
    }


    private void loadcontent(String fxml) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/"+fxml+".fxml"));
        container.getChildren().clear();
        container.getChildren().add(root);
        VBox.setVgrow(root, Priority.ALWAYS);

    }

    @FXML private void loadsalespoint() throws IOException {
        loadcontent("salespoint");
    }

    @FXML private void loadcustomer() throws IOException {
        loadcontent("customer");
    }

}
