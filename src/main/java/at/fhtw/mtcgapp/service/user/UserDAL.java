package at.fhtw.mtcgapp.service.user;

import at.fhtw.mtcgapp.model.User;
import at.fhtw.mtcgapp.model.UserCredentials;
import at.fhtw.sampleapp.model.Weather;

import javax.sound.midi.Soundbank;
import java.sql.*;
import java.util.Optional;

public class UserDAL {

    private static String url = "jdbc:postgresql://localhost:5432/DB_MTCG";
    private static String user = "postgres";
    private static String password = "tolpi222";
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    public static void addUser(UserCredentials user)
    {
        String SQL = "INSERT INTO Users (username, password) VALUES (?, ?)";
        try(Connection connection = connect();//auto commid aussachlten ist bei default immer an
            //connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);)
            //PreparedStatement statement = connection.prepareStatement("""
           /* INSERT INTO Users
            (username, password)
            VALUES (?, ?);
            """)*/
        {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();

        } catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    /*public static String getUser()
    {
        return "huh";
    }*/

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
                System.out.println(rs.getString("user_id")+ rs.getString("username"));
            }
        } catch (SQLException e) {
            //System.out.println("es geht nicht");
            throw new RuntimeException(e);
        }

        return null;
    }
}
