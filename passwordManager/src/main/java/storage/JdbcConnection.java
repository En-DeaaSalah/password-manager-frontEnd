package storage;
import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/passwordManagerClient";

    private static final String USER_NAME = "postgres";

    private static final String PASSWORD = "0000";


    public static Connection getConnection() {

        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return connection;

    }

}