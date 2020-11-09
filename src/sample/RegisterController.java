package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


import javafx.event.ActionEvent;

import javax.xml.soap.Text;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;

public class RegisterController {

    @FXML
    private Button closeButton;
    @FXML
    private Label registrationMessageLabel, confirmPasswordLabel;
    @FXML
    private PasswordField passwordField, confirmPasswordField;
    @FXML
    private TextField firstNameTextField, lastNameTextField, usernameTextField;


    public void registerButtonOnAction(ActionEvent event) {
        if (passwordField.getText().equals(confirmPasswordField.getText())) {
            registerUser();
            confirmPasswordLabel.setText("");
        } else {
            confirmPasswordLabel.setText("Passwords does not match!!!");
            registrationMessageLabel.setText("");
        }
    }

    public void closeRegistrationButtonOnAction (ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void registerUser() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection con = databaseConnection.getConnection();

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        String insertFields = "INSERT INTO users (firstname, lastname, username, password) VALUES ('";
        String insertValues = firstName + "', '" + lastName + "', '" + username + "', '" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(insertToRegister);
            registrationMessageLabel.setText("The user has been registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }



}
