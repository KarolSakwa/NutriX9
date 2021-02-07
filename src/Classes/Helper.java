package Classes;

import Controllers.AddNewDietController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Helper {

    public static Float twoDecimalsFloat(Float number) {
        BigDecimal bigDecimal = new BigDecimal(number.toString());
        BigDecimal roundOff = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return roundOff.floatValue();
    }

    public static Diet getDiet(Connection con, String username) {
        String query = "SELECT * FROM diets WHERE username = '" + username + "';";
        try {
            Statement statement = con.createStatement();
            ResultSet usersDiet = statement.executeQuery(query);
            usersDiet.next();
            Diet diet = new Diet(con, usersDiet.getString("diet_name"), username);
            return diet;
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }

    public static void addDataToDB(AddNewDietController addNewDietController, DatabaseConnection databaseConnection, TextField ageTextField, TextField heightTextField,
                             TextField weightTextField, TextField dietNameTextField, Label ageErrorLabel, Label heightErrorLabel, Label weightErrorLabel,
                             Label nameErrorLabel, ComboBox trainingLengthComboBox, ComboBox bodyTypeComboBox, ComboBox dietTypeComboBox, ComboBox trainingIntensityComboBox,
                             ComboBox numTrainingsComboBox) {
        Connection con = databaseConnection.getConnection();
        String username = addNewDietController.selectProfileController.getUsername();
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
        calculateTotalMacronutrientRequirement(user, addNewDietController);
        databaseConnection.executeQuery(con, "UPDATE diets SET kcal = '" + addNewDietController.kcalReq + "', proteins = '" + addNewDietController.proteinsReq +
                "', carbs = '" + addNewDietController.carbsReq + "', fats = '" + addNewDietController.fatsReq + "';");
    }



    public static void calculateTotalMacronutrientRequirement(User user, AddNewDietController addNewDietController) {
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
        addNewDietController.kcalReq = basicMetabolism + bodyTypeCalories + totalTrainingCaloriesPerDay + foodThermicEffect;
        addNewDietController.proteinsReq = user.weight * protPerKg;
        addNewDietController.fatsReq = user.weight * fatPerKg;
        addNewDietController.carbsReq = Float.valueOf(Math.round((addNewDietController.kcalReq - (addNewDietController.proteinsReq * proteinkcal) - (addNewDietController.fatsReq * fatkcal)) / carbkcal));
    }
}
