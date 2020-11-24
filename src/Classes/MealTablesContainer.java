package Classes;

import Controllers.DietViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

public class MealTablesContainer {
    public VBox vBox;
    DietViewController dietViewController;
    Integer layoutX, layoutY, height, width;
    public ObservableList<ObservableList> mealsList = FXCollections.observableArrayList();
    public ObservableList<MealTable> mealTablesList = FXCollections.observableArrayList();

    public MealTablesContainer(DietViewController dietViewController) {
        this.dietViewController = dietViewController;
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.height = height;
        this.width = width;
    }

    public void create() {
        vBox = new VBox();
        vBox.getChildren().addAll();

    }
}
