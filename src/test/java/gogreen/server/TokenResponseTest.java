package gogreen.server;

import gogreen.server.holders.TokenResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenResponseTest {
    private TokenResponse tokenResponse = new TokenResponse("Token", true);

    @Test
    public void constructorEmpty(){
        TokenResponse tokenResponse = new TokenResponse();
    }

    @Test
    public void constructor(){
        TokenResponse tokenResponse = new TokenResponse("token", false);
        assertEquals(tokenResponse.getToken(), "token");
        assertFalse(tokenResponse.isLegit());
    }

    @Test
    public void getToken() { assertEquals(tokenResponse.getToken(), "Token");
    }

    @Test
    public void setToken() {
        TokenResponse tokenResponse = new TokenResponse("Token", true);
        tokenResponse.setToken("token");
        assertEquals(tokenResponse.getToken(), "token");
    }

    @Test
    public void isLegit() { assertTrue(tokenResponse.isLegit());
    }

    @Test
    public void setLegit() {
        TokenResponse tokenResponse = new TokenResponse("Token", true);
        tokenResponse.setLegit(false);
        assertFalse(tokenResponse.isLegit());
    }
}