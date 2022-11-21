package at.fhtw.mtcgapp.service.user;

import at.fhtw.mtcgapp.model.User;
import at.fhtw.sampleapp.model.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testUserServiceSetUser() throws Exception {
        URL url = new URL("http://localhost:10001/user/1");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            User user = new ObjectMapper().readValue(bufferedReader.readLine(), User.class);
            assertEquals(1, user.getId());
            assertEquals("bea", user.getName());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        bufferedReader.close();
    }

    @Test
    void testWeatherServiceGetById() throws Exception {
        URL url = new URL("http://localhost:10001/user/1");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            User user = new ObjectMapper().readValue(bufferedReader.readLine(), User.class);
            assertEquals(1, user.getId());
            assertEquals("bea", user.getName());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        bufferedReader.close();
    }

}