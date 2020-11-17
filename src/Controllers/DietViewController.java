package Controllers;

import Classes.ChildrenWindow;
import Classes.MealTable;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Classes.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class DietViewController {
    private Stage thisStage;
    private final SelectProfileController selectProfileController;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    @FXML Group meal1Header, meal1Row1;
    @FXML ImageView minus1ImageView;
    @FXML Label dietNameLabel, meal1Label, product1Label, quantity1Label, kcal1Label, proteins1Label, carbs1Label, fats1Label, WI1Label;
    @FXML Button addMealButton, meal1AddProductButton;
    @FXML Pane dietViewPane;
    AddProductController addProductController = new AddProductController(this);
    public ArrayList<MealTable> mealsList = new ArrayList<>();

    public DietViewController(SelectProfileController selectProfileController) {
        //MealTable mealTable = new MealTable();
        this.selectProfileController = selectProfileController;
        thisStage = new Stage();
        ChildrenWindow dietViewWindow = new ChildrenWindow();
        dietViewWindow.create("../fxml/dietView.fxml", this, thisStage, false, 1038, 793);
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
        MealTable meal1Table = new MealTable();
        mealsList.add(meal1Table);


        //System.out.println(mealsList.size());
        meal1AddProductButton.setVisible(true);

    }

    public void meal1AddProductButtonOnAction() {
        addProductController.showStage();
    }

    public void minus1ImageViewOnAction() {
        System.out.println("ahu");
    }

    public void testButtonOnAction() {

    }
    
}