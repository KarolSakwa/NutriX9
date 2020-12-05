package Classes;

import Controllers.DietViewController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Meal {
    public ObservableList<Product> productsList = FXCollections.observableArrayList();
    DietViewController dietViewController;

    public void create() {
    }
}
