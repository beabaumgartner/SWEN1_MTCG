package at.fhtw.mtcgapp.service.user;

import at.fhtw.mtcgapp.dal.DatabaseConnection;
import at.fhtw.mtcgapp.model.UserCredentials;
import at.fhtw.mtcgapp.model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDummyDAL {

    /*private static String url = "jdbc:postgresql://localhost:5432/DB_MTCG";
    private static String user = "postgres";
    private static String password = "postgres";
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }*/

    //private static Connection connection = DatabaseConnection.getInstance().getConnection();


    public static void addUser(UserCredentials user) throws SQLException {
        try(Connection connection = DatabaseConnection.getInstance().getConnection();)
        {
            connection.setAutoCommit(false);
            String SQL = "INSERT INTO Users (username, password) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());

                preparedStatement.executeUpdate();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                // When some exception occurs rollback the transaction.
                connection.rollback();
                throw e;
            }
        }
        catch (SQLException e)
        {
            throw e;
        }
    }

    public static UserData getUserDataByUsername(String username) throws SQLException {
        try(Connection connection = DatabaseConnection.getInstance().getConnection();)
        {
            connection.setAutoCommit(false);
            String SQL = "SELECT name, bio, image from Users Where username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setString(1, username);
                ResultSet rs = preparedStatement.executeQuery();
                if(rs == null)
                {

                }
                while(rs.next())
                {
                    UserData userData = new UserData(rs.getString("name"), rs.getString("bio"), rs.getString("image"));
                    return userData;
                }

                connection.commit();
                connection.close();

            } catch (SQLException exception) {
                // When some exception occurs rollback the transaction.
                //connection.rollback();
                throw exception;
            }
        }
        catch (SQLException e)
        {
            throw e;
        }

        return null;
    }
    /*public static String getUser()
    {
        return "huh";
    }*/
    public static Integer updateUser(String username, UserData userData) throws SQLException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();)
        {
            connection.setAutoCommit(false);
            String SQL = "UPDATE USERS " +
                         "SET name = ?,\n" +
                         "    bio = ?,\n" +
                         "    image = ?\n" +
                         "    WHERE username = ?";



            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setString(1, userData.getName());
                preparedStatement.setString(2, userData.getBio());
                preparedStatement.setString(3, userData.getImage());
                preparedStatement.setString(4, username);

                int updatedRows = preparedStatement.executeUpdate();
                connection.commit();
                connection.close();

                return updatedRows;

            } catch (SQLException e) {
                // When some exception occurs rollback the transaction.
                connection.rollback();
                throw e;
            }
        }
        catch (SQLException e)
        {
            throw e;
        }
    }

    /*public static User getUser(Integer id)
    {
        //int id = 1;
        String SQL = "SELECT * FROM Users WHERE user_id=?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
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
    }*/
}

//in UOW mit new erstellen
//im commit closen, im Controller UOW mitgeben in einemAUfruf (entweder pro controller oder bei jeder methode)
//eigene Methode mit finish der Controller muss sich dann drum kümmern um die Verbindung zu schließen (die man von außen steuern kann)
//Bei 204 darf kein body mitkommen und ignoriert diesen
//Zuerst im Controller eine unit of work machen, diese macht extra eine connection (pro Thread eine UOW)
// dann nach dem commit kann man über UOW ein close connection machen
//eine classe pro exceptions
//singelton als enum ist stabiler
//außerhalb nur noch selbst geschriebene exceptions