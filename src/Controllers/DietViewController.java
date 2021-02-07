package Controllers;

import Classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;

public class DietViewController {
    private Stage thisStage;
    public final SelectProfileController selectProfileController;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    public Label dietNameLabel;
    @FXML Pane dietViewPane;
    public AddProductController addProductController = new AddProductController(this);
    public MealTablesContainer mealTablesContainer = new MealTablesContainer(this);
    public Separator separator;
    public HBox tablesContainer, topBar;
    public DailySummary dailySummary;
    public Diet diet;
    public User user;
    public ImageView dietImage;
    public final String MAINCOLOR = "#99deee";
    public final Integer WINDOWHEIGHT = 1020, WINDOWWIDTH = 1680;

    public DietViewController(SelectProfileController selectProfileController) {
        String username = selectProfileController.getUsername();
        user = new User(con, username);
        dailySummary = new DailySummary(this);
        this.selectProfileController = selectProfileController;
        thisStage = new Stage();
        ChildrenWindow.create("../fxml/dietView.fxml", this, thisStage, false, WINDOWWIDTH, WINDOWHEIGHT);
        //thisStage.initStyle(StageStyle.DECORATED); // DEBUG
        mealTablesContainer.create();
        Helper.styleTablesContainer(this);
        diet = Helper.getDiet(con, username);
        dailySummary.create();
        Helper.styleDietViewTopBar(this, diet);
        tablesContainer.getChildren().addAll(mealTablesContainer.vBox, separator, dailySummary.dailySummaryContainer);
        dietViewPane.setStyle("-fx-background-color: #fff;");
        dietViewPane.getChildren().addAll(tablesContainer, topBar);
    }

    public void showStage() {
        thisStage.show();
    }

}