package Classes;

import Controllers.DietViewController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();

    private ResultSet userData;
    private String query;
    Statement statement;
    public String name, bodyType, trainingIntensity;
    public Integer age, weight, height, numberOfTrainings, trainingLength;
    public ObservableList<Meal> mealsList = FXCollections.observableArrayList();
    public Float kcalConsumed = 0F, proteinsConsumed = 0F, carbsConsumed = 0F, fatsConsumed = 0F;

    public User(Connection con, String username) {
        query = "SELECT * from users WHERE username = '" + username + "';";
        try {
            statement = con.createStatement();
            userData = statement.executeQuery(query);
            userData.next();
            name = userData.getString("username");
            bodyType = userData.getString("body_type");
            trainingIntensity = userData.getString("training_intensity");
            age = userData.getInt("age");
            weight = userData.getInt("weight");
            height = userData.getInt("height");
            numberOfTrainings = userData.getInt("number_of_trainings");
            trainingLength = userData.getInt("training_length");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
