package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.httpserver.server.Request;
import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.exception.*;
import at.fhtw.mtcgapp.model.Card;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BattlesRepository {
    private UnitOfWork unitOfWork;
    public BattlesRepository(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public UnitOfWork getUnitOfWork ()
    {
        return this.unitOfWork;
    }

    public Boolean isBattleDone(Integer battle_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                SELECT * FROM Battle WHERE battle_id = ?
                """))
        {
            preparedStatement.setInt(1, battle_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next() == false)
            {
                throw new NotFoundException("Battle with battle id: " + battle_id + " not found");
            }

            return resultSet.getBoolean("battle_status");

        } catch (SQLException e) {
            throw new DataAccessException("Get Battle-Status could not be executed", e);
        }
    }

    public Boolean isLobbyEmpty()
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                SELECT COUNT(*) FROM Battle 
                WHERE user1_id IS NOT NULL 
                AND battle_status = false;
                """))
        {
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next() == false || resultSet.getInt(1) == 0)
            {
                return true;
            }

            return false;

        } catch (SQLException e) {
            throw new DataAccessException("Check if lobby is empty could not be executed", e);
        }
    }

    public Integer createBattleLobby(Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                INSERT INTO Battle (battle_id, user1_id) VALUES (DEFAULT, ?) RETURNING battle_id ;
                """))
        {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            //return battle_id
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new DataAccessException("Create Battle-Lobby could not be executed", e);
        }
    }

    public String getBattleLog(Integer battle_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                SELECT * FROM Battle_Log WHERE battle_id = ? AND log IS NOT NULL
                """))
        {
            preparedStatement.setInt(1, battle_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
            {
                throw new NoDataException("No Battle_log was found");
            }

            //return battle_log
            return resultSet.getString("log");

        } catch (SQLException e) {
            throw new DataAccessException("Create Battle-Lobby could not be executed", e);
        }
    }

    public Integer JoinBattleLobbyWithSecondPlayer(Integer user_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    UPDATE Battle
                    SET user2_id = ?
                    WHERE battle_id IS NOT NULL
                    AND user1_id IS NOT NULL
                    AND user1_id != ?
                    RETURNING battle_id;
                """))
        {
            preparedStatement.setInt(1, (user_id));
            preparedStatement.setInt(2, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                throw new NotFoundException("Second player could not join the lobby");
            }

            //return battle_id
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new DataAccessException("Join Battle-Lobby with second player could not be executed", e);
        }
    }

    public void updateBattleStatusToFinished(Integer battle_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    UPDATE Battle
                    SET battle_status = true
                    WHERE battle_id = ?
                """))
        {
            preparedStatement.setInt(1, (battle_id));
            Integer updtatedRows = preparedStatement.executeUpdate();

            if(updtatedRows < 1)
            {
                throw new DataUpdateException("Second player could not finish the game");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Finish battle with second player could not be executed", e);
        }
    }

    public Integer getFirstUserIdFromBattle(Integer battle_id)
    {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    SELECT * FROM Battle
                    WHERE battle_id = ?;
                """))
        {
            preparedStatement.setInt(1, (battle_id));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
            {
                throw new NoDataException("No first user-id from battle found");
            }

            //return battle_log
            return resultSet.getInt("user1_id");

        } catch (SQLException e) {
            throw new DataAccessException("Get first user-id from battle could not be executed", e);
        }
    }
}



