package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.mtcgapp.exception.*;
import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.model.User;
import at.fhtw.mtcgapp.model.UserCredentials;
import at.fhtw.mtcgapp.model.UserData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private UnitOfWork unitOfWork;
    public UserRepository(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public UnitOfWork getUnitOfWork ()
    {
        return this.unitOfWork;
    }

    public void addUser(UserCredentials user) {
        try (PreparedStatement preparedStatement =
                 this.unitOfWork.prepareStatement("""
                INSERT INTO Users (username, password) VALUES (?, ?)
                """))
        {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                //e.printStackTrace();
                throw new ConstraintViolationException("User already exists");
            }
            else
            {
                throw new DataAccessException("Create User could not be executed", e);

            }
        }
    }

    public UserData getUserDataByUsername(String username) {
        try (PreparedStatement preparedStatement =
                 this.unitOfWork.prepareStatement("""
                SELECT name, bio, image FROM Users WHERE username = ?
                """))
        {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                throw new NoDataException("User not found or no userdata exists.");
            }

            UserData user = new UserData(
                    resultSet.getString("name"),
                    resultSet.getString("bio"),
                    resultSet.getString("image"));
            return user;

        } catch (SQLException e) {
            throw new DataAccessException("Select could not be executed", e);
        }
    }

    public String getUsernameByUserId(Integer user_id) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                SELECT username FROM Users WHERE user_id = ?
                """))
        {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                throw new NoDataException("User not found or no userdata exists.");
            }
            return resultSet.getString(1);

        } catch (SQLException e) {
            throw new DataAccessException("Get username could not be executed", e);
        }
    }

    public User getUserByUserId(Integer user_id) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                SELECT user_id, username from Users Where user_id = ?
                """))
        {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                throw new NoDataException("User not found by user-id or no userdata exist.");
            }

            User user = new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("username"),
                    null);
            return user;

        } catch (SQLException e) {
            throw new DataAccessException("Select could not be executed", e);
        }
    }

    public void updateUser(String username, UserData userData) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    UPDATE USERS
                    SET name = ?,
                    bio = ?,
                    image = ?
                    WHERE username = ?;
                             """))
        {
            preparedStatement.setString(1, userData.getName());
            preparedStatement.setString(2, userData.getBio());
            preparedStatement.setString(3, userData.getImage());
            preparedStatement.setString(4, username);

            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows < 1)
            {
                throw new DataUpdateException("Update could not be executed");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Update could not be executed", e);
        }
    }

    public Integer getUserIdByCardId(String card_id) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                             SELECT * FROM Cards
                                WHERE card_id = ?;
                                      """)) {
            preparedStatement.setString(1, card_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new NotFoundException("No User with Card-Id: " + card_id + " found");
            }

            Integer user_id = resultSet.getInt("user_id");

            return user_id;

        } catch (SQLException e) {
            throw new DataAccessException("Get User by Card-Id could not be executed", e);
        }
    }
}

