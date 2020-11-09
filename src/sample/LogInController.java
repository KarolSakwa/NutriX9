package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;


public class LogInController {

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;

    public void loginButtonOnAction() {
        if (!usernameInput.getText().isEmpty() && !passwordInput.getText().isEmpty()) {
            validateLogIn();
        }
        else {
            loginMessageLabel.setText("Invalid login or password. Try again.");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogIn() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection dbconnection = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE username = '" + usernameInput.getText() + "' AND password = '" + passwordInput.getText() + "'";

        try {
            Statement statement = dbconnection.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if(queryResult.getInt(1) == 1) {
                    createAccountForm();
                } else {
                    loginMessageLabel.setText("Invalid");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void createAccountForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 474));
            registerStage.show();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
