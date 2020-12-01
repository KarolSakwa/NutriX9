package Controllers;

import Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
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


    public AddProductController(DietViewController dietViewController) {
        this.dietViewController = dietViewController;
        thisStage = new Stage();
        ChildrenWindow.create("../fxml/addProduct.fxml", this, thisStage, true, 1011, 750);
        addToTable();
        selectColumnChoiceBox.getItems().addAll(productNameColumn.getText(), macronutrientColumn.getText(), categoryColumn.getText(), wholesomenessIndexColumn.getText());
        selectColumnChoiceBox.setValue(productNameColumn.getText());
        changeUnitInQuantityPromptText();
        addSelectedButton.setOnAction(e -> {
            if (MTIndex == 1)
                addSelectedButtonOnAction(dietViewController.meal1Table);
            else if (MTIndex == 2)
                addSelectedButtonOnAction(dietViewController.meal2Table);
            else if (MTIndex == 3)
                addSelectedButtonOnAction(dietViewController.meal3Table);
            else if (MTIndex == 4)
                addSelectedButtonOnAction(dietViewController.meal4Table);
            else if (MTIndex == 5)
                addSelectedButtonOnAction(dietViewController.meal5Table);
        });
    }

    public void closeButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void addSelectedButtonOnAction(MealTable mealTable) {
        Product selectedProduct = (Product) productsTableView.getSelectionModel().getSelectedItem();
        if (!quantityTextField.getText().trim().isEmpty() && selectedProduct != null) {
            mealTable.productsList.add(selectedProduct);
            mealTable.insertRow(mealTable.productsList, selectedProduct, Float.valueOf(quantityTextField.getText()), mealTable.tableContent);
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
                        Float.valueOf(Math.round(product.getFloat("kcal"))),
                        Float.valueOf(Math.round(product.getFloat("proteins"))),
                        Float.valueOf(Math.round(product.getFloat("carbs"))),
                        Float.valueOf(Math.round(product.getFloat("fats"))),
                        product.getString("macronutrient_category"),
                        product.getString("category"),
                        Integer.parseInt(product.getString("wholesomeness_index")),
                        product.getString("unit_type"),
                        product.getString("unit_quantity"),
                        Float.valueOf(Math.round(product.getFloat("price")))

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
}

