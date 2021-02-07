package Controllers;

import Classes.*;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DietViewController {
    private Stage thisStage;
    public final SelectProfileController selectProfileController;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();
    Label dietNameLabel;
    @FXML Pane dietViewPane;
    public AddProductController addProductController = new AddProductController(this);
    public MealTablesContainer mealTablesContainer = new MealTablesContainer(this);
    public Separator separator;
    HBox tablesContainer, topBar;
    public DailySummary dailySummary;
    public Diet diet;
    public User user;
    ImageView dietImage;
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
        styleTablesContainer();
        diet = Helper.getDiet(con, username);
        dailySummary.create();
        styleTopBar();
        tablesContainer.getChildren().addAll(mealTablesContainer.vBox, separator, dailySummary.dailySummaryContainer);
        dietViewPane.setStyle("-fx-background-color: #fff;");
        dietViewPane.getChildren().addAll(tablesContainer, topBar);
    }

    public void showStage() {
        thisStage.show();
    }

    private void styleTablesContainer() {
        tablesContainer = new HBox();
        tablesContainer.setMinHeight(950);
        tablesContainer.setMinWidth(700);
        tablesContainer.setLayoutX(600);
        tablesContainer.setLayoutY(110);
        separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setMinWidth(50);
        separator.setVisible(false);
    }

    private void styleTopBar() {
        topBar = new HBox(20);
        topBar.setMinHeight(60);
        topBar.setMinWidth(WINDOWWIDTH);
        topBar.setStyle("-fx-background-color: " + MAINCOLOR);
        topBar.setLayoutX(0);
        topBar.setLayoutY(0);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setBorder(new Border(new BorderStroke(Color.rgb(0, 0, 0, 0.1), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        dietNameLabel = new Label(diet.name);
        dietNameLabel.getStyleClass().add("dietNameLabel");
        dietImage = new ImageView("/img/diet.png");
        dietImage.setFitHeight(60);
        dietImage.setFitWidth(60);

        topBar.getChildren().addAll(dietNameLabel, dietImage);
        dietNameLabel.toFront();
        dietImage.toBack();
    }

}