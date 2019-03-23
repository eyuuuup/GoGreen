package client;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EncrypteeTest {
    
    private Encryptee OBJECT;
    private byte[]    MESSAGE;
    
    @Before
    public void before() {
        MESSAGE = new byte[1];
        OBJECT = new Encryptee(MESSAGE, "ABC");
    }
    
    @Test
    public void constructor() {
        byte[]    message = new byte[1];
        Encryptee object  = new Encryptee(message, "abc");
        assertEquals(object.getMessage(), message);
        assertEquals(object.getToken(), "abc");
    }
    
    @Test
    public void getMessage() {
        assertEquals(OBJECT.getMessage(), MESSAGE);
    }
    
    @Test
    public void setMessage() {
        byte[]    message = new byte[1];
        Encryptee object  = new Encryptee(new byte[3], "abc");
        object.setMessage(message);
        assertEquals(object.getMessage(), message);
    }
    
    @Test
    public void getToken() {
        assertEquals(OBJECT.getToken(), "ABC");
    }
    
    @Test
    public void setToken() {
        byte[]    message = new byte[1];
        Encryptee object  = new Encryptee(message, "abc");
        object.setToken("def");
        assertEquals(object.getToken(), "def");
    }
}