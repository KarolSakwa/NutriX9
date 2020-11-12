package Controllers;

import Classes.ChildrenWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Classes.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DietViewController {
    private Stage thisStage;
    private final SelectProfileController selectProfileController;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    @FXML Label dietNameLabel, meal1Label, product1Label;
    @FXML Button addMealButton, addProductButton;

    public DietViewController(SelectProfileController selectProfileController) {
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
        meal1Label.setVisible(true);
        addProductButton.setVisible(true);
    }

    public void addProductButtonOnAction() {
        AddProductController addProductController = new AddProductController(this);
        addProductController.showStage();
    }

    
}