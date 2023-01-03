package at.fhtw.mtcgapp.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;
    private static DatabaseConnection dbIsntance;

    public static DatabaseConnection getInstance()
    {
        if(dbIsntance == null)
        {
            dbIsntance = new DatabaseConnection();
        }
        return dbIsntance;
    }

    public Connection getConnection()
    {
        if(connection == null)
        {
            try
            {
                String url = "jdbc:postgresql://localhost:5432/DB_MTCG";
                String user = "postgres";
                String password = "postgres";

                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
                //System.err.println("Cannot connect to database");
            }
        }

        return connection;
    }
}
