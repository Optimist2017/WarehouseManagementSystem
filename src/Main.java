import business.DBManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.PersistenceException;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        iniApp();
        primaryStage.setTitle("Warehouse Management System");
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }



    private void iniApp(){
        DBManager.createDbDirectory("database");
        DBManager.init("mysql");


    }

    private void cloeApp() {
        DBManager.close();
    }



}
