package Classes;

import Controllers.DietViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


import java.util.ArrayList;
import java.util.Arrays;

public class MealTable {
    public VBox contentContainer;
    public ArrayList<Product> productsList = new ArrayList<>();
    public ArrayList<Double> productsQuantityList = new ArrayList<>(); // not the best, but the fastest way to store info about products quantity, will modify later
    DietViewController dietViewController;



    public void create(Pane pane) {
        contentContainer = new VBox();
        contentContainer.setLayoutX(132);
        contentContainer.setLayoutY(159);
        pane.getChildren().addAll(contentContainer);
    }

    public void insertRow(Product product, Double quantity, VBox parent, MealTableSummary mealTableSummary){
        // Container
        HBox rowContainer = new HBox();
        rowContainer.setPrefHeight(13);
        rowContainer.setAlignment(Pos.CENTER_LEFT);

        Text quantityText = new Text(quantity.toString() + " " + product.getShorterUnit());
        addNecessarySpaces(quantityText, 10); // to get better alignment

        Text nameText = new Text(product.getName());
        nameText.setStyle("-fx-font-weight: 800;");

        TextFlow kcalTextFlow = new TextFlow();
        Text kcalTextNormal = new Text("kcal: ");
        Text kcalTextBold = new Text(String.format("%.1f", (product.getKcal() * quantity)) + "    ");
        kcalTextFlow.getChildren().addAll(kcalTextNormal, kcalTextBold);

        TextFlow proteinsTextFlow = new TextFlow();
        Text proteinsTextNormal = new Text(product.proteinsAbbr + ": ");
        Text proteinsTextBold = new Text(String.format("%.1f", (product.getProteins() * quantity))  + "    ");
        proteinsTextFlow.getChildren().addAll(proteinsTextNormal, proteinsTextBold);

        TextFlow carbsTextFlow = new TextFlow();
        Text carbsTextNormal = new Text(product.carbsAbbr + ": ");
        Text carbsTextBold = new Text(String.format("%.1f", (product.getCarbs() * quantity))  + "    ");
        carbsTextFlow.getChildren().addAll(carbsTextNormal, carbsTextBold);

        TextFlow fatsTextFlow = new TextFlow();
        Text fatsTextNormal = new Text(product.fatsAbbr + ": ");
        Text fatsTextBold = new Text(String.format("%.1f", (product.getFats() * quantity))  + "    ");
        fatsTextFlow.getChildren().addAll(fatsTextNormal, fatsTextBold);

        TextFlow WITextFlow = new TextFlow();
        Text WITextNormal = new Text("WI: ");
        Text WITextBold = new Text(product.getWholesomenessIndex().toString() + "    ");
        WITextFlow.getChildren().addAll(WITextNormal, WITextBold);

        TextFlow priceTextFlow = new TextFlow();
        Text priceTextBold = new Text(String.format("%.1f", (product.getPrice() * quantity)) + " ");
        Text priceTextNormal = new Text(product.priceAbbr);
        priceTextFlow.getChildren().addAll(priceTextBold, priceTextNormal);

        ImageView deleteButton = new ImageView("img/minus.png");
        deleteButton.setFitWidth(20);
        deleteButton.setFitHeight(16);
        deleteButton.setOnMouseClicked(e -> {
            System.out.println(parent.getChildren().indexOf(rowContainer));
            mealTableSummary.update(productsList, productsQuantityList.get(parent.getChildren().indexOf(rowContainer)), parent);
            System.out.println(productsList);
            System.out.println(productsQuantityList);
            parent.getChildren().remove(rowContainer);
            productsList.remove(product);
        });

        Text[] boldList = {quantityText, kcalTextBold, priceTextBold, carbsTextBold, fatsTextBold, proteinsTextBold, WITextBold};
        ArrayList<Text> boldArray = new ArrayList<>();
        boldArray.addAll(Arrays.asList(boldList));
        setTextsBold12Green(boldArray);
        addNecessarySpaces(nameText, 10);
        rowContainer.getChildren().addAll(quantityText, nameText, kcalTextFlow, proteinsTextFlow, carbsTextFlow, fatsTextFlow, WITextFlow, priceTextFlow, deleteButton);
        parent.getChildren().add(rowContainer);
    }

    public void insertSummary(Double quantity, VBox parent) {

    }


    private void addNecessarySpaces(Text text, Integer totalChars) {
        String spaces = "";
        String textContent = text.getText();
        Integer necessarySpaces = totalChars - textContent.length();
        for (Integer i = 0; i < necessarySpaces; i++) {
            spaces += " ";
        }
        text.setText(textContent + spaces);
    }
    private void setTextsBold12Green(ArrayList<Text> texts) {
        for (Integer i = 0; i < texts.size(); i++) {
            Text text = (Text) texts.get(i);
            text.setStyle("-fx-font-weight: 800;" +
                    "-fx-fill: green;");
        }
    }

}
