package at.fhtw.mtcgapp.service.user;

import at.fhtw.mtcgapp.model.User;
import at.fhtw.sampleapp.model.Weather;

import java.sql.*;
import java.util.Optional;

public class UserDAL {

    public static void addUser(User user)
    {
        try(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DB_MTCG", "postgres", "tolpi222");
            PreparedStatement statement = connection.prepareStatement("""
            INSERT INTO Users
            (name, coins, password)
            VALUES (?, ?, ?);
            """)
        ) {
            statement.setString(1, user.getName());
            statement.setInt(2, user.getCoins());
            statement.setString(3, user.getPassword());

        } catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    /*public static String getUser()
    {
        return "huh";
    }*/

    private static String url = "jdbc:postgresql://localhost:5432/DB_MTCG";
    private static String user = "postgres";
    private static String password = "tolpi222";
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static User getUser(Integer id)
    {
        //int id = 1;
        String SQL = "SELECT * FROM Users WHERE user_id=?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);)
        {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                System.out.println(rs.getString("user_id")+ rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("es geht nicht");
            throw new RuntimeException(e);
        }

        return null;
    }
}
