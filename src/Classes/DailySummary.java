package Classes;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DailySummary {
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    Integer kcalReq, proteinsReq, carbsReq, fatsReq;
    public VBox dailySummaryContainer;
    MealTablesContainer mealTablesContainer;
    TextFlow totalDaily = new TextFlow(), dailyRequirement = new TextFlow();
    Text totalHeader, dailyRequirementHeader, kcalText, proteinsText, carbsText, fatsText, kcalReqText, proteinsReqText, carbsReqText, fatsReqText, kcalValue = new Text(),
            proteinsValue = new Text(), carbsValue = new Text(), fatsValue = new Text();
    Button addFirstMeal;


    public DailySummary(MealTablesContainer mealTablesContainer) {
        this.mealTablesContainer = mealTablesContainer;
    }

    public void create() {
        setDailyRequirement();
        dailySummaryContainer = new VBox(20);
        dailySummaryContainer.setAlignment(Pos.CENTER);
        dailySummaryContainer.setPrefWidth(500);


        // I want to apply different style for values and labels, so I need to divide it into individual parts
        totalHeader = new Text("You have no meals in today's nutritional diary.");

        addFirstMeal = new Button("Add meal");
        addFirstMeal.setOnAction(e -> addFirstMealButtonOnAction());

        dailySummaryContainer.getChildren().addAll(totalHeader, addFirstMeal);
    }

    public void calculateTotalMacro() {
        // this method is called whenever meal or products list is changed. I need to reset every macronutrient counter everytime and calculate it again
        Double totalKcal = 0.0;
        Double totalProteins = 0.0;
        Double totalCarbs = 0.0;
        Double totalFats = 0.0;

        for (ObservableList<Product> productsList : mealTablesContainer.mealsList) {
            for (Product product : productsList) {
                totalKcal += product.getKcal();
                System.out.println(totalKcal);
                totalProteins += product.getProteins();
                totalCarbs += product.getCarbs();
                totalFats += product.getFats();
            }
        }

        kcalValue.setText(totalKcal.toString());
        proteinsValue.setText(totalProteins.toString());
        carbsValue.setText(totalCarbs.toString());
        fatsValue.setText(totalFats.toString());
        if (totalKcal < kcalReq)
            kcalValue.getStyleClass().add("daily-value-lower");
        else
            kcalValue.getStyleClass().add("daily-value-higher");
        proteinsValue.getStyleClass().add("daily-value");
        carbsValue.getStyleClass().add("daily-value");
        fatsValue.getStyleClass().add("daily-value");
    }

    private void addFirstMealButtonOnAction() {
        dailySummaryContainer.getChildren().remove(addFirstMeal);
        totalHeader.setText("You have eaten today: ");

        calculateTotalMacro();
        kcalText = new Text(" kcal ");
        proteinsText = new Text(" proteins ");
        carbsText = new Text(" carbohydrates ");
        fatsText = new Text(" fats ");
        kcalText.getStyleClass().add("daily-text");
        proteinsText.getStyleClass().add("daily-text");
        carbsText.getStyleClass().add("daily-text");
        fatsText.getStyleClass().add("daily-text");
        totalDaily.getChildren().addAll(kcalValue, kcalText, proteinsValue, proteinsText, carbsValue, carbsText, fatsValue, fatsText);
        totalDaily.setTextAlignment(TextAlignment.CENTER);

        Text kcalText2 = new Text(" kcal ");
        Text proteinsText2 = new Text(" proteins ");
        Text carbsText2 = new Text(" carbohydrates ");
        Text fatsText2 = new Text(" fats ");
        dailyRequirementHeader = new Text("Your daily requirement: ");
        dailyRequirement.setTextAlignment(TextAlignment.CENTER);
        dailyRequirement.getStyleClass().add("daily-text");
        dailyRequirement.getChildren().addAll(kcalReqText, kcalText2, proteinsReqText, proteinsText2, carbsReqText, carbsText2, fatsReqText, fatsText2);

        mealTablesContainer.dietViewController.addMealButtonOnAction();
        mealTablesContainer.dietViewController.separator.setVisible(true);

        dailySummaryContainer.getChildren().addAll(totalDaily, dailyRequirementHeader, dailyRequirement);
    }

    private void setDailyRequirement() {
        try {
            Statement statement = con.createStatement();
            String query = "SELECT * FROM diets WHERE username = '" + mealTablesContainer.dietViewController.selectProfileController.getUsername() + "';";
            ResultSet diet = statement.executeQuery(query);
            while (diet.next()) {
                kcalReq = diet.getInt("kcal");
                proteinsReq = diet.getInt("proteins");
                carbsReq = diet.getInt("carbohydrates");
                fatsReq = diet.getInt("fats");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        kcalReqText = new Text(kcalReq.toString());
        proteinsReqText = new Text(proteinsReq.toString());
        carbsReqText = new Text(carbsReq.toString());
        fatsReqText = new Text(fatsReq.toString());
    }

}