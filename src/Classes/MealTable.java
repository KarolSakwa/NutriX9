package Classes;

import Controllers.AddProductController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class MealTable {
    VBox tableContainer;
    HBox mealNameContainer;
    Label mealName;
    public TableView<Product> tableContent;
    TableColumn quantityColumn, nameColumn, kcalColumn, proteinsColumn, carbsColumn, fatsColumn, WIColumn, priceColumn;
    Integer mealNum, tableContainerLayoutX, tableContainerLayoutY;
    private final Integer tableContainerWidth = 507;
    private final Integer tableContainerHeight = 110;
    public ArrayList<Product> productsList = new ArrayList<>();
    Button addProductButton, deleteProductButton, deleteMealButton;
    AddProductController addProductController;

    public MealTable(Integer mealNum, Integer tableContainerLayoutX, Integer tableContainerLayoutY, AddProductController addProductController) {
        this.mealNum = mealNum;
        this.tableContainerLayoutX = tableContainerLayoutX;
        this.tableContainerLayoutY = tableContainerLayoutY;
        this.addProductController = addProductController;
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

    public void create(Pane pane) {

        tableContainer = new VBox();
        tableContainer.setPrefWidth(tableContainerWidth);
        tableContainer.setMaxHeight(tableContainerHeight);
        tableContainer.setLayoutX(tableContainerLayoutX);
        tableContainer.setLayoutY(tableContainerLayoutY);
        mealNameContainer = new HBox();
        mealNameContainer.setAlignment(Pos.CENTER);
        mealName = new Label("Meal" + mealNum);
        addProductButton = new Button("Add product");
        addProductButton.setOnAction(e -> addProductButtonOnAction());
        deleteProductButton = new Button("Delete");
        deleteProductButton.setOnAction(e -> deleteProductButtonOnAction());
        deleteMealButton = new Button("DELETE MEAL");
        mealNameContainer.getChildren().addAll(deleteMealButton, mealName, addProductButton, deleteProductButton);
        mealNameContainer.setAlignment(Pos.CENTER_RIGHT);
        mealNameContainer.setMargin(addProductButton, new Insets(0, 10, 0, 0));
        mealNameContainer.setMargin(mealName, new Insets(0, 90, 0, 0));
        mealNameContainer.setMargin(deleteMealButton, new Insets(0, 150, 0, 0));

        tableContent = new TableView();
        tableContent.setMinHeight(147);
        tableContent.setMaxHeight(147);
        createColumns(tableContent);

        tableContainer.getChildren().addAll(mealNameContainer, tableContent);
        pane.getChildren().add(tableContainer);
    }

    private void createColumns(TableView table) {
        quantityColumn = new TableColumn();
        nameColumn = new TableColumn();
        kcalColumn = new TableColumn("kcal");
        proteinsColumn = new TableColumn("Proteins");
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
        kcalColumn.setMaxWidth(50);
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

    public void insertRow(ArrayList<Product> productsList, Product product, Double quantity, TableView table) {
        table.getItems().clear();
        Product productCopy = new Product(product.getName(), product.getKcal(), product.getProteins(), product.getCarbs(), product.getFats(), product.getMacronutrientCategory(), product.getCategory(), product.getWholesomenessIndex(), product.getUnitType(), product.getUnitQuantity(), product.getPrice());
        productCopy.setUnitQuantity(quantity + " " + product.getShorterUnit());
        productCopy.setKcal(product.getKcal() * quantity);
        productCopy.setProteins(product.getProteins() * quantity);
        productCopy.setCarbs(product.getCarbs() * quantity);
        productCopy.setFats(product.getFats() * quantity);
        productCopy.setPrice(product.getPrice() * quantity);
        productCopy.setQuantity(quantity);
        productsList.remove(product);
        productsList.add(productCopy);
        table.getItems().addAll(productsList);
    }

    private void addProductButtonOnAction() {
        addProductController.MTIndex = mealNum;
        addProductController.showStage();
    }

    private void deleteProductButtonOnAction() {
        Product selectedProduct = (Product) tableContent.getSelectionModel().getSelectedItem();
        if (productsList.contains(selectedProduct)) {
            tableContent.getItems().remove(selectedProduct);
            productsList.remove(selectedProduct);
            addProductController.mealTableSummary.update(this);
        }
        else {
            System.out.println("AHU");
        }

    }
}
