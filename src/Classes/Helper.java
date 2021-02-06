package Classes;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Helper {

    public static Float twoDecimalsFloat(Float number) {
        BigDecimal bigDecimal = new BigDecimal(number.toString());
        BigDecimal roundOff = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return roundOff.floatValue();
    }

    public static Diet getDiet(Connection con, String username) {
        String query = "SELECT * FROM diets WHERE username = '" + username + "';";
        try {
            Statement statement = con.createStatement();
            ResultSet usersDiet = statement.executeQuery(query);
            usersDiet.next();
            Diet diet = new Diet(con, usersDiet.getString("diet_name"), username);
            return diet;
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
}
