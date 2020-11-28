package Controllers;

import Classes.ChildrenWindow;
import Classes.DatabaseConnection;
import Classes.TextFieldSanitizer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.sql.Connection;
import java.sql.Statement;
import java.util.function.UnaryOperator;

public class AddNewDietController {
    @FXML
    Button closeButton, submitButton;
    @FXML
    TextField ageTextField, heightTextField, weightTextField, dietNameTextField;
    @FXML
    ComboBox bodyTypeComboBox, trainingIntensityComboBox, numTrainingsComboBox, dietTypeComboBox;
    @FXML Label ageErrorLabel, heightErrorLabel, weightErrorLabel, nameErrorLabel;
    private Stage thisStage;
    private final SelectProfileController selectProfileController;
    ObservableList<String> bodyTypeOptions = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
    ObservableList<String> trainingIntensityOptions = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
    ObservableList<String> numTrainingsOptions = FXCollections.observableArrayList("1", "2", "3");
    ObservableList<String> dietTypeOptions = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();

    public AddNewDietController(SelectProfileController selectProfileController) {
        this.selectProfileController = selectProfileController;
        thisStage = new Stage();
        ChildrenWindow addNewDietWindow = new ChildrenWindow();
        addNewDietWindow.create("../fxml/addNewDiet.fxml", this, thisStage, false, 410, 573);
    }

    public void initialize() {
        bodyTypeComboBox.getItems().addAll(bodyTypeOptions);
        trainingIntensityComboBox.getItems().addAll(trainingIntensityOptions);
        numTrainingsComboBox.getItems().addAll(numTrainingsOptions);
        dietTypeComboBox.getItems().addAll(dietTypeOptions);
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    public void closeButtonOnAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void submitButtonOnAction() {
        addDataToDB();
        if(!ageErrorLabel.isVisible() && !heightErrorLabel.isVisible() && !weightErrorLabel.isVisible() && !nameErrorLabel.isVisible()) {
            thisStage.close();
            DietViewController dietViewController = new DietViewController(selectProfileController);
            dietViewController.showStage();
        }
    }

    private void addDataToDB() {
        String username = selectProfileController.getUsername();
        Integer age = TextFieldSanitizer.sanitizeIntegerTextField(ageTextField);
        if (age != null) {
            databaseConnection.executeQuery(con, "UPDATE users SET age = '" + age + "' WHERE username = '" + username + "';");
            ageErrorLabel.setVisible(false);
        }
        else
            ageErrorLabel.setVisible(true);
        Integer height = TextFieldSanitizer.sanitizeIntegerTextField(heightTextField);
        if (height != null) {
            databaseConnection.executeQuery(con, "UPDATE users SET height = '" + height + "' WHERE username = '" + username + "';");
            heightErrorLabel.setVisible(false);
        }
        else
            heightErrorLabel.setVisible(true);
        Integer weight = TextFieldSanitizer.sanitizeIntegerTextField(weightTextField);
        if (weight != null) {
            databaseConnection.executeQuery(con, "UPDATE users SET weight = '" + weight + "' WHERE username = '" + username + "';");
            weightErrorLabel.setVisible(false);
        }
        else
            weightErrorLabel.setVisible(true);
        String dietName = TextFieldSanitizer.sanitizeStringTextField(dietNameTextField);
        String dietType = dietTypeComboBox.getValue().toString();

        if (dietName != null) {
            databaseConnection.executeQuery(con, "INSERT INTO diets (diet_name, diet_type, username) VALUES ('" + dietName + "', '" + dietType + "', '" + username + "');");
            nameErrorLabel.setVisible(false);
        }
        else
            nameErrorLabel.setVisible(true);
        String bodyType = bodyTypeComboBox.getValue().toString();
        String trainingIntensity = trainingIntensityComboBox.getValue().toString();
        Integer numTrainings = Integer.parseInt(numTrainingsComboBox.getValue().toString());

        databaseConnection.executeQuery(con, "UPDATE users SET body_type = '" + bodyType + "', training_intensity = '" + trainingIntensity + "', number_of_trainings = '" + numTrainings + "' WHERE username = '" + username + "';");
    }
}