package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.exception.*;
import at.fhtw.mtcgapp.model.TradingDeal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class TradingRepository {
    private UnitOfWork unitOfWork;
    public TradingRepository(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public UnitOfWork getUnitOfWork ()
    {
        return this.unitOfWork;
    }

    public void creatTradingDeal(TradingDeal tradingDeal) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                INSERT INTO Trading (trading_id, type_card, minimum_damage) VALUES (?, ?, ?)
                """))
        {
            preparedStatement.setString(1, tradingDeal.getTrading_id());
            preparedStatement.setString(2, tradingDeal.getType());
            preparedStatement.setInt(3, tradingDeal.getMinimum_damage());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                throw new ConstraintViolationException("A deal with this deal ID already exists.");
            }
            else
            {
                throw new DataAccessException("Create Trading-Deal could not be executed", e);

            }
        }
    }

    public void updateCardForTradingDeal(TradingDeal tradingDeal, Integer user_id) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                   UPDATE Cards
                        SET trading_id = ?
                        WHERE user_id = ? AND card_id = ? AND deck_id IS NULL AND trading_id IS NULL;
                """))
        {
            preparedStatement.setString(1, tradingDeal.getTrading_id());
            preparedStatement.setInt(2, user_id);
            preparedStatement.setString(3, tradingDeal.getCard_to_trade());
            int updatedRows = preparedStatement.executeUpdate();

            if(updatedRows < 1)
            {
                throw new InvalidItemException("The deal contains a card that is not owned by the user or locked in the deck.");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Update Cards for Trading-Deal could not be executed", e);
        }
    }

    public Collection<TradingDeal> getTradingDeals()
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                        SELECT Cards.trading_id, Cards.card_id, Trading.type_card, Trading.minimum_damage
                            FROM Trading JOIN Cards
                            ON Trading.trading_id = Cards.trading_id;
                             """))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<TradingDeal> tradingDeals = new ArrayList<>();

            while(resultSet.next())
            {
                TradingDeal tradingDeal = new TradingDeal(
                        resultSet.getString("trading_id"),
                        resultSet.getString("card_id"),
                        resultSet.getString("type_card"),
                        resultSet.getInt("minimum_damage"));
                tradingDeals.add(tradingDeal);
            }

            if(tradingDeals.isEmpty())
            {
                throw new NoDataException("The request was fine, but there are no trading deals available");
            }

            return tradingDeals;

        } catch (SQLException e) {
            throw new DataAccessException("Get Trading-Deals could not be executed", e);
        }
    }

    public void deleteTradingDeal(String trading_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                         DELETE FROM Trading
                            WHERE trading_id = ?;
                             """))
        {
            preparedStatement.setString(1, trading_id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Delete Trading-Deal could not be executed", e);
        }
    }

    public void updateTradingDealCardForDelete(Integer user_id, String trading_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                        UPDATE Cards
                        SET trading_id = NULL
                        WHERE user_id = ? AND trading_id = ?;
                             """))
        {
            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, trading_id);
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows < 1)
            {
                throw new DataUpdateException("Update could not be executed");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Update Trading-Deal Card for Delete could not be executed", e);
        }
    }
}
