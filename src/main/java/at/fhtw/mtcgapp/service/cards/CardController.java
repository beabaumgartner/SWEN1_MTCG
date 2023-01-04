package at.fhtw.mtcgapp.service.cards;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcgapp.controller.Controller;
import at.fhtw.mtcgapp.dal.UnitOfWork;
import at.fhtw.mtcgapp.dal.repository.CardRepository;
import at.fhtw.mtcgapp.dal.repository.SessionRepository;
import at.fhtw.mtcgapp.exception.DataAccessException;
import at.fhtw.mtcgapp.exception.InvalidLoginDataException;
import at.fhtw.mtcgapp.exception.NoDataException;
import at.fhtw.mtcgapp.model.Card;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collection;

public class CardController extends Controller {

    public Response getCardsFromUser(Request request) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (unitOfWork) {

            new SessionRepository(unitOfWork).checkIfTokenIsValid(request);
            int user_id = new SessionRepository(unitOfWork).getUserIdByToken(request);
            Collection<Card> userCards = new CardRepository(unitOfWork).getAllCardsFromUser(user_id);

            unitOfWork.commitTransaction();

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
                    HttpStatus.NO_CONTENT,
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
}
