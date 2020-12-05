package Classes;

import Controllers.DietViewController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Meal {
    public ObservableList<Product> productsList = FXCollections.observableArrayList();
    DietViewController dietViewController;

    public void create() {
        productsList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                mealTableSummary.update(this);
                dietViewController.dailySummary.calculateTotalMacro();
            }
        });
    }
}
