package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.exception.DataAccessException;
import at.fhtw.mtcgapp.exception.DataUpdateException;
import at.fhtw.mtcgapp.exception.NoDataException;
import at.fhtw.mtcgapp.exception.NotEnoughItemsException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StackRepository {
    private UnitOfWork unitOfWork;
    public StackRepository(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public UnitOfWork getUnitOfWork ()
    {
        return this.unitOfWork;
    }

    public Integer createStack(Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
               INSERT INTO Stack (stack_id, user_id) VALUES(DEFAULT, ?) RETURNING stack_id;
                """))
        {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new DataAccessException("Create Stack could not be executed", e);
        }
    }

    public Integer getStackIdByUserId(Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
               SELECT * FROM Stack WHERE user_id = ?;
                """))
        {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                //if no Stack exists, create new STack and return id
               return createStack(user_id);
            }
            else
            {
                return resultSet.getInt("stack_id");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Get Stack-id could not be executed", e);
        }
    }

    public void createStackCards(Integer stack_id, String card_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
               INSERT INTO Stack_Cards (stack_id, card_id) VALUES(?, ?);
                """))
        {
            preparedStatement.setInt(1, stack_id);
            preparedStatement.setString(2, card_id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Create Stack-Cards could not be executed", e);
        }
    }
}
