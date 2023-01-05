package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.exception.DataAccessException;
import at.fhtw.mtcgapp.exception.InvalidLoginDataException;
import at.fhtw.mtcgapp.exception.NoDataException;
import at.fhtw.mtcgapp.model.Card;
import at.fhtw.mtcgapp.model.UserCredentials;
import at.fhtw.mtcgapp.model.UserStats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class StatsRepository {
    private UnitOfWork unitOfWork;
    public StatsRepository(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public UnitOfWork getUnitOfWork ()
    {
        return this.unitOfWork;
    }

    public UserStats getStatsByUserId(Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                SELECT * FROM Users WHERE user_id = ?
                """))
        {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                throw new NoDataException("Stats not found");
            }

            UserStats userStat = new UserStats(
                    resultSet.getString("name"),
                    resultSet.getInt("elo"),
                    resultSet.getInt("wins"),
                    resultSet.getInt("losses"));

            return userStat;

        } catch (SQLException e) {
            throw new DataAccessException("Get User-Stats could not be executed", e);
        }
    }
}
