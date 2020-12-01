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
    private final SelectProfileController selectProfileController;
    ObservableList<String> bodyTypeOptions = FXCollections.observableArrayList("Ectomorph", "Mesomorph", "Endomorph");
    ObservableList<String> trainingIntensityOptions = FXCollections.observableArrayList("Low", "Medium", "High");
    ObservableList<Integer> trainingLengthOptions = FXCollections.observableArrayList(30, 40, 50, 60, 70);
    ObservableList<String> numTrainingsOptions = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7");
    ObservableList<String> dietTypeOptions = FXCollections.observableArrayList("Reduction", "Mass");
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    public Diet diet;
    Float kcalReq, proteinsReq, carbsReq, fatsReq;


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

        User user = new User(con, username); // created just for calculation purposes
        calculateTotalMacronutrientRequirement(user);
        databaseConnection.executeQuery(con, "UPDATE diets SET kcal = '" + kcalReq + "', proteins = '" + proteinsReq +
                "', carbs = '" + carbsReq + "', fats = '" + fatsReq + "';");
    }

    private void calculateTotalMacronutrientRequirement(User user) {
        final Integer proteinkcal = 4;
        final Integer fatkcal = 9;
        final Integer carbkcal = 4;
        final Float protPerKg = 1.8F;
        final Float fatPerKg = 2F;

        // kcal requirement calculation
        Float basicMetabolism = (9.99F * user.weight) + (6.25F * user.height) - (4.92F * user.age) - 161;
        Float bodyTypeCalories;
        if (user.bodyType == "Ectomorph")
            bodyTypeCalories = 700.0F;
        else if (user.bodyType == "Mesomorph")
            bodyTypeCalories = 400.0F;
        else
            bodyTypeCalories = 200.0F;
        Integer trainingIntensityCaloriesPerMin;
        if (user.trainingIntensity == "Low")
            trainingIntensityCaloriesPerMin = 7;
        else if (user.trainingIntensity == "Medium")
            trainingIntensityCaloriesPerMin = 10;
        else
            trainingIntensityCaloriesPerMin = 12;
        Integer totalTrainingCaloriesPerDay = ((trainingIntensityCaloriesPerMin * user.trainingLength) * user.numberOfTrainings) / 7;
        Float foodThermicEffect = (basicMetabolism + bodyTypeCalories + totalTrainingCaloriesPerDay) / 10;
        kcalReq = basicMetabolism + bodyTypeCalories + totalTrainingCaloriesPerDay + foodThermicEffect;
        proteinsReq = user.weight * protPerKg;
        fatsReq = user.weight * fatPerKg;
        carbsReq = Float.valueOf(Math.round((kcalReq - (proteinsReq * proteinkcal) - (fatsReq * fatkcal)) / carbkcal));
    }
}