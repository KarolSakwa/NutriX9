package Classes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class DailySummary {
    public VBox dailySummaryContainer;
    MealTablesContainer mealTablesContainer;
    TextFlow totalDaily = new TextFlow(), dailyRequirement;
    Text totalHeader, dailyRequirementHeader, kcalText, proteinsText, carbsText, fatsText, kcalValue = new Text(),
            proteinsValue = new Text(), carbsValue = new Text(), fatsValue = new Text();
    Button addFirstMeal;


    public DailySummary(MealTablesContainer mealTablesContainer) {
        this.mealTablesContainer = mealTablesContainer;
    }

    public void create() {
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
        Double totalKcal = 0.0;
        Double totalProteins = 0.0;
        Double totalCarbs = 0.0;
        Double totalFats = 0.0;
        for (MealTable mealTable: mealTablesContainer.mealTablesList) {
            totalKcal += mealTable.mealTableSummary.totalKcal;
            totalProteins += mealTable.mealTableSummary.totalProteins;
            totalCarbs += mealTable.mealTableSummary.totalCarbs;
            totalFats += mealTable.mealTableSummary.totalFats;
        }
        kcalValue.setText(totalKcal.toString());
        proteinsValue.setText(totalProteins.toString());
        carbsValue.setText(totalCarbs.toString());
        fatsValue.setText(totalFats.toString());
        kcalValue.getStyleClass().add("daily-value");
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

        dailyRequirementHeader = new Text("Your daily requirement: ");

        dailySummaryContainer.getChildren().addAll(totalDaily, dailyRequirement);
    }

}
