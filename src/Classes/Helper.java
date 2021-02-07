package Classes;

import Controllers.AddNewDietController;
import Controllers.DietViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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

        // INSERTING DATA TO DB
        updateDBField(ageTextField, true, databaseConnection, con, username, ageErrorLabel, "age", null);
        updateDBField(heightTextField, true, databaseConnection, con, username, heightErrorLabel, "height", null);
        updateDBField(weightTextField, true, databaseConnection, con, username, weightErrorLabel, "weight", null);
        Integer trainingLength = Integer.parseInt(trainingLengthComboBox.getValue().toString());
        databaseConnection.executeQuery(con, "UPDATE users SET training_length = '" + trainingLength + "' WHERE username = '" + username + "';");
        updateDBField(dietNameTextField, false, databaseConnection, con, username, nameErrorLabel, "", dietTypeComboBox);

        String bodyType = bodyTypeComboBox.getValue().toString();
        String trainingIntensity = trainingIntensityComboBox.getValue().toString();
        Integer numTrainings = Integer.parseInt(numTrainingsComboBox.getValue().toString());

        databaseConnection.executeQuery(con, "UPDATE users SET body_type = '" + bodyType + "', training_intensity = '"
                + trainingIntensity + "', number_of_trainings = '" + numTrainings + "' WHERE username = '" + username + "';");

        User user = new User(con, username); // created just for calculation purposes
        calculateTotalMacronutrientRequirement(user, addNewDietController);
        databaseConnection.executeQuery(con, "UPDATE diets SET kcal = '" + addNewDietController.kcalReq + "', proteins = '" + addNewDietController.proteinsReq +
                "', carbs = '" + addNewDietController.carbsReq + "', fats = '" + addNewDietController.fatsReq + "' WHERE username = '" + username + "';");
    }

    private static void updateDBField(TextField field, Boolean isInt, DatabaseConnection databaseConnection, Connection con, String username, Label errorLabel, String fieldType, ComboBox comboBox) {
        if (isInt == true) {
            Integer fieldValue = TextFieldSanitizer.sanitizeIntegerTextField(field);
            if (fieldValue != null) {
                databaseConnection.executeQuery(con, "UPDATE users SET " + fieldType + " = '" + fieldValue + "' WHERE username = '" + username + "';");
                errorLabel.setVisible(false);
            } else
                errorLabel.setVisible(true);
        }
        else {
            String dietName = TextFieldSanitizer.sanitizeStringTextField(field);
            String dietType = comboBox.getValue().toString();
            if (dietName != null) {
                databaseConnection.executeQuery(con, "INSERT INTO diets (diet_name, diet_type, username) VALUES ('" + dietName + "', '" + dietType + "', '"
                        + username + "');");
                errorLabel.setVisible(false);
            } else
                errorLabel.setVisible(true);
        }
    };


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

    public static void styleTablesContainer(DietViewController dietViewController) {
        dietViewController.tablesContainer = new HBox();
        dietViewController.tablesContainer.setMinHeight(950);
        dietViewController.tablesContainer.setMinWidth(700);
        dietViewController.tablesContainer.setLayoutX(600);
        dietViewController.tablesContainer.setLayoutY(110);
        dietViewController.separator = new Separator();
        dietViewController.separator.setOrientation(Orientation.VERTICAL);
        dietViewController.separator.setMinWidth(50);
        dietViewController.separator.setVisible(false);
    }

    public static void styleDietViewTopBar(DietViewController dietViewController, Diet diet) {
        dietViewController.topBar = new HBox(20);
        dietViewController.topBar.setMinHeight(60);
        dietViewController.topBar.setMinWidth(dietViewController.WINDOWWIDTH);
        dietViewController.topBar.setStyle("-fx-background-color: " + dietViewController.MAINCOLOR);
        dietViewController.topBar.setLayoutX(0);
        dietViewController.topBar.setLayoutY(0);
        dietViewController.topBar.setAlignment(Pos.CENTER_LEFT);
        dietViewController.topBar.setBorder(new Border(new BorderStroke(Color.rgb(0, 0, 0, 0.1), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        dietViewController.dietNameLabel = new Label(diet.name);
        dietViewController.dietNameLabel.getStyleClass().add("dietNameLabel");
        dietViewController.dietImage = new ImageView("/img/diet.png");
        dietViewController.dietImage.setFitHeight(60);
        dietViewController.dietImage.setFitWidth(60);

        dietViewController.topBar.getChildren().addAll(dietViewController.dietNameLabel, dietViewController.dietImage);
        dietViewController.dietNameLabel.toFront();
        dietViewController.dietImage.toBack();
    }

    public static void addToTable(DatabaseConnection databaseConnection, TableColumn productNameColumn, TableColumn quantityTableColumn, TableColumn kcalColumn, TableColumn proteinsColumn,
                            TableColumn carbsColumn, TableColumn fatsColumn, TableColumn macronutrientColumn, TableColumn categoryColumn,
                            TableColumn wholesomenessIndexColumn, TableColumn priceColumn, TableView productsTableView, TextField searchProductTextField, ChoiceBox selectColumnChoiceBox) {
        Connection con = databaseConnection.getConnection();
        ObservableList products = FXCollections.observableArrayList();
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("unitQuantity"));
        kcalColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("kcal"));
        proteinsColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("proteins"));
        carbsColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("carbs"));
        fatsColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("fats"));
        macronutrientColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("macronutrientCategory"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
        wholesomenessIndexColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("wholesomenessIndex"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        FilteredList<Product> filteredProducts = new FilteredList(products, p -> true);
        productsTableView.setItems(filteredProducts);
        try {
            Statement statement = con.createStatement();
            String query = "SELECT * FROM products;";
            ResultSet product = statement.executeQuery(query);
            while (product.next()) {
                products.addAll(new Product(product.getString("name"),
                        product.getFloat("kcal"),
                        product.getFloat("proteins"),
                        product.getFloat("carbs"),
                        product.getFloat("fats"),
                        product.getString("macronutrient_category"),
                        product.getString("category"),
                        product.getInt("wholesomeness_index"),
                        product.getString("unit_type"),
                        product.getString("unit_quantity"),
                        product.getFloat("price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        searchProductTextField.setOnKeyReleased(keyEvent ->
        {
            Object value = selectColumnChoiceBox.getValue();//Switch on choiceBox value
            if (productNameColumn.getText().equals(value)) {
                filteredProducts.setPredicate(p -> p.getName().toLowerCase().contains(searchProductTextField.getText().toLowerCase().trim()));//filter table by first name
            } else if (macronutrientColumn.getText().equals(value)) {
                filteredProducts.setPredicate(p -> p.getMacronutrientCategory().toLowerCase().contains(searchProductTextField.getText().toLowerCase().trim()));//filter table by first name
            } else if (categoryColumn.getText().equals(value)) {
                filteredProducts.setPredicate(p -> p.getCategory().toLowerCase().contains(searchProductTextField.getText().toLowerCase().trim()));//filter table by first name
            } else if (wholesomenessIndexColumn.getText().equals(value)) {
                filteredProducts.setPredicate(p -> p.getWholesomenessIndex().toString().toLowerCase().contains(searchProductTextField.getText().toLowerCase().trim()));//filter table by first name
            }
        });
    }

    public static void changeUnitInQuantityPromptText(TableView productsTableView, TextField quantityTextField) {
        productsTableView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            Product product = (Product) productsTableView.getSelectionModel().getSelectedItem();
            quantityTextField.setPromptText("Quantity (" + product.getUnitType() + ")");
        });
    }

    public static Boolean isNumeric(String string) {
        if (string == null) {
            return false;
        }
        try {
            Double d = Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
