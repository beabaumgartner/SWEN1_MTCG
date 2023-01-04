package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.exception.*;
import at.fhtw.mtcgapp.model.Card;
import at.fhtw.sampleapp.model.Weather;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PackageRepository {
    private UnitOfWork unitOfWork;
    public PackageRepository(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public UnitOfWork getUnitOfWork ()
    {
        return this.unitOfWork;
    }

    public void createPackage(Card cards[])
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                INSERT INTO Package (package_id) VALUES(DEFAULT) RETURNING package_id;
                """))
        {
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int package_id = resultSet.getInt(1);

            for (Card card : cards)
            {
                createCardsForPackage(card);
                createPackageCards(package_id, card.getCard_id());
            }


        } catch (SQLException e) {
            throw new DataAccessException("Create Package could not be executed", e);
        }
    }

    public void createPackageCards(Integer package_id, String card_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                INSERT INTO Package_Cards (package_id, card_id) VALUES(?, ?);
                """))
        {
            preparedStatement.setInt(1, package_id);
            preparedStatement.setString(2, card_id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Create Package could not be executed", e);
        }
    }

    public void createCardsForPackage(Card card)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                INSERT INTO Cards (card_id, card_name, damage) VALUES(?, ?, ?);
                """))
        {
            preparedStatement.setString(1, card.getCard_id());
            preparedStatement.setString(2, card.getName());
            preparedStatement.setInt(3, card.getDamage());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                throw new ConstraintViolationException("Card for Package already exists");
            }
            else
            {
                throw new DataAccessException("Create Card for Package could not be executed", e);
            }
        }
    }

    public Integer choosePackage()
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                SELECT * FROM Package WHERE user_id IS NULL LIMIT 1;
                """))
        {
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                throw new NoDataException("No card package available for buying");
            }

            return resultSet.getInt("package_id");

        } catch (SQLException e) {
            throw new DataAccessException("Choose Package could not be executed", e);
        }
    }


    public void acquireCardPackage(Integer package_id, Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                          UPDATE Package
                          SET user_id = ?
                          WHERE package_id = ?;
                             """))
        {
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, package_id);
            int updatedRow = preparedStatement.executeUpdate();

            if(updatedRow < 1)
            {
                throw new DataUpdateException("Package could not be updated");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Acquire Package could not be executed", e);
        }
    }

    public void UpdateCoinsByUserId(Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    UPDATE Users
                    SET coins = ?
                    WHERE user_id = ?;
                """))
        {
            int coins = GetCoinsByUserId(user_id);

            if(coins < 1)
            {
                throw new NotEnoughItemsException("Not enough money for buying a card package");
            }

            preparedStatement.setInt(1, (coins - 5));
            preparedStatement.setInt(2, user_id);
            int updatedRow = preparedStatement.executeUpdate();

            if(updatedRow < 1)
            {
                throw new DataUpdateException("Coins could not be updated");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Update Coins could not be executed", e);
        }
    }

    public Integer GetCoinsByUserId(Integer user_id)
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
                throw new NoDataException("No user with id " + user_id + " found!");
            }

            return resultSet.getInt("coins");

        } catch (SQLException e) {
            throw new DataAccessException("Get Coins from User could not be executed", e);
        }
    }
}
