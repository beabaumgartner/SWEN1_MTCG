package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.exception.DataAccessException;
import at.fhtw.mtcgapp.exception.DataUpdateException;
import at.fhtw.mtcgapp.exception.InvalidItemException;
import at.fhtw.mtcgapp.model.Card;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
               INSERT INTO Deck (deck_id) VALUES(DEFAULT) RETURNING deck_id;
                """))
        {
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            //return deck_id
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new DataAccessException("Create Deck could not be executed", e);
        }
    }

    public void updateCardsForDeck(Integer user_id, Integer deck_id, String card_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                   UPDATE Cards
                        SET deck_id = ?
                        WHERE user_id = ? AND card_id = ?;
                """))
        {
            preparedStatement.setInt(1, deck_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setString(3, card_id);
            int updatedRows = preparedStatement.executeUpdate();

            if(updatedRows < 1)
            {
                throw new InvalidItemException("At least one of the provided cards does not belong to the user or is not available.");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Update Cards for Deck could not be executed", e);
        }
    }

    public void updateOldCardDeck(Integer user_id, Integer deck_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                        UPDATE Cards
                        SET deck_id = NULL
                        WHERE user_id = ? AND deck_id != ?;
                             """))
        {
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, deck_id);

        } catch (SQLException e) {
            throw new DataAccessException("Update Old-Cards for Deck Deck could not be executed", e);
        }
    }

    public Integer getDeckIdByUserId(Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
               SELECT card_id, card_name, damage From Cards WHERE user_id = ? AND deck_id IS NOT NULL;
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

    /*public void createDeckCards(Integer deck_id, String card_id)
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
    }*/
}
