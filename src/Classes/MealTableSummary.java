package Classes;

import Controllers.DietViewController;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.math.BigDecimal;

public class MealTableSummary {
    TableColumn kcalSummary, proteinsSummary, carbsSummary, fatsSummary, WISummary, priceSummary, emptyColumn;
    SummaryTableView tableSummary;

    HBox summaryContainer;
    Float totalKcal;
    Float totalProteins;
    Float totalCarbs;
    Float totalFats;
    Integer totalWI;
    Float totalPrice;
    DietViewController dietViewController;
    MealTableSummary thisMealTableSummary; // I can't use "this" in method onChanged because it would refer to another class, so I created this variable

    public MealTableSummary(DietViewController dietViewController) {
        this.dietViewController = dietViewController;
    }

    public void create(Meal meal, MealTable mealTable) {
        thisMealTableSummary = this;
        meal.productsList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                try {
                    thisMealTableSummary.update(meal);
                    change.next();
                    dietViewController.dailySummary.calculateTotalMacro(meal.productsList.get(change.getFrom()), change.wasAdded()); // passing recently added product, checking if was added if not to calculate macros correctly
                }
                catch (IndexOutOfBoundsException e) {
                    Product removedProduct = (Product) change.getRemoved().get(0);
                    dietViewController.dailySummary.calculateTotalMacro(removedProduct, change.wasAdded());
                }
            }
        });

        kcalSummary = new TableColumn();
        proteinsSummary = new TableColumn();
        carbsSummary = new TableColumn();
        fatsSummary = new TableColumn();
        WISummary = new TableColumn();
        priceSummary = new TableColumn();
        emptyColumn = new TableColumn();

        summaryContainer = new HBox();
        tableSummary = new SummaryTableView();



        kcalSummary.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        proteinsSummary.setCellValueFactory(new PropertyValueFactory<>("proteins"));
        carbsSummary.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        fatsSummary.setCellValueFactory(new PropertyValueFactory<>("fats"));
        WISummary.setCellValueFactory(new PropertyValueFactory<>("wholesomenessIndex"));
        priceSummary.setCellValueFactory(new PropertyValueFactory<>("price"));

        emptyColumn.setMinWidth(mealTable.quantityColumn.getWidth() + mealTable.nameColumn.getWidth()); // need it to align other columns correctly
        kcalSummary.setMaxWidth(mealTable.kcalColumn.getWidth());
        proteinsSummary.setMaxWidth(mealTable.priceColumn.getWidth());
        carbsSummary.setMaxWidth(mealTable.carbsColumn.getWidth());
        fatsSummary.setMaxWidth(mealTable.fatsColumn.getWidth());
        WISummary.setMaxWidth(mealTable.WIColumn.getWidth());
        priceSummary.setMaxWidth(mealTable.priceColumn.getWidth());

        totalKcal = 0.0F;
        totalProteins = 0.0F;
        totalCarbs = 0.0F;
        totalFats = 0.0F;
        totalWI = 0;
        totalPrice = 0.0F;

        Product product = new Product("" , 0.0F, 0.0F, 0.0F, 0.0F, "",
                "", 0, "", "", 0.0F); // just to display zeros instead of blank space

        tableSummary.getColumns().addAll(emptyColumn, kcalSummary, proteinsSummary, carbsSummary, fatsSummary, WISummary, priceSummary);
        tableSummary.getStyleClass().add(("table-summary"));

        tableSummary.getItems().add(product);
        summaryContainer.getChildren().add(tableSummary);
    }

    public void update(Meal meal) {
        summaryContainer.setMinHeight(26);
        tableSummary.setMinHeight(26);
        summaryContainer.setMaxHeight(26);
        tableSummary.setMaxHeight(26); // for some reason it resizes on adding another row, need to resize it back

        tableSummary.getItems().clear();
        totalKcal = 0.0F;
        totalProteins = 0.0F;
        totalCarbs = 0.0F;
        totalFats = 0.0F;
        totalWI = 0;
        totalPrice = 0.0F;


        for (Integer i = 0; i < meal.productsList.size(); i++) {
            totalKcal += Helper.twoDecimalsFloat(meal.productsList.get(i).getKcal());
            totalProteins += Helper.twoDecimalsFloat(meal.productsList.get(i).getProteins());
            totalCarbs += Helper.twoDecimalsFloat(meal.productsList.get(i).getCarbs());
            totalFats += Helper.twoDecimalsFloat(meal.productsList.get(i).getFats());
            totalWI += meal.productsList.get(i).getWholesomenessIndex();
            totalPrice += Helper.twoDecimalsFloat(meal.productsList.get(i).getPrice());
        }
        Product totalProduct = new Product("Total", totalKcal, totalProteins, totalCarbs, totalFats, "", "", totalWI.intValue(), "", "", totalPrice);
        tableSummary.getItems().add(totalProduct);
    }

    class SummaryTableView extends TableView { // table with no header - useful as table summary
        @Override
        public void resize(double width, double height) {
            super.resize(width, height);
            Pane header = (Pane) lookup("TableHeaderRow");
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setMaxHeight(0);
            header.setVisible(false);
        }
    }
}