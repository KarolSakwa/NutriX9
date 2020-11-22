package Controllers;

import Classes.ChildrenWindow;
import Classes.MealTable;
import Classes.Product;
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
import Classes.DatabaseConnection;

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
    ObservableList<ObservableList> mealsList = FXCollections.observableArrayList();
    public ArrayList<MealTable> mealTableList = new ArrayList<>();
    AddProductController addProductController = new AddProductController(this);

    public DietViewController(SelectProfileController selectProfileController) {
        this.selectProfileController = selectProfileController;
        thisStage = new Stage();
        ChildrenWindow dietViewWindow = new ChildrenWindow();
        dietViewWindow.create("../fxml/dietView.fxml", this, thisStage, false, 1600, 1200);
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
    }

    public void showStage(){
        thisStage.showAndWait();
    }

    public void addMealButtonOnAction() {
        if (mealsList.size() == 0) {
            meal1Table = new MealTable(1, 1, 1, addProductController); // just sample arguments to initiate, corrected in function
            createMealTable(meal1Table);
        }
        else if (mealsList.size() == 1) {
            meal2Table = new MealTable(1, 1, 1, addProductController);
            createMealTable(meal2Table);
        }
        else if (mealsList.size() == 2) {
            meal3Table = new MealTable(1, 1, 1, addProductController);
            createMealTable(meal3Table);
        }
        else if (mealsList.size() == 3) {
            meal4Table = new MealTable(1, 1, 1, addProductController);
            createMealTable(meal4Table);
        }
        else if (mealsList.size() == 4) {
            meal5Table = new MealTable(1, 1, 1, addProductController);
            createMealTable(meal5Table);
        }
    }

    private void createMealTable(MealTable mealTable) {
        mealTable.setMealNum(mealsList.size() + 1);
        mealTable.setTableContainerLayoutX(100);
        mealTable.setTableContainerLayoutY((mealsList.size() + 1) * 210);
        mealTable.create(dietViewPane);
        mealsList.add(mealTable.productsList);
    }

    public void testButtonOnAction() {

    }
    
}