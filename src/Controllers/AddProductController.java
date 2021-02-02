package Controllers;

import Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.*;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AddProductController {
    private Stage thisStage;
    private final DietViewController dietViewController;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    public Integer MTIndex; // for now it helps to recognize where to add products
    @FXML Button closeButton, addSelectedButton;
    @FXML ChoiceBox selectColumnChoiceBox;
    public MealTableSummary mealTableSummary;
    @FXML TableView productsTableView;
    @FXML TextField searchProductTextField, quantityTextField;
    @FXML TableColumn productNameColumn, quantityTableColumn, kcalColumn, proteinsColumn, carbsColumn,
            fatsColumn, macronutrientColumn, categoryColumn, wholesomenessIndexColumn, priceColumn;
    @FXML Label qualityAlertLabel;
    @FXML BorderPane mainBorderPane;


    public AddProductController(DietViewController dietViewController) {
        this.dietViewController = dietViewController;
        thisStage = new Stage();
        ChildrenWindow.create("../fxml/addProduct.fxml", this, thisStage, true, 1011, 750);
        addToTable();
        selectColumnChoiceBox.getItems().addAll(productNameColumn.getText(), macronutrientColumn.getText(), categoryColumn.getText(), wholesomenessIndexColumn.getText());
        selectColumnChoiceBox.setValue(productNameColumn.getText());
        changeUnitInQuantityPromptText();
        //mainBorderPane.setBorder(new Border(new BorderStroke(Color.rgb(0, 0, 0, 0.3), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
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
        if (!quantityTextField.getText().trim().isEmpty() && selectedProduct != null && isNumeric(quantityTextField.getText())) {
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

    private void addToTable() {
        ObservableList products = FXCollections.observableArrayList();
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("unitQuantity"));
        kcalColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("kcal"));
        proteinsColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("proteins"));
        carbsColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("carbs"));
        fatsColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("fats"));
        macronutrientColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("macronutrientCategory"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
        wholesomenessIndexColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("wholesomenessIndex"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        FilteredList<Product> filteredProducts = new FilteredList(products, p -> true);
        productsTableView.setItems(filteredProducts);
        try {
            Statement statement = con.createStatement();
            String query = "SELECT * FROM products;";
            ResultSet product = statement.executeQuery(query);
            while (product.next()) {
                products.addAll(new Product(product.getString("name"),
                        product.getFloat("kcal"),
                        product.getFloat("proteins"),
                        product.getFloat("carbs"),
                        product.getFloat("fats"),
                        product.getString("macronutrient_category"),
                        product.getString("category"),
                        product.getInt("wholesomeness_index"),
                        product.getString("unit_type"),
                        product.getString("unit_quantity"),
                        product.getFloat("price")
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        searchProductTextField.setOnKeyReleased(keyEvent ->
        {
            Object value = selectColumnChoiceBox.getValue();//Switch on choiceBox value
            if (productNameColumn.getText().equals(value)) {
                filteredProducts.setPredicate(p -> p.getName().toLowerCase().contains(searchProductTextField.getText().toLowerCase().trim()));//filter table by first name
            } else if (macronutrientColumn.getText().equals(value)) {
                filteredProducts.setPredicate(p -> p.getMacronutrientCategory().toLowerCase().contains(searchProductTextField.getText().toLowerCase().trim()));//filter table by first name
            } else if (categoryColumn.getText().equals(value)) {
                filteredProducts.setPredicate(p -> p.getCategory().toLowerCase().contains(searchProductTextField.getText().toLowerCase().trim()));//filter table by first name
            } else if (wholesomenessIndexColumn.getText().equals(value)) {
                filteredProducts.setPredicate(p -> p.getWholesomenessIndex().toString().toLowerCase().contains(searchProductTextField.getText().toLowerCase().trim()));//filter table by first name
            }
        });
    }

    private void changeUnitInQuantityPromptText() {
        productsTableView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            Product product = (Product) productsTableView.getSelectionModel().getSelectedItem();
            quantityTextField.setPromptText("Quantity (" + product.getUnitType() + ")");
        });
    }

    private Boolean isNumeric(String string) {
            if (string == null) {
                return false;
            }
            try {
                Double d = Double.parseDouble(string);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }
}

