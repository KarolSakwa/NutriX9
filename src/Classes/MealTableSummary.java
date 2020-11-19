package Classes;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MealTableSummary {
    TableColumn kcalSummary;
    TableColumn proteinsSummary;
    TableColumn carbsSummary;
    TableColumn fatsSummary;
    TableColumn WISummary;
    TableColumn priceSummary;
    TableView tableSummary;
    HBox summaryContainer;
    Double totalKcal, totalProteins, totalCarbs, totalFats, totalWI, totalPrice;
    ArrayList<Product> totalList = new ArrayList<>();

    public void create(MealTable mealTable) {
        kcalSummary = new TableColumn();
        proteinsSummary = new TableColumn();
        carbsSummary = new TableColumn();
        fatsSummary = new TableColumn();
        WISummary = new TableColumn();
        priceSummary = new TableColumn();

        summaryContainer = new HBox();
        summaryContainer.setMinHeight(200);
        tableSummary = new SummaryTableView();
        tableSummary.setMinWidth(150);
        summaryContainer.getChildren().add(tableSummary);

        kcalSummary.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        proteinsSummary.setCellValueFactory(new PropertyValueFactory<>("proteins"));
        carbsSummary.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        fatsSummary.setCellValueFactory(new PropertyValueFactory<>("fats"));
        WISummary.setCellValueFactory(new PropertyValueFactory<>("wholesomenessIndex"));
        priceSummary.setCellValueFactory(new PropertyValueFactory<>("price"));

        totalKcal = 0.0;
        totalProteins = 0.0;
        totalCarbs = 0.0;
        totalFats = 0.0;
        totalWI = 0.0;
        totalPrice = 0.0;

        Product product = mealTable.productsList.get(0);
        product.setKcal(product.getKcal() * product.getQuantity());
        product.setProteins(product.getProteins() * product.getQuantity());
        product.setCarbs(product.getCarbs() * product.getQuantity());
        product.setFats(product.getFats() * product.getQuantity());
        product.setPrice(product.getPrice() * product.getQuantity());

        tableSummary.getColumns().addAll(kcalSummary, proteinsSummary, carbsSummary, fatsSummary, WISummary, priceSummary);

        tableSummary.getItems().add(product);
        mealTable.tableContainer.getChildren().add(tableSummary);
    }

    public void update(MealTable mealTable) {
        mealTable.tableContainer.getChildren().remove(tableSummary);
        tableSummary.getItems().clear();
        totalKcal = 0.0;
        totalProteins = 0.0;
        totalCarbs = 0.0;
        totalFats = 0.0;
        totalWI = 0.0;
        totalPrice = 0.0;

        for (Product product : mealTable.productsList) {
            totalKcal += product.getKcal() * product.getQuantity();
            totalProteins += product.getProteins() * product.getQuantity();
            totalCarbs += product.getCarbs() * product.getQuantity();
            totalFats += product.getFats() * product.getQuantity();
            totalWI += product.getWholesomenessIndex();
            totalPrice += product.getPrice() * product.getQuantity();
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