package at.fhtw.mtcgapp.service.deck;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcgapp.controller.Controller;
import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.dal.repository.CardRepository;
import at.fhtw.mtcgapp.dal.repository.DeckRepository;
import at.fhtw.mtcgapp.dal.repository.SessionRepository;
import at.fhtw.mtcgapp.exception.*;
import at.fhtw.mtcgapp.model.Card;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collection;

public class DeckController extends Controller {
    public Response getDeckCardsFromUserJSON(Request request) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (unitOfWork) {
            new SessionRepository(unitOfWork).checkIfTokenIsValid(request);
            int user_id = new SessionRepository(unitOfWork).getUserIdByToken(request);

            Collection<Card> userCards = new CardRepository(unitOfWork).getAllDeckCardsFromUser(user_id);

            unitOfWork.commitTransaction();

            if(userCards.isEmpty())
            {
                throw new NoDataException("The request was fine, but the deck doesn't have any cards");
            }

            String userCardsJSON = this.getObjectMapper().writeValueAsString(userCards);

            return  new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    userCardsJSON
            );
        }
        catch (JsonProcessingException exception) {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.PLAIN_TEXT,
                    "Internal Server Error"
            );
        }
        catch (InvalidLoginDataException e)
        {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.PLAIN_TEXT,
                    "Invalid username/password provided"
            );
        }
        catch (NoDataException e)
        {
            unitOfWork.rollbackTransaction();
            e.printStackTrace();
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    "The request was fine, but the user doesn't have any cards"
            );
        }
        catch (DataAccessException e)
        {
            unitOfWork.rollbackTransaction();
            e.printStackTrace();
            return new Response(
                    HttpStatus.CONFLICT,
                    ContentType.PLAIN_TEXT,
                    "Database Server Error"
            );
        }
        catch (Exception e)
        {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "Internal Server Error"
            );
        }
    }

    public Response getDeckCardsFromUserPLAIN(Request request) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (unitOfWork) {

            new SessionRepository(unitOfWork).checkIfTokenIsValid(request);
            int user_id = new SessionRepository(unitOfWork).getUserIdByToken(request);

            Collection<Card> userCards = new CardRepository(unitOfWork).getAllDeckCardsFromUser(user_id);
            if(userCards.isEmpty())
            {
                throw new NoDataException("The request was fine, but the deck doesn't have any cards");
            }

            unitOfWork.commitTransaction();

            String userCardsPLAIN = "";

            for(Card userCard : userCards)
            {
                userCardsPLAIN += "card-id: " + userCard.getCard_id() + ", name: " + userCard.getName() + ", damage: " + userCard.getDamage() + "\n";
            }

            return  new Response(
                    HttpStatus.OK,
                    ContentType.PLAIN_TEXT,
                    userCardsPLAIN
            );
        }
        catch (JsonProcessingException exception) {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.PLAIN_TEXT,
                    "Internal Server Error"
            );
        }
        catch (InvalidLoginDataException e)
        {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.PLAIN_TEXT,
                    "Invalid username/password provided"
            );
        }
        catch (NoDataException e)
        {
            unitOfWork.rollbackTransaction();
            e.printStackTrace();
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    "The request was fine, but the user doesn't have any cards"
            );
        }
        catch (DataAccessException e)
        {
            unitOfWork.rollbackTransaction();
            e.printStackTrace();
            return new Response(
                    HttpStatus.CONFLICT,
                    ContentType.PLAIN_TEXT,
                    "Database Server Error"
            );
        }
        catch (Exception e)
        {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "Internal Server Error"
            );
        }
    }

    public Response configureDeckCardsFromUser(Request request) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (unitOfWork) {
            String userCards[] = this.getObjectMapper().readValue(request.getBody(), String[].class);
            if(userCards.length != 4)
            {
                throw new InvalidDataException("The provided deck did not include the required amount of cards");
            }

            new SessionRepository(unitOfWork).checkIfTokenIsValid(request);
            int user_id = new SessionRepository(unitOfWork).getUserIdByToken(request);
            int deck_id = new DeckRepository(unitOfWork).createDeck(user_id);

            for(String userCard : userCards)
            {
                new DeckRepository(unitOfWork).updateCardsForDeck(user_id, deck_id, userCard);
            }

            new DeckRepository(unitOfWork).updateOldCardDeck(user_id, deck_id);

            unitOfWork.commitTransaction();

            return  new Response(
                    HttpStatus.OK,
                    ContentType.PLAIN_TEXT,
                    "The deck has been successfully configured"
            );
        }
        catch (JsonProcessingException exception) {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.PLAIN_TEXT,
                    "Internal Server Error"
            );
        }
        catch (InvalidLoginDataException e)
        {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.PLAIN_TEXT,
                    "Authentication information is missing or invalid"
            );
        }
        catch (NoDataException e)
        {
            unitOfWork.rollbackTransaction();
            e.printStackTrace();
            return new Response(
                    HttpStatus.NOT_FOUND,
                    ContentType.PLAIN_TEXT,
                    "The request was fine, but the user doesn't have any cards"
            );
        }
        catch (InvalidItemException e)
        {
            unitOfWork.rollbackTransaction();
            e.printStackTrace();
            return new Response(
                    HttpStatus.FORBIDDEN,
                    ContentType.PLAIN_TEXT,
                    "At least one of the provided cards does not belong to the user or is not available."
            );
        }
        catch (InvalidDataException e)
        {
            unitOfWork.rollbackTransaction();
            e.printStackTrace();
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    "The provided deck did not include the required amount of cards"
            );
        }
        catch (DataAccessException e)
        {
            unitOfWork.rollbackTransaction();
            e.printStackTrace();
            return new Response(
                    HttpStatus.CONFLICT,
                    ContentType.PLAIN_TEXT,
                    "Database Server Error"
            );
        }
        catch (Exception e)
        {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "Internal Server Error"
            );
        }
    }
}
