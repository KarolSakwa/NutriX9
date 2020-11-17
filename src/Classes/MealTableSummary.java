package Classes;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Arrays;

public class MealTableSummary {
    public HBox mealSummaryContainer = new HBox();
    Double totalKcal, totalProteins, totalCarbs, totalFats, totalWI, totalPrice;
    Text totalLabel, kcalLabel, proteinsLabel, carbsLabel, fatsLabel, WILabel, priceLabel, totalKcalText, totalProteinsText,
            totalCarbsText, totalFatsText, totalWIText, totalPriceText;
    TextFlow summaryTextFlow;

    public void initialize() {
        mealSummaryContainer.setVisible(false);
    }

    public void create(MealTable mealTable, Double quantity, VBox parent) {
        Separator summarySeparator = new Separator();

        mealSummaryContainer.setAlignment(Pos.CENTER_RIGHT);

        summaryTextFlow = new TextFlow();
        totalKcal = new Double(0);
        totalProteins = new Double(0);
        totalCarbs = new Double(0);
        totalFats = new Double(0);
        totalWI = new Double(0);
        totalPrice = new Double(0);

        totalLabel = new Text("Total: ");
        kcalLabel = new Text("kcal: ");
        proteinsLabel = new Text(mealTable.productsList.get(0).proteinsAbbr); // I get some product on the list just to access macronutrient abbreviations stored in final Product variables
        carbsLabel = new Text(mealTable.productsList.get(0).carbsAbbr);
        fatsLabel = new Text(mealTable.productsList.get(0).fatsAbbr);
        WILabel = new Text("WI: ");
        priceLabel = new Text(mealTable.productsList.get(0).priceAbbr);

        for (int i = 0; i < mealTable.productsList.size(); i++) {
            Double convertedQuantity = convertQuantity(mealTable.productsList.get(i), quantity);
            totalKcal = mealTable.productsList.get(i).getKcal() * convertedQuantity;
            totalProteins = mealTable.productsList.get(i).getProteins() * convertedQuantity;
            totalCarbs = mealTable.productsList.get(i).getCarbs() * convertedQuantity;
            totalFats = mealTable.productsList.get(i).getFats() * convertedQuantity;
            totalPrice = mealTable.productsList.get(i).getPrice() * convertedQuantity;
        }

        totalKcalText = new Text(totalKcal.toString());
        totalProteinsText = new Text(totalProteins.toString());
        totalCarbsText = new Text(totalCarbs.toString());
        totalFatsText = new Text(totalFats.toString());
        totalWIText = new Text(totalWI.toString());
        totalPriceText = new Text(totalPrice.toString());

        Text[] boldList = {totalKcalText, totalProteinsText, totalFatsText, totalCarbsText, totalWIText, totalPriceText};
        ArrayList<Text> boldArray = new ArrayList<>();
        boldArray.addAll(Arrays.asList(boldList));
        setTextsBold14GreenAndAddSpaces(boldArray);

        Text[] normalList = {totalLabel, kcalLabel, proteinsLabel, priceLabel, WILabel, carbsLabel, fatsLabel};
        ArrayList<Text> normalArray = new ArrayList<>();
        normalArray.addAll(Arrays.asList(normalList));
        setTexts14(normalArray);

        summaryTextFlow.getChildren().addAll(totalLabel, kcalLabel, totalKcalText, proteinsLabel, totalProteinsText, carbsLabel, totalCarbsText,
                fatsLabel, totalFatsText, WILabel, totalWIText, totalPriceText, priceLabel);
        mealSummaryContainer.getChildren().add(summaryTextFlow);
        parent.getChildren().add(mealSummaryContainer);
    }

    public void update(ArrayList<Product> productsList, Double quantity, VBox parent) {
        // first I remove old values to make new ones appear as last
        parent.getChildren().remove(mealSummaryContainer);
        // for correct calculations, I need to reset values
        totalKcal = 0.0;
        totalProteins = 0.0;
        totalCarbs = 0.0;
        totalFats = 0.0;
        totalPrice = 0.0;
        for (Product product : productsList) {
            totalKcal += (product.getKcal() * quantity);
            totalProteins += (product.getProteins() * quantity);
            totalCarbs += (product.getCarbs() * quantity);
            totalFats += (product.getFats() * quantity);
            totalPrice += (product.getPrice() * quantity);
        }
        System.out.println(totalKcal.toString());
        totalKcalText.setText(totalKcal.toString());
        totalProteinsText.setText(totalProteins.toString());
        totalCarbsText.setText(totalCarbs.toString());
        totalFatsText.setText(totalFats.toString());
        totalPriceText.setText(totalPrice.toString());

        parent.getChildren().add(mealSummaryContainer);
    }

    private void setTextsBold14GreenAndAddSpaces(ArrayList<Text> texts) {
        for (Integer i = 0; i < texts.size(); i++) {
            Text text = (Text) texts.get(i);
            text.setStyle("-fx-font-weight: 800;" +
                    "-fx-fill: green;" +
                    "-fx-font-size: 14px;");
            if (i != texts.size() -1) // don't want last element to be included
                text.setText(text.getText() + "  ");
        }
    }
    private void setTexts14(ArrayList<Text> texts) {
        for (Integer i = 0; i < texts.size(); i++) {
            Text text = (Text) texts.get(i);
            text.setStyle("-fx-font-size: 14px;");
            text.setText(text.getText() + " ");
        }
    }

    private Double convertQuantity(Product product, Double quantity) { // we want some unit types to be counted the other way than the others, e.g. 100 ml = 1 unit
        if (product.getUnitType() == "milliliter" || product.getUnitType() == "gram")
            return quantity / 100;
        return quantity;
    }

}
/*
LISTA ZADAŃ:
        POPRAWIĆ PROBLEM Z LICZBĄ PRODUKTÓW
        ZROBIĆ, ŻEBY W PRZYPADKU BRAKU PRODUKTÓW - SUMMARY ZNIKAŁO


 */