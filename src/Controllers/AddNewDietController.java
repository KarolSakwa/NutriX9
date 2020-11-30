package Controllers;

import Classes.ChildrenWindow;
import Classes.DatabaseConnection;
import Classes.Diet;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.function.UnaryOperator;

import static jdk.nashorn.internal.objects.NativeMath.round;

public class AddNewDietController {
    @FXML
    Button closeButton, submitButton;
    @FXML
    TextField ageTextField, heightTextField, weightTextField, dietNameTextField;
    @FXML
    ComboBox bodyTypeComboBox, trainingIntensityComboBox, numTrainingsComboBox, dietTypeComboBox, trainingLengthComboBox;
    @FXML Label ageErrorLabel, heightErrorLabel, weightErrorLabel, nameErrorLabel;
    private Stage thisStage;
    private final SelectProfileController selectProfileController;
    ObservableList<String> bodyTypeOptions = FXCollections.observableArrayList("Ectomorph", "Mesomorph", "Endomorph");
    ObservableList<String> trainingIntensityOptions = FXCollections.observableArrayList("Low", "Medium", "High");
    ObservableList<Integer> trainingLengthOptions = FXCollections.observableArrayList(30, 40, 50, 60, 70);
    ObservableList<String> numTrainingsOptions = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7");
    ObservableList<String> dietTypeOptions = FXCollections.observableArrayList("Reduction", "Mass");
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    public Diet diet;


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
        addDataToDB();
        if(!ageErrorLabel.isVisible() && !heightErrorLabel.isVisible() && !weightErrorLabel.isVisible() && !nameErrorLabel.isVisible()) {
            Stage stage = (Stage) submitButton.getScene().getWindow();
            DietViewController dietViewController = new DietViewController(selectProfileController);
            dietViewController.showStage();
            stage.close();
        }
    }

    private void addDataToDB() throws SQLException {
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
        Integer trainingLength = Integer.parseInt(trainingLengthComboBox.getValue().toString());
        databaseConnection.executeQuery(con, "UPDATE users SET training_length = '" + trainingLength + "' WHERE username = '" + username + "';");

        String dietName = TextFieldSanitizer.sanitizeStringTextField(dietNameTextField);
        String dietType = dietTypeComboBox.getValue().toString();

        if (dietName != null) {
            databaseConnection.executeQuery(con, "INSERT INTO diets (diet_name, diet_type, username) VALUES ('" + dietName + "', '" + dietType + "', '"
                    + username + "');");
            nameErrorLabel.setVisible(false);
        }
        else
            nameErrorLabel.setVisible(true);
        String bodyType = bodyTypeComboBox.getValue().toString();
        String trainingIntensity = trainingIntensityComboBox.getValue().toString();
        Integer numTrainings = Integer.parseInt(numTrainingsComboBox.getValue().toString());


        databaseConnection.executeQuery(con, "UPDATE users SET body_type = '" + bodyType + "', training_intensity = '"
                + trainingIntensity + "', number_of_trainings = '" + numTrainings + "' WHERE username = '" + username + "';");
        System.out.println(calculateTotalKcalRequirement(age, height, weight, bodyType, trainingIntensity, trainingLength, numTrainings));
    }

    private Float calculateTotalKcalRequirement(Integer age, Integer height, Integer weight, String bodyType, String trainingIntensity,
                                                 Integer trainingLength, Integer numTrainings) {
        Float basicMetabolism = (9.99F * weight) + (6.25F * height) - (4.92F * age) - 161;
        Float bodyTypeCalories;
        if (bodyType == "Ectomorph")
            bodyTypeCalories = 700.0F;
        else if (bodyType == "Mesomorph")
            bodyTypeCalories = 400.0F;
        else
            bodyTypeCalories = 200.0F;
        Integer trainingIntensityCaloriesPerMin;
        if (trainingIntensity == "Low")
            trainingIntensityCaloriesPerMin = 7;
        else if (trainingIntensity == "Medium")
            trainingIntensityCaloriesPerMin = 10;
        else
            trainingIntensityCaloriesPerMin = 12;
        Integer totalTrainingCaloriesPerDay = ((trainingIntensityCaloriesPerMin * trainingLength) * numTrainings) / 7;
        Float foodThermicEffect = (basicMetabolism + bodyTypeCalories + totalTrainingCaloriesPerDay) / 10;

        return basicMetabolism + bodyTypeCalories + totalTrainingCaloriesPerDay + foodThermicEffect;
    }

}