package Classes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "nutrix9";
        String databaseUser = "root";
        String databasePassword = "admin";
        String url = "jdbc:mysql://localhost:3306/" + databaseName + "?autoReconnect=true&useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }

    public void executeQuery(Connection con, String query){
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //public

}
