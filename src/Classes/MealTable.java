package Classes;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MealTable {
    VBox tableContainer;
    HBox mealNameContainer;
    Label mealName;
    TableView tableContent, tableSummary;
    TableColumn quantityColumn, nameColumn, kcalColumn, proteinsColumn, carbsColumn, fatsColumn, WIColumn, priceColumn;
    Integer mealNum;
    private final Integer tableContainerWidth = 560;
    private final Integer tableContainerHeight = 370;
    Double tableContainerLayoutX, tableContainerLayoutY;

    public MealTable(Integer mealNum, Double tableContainerLayoutX, Double tableContainerLayoutY) {
        this.mealNum = mealNum;
        this.tableContainerLayoutX = tableContainerLayoutX;
        this.tableContainerLayoutY = tableContainerLayoutY;
    }

    public void create() {
        tableContainer = new VBox();
        tableContainer.setPrefWidth(tableContainerWidth);
        tableContainer.setPrefHeight(tableContainerHeight);
        tableContainer.setLayoutX(tableContainerLayoutX);
        tableContainer.setLayoutY(tableContainerLayoutY);

        mealNameContainer = new HBox();
        mealName = new Label("Meal" + mealNum);

        tableContent = new TableView();
    }

}
