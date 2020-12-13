package Classes;

import Controllers.DietViewController;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    Text totalHeader = new Text(), emptyHeader = new Text(), dailyRequirementHeader = new Text(), kcalText = new Text(), proteinsText = new Text(), carbsText = new Text(),
            fatsText = new Text(), kcalReqText = new Text(), proteinsReqText = new Text(), carbsReqText = new Text(), fatsReqText = new Text(), kcalValue = new Text(),
            proteinsValue = new Text(), carbsValue = new Text(), fatsValue = new Text(), kcalText2 = new Text(), proteinsText2 = new Text(), carbsText2 = new Text(), fatsText2 = new Text();
    Button addFirstMeal = new Button();
    Diet diet;
    public DietViewController dietViewController;
    User user;


    public DailySummary(DietViewController dietViewController) {
        this.dietViewController = dietViewController;
    }

    public void create() {
        diet = dietViewController.diet;
        dailySummaryContainer = new VBox(20);
        dailySummaryContainer.setAlignment(Pos.CENTER);
        dailySummaryContainer.setPrefWidth(500);
        user = dietViewController.user;
        rightPanelNoMeals();
    }

    public void calculateTotalMacro() {
        // this method is called whenever meal or products list is changed. I need to reset every macronutrient counter everytime and calculate it again\
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
        emptyHeader.setVisible(false);
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
        totalHeader.setText("You have eaten today: ");
        kcalText.setText(" kcal ");
        proteinsText.setText(" proteins ");
        carbsText.setText(" carbs ");
        fatsText.setText(" fats ");

        kcalText.getStyleClass().add("daily-text");
        proteinsText.getStyleClass().add("daily-text");
        carbsText.getStyleClass().add("daily-text");
        fatsText.getStyleClass().add("daily-text");
        if(!totalDaily.getChildren().contains(kcalValue))
            totalDaily.getChildren().addAll(kcalValue, kcalText, proteinsValue, proteinsText, carbsValue, carbsText, fatsValue, fatsText);
        totalDaily.setTextAlignment(TextAlignment.CENTER);

        kcalReqText.setText(diet.kcal.toString());
        proteinsReqText.setText(diet.proteins.toString());
        carbsReqText.setText(diet.carbs.toString());
        fatsReqText.setText(diet.fats.toString());

        kcalText2.setText(" kcal ");
        proteinsText2.setText(" proteins ");
        carbsText2.setText(" carbs ");
        fatsText2.setText(" fats ");
        dailyRequirementHeader.setText("Your daily requirement: ");
        dailyRequirement.setTextAlignment(TextAlignment.CENTER);
        dailyRequirement.getStyleClass().add("daily-text");
        if (!dailyRequirement.getChildren().contains(kcalReqText))
            dailyRequirement.getChildren().addAll(kcalReqText, kcalText2, proteinsReqText, proteinsText2, carbsReqText, carbsText2, fatsReqText, fatsText2);
        for (Node children: totalDaily.getChildren())
            children.setVisible(true);
        for (Node children: dailyRequirement.getChildren())
            children.setVisible(true);

        dietViewController.separator.setVisible(true);
        if (!dailyRequirement.getChildren().contains(totalHeader))
            dailySummaryContainer.getChildren().addAll(totalHeader, totalDaily, dailyRequirementHeader, dailyRequirement);
        for (Node children: dailySummaryContainer.getChildren())
            children.setVisible(true);
    }

    public void rightPanelNoMeals() {
        emptyHeader.setText("You have no meals in today's nutritional diary.");
        addFirstMeal.setText("Add meal");
        addFirstMeal.setOnAction(e -> addFirstMealButtonOnAction());
        if(!dailySummaryContainer.getChildren().contains(emptyHeader))
            dailySummaryContainer.getChildren().addAll(emptyHeader, addFirstMeal);
    }
    public void hideDailySummary() {
        addFirstMeal.setVisible(true);
        emptyHeader.setVisible(true);
        totalHeader.setVisible(false);
        totalDaily.setVisible(false);
        dailyRequirement.setVisible(false);
    }

}
