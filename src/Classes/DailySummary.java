package Classes;

import Controllers.DietViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.sql.Connection;

public class DailySummary {
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    public VBox dailySummaryContainer;
    TextFlow totalDaily = new TextFlow(), dailyRequirement = new TextFlow();
    Text totalHeader, emptyHeader, dailyRequirementHeader, kcalText, proteinsText, carbsText, fatsText, kcalReqText, proteinsReqText, carbsReqText, fatsReqText, kcalValue = new Text(),
            proteinsValue = new Text(), carbsValue = new Text(), fatsValue = new Text();
    Button addFirstMeal;
    Diet diet;
    public DietViewController dietViewController;


    public DailySummary(DietViewController dietViewController) {
        this.dietViewController = dietViewController;
    }

    public void create() {
        diet = dietViewController.diet;
        dailySummaryContainer = new VBox(20);
        dailySummaryContainer.setAlignment(Pos.CENTER);
        dailySummaryContainer.setPrefWidth(500);
        rightPanelNoMeals();
    }

    public void calculateTotalMacro() {
        // this method is called whenever meal or products list is changed. I need to reset every macronutrient counter everytime and calculate it again\
        User user = dietViewController.user;
        user.kcalConsumed = 0F;
        user.proteinsConsumed = 0F;
        user.carbsConsumed = 0F;
        user.fatsConsumed = 0F;

        for (Meal meal : dietViewController.user.mealsList) {
            for (Product product : meal.productsList) {
                user.kcalConsumed += product.getKcal();
                user.proteinsConsumed += product.getProteins();
                user.carbsConsumed += product.getCarbs();
                user.fatsConsumed += product.getFats();
            }
        }

        kcalValue.setText(Helper.twoDecimalsFloat(user.kcalConsumed).toString());
        proteinsValue.setText(Helper.twoDecimalsFloat(user.proteinsConsumed).toString());
        carbsValue.setText(Helper.twoDecimalsFloat(user.carbsConsumed).toString());
        fatsValue.setText(Helper.twoDecimalsFloat(user.fatsConsumed).toString());

        setStyleClassByRequirements(user.kcalConsumed, diet.kcal, kcalValue);
        setStyleClassByRequirements(user.proteinsConsumed, diet.proteins, proteinsValue);
        setStyleClassByRequirements(user.carbsConsumed, diet.carbs, carbsValue);
        setStyleClassByRequirements(user.fatsConsumed, diet.fats, fatsValue);
    }

    private void addFirstMealButtonOnAction() {
        addFirstMeal.setVisible(false);

        setRightPanelContent();

        dietViewController.mealTablesContainer.addMealButtonOnAction();
        dietViewController.mealTablesContainer.addMealButton.setVisible(true);

    }
    private void setStyleClassByRequirements(Float provided, Float required, Text text) {
        // There is some issue with "getStyleClass().add" so I need to do it another way
        if (provided < required) {
            text.setStyle("-fx-fill: green;" +
                    "-fx-font-weight: 900; " +
                    "-fx-font-size: 16;");
        }
        else {
            text.setStyle("-fx-fill: red;" +
                    "-fx-font-weight: 900; " +
                    "-fx-font-size: 16;");
        }
    }

    public void setRightPanelContent() {
        totalHeader = new Text("You have eaten today: ");
        kcalText = new Text(" kcal ");
        proteinsText = new Text(" proteins ");
        carbsText = new Text(" carbs ");
        fatsText = new Text(" fats ");
        kcalText.getStyleClass().add("daily-text");

        proteinsText.getStyleClass().add("daily-text");
        carbsText.getStyleClass().add("daily-text");
        fatsText.getStyleClass().add("daily-text");
        totalDaily.getChildren().addAll(kcalValue, kcalText, proteinsValue, proteinsText, carbsValue, carbsText, fatsValue, fatsText);
        totalDaily.setTextAlignment(TextAlignment.CENTER);

        kcalReqText = new Text(diet.kcal.toString());
        proteinsReqText = new Text(diet.proteins.toString());
        carbsReqText = new Text(diet.carbs.toString());
        fatsReqText = new Text(diet.fats.toString());

        Text kcalText2 = new Text(" kcal ");
        Text proteinsText2 = new Text(" proteins ");
        Text carbsText2 = new Text(" carbs ");
        Text fatsText2 = new Text(" fats ");
        dailyRequirementHeader = new Text("Your daily requirement: ");
        dailyRequirement.setTextAlignment(TextAlignment.CENTER);
        dailyRequirement.getStyleClass().add("daily-text");
        dailyRequirement.getChildren().addAll(kcalReqText, kcalText2, proteinsReqText, proteinsText2, carbsReqText, carbsText2, fatsReqText, fatsText2);

        dietViewController.separator.setVisible(true);
        dailySummaryContainer.getChildren().addAll(totalHeader, totalDaily, dailyRequirementHeader, dailyRequirement);
        dailySummaryContainer.getChildren().remove(emptyHeader);
    }

    public void rightPanelNoMeals() {
        emptyHeader = new Text("You have no meals in today's nutritional diary.");

        addFirstMeal = new Button("Add meal");
        addFirstMeal.setOnAction(e -> addFirstMealButtonOnAction());

        dailySummaryContainer.getChildren().addAll(emptyHeader, addFirstMeal);
    }

    public void hideDailySummary() {
        dailySummaryContainer.getChildren().removeAll(totalHeader, totalDaily, dailyRequirementHeader, dailyRequirement, emptyHeader, addFirstMeal);
    }

}
