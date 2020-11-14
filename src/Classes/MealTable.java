package Classes;

import Controllers.SelectProfileController;
import javafx.fxml.FXML;
import Controllers.DietViewController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.InputStream;

public class MealTable {
    public Integer productsInMeal1Count = 0;
    Label mealHeader, kcalHeader, proteinsHeader, carbsHeader, fatsHeader, WIHeader;
    DietViewController dietViewController;


    public void showHeaders(String mealName, Double kcalHeaderX, Double kcalHeaderY, Pane paneName) {
        mealHeader = new Label(mealName);
        kcalHeader = new Label("kcal");
        kcalHeader.setLayoutX(kcalHeaderX);
        kcalHeader.setLayoutY(kcalHeaderY);
        proteinsHeader = new Label("Proteins");
        carbsHeader = new Label("Carbs");
        fatsHeader = new Label("Fats");
        WIHeader = new Label("WI*");
        createChildrenLabel(mealHeader, kcalHeader, -30, -20, "-fx-font-weight: bold", paneName);
        createChildrenLabel(kcalHeader, kcalHeader, 0, 0, "-fx-font-weight: bold", paneName);
        createChildrenLabel(proteinsHeader, kcalHeader, 50, 0, "-fx-font-weight: bold", paneName);
        createChildrenLabel(carbsHeader, kcalHeader, 103, 0, "-fx-font-weight: bold", paneName);
        createChildrenLabel(fatsHeader, kcalHeader, 148, 0, "-fx-font-weight: bold", paneName);
        createChildrenLabel(WIHeader, kcalHeader, 185, 0, "-fx-font-weight: bold", paneName);
        Tooltip WItooltip = new Tooltip("Wholesomeness Index");
        WIHeader.setTooltip(WItooltip);
    }

    public void insertRow(Product product, Double quantity, Integer rowNum, Label headers, Pane paneName) {
        final Integer distanceBetweenRows = 20;
        Label productName = new Label(product.getName());
        Label productQuantity = new Label(quantity.toString() + " " + product.getShorterUnit());
        Label productKcal = new Label(String.format("%.1f", (product.getKcal() * quantity)));
        Label productProteins = new Label(String.format("%.1f", (product.getProteins() * quantity)));
        Label productCarbs = new Label(String.format("%.1f", (product.getCarbohydrates() * quantity)));
        Label productFats = new Label(String.format("%.1f", (product.getFats() * quantity)));
        Label productWI = new Label(product.getWholesomenessIndex().toString());
        ImageView deleteButton = new ImageView("img/minus.png");
        deleteButton.setFitHeight(16);
        deleteButton.setFitWidth(20);
        deleteButton.setOnMouseClicked(e -> {
            productName.setVisible(false);
            productQuantity.setVisible(false);
            productKcal.setVisible(false);
            productCarbs.setVisible(false);
            productProteins.setVisible(false);
            productFats.setVisible(false);
            productWI.setVisible(false);
            deleteButton.setVisible(false);
            productsInMeal1Count -= 1;
        });
        productName.setLayoutX(headers.getLayoutX() -127);
        productName.setLayoutY(headers.getLayoutY() + (distanceBetweenRows * rowNum));
        productQuantity.setLayoutX(headers.getLayoutX() -188);
        productQuantity.setLayoutY(headers.getLayoutY() + (distanceBetweenRows * rowNum));
        productKcal.setLayoutX(headers.getLayoutX());
        productKcal.setLayoutY(headers.getLayoutY() + (distanceBetweenRows * rowNum));
        productProteins.setLayoutX(headers.getLayoutX() + 51);
        productProteins.setLayoutY(headers.getLayoutY() + (distanceBetweenRows * rowNum));
        productCarbs.setLayoutX(headers.getLayoutX() + 105);
        productCarbs.setLayoutY(headers.getLayoutY() + (distanceBetweenRows * rowNum));
        productFats.setLayoutX(headers.getLayoutX() + 149);
        productFats.setLayoutY(headers.getLayoutY() + (distanceBetweenRows * rowNum));
        productWI.setLayoutX(headers.getLayoutX() + 183);
        productWI.setLayoutY(headers.getLayoutY() + (distanceBetweenRows * rowNum));
        deleteButton.setLayoutX(headers.getLayoutX() + 218);
        deleteButton.setLayoutY(headers.getLayoutY() + (distanceBetweenRows * rowNum));
        paneName.getChildren().addAll(productName, productQuantity, productKcal, productProteins, productCarbs, productFats, productWI, deleteButton);
    }

    private void createChildrenLabel(Label childrenLabel, Label parentLabel, Integer differenceX, Integer differenceY, String style, Pane paneName) {
        childrenLabel.setStyle(style);
        childrenLabel.setLayoutX(parentLabel.getLayoutX() + differenceX);
        childrenLabel.setLayoutY(parentLabel.getLayoutY() + differenceY);
        paneName.getChildren().add(childrenLabel);
    }

}
