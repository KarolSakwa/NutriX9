package Classes;

import Controllers.DietViewController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MealTablesContainer {
    public VBox vBox;
    DietViewController dietViewController;
    public Button addMealButton;
    public ObservableList<MealTable> mealTablesList = FXCollections.observableArrayList();
    final Integer MAX_MEALS_NUM = 4;

    public MealTablesContainer(DietViewController dietViewController) {
        this.dietViewController = dietViewController;
    }

    public void create() {
        // need mealslist listener as well as productlist listener because I want macronutrient counter to be updated every time not only product is added or removed, but also meal
        dietViewController.user.mealsList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                for (Integer i = 0; i < mealTablesList.size(); i++) {
                    mealTablesList.get(i).setMealNum(i);
                    mealTablesList.get(i).mealName.setText("Meal " + (mealTablesList.get(i).getMealNum() + 1));
                }
                if (dietViewController.user.mealsList.size() == MAX_MEALS_NUM) // addmealbutton displaying turned off if there's enough meals already
                    addMealButton.setVisible(false);
                else
                    addMealButton.setVisible(true);

                // change of right panel content based on whether there are meals in user's meals list or not

                if (dietViewController.user.mealsList.size() < 1) {
                    dietViewController.dailySummary.hideDailySummaryContent();
                    dietViewController.dailySummary.showRightPanelNoMealsContent();
                }
                else {
                    dietViewController.dailySummary.showDailySummaryContent();
                    dietViewController.dailySummary.hideRightPanelNoMealsContent();
                }

                dietViewController.dailySummary.calculateTotalMacro();
            }
        });

        vBox = new VBox();
        addMealButton = new Button("ADD ANOTHER MEAL");
        addMealButton.setVisible(false); // will show up only when first meal is added
        addMealButton.setOnAction(e -> addMealButtonOnAction());
        vBox.getChildren().add(addMealButton);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(10);
    }

    public void addMealButtonOnAction() {
        if (dietViewController.user.mealsList.size() < 4) {
            Meal meal = new Meal();
            MealTable mealTable = new MealTable(meal, dietViewController,1, 1, this, dietViewController.addProductController);
            mealTable.create();
            mealTablesList.add(mealTable);
            dietViewController.user.mealsList.add(meal);;
        }
    }

}
