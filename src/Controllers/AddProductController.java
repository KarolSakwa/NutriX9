package Controllers;

import Classes.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.sql.Connection;

public class AddProductController {
    private Stage thisStage;
    private final DietViewController dietViewController;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    public Integer MTIndex; // helps to recognize where to add products
    @FXML Button closeButton, addSelectedButton;
    @FXML ChoiceBox selectColumnChoiceBox;
    @FXML TableView productsTableView;
    @FXML TextField searchProductTextField, quantityTextField;
    @FXML TableColumn productNameColumn, quantityTableColumn, kcalColumn, proteinsColumn, carbsColumn,
            fatsColumn, macronutrientColumn, categoryColumn, wholesomenessIndexColumn, priceColumn;
    @FXML Label qualityAlertLabel;


    public AddProductController(DietViewController dietViewController) {
        this.dietViewController = dietViewController;
        thisStage = new Stage();
        ChildrenWindow.create("../fxml/addProduct.fxml", this, thisStage, true, 1011, 750);
        Helper.addToTable(databaseConnection, productNameColumn, quantityTableColumn, kcalColumn, proteinsColumn, carbsColumn, fatsColumn,
                macronutrientColumn, categoryColumn, wholesomenessIndexColumn, priceColumn, productsTableView, searchProductTextField, selectColumnChoiceBox);
        selectColumnChoiceBox.getItems().addAll(productNameColumn.getText(), macronutrientColumn.getText(), categoryColumn.getText(), wholesomenessIndexColumn.getText());
        selectColumnChoiceBox.setValue(productNameColumn.getText());
        Helper.changeUnitInQuantityPromptText(productsTableView, quantityTextField);
        addSelectedButton.setOnAction(e -> {
            if (MTIndex == null)
                addSelectedButtonOnAction(dietViewController.mealTablesContainer.mealTablesList.get(0));
            else
                addSelectedButtonOnAction(dietViewController.mealTablesContainer.mealTablesList.get(MTIndex));
        });
    }
    public void closeButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void addSelectedButtonOnAction(MealTable mealTable) {
        Product selectedProduct = (Product) productsTableView.getSelectionModel().getSelectedItem();
        Integer realUnitQuantity = Integer.parseInt(selectedProduct.getUnitQuantity().replaceAll("[^0-9]", ""));
        if (!quantityTextField.getText().trim().isEmpty() && selectedProduct != null && Helper.isNumeric(quantityTextField.getText())) {
            mealTable.meal.productsList.add(selectedProduct);
            if (realUnitQuantity <= 1)
                mealTable.insertRow(mealTable.meal.productsList, selectedProduct, Float.valueOf(quantityTextField.getText()), mealTable.tableContent);
            else
                mealTable.insertRow(mealTable.meal.productsList, selectedProduct, Float.valueOf(quantityTextField.getText())/realUnitQuantity, mealTable.tableContent);
            Stage stage = (Stage) addSelectedButton.getScene().getWindow();
            stage.close();
            quantityTextField.setText("");
            qualityAlertLabel.setVisible(false);
        }
        else {
            qualityAlertLabel.setVisible(true);
        }
    }

    public void showStage(){
        thisStage.showAndWait();
    }
}

