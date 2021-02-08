package Controllers;

import Classes.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import Classes.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectProfileController {

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    @FXML ComboBox selectProfileComboBox;
    @FXML Button cancelButton, selectButton;
    String username;

    public void initialize() throws SQLException {
        selectProfileComboBox.setEditable(true);
        String query = "SELECT username FROM users;";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            username = resultSet.getString("username");
            selectProfileComboBox.getItems().add(username);
        }
    }
    // ON ACTION METHODS

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void selectButtonOnAction(ActionEvent event) throws SQLException {
        String username = selectProfileComboBox.getValue().toString();
        try {
            Statement statement = con.createStatement();
            ResultSet userExists = statement.executeQuery("SELECT COUNT(*) AS 'rowCount' FROM users WHERE username = '" + username + "'");
            userExists.next();
            Integer count = userExists.getInt("rowCount");
            if (count == 0) // adding new user to the db only when there's no other with this name
                statement.executeUpdate("INSERT INTO users (username) VALUE ('" + username + "')");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        Stage stage = (Stage) selectButton.getScene().getWindow();
        stage.close();
        if (Helper.userDietsNum(username) == 0)
            openAddNewDietWindow();
        else
            openDietView();
    }

    // OTHER

    public String getUsername() {
        return selectProfileComboBox.getValue().toString();
    }

    private void openAddNewDietWindow() {
        AddNewDietController addNewDietController = new AddNewDietController(this);
        addNewDietController.showStage();
    }

    private void openDietView() throws SQLException {
        DietViewController dietViewController = new DietViewController(this);
        dietViewController.showStage();
    }
}
