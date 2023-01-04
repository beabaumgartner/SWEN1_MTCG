package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeckRepository {
    private UnitOfWork unitOfWork;
    public DeckRepository(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public UnitOfWork getUnitOfWork ()
    {
        return this.unitOfWork;
    }

    public Integer createDeck(Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
               INSERT INTO Deck (deck_id, user_id) VALUES(DEFAULT, ?) RETURNING deck_id;
                """))
        {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            //return deck_id
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new DataAccessException("Create Deck could not be executed", e);
        }
    }

    public Integer getDeckIdByUserId(Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
               SELECT * FROM Deck WHERE user_id = ?;
                """))
        {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                //if no Deck exists, create new STack and return id
                return createDeck(user_id);
            }
            else
            {
                return resultSet.getInt("stack_id");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Get dec-id could not be executed", e);
        }
    }

    public void createDeckCards(Integer deck_id, String card_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
               INSERT INTO Deck_Cards (deck_id, card_id) VALUES(?, ?);
                """))
        {
            preparedStatement.setInt(1, deck_id);
            preparedStatement.setString(2, card_id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Create Deck-Cards could not be executed", e);
        }
    }
}
