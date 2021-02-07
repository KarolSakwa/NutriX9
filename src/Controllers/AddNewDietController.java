package Controllers;

import Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class AddNewDietController {

    @FXML
    Button closeButton, submitButton;
    @FXML
    TextField ageTextField, heightTextField, weightTextField, dietNameTextField;
    @FXML
    ComboBox bodyTypeComboBox, trainingIntensityComboBox, numTrainingsComboBox, dietTypeComboBox, trainingLengthComboBox;
    @FXML Label ageErrorLabel, heightErrorLabel, weightErrorLabel, nameErrorLabel;
    private Stage thisStage;
    public final SelectProfileController selectProfileController;
    ObservableList<String> bodyTypeOptions = FXCollections.observableArrayList("Ectomorph", "Mesomorph", "Endomorph");
    ObservableList<String> trainingIntensityOptions = FXCollections.observableArrayList("Low", "Medium", "High");
    ObservableList<Integer> trainingLengthOptions = FXCollections.observableArrayList(30, 40, 50, 60, 70);
    ObservableList<String> numTrainingsOptions = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7");
    ObservableList<String> dietTypeOptions = FXCollections.observableArrayList("Reduction", "Mass");
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    public Diet diet;
    public Float kcalReq, proteinsReq, carbsReq, fatsReq;


    public AddNewDietController(SelectProfileController selectProfileController) {
        this.selectProfileController = selectProfileController;
        thisStage = new Stage();
        ChildrenWindow.create("../fxml/addNewDiet.fxml", this, thisStage, false, 410, 661);
    }

    public void initialize() {
        bodyTypeComboBox.getItems().addAll(bodyTypeOptions);
        trainingIntensityComboBox.getItems().addAll(trainingIntensityOptions);
        numTrainingsComboBox.getItems().addAll(numTrainingsOptions);
        dietTypeComboBox.getItems().addAll(dietTypeOptions);
        trainingLengthComboBox.getItems().addAll(trainingLengthOptions);
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    public void closeButtonOnAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void submitButtonOnAction() throws SQLException {
        Helper.addDataToDB(this, databaseConnection, ageTextField, heightTextField, weightTextField, dietNameTextField, ageErrorLabel, heightErrorLabel, weightErrorLabel, nameErrorLabel, trainingLengthComboBox, bodyTypeComboBox, dietTypeComboBox, trainingIntensityComboBox, numTrainingsComboBox);
        if(!ageErrorLabel.isVisible() && !heightErrorLabel.isVisible() && !weightErrorLabel.isVisible() && !nameErrorLabel.isVisible()) {
            Stage stage = (Stage) submitButton.getScene().getWindow();
            DietViewController dietViewController = new DietViewController(selectProfileController);
            dietViewController.showStage();
            stage.close();
        }
    }

}