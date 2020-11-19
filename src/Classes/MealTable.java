package Classes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MealTable {
    VBox tableContainer;
    HBox mealNameContainer;
    Label mealName;
    public TableView<Product> tableContent;
    TableColumn quantityColumn, nameColumn, kcalColumn, proteinsColumn, carbsColumn, fatsColumn, WIColumn, priceColumn;
    Integer mealNum, tableContainerLayoutX, tableContainerLayoutY;
    private final Integer tableContainerWidth = 560;
    private final Integer tableContainerHeight = 200;
    public ArrayList<Product> productsList = new ArrayList<>();
    public Map<Product, Double> productsDictionary = new HashMap<>(); // the easiest way to store information about specific product quantity

    Pane pane;

    public MealTable(Integer mealNum, Integer tableContainerLayoutX, Integer tableContainerLayoutY) {
        this.mealNum = mealNum;
        this.tableContainerLayoutX = tableContainerLayoutX;
        this.tableContainerLayoutY = tableContainerLayoutY;
    }

    public void create(Pane pane) {

        tableContainer = new VBox();
        tableContainer.setMaxWidth(tableContainerWidth);
        tableContainer.setMaxHeight(tableContainerHeight);
        tableContainer.setLayoutX(tableContainerLayoutX);
        tableContainer.setLayoutY(tableContainerLayoutY);

        mealNameContainer = new HBox();
        mealNameContainer.setAlignment(Pos.CENTER);
        mealName = new Label("Meal" + mealNum);
        mealNameContainer.getChildren().add(mealName);

        tableContent = new TableView();
        tableContent.setMinWidth(170);
        createColumns(tableContent);

        tableContainer.getChildren().addAll(mealNameContainer, tableContent);
        //MealTableSummary mealTableSummary = new MealTableSummary();
        //mealTableSummary.create(this);
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

        quantityColumn.setPrefWidth(90);
        nameColumn.setPrefWidth(120);
        kcalColumn.setPrefWidth(55);
        proteinsColumn.setPrefWidth(55);
        carbsColumn.setPrefWidth(55);
        fatsColumn.setPrefWidth(55);
        WIColumn.setPrefWidth(55);
        priceColumn.setPrefWidth(55);

        table.getColumns().addAll(quantityColumn, nameColumn, kcalColumn, proteinsColumn, carbsColumn, fatsColumn, WIColumn, priceColumn);
    }

    public void insertRow(Map<Product, Double> productsDictionary, ArrayList<Product> productsList, Product product, Double quantity, TableView table) {
        table.getItems().clear();
        ArrayList<Product> newProductsList = new ArrayList<>();
        Product productCopy = new Product(product.getName(), product.getKcal(), product.getProteins(), product.getCarbs(), product.getFats(), product.getMacronutrientCategory(), product.getCategory(), product.getWholesomenessIndex(), product.getUnitType(), product.getUnitQuantity(), product.getPrice());
        productCopy.setUnitQuantity(productsDictionary.get(product) + " " + product.getShorterUnit());
        productCopy.setKcal(product.getKcal() * productsDictionary.get(product));
        productCopy.setProteins(product.getProteins() * productsDictionary.get(product));
        productCopy.setCarbs(product.getCarbs() * productsDictionary.get(product));
        productCopy.setFats(product.getFats() * productsDictionary.get(product));
        productCopy.setPrice(product.getPrice() * productsDictionary.get(product));
        productCopy.setQuantity(quantity);
        productsList.remove(product);
        productsList.add(productCopy);
        table.getItems().addAll(productsList);
    }
    /*
    private void createProductCopy(Product product) {
        Product productCopy = new Product(product.getName(), product.getKcal(), product.getProteins(), product.getCarbs(), product.getFats(), product.getMacronutrientCategory(), product.getCategory(), product.getWholesomenessIndex(), product.getUnitType(), product.getUnitQuantity(), product.getPrice());
        productCopy.setUnitQuantity(productsDictionary.get(product) + " " + product.getShorterUnit());
        productCopy.setKcal(product.getKcal() * productsDictionary.get(product));
        productCopy.setProteins(product.getProteins() * productsDictionary.get(product));
        productCopy.setCarbs(product.getCarbs() * productsDictionary.get(product));
        productCopy.setFats(product.getFats() * productsDictionary.get(product));
        productCopy.setPrice(product.getPrice() * productsDictionary.get(product));
        productCopy.setQuantity(quantity);
    }


     */

}
