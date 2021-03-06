package Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Diet {

    private ResultSet dietData;
    private String query;
    Statement statement;
    public String name, dietUsername, dietType;
    public Float kcal, proteins, carbs, fats;

    public Diet(Connection con, String dietName, String username) {
        query = "SELECT * from diets WHERE diet_name = '" + dietName + "' AND username = '" + username + "';";
        try {
            statement = con.createStatement();
            dietData = statement.executeQuery(query);
            dietData.next();
            name = dietData.getString("diet_name");
            dietUsername = dietData.getString("username");
            dietType = dietData.getString("diet_type");
            kcal = dietData.getFloat("kcal");
            proteins = dietData.getFloat("proteins");
            carbs = dietData.getFloat("carbs");
            fats = dietData.getFloat("fats");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
