package Classes;

import Controllers.AddProductController;
import Controllers.DietViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class MealTable {
    public VBox tableContainer;
    HBox mealNameContainer;
    Label mealName;
    public TableView<Product> tableContent;
    TableColumn quantityColumn, nameColumn, kcalColumn, proteinsColumn, carbsColumn, fatsColumn, WIColumn, priceColumn;
    Integer mealNum, tableContainerLayoutX, tableContainerLayoutY;
    private final Integer tableContainerWidth = 507;
    private final Integer tableContainerHeight = 110;
    Button addProductButton, deleteProductButton, deleteMealButton;
    AddProductController addProductController;
    MealTableSummary mealTableSummary;
    DietViewController dietViewController;
    MealTablesContainer mealTablesContainer;
    Separator separator;
    public Meal meal;


    public MealTable(Meal meal, DietViewController dietViewController, Integer tableContainerLayoutX, Integer tableContainerLayoutY, MealTablesContainer mealTablesContainer, AddProductController addProductController) {
        this.meal = meal;
        this.tableContainerLayoutX = tableContainerLayoutX;
        this.tableContainerLayoutY = tableContainerLayoutY;
        this.addProductController = addProductController;
        this.dietViewController = dietViewController;
        this.mealTablesContainer = mealTablesContainer;
        tableContent = new TableView<>(meal.productsList);
    }
    public Integer getMealNum() {
        return mealNum;
    }

    public void setMealNum(Integer mealNum) {
        this.mealNum = mealNum;
    }

    public Integer getTableContainerLayoutX() {
        return tableContainerLayoutX;
    }

    public void setTableContainerLayoutX(Integer tableContainerLayoutX) {
        this.tableContainerLayoutX = tableContainerLayoutX;
    }

    public Integer getTableContainerLayoutY() {
        return tableContainerLayoutY;
    }

    public void setTableContainerLayoutY(Integer tableContainerLayoutY) {
        this.tableContainerLayoutY = tableContainerLayoutY;
    }

    public void create() {
        tableContainer = new VBox();
        tableContainer.setMaxWidth(tableContainerWidth);
        tableContainer.setMaxHeight(tableContainerHeight);
        tableContainer.setLayoutX(tableContainerLayoutX);
        tableContainer.setLayoutY(tableContainerLayoutY);
        mealNameContainer = new HBox();
        mealNameContainer.setAlignment(Pos.CENTER);
        mealName = new Label("");
        mealName.setMinWidth(36);
        addProductButton = new Button("Add product");
        addProductButton.setOnAction(e -> addProductButtonOnAction());
        addProductButton.setMinWidth(83);
        deleteProductButton = new Button("Delete");
        deleteProductButton.setOnAction(e -> deleteProductButtonOnAction());
        deleteProductButton.setMinWidth(51);
        deleteMealButton = new Button("DELETE MEAL");
        deleteMealButton.setMinWidth(90);
        deleteMealButton.setOnAction(e -> deleteMealButtonOnAction(mealTablesContainer));
        mealNameContainer.getChildren().addAll(deleteMealButton, mealName, addProductButton, deleteProductButton);
        mealNameContainer.setAlignment(Pos.CENTER_RIGHT);
        mealNameContainer.setMargin(addProductButton, new Insets(0, 10, 0, 0));
        mealNameContainer.setMargin(mealName, new Insets(0, 90, 0, 0));
        mealNameContainer.setMargin(deleteMealButton, new Insets(0, 150, 0, 0));

        tableContent = new TableView();
        tableContent.setMinHeight(147);
        tableContent.setMaxHeight(147);
        tableContent.setPlaceholder(new Label("The meal does not contain any products"));
        createColumns(tableContent);

        mealTableSummary = new MealTableSummary(dietViewController);
        mealTableSummary.create(meal, this);

        separator = new Separator();
        separator.setMinHeight(30);

        tableContainer.getChildren().addAll(mealNameContainer, tableContent, mealTableSummary.tableSummary, separator);

        mealTablesContainer.vBox.getChildren().add(tableContainer);
    }

    private void createColumns(TableView table) {
        quantityColumn = new TableColumn();
        nameColumn = new TableColumn();
        kcalColumn = new TableColumn("kcal");
        proteinsColumn = new TableColumn("Prot.");
        carbsColumn = new TableColumn("Carbs");
        fatsColumn = new TableColumn("Fats");
        WIColumn = new TableColumn("WI*");
        priceColumn = new TableColumn("Price");

        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("unitQuantity"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        kcalColumn.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        proteinsColumn.setCellValueFactory(new PropertyValueFactory<>("proteins"));
        carbsColumn.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        fatsColumn.setCellValueFactory(new PropertyValueFactory<>("fats"));
        WIColumn.setCellValueFactory(new PropertyValueFactory<>("wholesomenessIndex"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        quantityColumn.setMinWidth(85);
        nameColumn.setMinWidth(120);
        kcalColumn.setMaxWidth(53);
        proteinsColumn.setMaxWidth(50);
        carbsColumn.setMaxWidth(50);
        fatsColumn.setMaxWidth(50);
        WIColumn.setMaxWidth(50);
        priceColumn.setMaxWidth(50);

        quantityColumn.setResizable(false);
        nameColumn.setResizable(false);
        kcalColumn.setResizable(false);
        proteinsColumn.setResizable(false);
        carbsColumn.setResizable(false);
        fatsColumn.setResizable(false);
        WIColumn.setResizable(false);
        priceColumn.setResizable(false);

        table.getColumns().addAll(quantityColumn, nameColumn, kcalColumn, proteinsColumn, carbsColumn, fatsColumn, WIColumn, priceColumn);
    }

    public void insertRow(ObservableList<Product> productsList, Product product, Float quantity, TableView table) {
        table.getItems().clear();
        Product productCopy = new Product(product.getName(), product.getKcal(), product.getProteins(), product.getCarbs(), product.getFats(), product.getMacronutrientCategory(), product.getCategory(), product.getWholesomenessIndex(), product.getUnitType(), product.getUnitQuantity(), product.getPrice());
        productCopy.setUnitQuantity(quantity + " " + product.getShorterUnit());
        productCopy.setKcal(Helper.twoDecimalsFloat(product.getKcal() * quantity));
        productCopy.setProteins(Helper.twoDecimalsFloat(product.getProteins() * quantity));
        productCopy.setCarbs(Helper.twoDecimalsFloat(product.getCarbs() * quantity));
        productCopy.setFats(Helper.twoDecimalsFloat(product.getFats() * quantity));
        productCopy.setPrice(Helper.twoDecimalsFloat(product.getPrice() * quantity));
        productCopy.setQuantity(quantity);
        productsList.remove(product);
        productsList.add(productCopy);
        table.getItems().addAll(productsList);
    }

    private void addProductButtonOnAction() {
        addProductController.MTIndex = getMealNum();
        addProductController.showStage();
    }

    private void deleteProductButtonOnAction() {
        Product selectedProduct = (Product) tableContent.getSelectionModel().getSelectedItem();
        if (meal.productsList.contains(selectedProduct)) {
            tableContent.getItems().remove(selectedProduct);
            meal.productsList.remove(selectedProduct);
            mealTableSummary.update(meal);
        }
    }

    private void deleteMealButtonOnAction(MealTablesContainer mealTablesContainer) {
        mealTablesContainer.vBox.getChildren().removeAll(this.tableContainer, this.mealTableSummary.tableSummary, this.mealNameContainer);
        mealTablesContainer.mealTablesList.remove(this);
        dietViewController.user.mealsList.remove(this.meal);
    }

}
