package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectProfileController {

    //private final Stage thisStage;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    @FXML
    private ChoiceBox profileChoiceBox;
    @FXML
    private Button cancelButton, selectButton;
    String username;
    String selectedProfile;

    /*
    public SelectProfileController () {
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("selectProfile.fxml"));
            loader.setController(this);
            thisStage.initStyle(StageStyle.UNDECORATED);
            thisStage.setScene(new Scene(loader.load(), 520, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }

     */


    public void initialize() throws SQLException {
        selectButton.setOnAction(e -> openWelcomeWindow());
        String query = "SELECT username FROM users;";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            username = resultSet.getString("username");
            profileChoiceBox.getItems().add(username);
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void selectButtonOnAction(ActionEvent event) throws IOException {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("welcomeWindow.fxml"));
        Stage registerStage = new Stage();
        registerStage.initStyle(StageStyle.UNDECORATED);
        registerStage.setScene(new Scene(root, 600, 400));
        registerStage.show();
         */
        openWelcomeWindow();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        System.out.println(getUsername());
        stage.close();
        //WelcomeWindowController welcomeWindowController = new WelcomeWindowController(this);
        //selectedProfile = profileChoiceBox.getValue().toString();
    }
    public String getUsername() {
        return profileChoiceBox.getValue().toString();
    }

    private void openWelcomeWindow() {
        WelcomeWindowController welcomeWindowController = new WelcomeWindowController(this);
        welcomeWindowController.showStage();
    }
}
