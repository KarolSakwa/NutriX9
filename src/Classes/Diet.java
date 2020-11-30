package Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Diet {

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection con = databaseConnection.getConnection();

    private ResultSet dietData;
    private String query;
    Statement statement;
    public String name, dietUsername, dietType;
    public Float kcal, proteins, carbs, fats;

    public Diet(Connection con, String dietName, String username) throws SQLException {
        query = "SELECT * from diets WHERE diet_name = '" + dietName + "' AND username = '" + username + "';";
        statement = con.createStatement();
        dietData = statement.executeQuery(query);
        while (dietData.next()) {
            name = dietData.getString("diet_name");
            dietUsername = dietData.getString("username");
            dietType = dietData.getString("diet_type");
            kcal = dietData.getFloat("kcal");
            proteins = dietData.getFloat("proteins");
            carbs = dietData.getFloat("carbs");
            fats = dietData.getFloat("fats");

        }
    }
}
