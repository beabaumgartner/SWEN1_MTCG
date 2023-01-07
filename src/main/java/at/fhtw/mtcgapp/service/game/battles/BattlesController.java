package at.fhtw.mtcgapp.service.game.battles;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcgapp.controller.Controller;
import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.dal.repository.BattlesRepository;
import at.fhtw.mtcgapp.dal.repository.CardRepository;
import at.fhtw.mtcgapp.dal.repository.DeckRepository;
import at.fhtw.mtcgapp.dal.repository.SessionRepository;
import at.fhtw.mtcgapp.exception.*;
import at.fhtw.mtcgapp.model.Card;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collection;
import java.util.Objects;
import java.util.Random;

public class BattlesController extends Controller {
    public Response manageBattle(Request request) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (unitOfWork) {

            new SessionRepository(unitOfWork).checkIfTokenIsValid(request);

            String battle_log = "";

            // first player
            if (new BattlesRepository(unitOfWork).isLobbyEmpty()) {
                int firstPlayerUser_id = new SessionRepository(unitOfWork).getUserIdByToken(request);
                int battle_lobby_id = new BattlesRepository(unitOfWork).createBattleLobby(firstPlayerUser_id);
                unitOfWork.commitTransaction();

                UnitOfWork secondUnitOfWork = new UnitOfWork();

                boolean isBattleDone = false;
                int count = 0;
                int timeout = 100;

                do {
                    Thread.sleep(1000);
                    isBattleDone = new BattlesRepository(secondUnitOfWork).isBattleDone(battle_lobby_id);
                    ++count;

                } while (!isBattleDone && count < timeout);

                if (isBattleDone) {
                    //battle_log = new BattlesRepository(secondUnitOfWork).getBattleLog(battle_lobby_id);
                } else if (count >= timeout) {
                    throw new NoPlayerFoundException("No player found");
                }
                secondUnitOfWork.commitTransaction();

            }
            else // second Player
            {
                int secondPlayerUser_id = new SessionRepository(unitOfWork).getUserIdByToken(request);
                int battle_lobby_id = new BattlesRepository(unitOfWork).JoinBattleLobbyWithSecondPlayer(secondPlayerUser_id);
                int firstPlayerUser_id = new BattlesRepository(unitOfWork).getFirstUserIdFromBattle(battle_lobby_id);
                new BattlesRepository(unitOfWork).updateBattleStatusToFinished(battle_lobby_id);
                // battle logic !!!!!!!!!!!!!


                Collection<Card> firstPlayerDeck = new DeckRepository(unitOfWork).getDeckByUserId(firstPlayerUser_id);
                Collection<Card> secondPlayerDeck = new DeckRepository(unitOfWork).getDeckByUserId(secondPlayerUser_id);

                Random rnd = new Random();
                int i = rnd.nextInt(firstPlayerDeck.size());
                /*Card firstPlayerCarrForRound = firstPlayerDeck.toArray()[i];
                Card card = firstPlayerDeck.toArray()[0]
                Objects.checkIndex(firstPlayerDeck);*/







                //battle_log = new BattlesRepository(secondUnitOfWork).getBattleLog(battle_lobby_id);
                unitOfWork.commitTransaction();
            }

            return new Response(
                    HttpStatus.OK,
                    ContentType.PLAIN_TEXT,
                    "The battle has been carried out successfully.\n" +
                            battle_log

            );
        } catch (JsonProcessingException exception) {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.PLAIN_TEXT,
                    "Internal Server Error"
            );
        } catch (InvalidLoginDataException e) {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.PLAIN_TEXT,
                    "Authentication information is missing or invalid"
            );
        } catch (NotFoundException e) {
            unitOfWork.rollbackTransaction();
            e.printStackTrace();
            return new Response(
                    HttpStatus.NO_CONTENT,
                    ContentType.PLAIN_TEXT,
                    "No Data found"
            );
        } catch (NoDataException e) {
            unitOfWork.rollbackTransaction();
            e.printStackTrace();
            return new Response(
                    HttpStatus.NO_CONTENT,
                    ContentType.PLAIN_TEXT,
                    "No Battle-Log was found"
            );
        } catch (NoPlayerFoundException e) {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.NOT_FOUND,
                    ContentType.PLAIN_TEXT,
                    "No player found"
            );
        } catch (DataAccessException e) {
            unitOfWork.rollbackTransaction();
            e.printStackTrace();
            return new Response(
                    HttpStatus.CONFLICT,
                    ContentType.PLAIN_TEXT,
                    "Database Server Error"
            );
        } catch (Exception e) {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "Internal Server Error"
            );
        }
    }
}
