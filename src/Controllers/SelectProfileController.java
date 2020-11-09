package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import Classes.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectProfileController {

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    @FXML
    private ChoiceBox profileChoiceBox;
    @FXML
    private Button cancelButton, selectButton, addNewProfileButton;
    String username;


    public void initialize() throws SQLException {
        String query = "SELECT username FROM users;";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            username = resultSet.getString("username");
            profileChoiceBox.getItems().add(username);
        }
    }
    // ON ACTION METHODS

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void selectButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) selectButton.getScene().getWindow();
        stage.close();
        openWelcomeWindow();
    }
    public void addNewProfileButtonOnAction() {
        AddNewProfileController addNewProfileController = new AddNewProfileController(this);
        addNewProfileController.showStage();
    }

    // OTHER

    public String getUsername() {
        return profileChoiceBox.getValue().toString();
    }

    private void openWelcomeWindow() {
        WelcomeWindowController welcomeWindowController = new WelcomeWindowController(this);
        welcomeWindowController.showStage();
    }

}
