package Controllers;

import Classes.ChildrenWindow;
import Classes.DatabaseConnection;
import Classes.MealTable;
import Classes.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Group;
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
    @FXML TableView productsTableView;
    @FXML TextField searchProductTextField, quantityTextField;
    @FXML TableColumn productNameColumn, quantityTableColumn, kcalColumn, proteinsColumn, carbohydratesColumn,
            fatsColumn, macronutrientColumn, categoryColumn, wholesomenessIndexColumn, priceColumn;
    MealTable meal1Table = new MealTable();

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
        if (meal1Table.productsList.size() == 0)
            meal1Table.create(dietViewController.dietViewPane);
        meal1Table.productsList.add(selectedProduct);
        //mealTable.insertHeaders("Meal 1", 251.0, 151.0, dietViewController.dietViewPane);
        //mealTable.currentProductsNum += 1;
        //mealTable.insertRow(selectedProduct, Double.parseDouble(quantityTextField.getText()), mealTable.rowList.indexOf(), dietViewController.kcal1Label, dietViewController.dietViewPane);
        //System.out.println(mealTable.currentProductsNum);

        meal1Table.insertRow(selectedProduct, Double.parseDouble(quantityTextField.getText()), meal1Table.contentContainer);
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
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));
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
                        product.getString("quantity"),
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

