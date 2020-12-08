package Classes;

import Controllers.DietViewController;
import javafx.collections.ListChangeListener;
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
    public VBox dailySummaryContainer;
    TextFlow totalDaily = new TextFlow(), dailyRequirement = new TextFlow();
    Text totalHeader, dailyRequirementHeader, kcalText, proteinsText, carbsText, fatsText, kcalReqText, proteinsReqText, carbsReqText, fatsReqText, kcalValue = new Text(),
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
        // I want to apply different style for values and labels, so I need to divide it into individual parts
        totalHeader = new Text("You have no meals in today's nutritional diary.");

        addFirstMeal = new Button("Add meal");
        addFirstMeal.setOnAction(e -> addFirstMealButtonOnAction());

        dailySummaryContainer.getChildren().addAll(totalHeader, addFirstMeal);
    }

    public void calculateTotalMacro(Product addedProduct, Boolean wasAdded) {
        // this method is called whenever meal or products list is changed. I need to reset every macronutrient counter everytime and calculate it again
        User user = dietViewController.user;

        if (wasAdded) {
            user.kcalConsumed += addedProduct.getKcal();
            user.proteinsConsumed += addedProduct.getProteins();
            user.carbsConsumed += addedProduct.getCarbs();
            user.fatsConsumed += addedProduct.getFats();
        }
        else {
            user.kcalConsumed -= addedProduct.getKcal();
            user.proteinsConsumed -= addedProduct.getProteins();
            user.carbsConsumed -= addedProduct.getCarbs();
            user.fatsConsumed -= addedProduct.getFats();
        }

        kcalValue.setText(Helper.twoDecimalsFloat(user.kcalConsumed).toString());
        proteinsValue.setText(Helper.twoDecimalsFloat(user.proteinsConsumed).toString());
        carbsValue.setText(Helper.twoDecimalsFloat(user.carbsConsumed).toString());
        fatsValue.setText(Helper.twoDecimalsFloat(user.fatsConsumed).toString());

        setStyleClassByRequirements(user.kcalConsumed, diet.kcal, kcalValue, addedProduct);
        setStyleClassByRequirements(user.proteinsConsumed, diet.proteins, proteinsValue, addedProduct);
        setStyleClassByRequirements(user.carbsConsumed, diet.carbs, carbsValue, addedProduct);
        setStyleClassByRequirements(user.fatsConsumed, diet.fats, fatsValue, addedProduct);
    }

    private void addFirstMealButtonOnAction() {
        dailySummaryContainer.getChildren().remove(addFirstMeal);
        totalHeader.setText("You have eaten today: ");

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

        dietViewController.addMealButtonOnAction();
        dietViewController.separator.setVisible(true);

        dailySummaryContainer.getChildren().addAll(totalDaily, dailyRequirementHeader, dailyRequirement);
    }
    private void setStyleClassByRequirements(Float provided, Float required, Text text, Product addedProduct) {
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

}
