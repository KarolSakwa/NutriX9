package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;

public class WelcomeWindowController {
    private Stage thisStage;
    private final SelectProfileController selectProfileController;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    @FXML
    private Label welcomeLabel;

    public WelcomeWindowController (SelectProfileController selectProfileController){
        this.selectProfileController = selectProfileController;
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("welcomeWindow.fxml"));
            loader.setController(this);
            thisStage.initStyle(StageStyle.UNDECORATED);
            thisStage.setScene(new Scene(loader.load(), 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        welcomeLabel.setText(selectProfileController.getUsername());
    }
    public void showStage(){
        thisStage.showAndWait();
    }
}
