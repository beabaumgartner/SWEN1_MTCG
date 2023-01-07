package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.mtcgapp.dal.DatabaseConnection;
import at.fhtw.mtcgapp.exception.*;
import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.model.Card;
import at.fhtw.mtcgapp.model.User;
import at.fhtw.mtcgapp.model.UserCredentials;
import at.fhtw.mtcgapp.model.UserData;
import at.fhtw.sampleapp.model.Weather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
                SELECT name, bio, image from Users Where username = ?
                """))
        {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                throw new NoDataException("User not found or no userdata exist.");
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

    public Integer getUserByCardId(String card_id) {
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

