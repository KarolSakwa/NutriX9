package Controllers;

import Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DietViewController {
    private Stage thisStage;
    public final SelectProfileController selectProfileController;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    @FXML Label dietNameLabel;
    @FXML Pane dietViewPane;
    AddProductController addProductController = new AddProductController(this);
    public MealTablesContainer mealTablesContainer = new MealTablesContainer(this);
    public Separator separator;
    HBox tablesContainer;
    public DailySummary dailySummary;
    public Diet diet;
    public User user;


    public DietViewController(SelectProfileController selectProfileController) {
        String username = selectProfileController.getUsername();
        user = new User(con, username);
        dailySummary = new DailySummary(this);
        this.selectProfileController = selectProfileController;
        thisStage = new Stage();
        ChildrenWindow.create("../fxml/dietView.fxml", this, thisStage, false, 1680, 1050);
        mealTablesContainer.create();
        tablesContainer = new HBox();
        tablesContainer.setMinHeight(950);
        tablesContainer.setMinWidth(700);
        tablesContainer.setLayoutX(600);
        tablesContainer.setLayoutY(100);
        separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setMinWidth(50);
        separator.setVisible(false);
        String query = "SELECT * FROM diets WHERE username = '" + username + "';";
        try {
            Statement statement = con.createStatement();
            ResultSet usersDiet = statement.executeQuery(query);
            usersDiet.next();
            diet = new Diet(con, usersDiet.getString("diet_name"), username);
            dietNameLabel.setText(diet.name);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        dailySummary.create();
        tablesContainer.getChildren().addAll(mealTablesContainer.vBox, separator, dailySummary.dailySummaryContainer);
        dietViewPane.getChildren().add(tablesContainer);

    }

    public void showStage() {
        thisStage.show();
    }

    public void addMealButtonOnAction() {
        if (user.mealsList.size() < 4) {
            createMealTable();

        }
    }

    private void createMealTable() {
        Meal meal = new Meal();
        user.mealsList.add(meal);
        MealTable mealTable = new MealTable(meal, this,1, 1, mealTablesContainer, addProductController);
        mealTable.create();
        mealTablesContainer.mealTablesList.add(mealTable);
    }

    public void testButtonOnAction() {
    }

}