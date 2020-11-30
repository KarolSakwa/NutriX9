package Classes;

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

    public User(Connection con, String username) throws SQLException {
        query = "SELECT * from users WHERE username = '" + username + "';";
        statement = con.createStatement();
        userData = statement.executeQuery(query);
        while (userData.next()) {
            name = userData.getString("username");
            bodyType = userData.getString("body_type");
            trainingIntensity = userData.getString("training_intensity");
            age = userData.getInt("age");
            weight = userData.getInt("weight");
            height = userData.getInt("height");
            numberOfTrainings = userData.getInt("number_of_trainings");
            trainingLength = userData.getInt("training_length");
        }
    }
}
