package at.fhtw.mtcgapp.service.user;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;

public class UserService {
    private final UserController userController;

    public UserService()
    {
        this.userController = new UserController(new UserDAL());
    }
/*    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.GET &&
                request.getPathParts().size() > 1) {
            return this.weatherController.getWeather(request.getPathParts().get(1));
        } else if (request.getMethod() == Method.GET) {
            return this.weatherController.getWeather();
        } else if (request.getMethod() == Method.POST) {
            return this.weatherController.addWeather(request);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }*/
}
