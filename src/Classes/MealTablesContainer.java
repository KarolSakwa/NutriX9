package Classes;

import Controllers.DietViewController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

public class MealTablesContainer {
    public VBox vBox;
    DietViewController dietViewController;
    Integer layoutX, layoutY, height, width;
    public ObservableList<MealTable> mealTablesList = FXCollections.observableArrayList();

    public MealTablesContainer(DietViewController dietViewController) {
        this.dietViewController = dietViewController;
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.height = height;
        this.width = width;
    }

    public void create() {
        // need mealslist listener as well as productlist listener because I want macronutrient counter to be updated every time not only product is added or removed, but also meal
        dietViewController.user.mealsList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                //dietViewController.dailySummary.calculateTotalMacro();
                System.out.println("Meal added");
            }
        });

        vBox = new VBox();

    }
}
