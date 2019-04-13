package gogreen.server;

import gogreen.server.holders.Action;
import gogreen.server.holders.ActionList;
import gogreen.server.holders.Challenge;
import gogreen.server.holders.ChallengesList;
import gogreen.server.holders.FriendsList;
import gogreen.server.holders.OnLoadValues;
import gogreen.server.holders.TokenResponse;
import gogreen.server.holders.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommunicationTest {

    ObjectMapper mapper = new ObjectMapper();
    private int          port         = 1080;
    @Rule
    public  WireMockRule wireMockRule = new WireMockRule(port);

    //    private WireMockServer wireMockServer;
    private String baseUrl;

    @BeforeAll
    static void turnOffJettyLog() {
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
    }

    /**
     * Turning of Jetty logging to not use too much memory.
     */
    @Before
    public void init() {
        baseUrl = "http://localhost:" + port;

        Communication.setHostURL(baseUrl);
    }

    @BeforeEach
    void startServer() {
        // force WireMock to use ONE port
        WireMock.configureFor("localhost", port);

        wireMockRule.start();
    }

    @AfterEach
    void stopServer() {
        wireMockRule.stop();
    }

    // ========== PART METHODS =================================================

    @Test
    public void submitFalse() throws JsonProcessingException {
        wireMockRule.stubFor(post(urlEqualTo("/register"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(new TokenResponse("abc", false)))
                )
        );
        assertFalse(Communication.register("user", "pwd", true));
    }

    @Test
    public void submitTrue() throws JsonProcessingException {
        wireMockRule.stubFor(post(urlEqualTo("/register"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(new TokenResponse("abc", true)))
                )
        );
        assertTrue(Communication.register("user", "pwd", true));
    }

    @Test
    public void submitWrongFile() {
        // not needed ?
    }

    @Test
    public void postToken() throws JsonProcessingException {
        wireMockRule.stubFor(post(urlEqualTo("/getTotalScore"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(new Integer(123)))
                )
        );

        assertEquals(123, Communication.getMyTotalScore());
    }

    // ========== USER AUTHENTICATION ==========================================

    @Test
    public void register() throws JsonProcessingException {
        wireMockRule.stubFor(post(urlEqualTo("/register"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(new TokenResponse("testToken", true)))
                )
        );

        assertTrue(Communication.register("user", "pwd", true));
    }

    @Test
    public void login() throws JsonProcessingException {
        wireMockRule.stubFor(post(urlEqualTo("/login"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(new TokenResponse("testToken", true)))
                )
        );

        assertTrue(Communication.login("user", "pwd", true));
    }

    @Test
    public void silentLogin() throws JsonProcessingException {
        wireMockRule.stubFor(post(urlEqualTo("/silentLogin"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(true))
                )
        );

        assertTrue(Communication.silentLogin());
    }

    @Test
    public void silentLoginWrongFile() throws JsonProcessingException {
        // not needed?
    }

    @Test
    public void logout() {
        Communication.logout();
    }

    // ========== ACTION HANDLERS ==============================================

    @Test
    public void addAction() throws JsonProcessingException {
        wireMockRule.stubFor(post(urlEqualTo("/addAction"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(true))
                )
        );
        assertTrue(Communication.addAction("testAction", 100, 50, 50));
    }

    @Test
    public void addActionDescription() throws JsonProcessingException {
        wireMockRule.stubFor(post(urlEqualTo("/addAction"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(true))
                )
        );
        assertTrue(Communication.addAction("testAction", 100, 50, 50, "test"));
    }

    @Test
    public void getLastThreeActions() throws JsonProcessingException {//        RestTemplate template = PowerMockito.mock(RestTemplate.class);
        ArrayList<Action> list     = new ArrayList<>();
        ActionList        response = new ActionList(list);

        wireMockRule.stubFor(post(urlEqualTo("/retract"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(response))
                )
        );

        ArrayList answer = Communication.getLastThreeActions();

        assertEquals(list, answer);
    }

    @Test
    public void getMyTotalScore() throws JsonProcessingException {
        wireMockRule.stubFor(post(urlEqualTo("/getTotalScore"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(new Integer(123)))
                )
        );

        assertEquals(123, Communication.getMyTotalScore());
    }

    // ========== SOCIAL HANDLERS ==============================================

    @Test
    public void checkUsername() {
        wireMockRule.stubFor(post(urlEqualTo("/searchUser"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody("true")
                )
        );

        assertTrue(Communication.checkUsername("testUser"));
    }

    @Test
    public void getUser() throws JsonProcessingException {
        User user = new User("a", "b");
        user.setTotalScore(123);
        user.setEmail("mail");

        wireMockRule.stubFor(post(urlEqualTo("/getUser"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(user))
                )
        );

        assertEquals(user.getName(), Communication.getUser().getName());
        assertEquals(user.getPassword(), Communication.getUser().getPassword());
        assertEquals(user.getTotalScore(), Communication.getUser().getTotalScore());
        assertEquals(user.getEmail(), Communication.getUser().getEmail());
    }

    @Test
    public void addFriend() throws JsonProcessingException {
        wireMockRule.stubFor(post(urlEqualTo("/addFriend"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(true))
                )
        );

        assertTrue(Communication.addFriend("addFriend"));
    }

    @Test
    public void getFriends() throws JsonProcessingException {
        FriendsList response = new FriendsList();

        wireMockRule.stubFor(post(urlEqualTo("/showFriends"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(response))
                )
        );

        ArrayList answer = Communication.getFriends();

        assertEquals(response.getList(), answer);
    }

    @Test
    public void getFollowers() throws JsonProcessingException {
        FriendsList response = new FriendsList();

        wireMockRule.stubFor(post(urlEqualTo("/showFollowers"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(response))
                )
        );

        ArrayList answer = Communication.getFollowers();

        assertEquals(response.getList(), answer);
    }

    @Test
    public void getLeaderboard() throws JsonProcessingException {
        FriendsList response = new FriendsList();

        wireMockRule.stubFor(get(urlEqualTo("/getLeaderboard"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(response))
                )
        );

        ArrayList answer = Communication.getLeaderboard();

        assertEquals(response.getList(), answer);
    }

    @Test
    public void carbon() throws JsonProcessingException {
        Action action = new Action("a", "b", 10);

        wireMockRule.stubFor(post(urlEqualTo("/carbon"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(action))
                )
        );

        Action answer = Communication.carbon();

        assertEquals(action.getAction(), answer.getAction());
        assertEquals(action.getValue(), answer.getValue());
    }

    @Test
    public void onLoad() throws JsonProcessingException {
        OnLoadValues olv = new OnLoadValues();

        wireMockRule.stubFor(post(urlEqualTo("/onLoad"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(olv))
                )
        );

        OnLoadValues res = Communication.onLoad();

        assertEquals(olv.getUser().getTotalScore(), res.getUser().getTotalScore());
        assertEquals(olv.getCarbonReduce(), res.getCarbonReduce());
    }

    @Test
    public void addChallenge() throws JsonProcessingException {
        wireMockRule.stubFor(post(urlEqualTo("/addChallenge"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(true))
                )
        );
        assertTrue(Communication.addChallenge("a", 10));
    }

    @Test
    public void getChallenges() throws JsonProcessingException {
        ChallengesList response = new ChallengesList();

        wireMockRule.stubFor(post(urlEqualTo("/getChallenges"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(response))
                )
        );

        ArrayList<Challenge> answer = Communication.getChallenges();

        assertEquals(response.getList().size(), answer.size());
    }

    @Test
    public void acceptChallenge() {
        wireMockRule.stubFor(post(urlEqualTo("/acceptChallenge"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody("true")
                )
        );
        assertTrue(Communication.acceptChallenge(new Challenge()));
    }

    @Test
    public void getRecentCOSavings() throws JsonProcessingException {
        ArrayList<Action> alist = new ArrayList<>();
        alist.add(new Action("auto", "abc", 10));
        ActionList list = new ActionList(alist);

        wireMockRule.stubFor(post(urlEqualTo("/getRecentCOSavings"))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(mapper.writeValueAsString(list))
                )
        );

        double[] res = Communication.getRecentCOSavings();

        assertEquals(alist.get(0).getCarbonReduced(), res[0], 0.1);
    }
}
