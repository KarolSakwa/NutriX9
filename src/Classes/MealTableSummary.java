package Classes;

import javafx.geometry.NodeOrientation;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MealTableSummary {
    TableColumn kcalSummary, proteinsSummary, carbsSummary, fatsSummary, WISummary, priceSummary, emptyColumn;
    TableView tableSummary;

    HBox summaryContainer;
    Double totalKcal;
    Double totalProteins;
    Double totalCarbs;
    Double totalFats;
    Integer totalWI;
    Double totalPrice;
    ArrayList<Product> totalList = new ArrayList<>();

    public void create(MealTable mealTable) {
        kcalSummary = new TableColumn();
        proteinsSummary = new TableColumn();
        carbsSummary = new TableColumn();
        fatsSummary = new TableColumn();
        WISummary = new TableColumn();
        priceSummary = new TableColumn();
        emptyColumn = new TableColumn();

        summaryContainer = new HBox();
        tableSummary = new SummaryTableView();
        summaryContainer.getChildren().add(tableSummary);

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

        totalKcal = 0.0;
        totalProteins = 0.0;
        totalCarbs = 0.0;
        totalFats = 0.0;
        totalWI = 0;
        totalPrice = 0.0;

        Product product = mealTable.productsList.get(0);
        product.setKcal(product.getKcal() * product.getQuantity());
        product.setProteins(product.getProteins() * product.getQuantity());
        product.setCarbs(product.getCarbs() * product.getQuantity());
        product.setFats(product.getFats() * product.getQuantity());
        product.setPrice(product.getPrice() * product.getQuantity());

        tableSummary.getColumns().addAll(emptyColumn, kcalSummary, proteinsSummary, carbsSummary, fatsSummary, WISummary, priceSummary);
        tableSummary.getStyleClass().add(("table-summary"));

        tableSummary.getItems().add(product);
        mealTable.tableContainer.getChildren().add(tableSummary);
    }

    public void update(MealTable mealTable) {
        summaryContainer.setMinHeight(26);
        tableSummary.setMinHeight(26);
        summaryContainer.setMaxHeight(26);
        tableSummary.setMaxHeight(26); // for some reason it resizes on adding another row, need to resize it back

        mealTable.tableContainer.getChildren().remove(tableSummary);
        tableSummary.getItems().clear();
        totalKcal = 0.0;
        totalProteins = 0.0;
        totalCarbs = 0.0;
        totalFats = 0.0;
        totalWI = 0;
        totalPrice = 0.0;

        for (Integer i = 0; i < mealTable.tableContent.getItems().size(); i++) {
            totalKcal += (Double) mealTable.tableContent.getColumns().get(2).getCellObservableValue(i).getValue();
            totalProteins += (Double) mealTable.tableContent.getColumns().get(3).getCellObservableValue(i).getValue();
            totalCarbs += (Double) mealTable.tableContent.getColumns().get(4).getCellObservableValue(i).getValue();
            totalFats += (Double) mealTable.tableContent.getColumns().get(5).getCellObservableValue(i).getValue();
            totalWI += (Integer) mealTable.tableContent.getColumns().get(6).getCellObservableValue(i).getValue();
            totalPrice += (Double) mealTable.tableContent.getColumns().get(7).getCellObservableValue(i).getValue();
        }
        Product totalProduct = new Product("Total", totalKcal, totalProteins, totalCarbs, totalFats, "", "", totalWI.intValue(), "", "", totalPrice);

        tableSummary.getItems().add(totalProduct);
        mealTable.tableContainer.getChildren().add(tableSummary);
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