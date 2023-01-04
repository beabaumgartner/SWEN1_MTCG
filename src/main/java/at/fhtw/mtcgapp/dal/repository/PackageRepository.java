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
        System.out.println("bin hier 1");
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                INSERT INTO Package (package_id) VALUES(DEFAULT) RETURNING package_id;
                """))
        {
            System.out.println("bin hier 2");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("bin hier 3");


            resultSet.next();
            int package_id = resultSet.getInt(1);
            System.out.println("bin hier 4");

            for (Card card : cards)
            {
                System.out.println("bin hier 5");
                System.out.println("card: " + card.getCard_id());
                System.out.println("bin hier 6");
                createCardsForPackage(card, package_id);
                System.out.println("bin hier 7");
            }


        } catch (SQLException e) {
            throw new DataAccessException("Create Package could not be executed", e);
        }
    }

    /*public void createPackageCards(Integer package_id, String card_id)
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
    }*/

    public void createCardsForPackage(Card card, Integer package_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                INSERT INTO Cards (card_id, card_name, damage, package_id) VALUES(?, ?, ?, ?);
                """))
        {
            preparedStatement.setString(1, card.getCard_id());
            preparedStatement.setString(2, card.getName());
            preparedStatement.setInt(3, card.getDamage());
            preparedStatement.setInt(4, package_id);
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
}
