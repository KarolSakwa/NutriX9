package Classes;

import Controllers.DietViewController;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MealTable {
    public Integer currentProductsNum = 0;
    Label mealHeader, kcalHeader, proteinsHeader, carbsHeader, fatsHeader, WIHeader, priceHeader;
    DietViewController dietViewController;
    MealTableRow row1, row2, row3, row4, row5;
    MealTable meal1Table = new MealTable(null, null, null, null, null);

    public MealTable(MealTableRow row1, MealTableRow row2, MealTableRow row3, MealTableRow row4, MealTableRow row5) {
        this.row1 = row1;
        this.row2 = row2;
        this.row3 = row3;
        this.row4 = row4;
        this.row5 = row5;
    }

    public MealTableRow getRow1() {
        return row1;
    }

    public void setRow1(MealTableRow row1) {
        this.row1 = row1;
    }

    public MealTableRow getRow2() {
        return row2;
    }

    public void setRow2(MealTableRow row2) {
        this.row2 = row2;
    }

    public MealTableRow getRow3() {
        return row3;
    }

    public void setRow3(MealTableRow row3) {
        this.row3 = row3;
    }

    public MealTableRow getRow4() {
        return row4;
    }

    public void setRow4(MealTableRow row4) {
        this.row4 = row4;
    }

    public MealTableRow getRow5() {
        return row5;
    }

    public void setRow5(MealTableRow row5) {
        this.row5 = row5;
    }

    public void insertHeaders(String mealName, Double kcalHeaderX, Double kcalHeaderY, Pane paneName) {
        mealHeader = new Label(mealName);
        kcalHeader = new Label("kcal");
        kcalHeader.setLayoutX(kcalHeaderX);
        kcalHeader.setLayoutY(kcalHeaderY);
        proteinsHeader = new Label("Proteins");
        carbsHeader = new Label("Carbs");
        fatsHeader = new Label("Fats");
        WIHeader = new Label("WI*");
        priceHeader = new Label("Price");
        createChildrenLabel(mealHeader, kcalHeader, -30, -20, "-fx-font-weight: bold", paneName);
        createChildrenLabel(kcalHeader, kcalHeader, 0, 0, "-fx-font-weight: bold", paneName);
        createChildrenLabel(proteinsHeader, kcalHeader, 50, 0, "-fx-font-weight: bold", paneName);
        createChildrenLabel(carbsHeader, kcalHeader, 103, 0, "-fx-font-weight: bold", paneName);
        createChildrenLabel(fatsHeader, kcalHeader, 148, 0, "-fx-font-weight: bold", paneName);
        createChildrenLabel(WIHeader, kcalHeader, 185, 0, "-fx-font-weight: bold", paneName);
        createChildrenLabel(priceHeader, kcalHeader, 215, 0, "-fx-font-weight: bold", paneName);
        Tooltip WItooltip = new Tooltip("Wholesomeness Index");
        WIHeader.setTooltip(WItooltip);
    }

    public void insertRow(Product product, Double quantity, Integer rowNum, Label headers, Pane paneName) {
        MealTableRow mealTableRow = new MealTableRow();
        if (meal1Table.row1 == null)
            meal1Table.row1 = mealTableRow;
            mealTableRow.create(this, product, quantity, rowNum, paneName);
            mealTableRow.setEmpty(false);
        final Integer distanceBetweenRows = 20;
        Label productName = new Label(product.getName());
        Label productQuantity = new Label(quantity.toString() + " " + product.getShorterUnit());
        Label productKcal = new Label(String.format("%.1f", (product.getKcal() * quantity)));
        Label productProteins = new Label(String.format("%.1f", (product.getProteins() * quantity)));
        Label productCarbs = new Label(String.format("%.1f", (product.getCarbohydrates() * quantity)));
        Label productFats = new Label(String.format("%.1f", (product.getFats() * quantity)));
        Label productWI = new Label(product.getWholesomenessIndex().toString());
        Label productPrice = new Label(String.format("%.1f", (product.getPrice() * quantity)));
        ImageView deleteButton = new ImageView("img/minus.png");
        deleteButton.setFitHeight(16);
        deleteButton.setFitWidth(20);
        deleteButton.setOnMouseClicked(e -> {
            mealTableRow.delete(this, paneName, mealTableRow);
            productName.setVisible(false);
            productQuantity.setVisible(false);
            productKcal.setVisible(false);
            productCarbs.setVisible(false);
            productProteins.setVisible(false);
            productFats.setVisible(false);
            productWI.setVisible(false);
            productPrice.setVisible(false);
            deleteButton.setVisible(false);
            mealTableRow.setEmpty(true);
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
        productPrice.setLayoutX(headers.getLayoutX() + 219);
        productPrice.setLayoutY(headers.getLayoutY() + (distanceBetweenRows * rowNum));
        deleteButton.setLayoutX(headers.getLayoutX() + 245);
        deleteButton.setLayoutY(headers.getLayoutY() + (distanceBetweenRows * rowNum));
        paneName.getChildren().addAll(productName, productQuantity, productKcal, productProteins, productCarbs, productFats, productWI, productPrice, deleteButton);
    }

    private void createChildrenLabel(Label childrenLabel, Label parentLabel, Integer differenceX, Integer differenceY, String style, Pane paneName) {
        childrenLabel.setStyle(style);
        childrenLabel.setLayoutX(parentLabel.getLayoutX() + differenceX);
        childrenLabel.setLayoutY(parentLabel.getLayoutY() + differenceY);
        paneName.getChildren().add(childrenLabel);
    }

}
