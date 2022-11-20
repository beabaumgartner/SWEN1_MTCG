package at.fhtw.mtcgapp.service.user;

import at.fhtw.sampleapp.service.weather.WeatherDAL;

public class UserController {

    private UserDAL userDAL;

    public UserController(UserDAL userDAL) {
        this.userDAL = userDAL;
    }
}
