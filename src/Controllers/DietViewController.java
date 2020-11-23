package Controllers;

import Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
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
    @FXML Label dietNameLabel;
    @FXML Pane dietViewPane;
    public MealTable meal1Table, meal2Table, meal3Table, meal4Table, meal5Table;
    //public ArrayList<MealTable> mealTableList = new ArrayList<>();
    AddProductController addProductController = new AddProductController(this);
    public MealTablesContainer mealTablesContainer = new MealTablesContainer(this, 0, 0, 10, 6);

    public DietViewController(SelectProfileController selectProfileController) {
        thisStage = new Stage();
        this.selectProfileController = selectProfileController;
        mealTablesContainer.create();
        ChildrenWindow dietViewWindow = new ChildrenWindow();
        dietViewWindow.create("../fxml/dietView.fxml", this, thisStage, false, 1600, 900);
        String username = selectProfileController.getUsername();
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
        //        dietViewPane.getChildren().add(mealTablesContainer.vBox);
    }

    public void showStage(){
        thisStage.showAndWait();
    }

    public void addMealButtonOnAction() {
        if (mealTablesContainer.mealsList.size() == 0) {
            meal1Table = new MealTable(1, 1, 1, mealTablesContainer, addProductController); // just sample arguments to initiate, corrected in function
            createMealTable(meal1Table);
        }
        else if (mealTablesContainer.mealsList.size() == 1) {
            meal2Table = new MealTable(1, 1, 1, mealTablesContainer, addProductController);
            createMealTable(meal2Table);
        }
        else if (mealTablesContainer.mealsList.size() == 2) {
            meal3Table = new MealTable(1, 1, 1, mealTablesContainer, addProductController);
            createMealTable(meal3Table);
        }
        else if (mealTablesContainer.mealsList.size() == 3) {
            meal4Table = new MealTable(1, 1, 1, mealTablesContainer, addProductController);
            createMealTable(meal4Table);
        }
        else if (mealTablesContainer.mealsList.size() == 4) {
            meal5Table = new MealTable(1, 1, 1, mealTablesContainer, addProductController);
            createMealTable(meal5Table);
        }
    }

    private void createMealTable(MealTable mealTable) {
        mealTable.setMealNum(mealTablesContainer.mealsList.size() + 1);
        mealTable.create();
        mealTablesContainer.mealsList.add(mealTable.productsList);
        mealTablesContainer.vBox.getChildren().add(mealTable.tableContainer);
        System.out.println(mealTablesContainer.vBox.getChildren());
    }

    public void testButtonOnAction() {

    }
    
}