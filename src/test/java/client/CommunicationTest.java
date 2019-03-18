//package client;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.powermock.reflect.Whitebox;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(properties = "spring.main.web-application-type=reactive")
//public class CommunicationTest {
//    @Test
//    public void register() {
//        Communication.register("Username", "Password", true);
//    }
//
//    @Test
//    public void login() {
//        Communication.login("Username", "Password", true);
//    }
//
//    @Test
//    public void silentLogin() {
//        Communication.silentLogin();
//    }
//
//    @Test
//    public void addAction() {
//        Communication.addAction("Action", 100);
//    }
//
//    @Test
//    public void addActionLoggedIn() {
//        Whitebox.setInternalState( Communication.class, "token", "token");
//        Communication.addAction("Action", 100);
//    }
//
//    @Test
//    public void logout() {
//        Communication.logout();
//    }
//
//    @Test
//    public void isLoggedIn() throws Exception {
//        Whitebox.invokeMethod(Communication.class, "isLoggedIn");
//    }
//
//    @Test
//    public void getLastThreeActions() {
//        Communication.getLastThreeActions();
//    }
//
//    @Test
//    public void getLastThreeActionsLoggedIn() throws IllegalAccessException {
//        Whitebox.setInternalState( Communication.class, "token", "token");
//        Communication.getLastThreeActions();
//    }
//}