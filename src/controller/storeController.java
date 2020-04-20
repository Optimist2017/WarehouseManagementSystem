package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class storeController {

    @FXML private VBox container ;

    public void initialize() throws IOException {
        loadproduct();
    }



    private void loadcontent(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/"+fxml+".fxml"));
        container.getChildren().clear();
        container.getChildren().add(root);
        VBox.setVgrow(root, Priority.ALWAYS);
    }

    @FXML private void loadproduct() throws IOException {
        loadcontent("products");
    }
    @FXML private void loadsupplier() throws IOException {
        loadcontent("suppliers");
    }

    @FXML private void loadbrand() throws IOException {
        loadcontent("Brand");
    }

    @FXML private void loadcategory() throws IOException {
        loadcontent("category");
    }
}
