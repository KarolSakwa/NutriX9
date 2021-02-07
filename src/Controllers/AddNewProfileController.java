package Controllers;

import Classes.ChildrenWindow;
import Classes.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.Statement;

public class AddNewProfileController {
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    Stage thisStage = new Stage();
    @FXML private Button closeButton;
    @FXML private TextField firstNameTextField, lastNameTextField, usernameTextField;
    private final SelectProfileController selectProfileController;

    public AddNewProfileController(SelectProfileController selectProfileController) {
        this.selectProfileController = selectProfileController;
        ChildrenWindow.create("../fxml/addNewProfile.fxml", this, thisStage, true, 520, 368);
    }

    public void showStage(){
        thisStage.showAndWait();
    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void addButtonOnAction(ActionEvent event) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();

        String query = "INSERT INTO users (firstname, lastname, username) VALUES ('";
        String insertValues = firstName + "', '" + lastName + "', '" + username + "')";
        String addToDB = query + insertValues;

        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(addToDB);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
