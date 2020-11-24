package Classes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class DailySummary {
    public VBox dailySummaryContainer;
    MealTablesContainer mealTablesContainer;
    TextFlow totalDaily, dailyRequirement;
    Text totalHeader, dailyRequirementHeader, kcalText, proteinsText, carbsText, fatsText;

    public DailySummary(MealTablesContainer mealTablesContainer) {
        this.mealTablesContainer = mealTablesContainer;
    }

    public void create() {
        dailySummaryContainer = new VBox(20);
        dailySummaryContainer.setAlignment(Pos.CENTER);
        dailySummaryContainer.setPrefWidth(500);

        totalHeader = new Text("You have eaten today: ");
        kcalText = new Text(calculateKcal().toString());

        dailyRequirementHeader = new Text("Your daily requirement: ");
        


        dailySummaryContainer.getChildren().addAll(totalHeader, kcalText, dailyRequirement);
    }

    public Double calculateKcal() {
        Double totalKcal = 0.0;
        for (MealTable mealTable: mealTablesContainer.mealTablesList) {
            totalKcal += mealTable.mealTableSummary.totalKcal;
        }
        return totalKcal;
    }

}
