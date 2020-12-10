package Classes;

import Controllers.DietViewController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

public class MealTablesContainer {
    public VBox vBox;
    DietViewController dietViewController;
    public ObservableList<MealTable> mealTablesList = FXCollections.observableArrayList();

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
                dietViewController.dailySummary.calculateTotalMacro();
            }
        });

        vBox = new VBox();

    }
}
