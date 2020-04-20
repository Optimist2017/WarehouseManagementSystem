package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class dashboardController {

    @FXML private VBox container ;

    public void initialize() throws IOException {
        loadhome();
    }

    private void loadcontent(String fxml) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/"+fxml+".fxml"));
        container.getChildren().clear();
        container.getChildren().add(root);
        VBox.setVgrow(root, Priority.ALWAYS);

    }

    @FXML private void loadhome() throws IOException {
        loadcontent("home");
    }

    @FXML private void loadstore() throws IOException {
        loadcontent("store");
    }
    @FXML private void loadsales() throws IOException {
        loadcontent("sales");
    }

    @FXML private void loademployee() throws IOException {
        loadcontent("employee");
    }

    @FXML private void loadsetting() throws IOException {
        loadcontent("setting");
    }


}
