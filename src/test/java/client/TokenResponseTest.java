package client;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenResponseTest {
    private TokenResponse tokenResponse = new TokenResponse("Token", true);
    @Test
    public void constructor(){
        TokenResponse tokenResponse = new TokenResponse("token", false);
        assertEquals(tokenResponse.getToken(), "token");
        assertFalse(tokenResponse.isLegit());
    }

    @Test
    public void getToken() {
    }

    @Test
    public void setToken() {
    }

    @Test
    public void isLegit() {
    }

    @Test
    public void setLegit() {
    }
}