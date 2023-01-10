package at.fhtw.mtcgapp;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcgapp.service.battlelogs.BattlelogsService;
import at.fhtw.mtcgapp.service.deck.DeckService;
import at.fhtw.mtcgapp.service.game.battles.battleImplementations.MyThread;
import at.fhtw.mtcgapp.service.packages.PackageService;
import at.fhtw.mtcgapp.service.session.SessionService;
import at.fhtw.mtcgapp.service.trading.TradingService;
import at.fhtw.mtcgapp.service.user.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MTCGUnitTests {
    @Test
    @Order(1)
    void testCreateSeveralNewUser() {
        UserService userService = new UserService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.setPathname("/users");
        request.setBody("{\"Username\": \"bea\",\"Password\": \"123\"}");
        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));

        Response response = userService.handleRequest(request);

        assertEquals( "User successfully created", response.getContent());
        assertEquals(201, response.getStatus());

        request.setBody("{\"Username\": \"simba\",\"Password\": \"123456\"}");
        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));

        response = userService.handleRequest(request);

        assertEquals( "User successfully created", response.getContent());
        assertEquals(201, response.getStatus());
        request.setBody("{\"Username\": \"admin\",\"Password\": \"istrator\"}");
        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));

        response = userService.handleRequest(request);

        assertEquals( "User successfully created", response.getContent());
        assertEquals(201, response.getStatus());
    }
    @Test
    @Order(2)
    void testCreateAlreadyExistingUser() {
        UserService userService = new UserService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.setPathname("/users");
        request.setBody("{\"Username\": \"bea\",\"Password\": \"123\"}");
        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));

        Response response = userService.handleRequest(request);

        assertEquals( "User with same username already registered", response.getContent());
        assertEquals(409, response.getStatus());
    }
    @Test
    @Order(3)
    void testSeveralUserLogIn() {
        SessionService sessionService = new SessionService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.setPathname("/sessions");
        request.setBody("{\"Username\": \"bea\",\"Password\": \"123\"}");
        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));

        Response response = sessionService.handleRequest(request);

        assertEquals("User login successful", response.getContent());
        assertEquals(200, response.getStatus());

        request.setBody("{\"Username\": \"simba\",\"Password\": \"123456\"}");
        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));

        response = sessionService.handleRequest(request);

        assertEquals("User login successful", response.getContent());
        assertEquals(200, response.getStatus());

        request.setBody("{\"Username\": \"admin\",\"Password\": \"istrator\"}");
        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));

        response = sessionService.handleRequest(request);

        assertEquals("User login successful", response.getContent());
        assertEquals(200, response.getStatus());
    }
    @Test
    @Order(4)
    void testUserLogInWithInvalidPassword() {
        SessionService sessionService = new SessionService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.setPathname("/sessions");
        request.setBody("{\"Username\": \"bea\",\"Password\": \"1234\"}");
        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));

        Response response = sessionService.handleRequest(request);

        assertEquals("Invalid username/password provided", response.getContent());
        assertEquals(401, response.getStatus());
    }
    @Test
    @Order(5)
    void testGetUserDataWithInvalidToken() {
        UserService userService = new UserService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.GET);
        request.setPathname("/users/bea");
        request.getHeaderMap().setAuthorizationTokenHeader("Basic test-mtcgToken");

        Response response = userService.handleRequest(request);

        assertEquals("Authentication information is missing or invalid", response.getContent());
        assertEquals(401, response.getStatus());
    }
    @Test
    @Order(6)
    void testGetUserDataWithInvalidUsername() {
        UserService userService = new UserService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.GET);
        request.setPathname("/users/test");
        request.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");

        Response response = userService.handleRequest(request);

        assertEquals("User not found.", response.getContent());
        assertEquals(404, response.getStatus());
    }
    @Test
    @Order(7)
    void testUpdateUserData() {
        UserService userService = new UserService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.PUT);
        request.setPathname("/users/bea");
        request.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");
        request.setBody("{\"Name\": \"Beatrice\", \"Bio\": \"me programming...\", \"Image\": \":3\"}");

        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));


        Response response = userService.handleRequest(request);

        assertEquals("User sucessfully updated.", response.getContent());
        assertEquals(200, response.getStatus());
    }
    @Test
    @Order(8)
    void testCreatePackageWithInvalidAdminToken() {
        PackageService packageService = new PackageService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.setPathname("/packages");
        request.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");
        request.setBody("" +
                "[" +
                "{\"Id\": \"123s3f64-5717-4562-b3fc-2c963f66afa6\",\"Name\": \"Knight\",\"Damage\": 30}," +
                "{\"Id\": \"3133s3f64-5717-4562-b3fc-2c963f66afa6\",\"Name\": \"Krake\",\"Damage\": 15}," +
                "{\"Id\": \"fd3444d4fs3f64-5717-4562-b3963f66afa6\",\"Name\": \"Troll\",\"Damage\": 40}," +
                "{\"Id\": \"45566666s3f64-5717-4562-b3fc-23f66afa6\",\"Name\": \"Wizzard\",\"Damage\": 35}," +
                "{\"Id\": \"25256563f64-5717-4562-b3fc-2c963f66afa6\",\"Name\": \"FireElf\",\"Damage\": 30}" +
                "]"
        );

        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));


        Response response = packageService.handleRequest(request);

        assertEquals("Provided user is not \"admin\"", response.getContent());
        assertEquals(403, response.getStatus());
    }
    @Test
    @Order(9)
    void testCreatePackageForUserA() {
        PackageService packageService = new PackageService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.setPathname("/packages");
        request.getHeaderMap().setAuthorizationTokenHeader("Basic admin-mtcgToken");
        request.setBody("" +
                "[" +
                    "{\"Id\": \"123s3f64-5717-4562-b3fc-2c963f66afa6\",\"Name\": \"WaterSpell\",\"Damage\": 30}," +
                    "{\"Id\": \"3133s3f64-5717-4562-b3fc-2c963f66afa6\",\"Name\": \"Krake\",\"Damage\": 15}," +
                    "{\"Id\": \"fd3444d4fs3f64-5717-4562-b3963f66afa6\",\"Name\": \"Troll\",\"Damage\": 40}," +
                    "{\"Id\": \"45566666s3f64-5717-4562-b3fc-23f66afa6\",\"Name\": \"Wizzard\",\"Damage\": 35}," +
                    "{\"Id\": \"25256563f64-5717-4562-b3fc-2c963f66afa6\",\"Name\": \"FireElf\",\"Damage\": 30}" +
                "]"
        );

        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));


        Response response = packageService.handleRequest(request);

        assertEquals("Package and cards successfully created", response.getContent());
        assertEquals(201, response.getStatus());
    }
    @Test
    @Order(10)
    void testAcquirePackageForUserA() {
        PackageService packageService = new PackageService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");
        request.setPathname("/transactions/packages");

        Response response = packageService.handleRequest(request);

        assertEquals("A package has been successfully bought", response.getContent());
        assertEquals(200, response.getStatus());
    }
    @Test
    @Order(11)
    void testCreatePackageForUserB() {
        PackageService packageService = new PackageService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.setPathname("/packages");
        request.getHeaderMap().setAuthorizationTokenHeader("Basic admin-mtcgToken");
        request.setBody("" +
                "[" +
                "{\"Id\": \"135trgvvv-5717-4562-b3fc-2c963f66afa6\",\"Name\": \"Knight\",\"Damage\": 35}," +
                "{\"Id\": \"ta454454q5-5717-4562-b3fc-2c963f66afa6\",\"Name\": \"Ork\",\"Damage\": 0}," +
                "{\"Id\": \"ttq545q5q5tq5tqt-5717-4562-b3963f66afa6\",\"Name\": \"Dragon\",\"Damage\": 0}," +
                "{\"Id\": \"4q5q5fgffffa44433337-4562-b3fc-23f66afa6\",\"Name\": \"WaterSpell\",\"Damage\": 0}," +
                "{\"Id\": \"453fdsfrer34343443-4562-b3fc-2c963f66afa6\",\"Name\": \"FireElf\",\"Damage\": 0}" +
                "]"
        );

        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));


        Response response = packageService.handleRequest(request);

        assertEquals("Package and cards successfully created", response.getContent());
        assertEquals(201, response.getStatus());
    }
    @Test
    @Order(12)
    void testAcquirePackageForUserB() {
        PackageService packageService = new PackageService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic simba-mtcgToken");
        request.setPathname("/transactions/packages");

        Response response = packageService.handleRequest(request);

        assertEquals("A package has been successfully bought", response.getContent());
        assertEquals(200, response.getStatus());
    }
    @Test
    @Order(13)
    void testSetDeckForSeveralUser() {
        DeckService deckService = new DeckService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.PUT);
        request.setPathname("/deck");
        request.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");
        request.setBody("" +
                "[" +
                "\"123s3f64-5717-4562-b3fc-2c963f66afa6\", " +
                "\"3133s3f64-5717-4562-b3fc-2c963f66afa6\", " +
                "\"25256563f64-5717-4562-b3fc-2c963f66afa6\", " +
                "\"45566666s3f64-5717-4562-b3fc-23f66afa6\"" +
                "]"
        );

        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));


        Response response = deckService.handleRequest(request);

        assertEquals("The deck has been successfully configured", response.getContent());
        assertEquals(200, response.getStatus());

        request.getHeaderMap().setAuthorizationTokenHeader("Basic simba-mtcgToken");
        request.setBody("" +
                "[" +
                "\"135trgvvv-5717-4562-b3fc-2c963f66afa6\", " +
                "\"ta454454q5-5717-4562-b3fc-2c963f66afa6\", " +
                "\"ttq545q5q5tq5tqt-5717-4562-b3963f66afa6\", " +
                "\"4q5q5fgffffa44433337-4562-b3fc-23f66afa6\"" +
                "]"
        );
        request.getHeaderMap().setContentLength(String.valueOf(request.getBody().length()));
        request.getHeaderMap().setContentTypeHeader(String.valueOf(ContentType.JSON));


        response = deckService.handleRequest(request);

        assertEquals("The deck has been successfully configured", response.getContent());
        assertEquals(200, response.getStatus());
    }
    @Test
    @Order(14)
    void testGetTradingsWithoutAvailableTradings() {
        TradingService tradingService = new TradingService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.GET);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");
        request.setPathname("/tradings");

        Response response = tradingService.handleRequest(request);

        assertEquals("The request was fine, but there are no trading deals available", response.getContent());
        assertEquals(400, response.getStatus());
    }
    @Test
    @Order(15)
    void testCreateTradingDeal() {
        TradingService tradingService = new TradingService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic simba-mtcgToken");
        request.setPathname("/tradings");
        request.setBody("" +
                "{" +
                "\"Id\": \"3faz85f64-5717-4562-b3fc-2c963f66afa6\", " +
                "\"CardToTrade\": \"453fdsfrer34343443-4562-b3fc-2c963f66afa6\", " +
                "\"Type\": \"monster\", " +
                "\"MinimumDamage\": 15" +
                "}"
        );


        Response response = tradingService.handleRequest(request);

        assertEquals("Trading deal successfully created", response.getContent());
        assertEquals(201, response.getStatus());
    }
    @Test
    @Order(16)
    void testCreateTradingDealWithoutBeTheOwnerOfCard() {
        TradingService tradingService = new TradingService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic simba-mtcgToken");
        request.setPathname("/tradings");
        request.setBody("" +
                "{" +
                "\"Id\": \"u3faz8rr5f64-5717-4562-b3fc-2c963f66afa6\", " +
                "\"CardToTrade\": \"fd3444d4fs3f64-5717-4562-b3963f66afa6\", " +
                "\"Type\": \"monster\", " +
                "\"MinimumDamage\": 15" +
                "}"
        );


        Response response = tradingService.handleRequest(request);

        assertEquals("The deal contains a card that is not owned by the user or locked in the deck.", response.getContent());
        assertEquals(403, response.getStatus());
    }
    @Test
    @Order(17)
    void testCreateTradingDealAndCardIsInDeck() {
        TradingService tradingService = new TradingService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic simba-mtcgToken");
        request.setPathname("/tradings");
        request.setBody("" +
                "{" +
                "\"Id\": \"tu3faz8r64-5717-4562-b3fc-2c963f66afa6\", " +
                "\"CardToTrade\": \"135trgvvv-5717-4562-b3fc-2c963f66afa6\", " +
                "\"Type\": \"monster\", " +
                "\"MinimumDamage\": 15" +
                "}"
        );


        Response response = tradingService.handleRequest(request);

        assertEquals("The deal contains a card that is not owned by the user or locked in the deck.", response.getContent());
        assertEquals(403, response.getStatus());
    }
    @Test
    @Order(18)
    void testCarryOutTradingWithWrongCardOwner() {
        TradingService tradingService = new TradingService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic simba-mtcgToken");
        request.setPathname("/tradings/3faz85f64-5717-4562-b3fc-2c963f66afa6");
        request.setBody("\"fd3444d4fs3f64-5717-4562-b3963f66afa6\"");


        Response response = tradingService.handleRequest(request);

        assertEquals("The offered card is not owned by the user, or the requirements are not met (Type, MinimumDamage), or the offered card is locked in the deck.", response.getContent());
        assertEquals(403, response.getStatus());
    }
    @Test
    @Order(19)
    void testCarryOutTrading() {
        TradingService tradingService = new TradingService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");
        request.setPathname("/tradings/3faz85f64-5717-4562-b3fc-2c963f66afa6");
        request.setBody("\"fd3444d4fs3f64-5717-4562-b3963f66afa6\"");


        Response response = tradingService.handleRequest(request);

        assertEquals("Trading deal successfully executed.", response.getContent());
        assertEquals(200, response.getStatus());
    }
    @Test
    @Order(20) // test if user can create a deal with gained card from trade
    void testCreateTradingAnotherDeal() {
        TradingService tradingService = new TradingService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic simba-mtcgToken");
        request.setPathname("/tradings");
        request.setBody("" +
                "{" +
                "\"Id\": \"3fazf64-571f7-4562-b3fc-2c963f66afa6\", " +
                "\"CardToTrade\": \"fd3444d4fs3f64-5717-4562-b3963f66afa6\", " +
                "\"Type\": \"spell\", " +
                "\"MinimumDamage\": 100" +
                "}"
        );


        Response response = tradingService.handleRequest(request);

        assertEquals("Trading deal successfully created", response.getContent());
        assertEquals(201, response.getStatus());
    }
    @Test
    @Order(21)
    void testCarryOutTradingWithWrongTradingId() {
        TradingService tradingService = new TradingService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");
        request.setPathname("/tradings/u3fazfr64-571f7-4562-zzb3fc-2c963f66afa6");
        request.setBody("\"453fdsfrer34343443-4562-b3fc-2c963f66afa6\"");


        Response response = tradingService.handleRequest(request);

        assertEquals("The provided deal ID was not found.", response.getContent());
        assertEquals(404, response.getStatus());
    }
    @Test
    @Order(22)
    void testCarryOutTradingWithWrongCardStats() {
        TradingService tradingService = new TradingService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.POST);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");
        request.setPathname("/tradings/3fazf64-571f7-4562-b3fc-2c963f66afa6");
        request.setBody("\"453fdsfrer34343443-4562-b3fc-2c963f66afa6\"");


        Response response = tradingService.handleRequest(request);

        assertEquals("The offered card is not owned by the user, or the requirements are not met (Type, MinimumDamage), or the offered card is locked in the deck.", response.getContent());
        assertEquals(403, response.getStatus());
    }
    @Test
    @Order(23)
    void testDeleateTradingDealWithWrongOwner() {
        TradingService tradingService = new TradingService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.DELETE);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");
        request.setPathname("/tradings/3fazf64-571f7-4562-b3fc-2c963f66afa6");


        Response response = tradingService.handleRequest(request);

        assertEquals("The deal contains a card that is not owned by the user.", response.getContent());
        assertEquals(403, response.getStatus());
    }
    @Test
    @Order(24)
    void testDeleateTradingDeal() {
        TradingService tradingService = new TradingService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.DELETE);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic simba-mtcgToken");
        request.setPathname("/tradings/3fazf64-571f7-4562-b3fc-2c963f66afa6");


        Response response = tradingService.handleRequest(request);

        assertEquals("Trading deal successfully deleted", response.getContent());
        assertEquals(200, response.getStatus());
    }
    @Test
    @Order(25)
    void testBattleForUserAAndB() throws InterruptedException {
        Request request1 = new Request();
        Request request2 = new Request();

        request1.setHeaderMap(new HeaderMap());
        request1.setMethod(Method.POST);
        request1.setPathname("/battles");
        request1.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");

        request2.setHeaderMap(new HeaderMap());
        request2.setMethod(Method.POST);
        request2.setPathname("/battles");
        request2.getHeaderMap().setAuthorizationTokenHeader("Basic simba-mtcgToken");

        MyThread myThread1 = new MyThread(request1);
        MyThread myThread2 = new MyThread(request2);
        Thread thread1 = new Thread(myThread1);
        Thread thread2 = new Thread(myThread2);
        thread1.start();
        Thread.sleep(2000);
        thread2.start();
        thread1.join();
        thread2.join();
        Response response2 = myThread2.getResponse();

        assertTrue(response2.getContent().startsWith("Battle: bea vs simba"));
        assertTrue(response2.getContent().endsWith("=> bea wins"));
        assertEquals(200, response2.getStatus());
    }
    @Test
    @Order(26) //unique feature
    void testGetSpecificBattlelog() {
        BattlelogsService battlelogsService = new BattlelogsService();
        Request request = new Request();

        request.setHeaderMap(new HeaderMap());
        request.setMethod(Method.GET);
        request.getHeaderMap().setAuthorizationTokenHeader("Basic bea-mtcgToken");
        request.setPathname("/battlelogs/1");


        Response response = battlelogsService.handleRequest(request);

        assertTrue(response.getContent().startsWith("Battle: bea vs simba"));
        assertEquals(200, response.getStatus());
    }
}