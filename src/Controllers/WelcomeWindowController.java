package Controllers;

import Classes.ChildrenWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Classes.DatabaseConnection;

import java.sql.Connection;

public class WelcomeWindowController {
    private Stage thisStage;
    private final SelectProfileController selectProfileController;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    @FXML
    private Label welcomeLabel;

    public WelcomeWindowController(SelectProfileController selectProfileController){
        this.selectProfileController = selectProfileController;
        thisStage = new Stage();
        ChildrenWindow welcomeWindow = new ChildrenWindow();
        welcomeWindow.create("../fxml/welcomeWindow.fxml", this, thisStage, false, 600, 400);
    }

    public void initialize() {
        welcomeLabel.setText(selectProfileController.getUsername());
    }

    public void showStage(){
        thisStage.showAndWait();
    }
}