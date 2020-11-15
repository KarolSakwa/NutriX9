package Classes;

import Controllers.DietViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.util.ArrayList;

public class MealTable {
    public VBox contentContainer;
    public ArrayList<Product> productsList = new ArrayList<>();
    HBox mealName, headers, mealSummary;
    DietViewController dietViewController;

    public void create(Pane pane) {
        contentContainer = new VBox();
        contentContainer.setLayoutX(132);
        contentContainer.setLayoutY(159);
        createHeaders();
        contentContainer.getChildren().addAll(mealName, headers);
        pane.getChildren().addAll(contentContainer);
    }

    private void createHeaders() {
        mealName = new HBox();
        mealName.setPrefHeight(13);
        mealName.setAlignment(Pos.CENTER);
        Label mealNameLabel = new Label("Meal ");
        mealName.getChildren().addAll(mealNameLabel);
        mealNameLabel.setStyle("-fx-font-weight: bold;");

        // Other headers
        headers = new HBox(20);
        headers.setPrefHeight(35);
        headers.setAlignment(Pos.CENTER_RIGHT);
        Label kcalHeader = new Label("kcal");
        Label proteinsHeader = new Label("Proteins");
        Label carbsHeader = new Label("Carbs");
        Label fatsHeader = new Label("Fats");
        Label WIsHeader = new Label("WI*");
        Label priceHeader = new Label("Price           "); // spaces to align headers with content
        Tooltip WITooltip = new Tooltip("Wholesomeness Index");
        WIsHeader.setTooltip(WITooltip);
        headers.getChildren().addAll(kcalHeader, proteinsHeader, carbsHeader, fatsHeader, WIsHeader, priceHeader);
        setHeadersStyle(headers);
    }
    public void insertRow(Product product, Double quantity, VBox parent){
        // Container
        HBox rowContainer = new HBox();
        rowContainer.setPrefHeight(13);
        rowContainer.setAlignment(Pos.CENTER_RIGHT);

        // Rows
        Label productName = new Label(product.getName());
        Label productQuantity = new Label(quantity.toString() + " " + product.getShorterUnit());
        Label productKcal = new Label(String.format("%.1f", (product.getKcal() * quantity)));
        Label productProteins = new Label(String.format("%.1f", (product.getProteins() * quantity)));
        Label productCarbs = new Label(String.format("%.1f", (product.getCarbs() * quantity)));
        Label productFats = new Label(String.format("%.1f", (product.getFats() * quantity)));
        Label productWI = new Label(product.getWholesomenessIndex().toString());
        Label productPrice = new Label(String.format("%.1f", (product.getPrice() * quantity)));

        ImageView deleteButton = new ImageView("img/minus.png");
        deleteButton.setFitWidth(20);
        deleteButton.setFitHeight(16);
        deleteButton.setOnMouseClicked(e -> parent.getChildren().remove(rowContainer));

        rowContainer.getChildren().addAll(productName, productQuantity, productKcal, productProteins, productCarbs, productFats, productWI, productPrice, deleteButton);
        setLabelsStyle(rowContainer);
        parent.getChildren().add(rowContainer);
        executeLabelLengthEqualization(rowContainer);

    }


    private void adjustHeader(Label header, Integer prefHeight) {
        header.setPrefHeight(prefHeight);
    }

    private void setHeadersStyle(HBox hBox) {
        for (Node label : hBox.getChildren()) {
            label.setStyle("-fx-font-weight: bold;");
        }
    }
    private void setLabelsStyle(HBox hBox) {
        String space="";
        for (Integer i = 0; i < hBox.getChildren().size() -1; i++) { // -1, cause I don't want to include the last element of the list (deleteButton)
            Label label = (Label) hBox.getChildren().get(i);
        }
    }

    // Had some issues with layout, decided to add some spaces to labels to obtain the same labels' length
    private void labelLengthEqualization(Label label, Integer numChars) {
        Integer necessaryChars = numChars - label.getText().length();
        String spaces = new String();
        for (Integer i = 0; i < necessaryChars; i++) {
            spaces += " ";
        }
        label.setText(label.getText() + spaces);
    }


    private void executeLabelLengthEqualization(HBox hBox) {
        for (Integer i = 1; i < hBox.getChildren().size() -1; i++) { // -1, cause I don't want to include the last element of the list (deleteButton)
            Label label = (Label) hBox.getChildren().get(i);
            labelLengthEqualization(label, 7);
            System.out.println(label.getText());
            label.setPadding(new Insets(0, 10, 0,10));
            label.setStyle(
                    "-fx-border-insets: 8px;" +
                    "-fx-background-insets: 8px;");
        }
    }

}
