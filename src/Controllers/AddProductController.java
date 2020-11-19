package Controllers;

import Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.*;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddProductController {
    private Stage thisStage;
    private final DietViewController dietViewController;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    @FXML Button closeButton, addSelectedButton;
    @FXML ChoiceBox selectColumnChoiceBox;
    MealTableSummary mealTableSummary;
    @FXML TableView productsTableView;
    @FXML TextField searchProductTextField, quantityTextField;
    @FXML TableColumn productNameColumn, quantityTableColumn, kcalColumn, proteinsColumn, carbohydratesColumn,
            fatsColumn, macronutrientColumn, categoryColumn, wholesomenessIndexColumn, priceColumn;


    public AddProductController(DietViewController dietViewController) {
        this.dietViewController = dietViewController;
        thisStage = new Stage();
        ChildrenWindow addProductWindow = new ChildrenWindow();
        addProductWindow.create("../fxml/addProduct.fxml", this, thisStage, true, 1011, 750);
        addToTable();
        selectColumnChoiceBox.getItems().addAll(productNameColumn.getText(), macronutrientColumn.getText(), categoryColumn.getText(), wholesomenessIndexColumn.getText());
        selectColumnChoiceBox.setValue(productNameColumn.getText());
        changeUnitInQuantityPromptText();

    }

    public void closeButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void addSelectedButtonOnAction() {
        Product selectedProduct = (Product) productsTableView.getSelectionModel().getSelectedItem();
        if (dietViewController.meal1Table.productsList.size() == 0)
            dietViewController.meal1Table.create(dietViewController.dietViewPane);
        dietViewController.meal1Table.productsDictionary.put(selectedProduct, Double.valueOf(quantityTextField.getText()));
        dietViewController.meal1Table.productsList.add(selectedProduct);
        dietViewController.meal1Table.insertRow(dietViewController.meal1Table.productsDictionary, dietViewController.meal1Table.productsList, selectedProduct, Double.valueOf(quantityTextField.getText()), dietViewController.meal1Table.tableContent);
        if (dietViewController.meal1Table.productsList.size() == 1) {
            mealTableSummary = new MealTableSummary();
            mealTableSummary.create(dietViewController.meal1Table);
        }
        else if (dietViewController.meal1Table.productsList.size() > 1)
            mealTableSummary.update(dietViewController.meal1Table);

        Object ahu;
        for (Integer i = 0; i < dietViewController.meal1Table.tableContent.getItems().size(); i++) {
            ahu = dietViewController.meal1Table.tableContent.getColumns().get(2).getCellObservableValue(i).getValue();
            System.out.println(ahu);
        }


        Stage stage = (Stage) addSelectedButton.getScene().getWindow();
        stage.close();
        quantityTextField.setText("");
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
        carbohydratesColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("carbs"));
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
                        Double.parseDouble(product.getString("kcal")),
                        Double.parseDouble(product.getString("proteins")),
                        Double.parseDouble(product.getString("carbohydrates")),
                        Double.parseDouble(product.getString("fats")),
                        product.getString("macronutrient_category"),
                        product.getString("category"),
                        Integer.parseInt(product.getString("wholesomeness_index")),
                        product.getString("unit_type"),
                        product.getString("unit_quantity"),
                        Double.parseDouble(product.getString("price"))
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
/*
    private void changeLabels(Group header, Group rowGroup, Label quantityLabel, Label productLabel, Label kcalLabel, Label carbsLabel, Label proteinsLabel, Label fatsLabel, Label WILabel, Label priceLabel) {
        Product selectedProduct = (Product) productsTableView.getSelectionModel().getSelectedItem();
        Double productQuantity = Double.parseDouble(quantityTextField.getText());
        header.setVisible(true);
        rowGroup.setVisible(true);
        quantityLabel.setText(quantityTextField.getText() + " " + selectedProduct.getShorterUnit());
        productLabel.setText(selectedProduct.getName());
        kcalLabel.setText(String.valueOf(Math.round(selectedProduct.getKcal() * productQuantity)));
        proteinsLabel.setText(String.valueOf(Math.round(selectedProduct.getProteins() * productQuantity)));
        carbsLabel.setText(String.valueOf(Math.round(selectedProduct.getCarbohydrates() * productQuantity)));
        fatsLabel.setText((String.valueOf(Math.round(selectedProduct.getFats() * productQuantity))));
        priceLabel.setText((String.valueOf(Math.round(selectedProduct.getPrice() * productQuantity))));
        WILabel.setText(selectedProduct.getWholesomenessIndex().toString());
    }

 */

}

