package Controllers;

import Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.annotation.Generated;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DietViewController {
    private Stage thisStage;
    private final SelectProfileController selectProfileController;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    @FXML Group meal1Header, meal1Row1;
    @FXML ImageView minus1ImageView;
    @FXML Label dietNameLabel, meal1Label, product1Label, quantity1Label, kcal1Label, proteins1Label, carbs1Label, fats1Label, WI1Label;
    @FXML Button meal1AddProductButton;
    @FXML Pane dietViewPane;
    public MealTable meal1Table, meal2Table, meal3Table, meal4Table, meal5Table;
    public ObservableList<ObservableList> mealsList = FXCollections.observableArrayList();
    public ArrayList<MealTable> mealTableList = new ArrayList<>();
    AddProductController addProductController = new AddProductController(this);
    public MealTablesContainer mealTablesContainer = new MealTablesContainer(this, 300, 100, 300, 400);
    Separator separator;
    HBox tablesContainer;
    public DailySummary dailySummary = new DailySummary(mealTablesContainer);


    public DietViewController(SelectProfileController selectProfileController) {
        this.selectProfileController = selectProfileController;
        thisStage = new Stage();
        ChildrenWindow dietViewWindow = new ChildrenWindow();
        dietViewWindow.create("../fxml/dietView.fxml", this, thisStage, false, 1680, 1050);
        String username = selectProfileController.getUsername();
        mealTablesContainer.create();
        dailySummary.create();
        tablesContainer = new HBox();
        tablesContainer.setMinHeight(300);
        tablesContainer.setMinWidth(300);
        tablesContainer.setLayoutX(500);
        tablesContainer.setLayoutY(100);
        separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setMinWidth(50);

        tablesContainer.getChildren().addAll(mealTablesContainer.vBox, separator, dailySummary.dailySummaryContainer);
        dietViewPane.getChildren().add(tablesContainer);
        String query = "SELECT * FROM diets WHERE username = '" + username + "';";
        try {
            Statement statement = con.createStatement();
            ResultSet usersDiet = statement.executeQuery(query);
            usersDiet.next();
            dietNameLabel.setText(usersDiet.getString("diet_name"));
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void showStage(){
        thisStage.showAndWait();
    }

    public void addMealButtonOnAction() {
        if (mealTablesContainer.mealsList.size() == 0) {
            meal1Table = new MealTable(this,1, 1, mealTablesContainer, addProductController); // just sample arguments to initiate, corrected in function
            createMealTable(meal1Table);
        }
        else if (mealTablesContainer.mealsList.size() == 1) {
            meal2Table = new MealTable(this,1, 1, mealTablesContainer, addProductController);
            createMealTable(meal2Table);
        }
        else if (mealTablesContainer.mealsList.size() == 2) {
            meal3Table = new MealTable(this,1, 1, mealTablesContainer, addProductController);
            createMealTable(meal3Table);
        }
        else if (mealTablesContainer.mealsList.size() == 3) {
            meal4Table = new MealTable(this,1, 1, mealTablesContainer, addProductController);
            createMealTable(meal4Table);
        }
        else if (mealTablesContainer.mealsList.size() == 4) {
            meal5Table = new MealTable(this,1, 1, mealTablesContainer, addProductController);
            createMealTable(meal5Table);
        }
    }

    private void createMealTable(MealTable mealTable) {
        mealTable.create();
        mealTablesContainer.mealsList.add(mealTable.productsList);
        mealTablesContainer.mealTablesList.add(mealTable);
    }

    public void testButtonOnAction() {
        System.out.println(mealTablesContainer.mealsList);
    }

}