package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.exception.DataAccessException;
import at.fhtw.mtcgapp.exception.NoDataException;
import at.fhtw.mtcgapp.exception.NotFoundException;
import at.fhtw.mtcgapp.model.Card;
import at.fhtw.mtcgapp.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class CardRepository {
    private UnitOfWork unitOfWork;
    public CardRepository(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public UnitOfWork getUnitOfWork ()
    {
        return this.unitOfWork;
    }

    public Collection<Card> getAllCardsFromUser(Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                       SELECT card_id, card_name, damage From Cards WHERE user_id = ?;
                """))
        {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<Card> userCards = new ArrayList<>();

            while(resultSet.next())
            {
                Card card = new Card(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                userCards.add(card);
            }

            if(userCards.isEmpty())
            {
                throw new NoDataException("The request was fine, but the user doesn't have any cards");
            }

            return userCards;


        } catch (SQLException e) {
            throw new DataAccessException("Create Package could not be executed", e);
        }
    }

    public Collection<Card> getAllDeckCardsFromUser(Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                       SELECT card_id, card_name, damage From Cards WHERE user_id = ? AND deck_id IS NOT NULL;
                """))
        {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<Card> userCards = new ArrayList<>();

            while(resultSet.next())
            {
                Card card = new Card(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                userCards.add(card);
            }

            if(userCards.isEmpty())
            {
                throw new NoDataException("The request was fine, but the deck doesn't have any cards");
            }

            return userCards;


        } catch (SQLException e) {
            throw new DataAccessException("Create Package could not be executed", e);
        }
    }

    public Card getCardByCardId(String card_id) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                             SELECT Cards.card_id, Cards.card_name, Cards.damage
                             FROM Cards
                                WHERE card_id = ?
                                AND deck_id IS NULL
                                AND trading_id IS NULL;
                                      """)) {
            preparedStatement.setString(1, card_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new NotFoundException("No Card with Card-Id: " + card_id + " found");
            }

            Card card = new Card(
                    resultSet.getString("card_id"),
                    resultSet.getString("card_name"),
                    resultSet.getInt("damage")
            );

            return card;

        } catch (SQLException e) {
            throw new DataAccessException("Get Card by Card-Id could not be executed", e);
        }
    }
}
