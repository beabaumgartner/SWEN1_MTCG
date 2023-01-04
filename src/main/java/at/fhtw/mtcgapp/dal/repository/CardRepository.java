package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.exception.DataAccessException;
import at.fhtw.mtcgapp.exception.NoDataException;
import at.fhtw.mtcgapp.model.Card;

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
                SELECT Cards.card_id, Cards.card_name, Cards.damage, Stack.user_id FROM Cards 
                        JOIN Stack_Cards 
                            ON Cards.card_id = Stack_Cards.card_id 
                        JOIN Stack 
                            ON Stack.stack_id = Stack_Cards.stack_id 
                        WHERE Stack.user_id = ?;
                """))
        {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<Card> userCards = new ArrayList<>();

            if(!resultSet.next())
            {
                throw new NoDataException("The request was fine, but the user doesn't have any cards");
            }
            while(resultSet.next())
            {
                Card card = new Card(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                userCards.add(card);
            }

            return userCards;


        } catch (SQLException e) {
            throw new DataAccessException("Create Package could not be executed", e);
        }
    }
}
