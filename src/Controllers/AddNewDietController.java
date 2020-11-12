package Controllers;

import Classes.ChildrenWindow;
import Classes.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.Statement;

public class AddNewDietController {
    @FXML Button closeButton, submitButton;
    @FXML TextField ageTextField, heightTextField, weightTextField, dietNameTextField;
    @FXML ComboBox bodyTypeComboBox, trainingIntensityComboBox, numTrainingsComboBox, dietTypeComboBox;
    private Stage thisStage;
    private final SelectProfileController selectProfileController;
    ObservableList<String> bodyTypeOptions = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
    ObservableList<String> trainingIntensityOptions = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
    ObservableList<String> numTrainingsOptions = FXCollections.observableArrayList("1", "2", "3");
    ObservableList<String> dietTypeOptions = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();

    public AddNewDietController(SelectProfileController selectProfileController){
        this.selectProfileController = selectProfileController;
        thisStage = new Stage();
        ChildrenWindow addNewDietWindow = new ChildrenWindow();
        addNewDietWindow.create("../fxml/addNewDiet.fxml", this, thisStage, false, 410, 573);
    }

    public void initialize(){
        bodyTypeComboBox.getItems().addAll(bodyTypeOptions);
        trainingIntensityComboBox.getItems().addAll(trainingIntensityOptions);
        numTrainingsComboBox.getItems().addAll(numTrainingsOptions);
        dietTypeComboBox.getItems().addAll(dietTypeOptions);
    }

    public void showStage(){
        thisStage.showAndWait();
    }

    public void closeButtonOnAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void submitButtonOnAction() {
        addDataToDB();
    }

    private void addDataToDB() {
        Integer age = Integer.parseInt(ageTextField.getText());
        Integer height = Integer.parseInt(heightTextField.getText());
        Integer weight = Integer.parseInt(weightTextField.getText());
        String bodyType = bodyTypeComboBox.getValue().toString();
        String trainingIntensity = trainingIntensityComboBox.getValue().toString();
        Integer numTrainings = Integer.parseInt(numTrainingsComboBox.getValue().toString());
        String dietType = dietTypeComboBox.getValue().toString();
        String dietName = dietNameTextField.getText(); // set to max 10 characters
        String username = selectProfileController.getUsername();
        String queryPersonal = "UPDATE users SET age = '" + age + "', height = '" + height + "', weight = '" + weight + "', body_type = '" + bodyType + "', training_intensity = '" + trainingIntensity + "', number_of_trainings = '" + numTrainings + "' WHERE username = '" + username + "';";
        databaseConnection.executeQuery(con, queryPersonal);
        String queryDiet = "INSERT INTO diets (diet_name, diet_type, username) VALUES ('" + dietName + "', '" + dietType + "', '" + username + "');";
        databaseConnection.executeQuery(con, queryDiet);
    }

}
