package gogreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleNode;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.octicons.OctIconView;

import javafx.geometry.Pos;

import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Application extends javafx.application.Application {
    //the stage this application uses
    private static Stage stage;
    private static String theme;

    //launches the app
    public static void main(String[] args) {
        launch();
    }

    /**
     * this method starts the application.
     * @param stage stage
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("GoGreen");

        // sets the theme
        theme = "src/styles/mainSceneDefaultTheme.css";

        //the silentLogin will login for the user
        if (client.Communication.silentLogin()) {
            mainScreen();
        } else {
            loginScene();
        }
    }

    /**
     * This method displays the Login screen.
     */
    private static void loginScene() {
        Scene loginScene = new Scene(loginScreen(), 500, 500);
        loginScene.getStylesheets().add(new File(theme).toURI().toString());
        show(loginScene);
    }

    /**
     * This method displays the register screen.
     */
    private static void registerScene() {
        Scene registerScene = new Scene(registerScreen(), 500, 500);
        registerScene.getStylesheets().add(new File(theme).toURI().toString());
        show(registerScene);
    }

    /**
     * The login screen.
     * @return the screen
     */
    private static GridPane loginScreen() {

        //textfield for the username
        TextField username = new TextField();
        username.setPromptText("username");

        //passwordfield
        PasswordField password = new PasswordField();
        password.setPromptText("password");

        //passwordfield for if the user wants to see the password
        TextField visiblePassword = new TextField();
        visiblePassword.setVisible(false);

        //checkbox to toggle between visible password and masked password
        JFXToggleNode showPassword = new JFXToggleNode();
        MaterialDesignIconView showIcon = new MaterialDesignIconView(MaterialDesignIcon.EYE);
        showIcon.setSize("20px");
        showPassword.setGraphic(new Label("Show password", showIcon));
        showPassword.setOnAction(e -> {
            toggleVisibility(
                    visiblePassword, password, showPassword.isSelected());
        });

        //checkbox if the user wants the application to remember the username and password
        JFXToggleNode rememberUser = new JFXToggleNode();
        rememberUser.setGraphic(new Label("Remember me"));


        //button to log in with the given credentials
        JFXButton login = new JFXButton("Login");
        login.setOnAction(e -> {
            try {
                ApplicationMethods.login(
                        username.getText(), password.getText(), rememberUser.isSelected());
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        });

        //button if the user wants to register instead of to log in
        JFXButton register = new JFXButton("or register");
        register.setOnAction(e -> {
            registerScene();
        });

        // make the login title
        Label loginText = new Label("Login:");
        loginText.setId("loginText");

        //create the page
        GridPane loginPage = new GridPane();
        loginPage.setVgap(5);
        loginPage.setHgap(10);
        loginPage.add(loginText, 0, 0);
        loginPage.add(username, 0, 1);
        loginPage.add(rememberUser, 1, 1);
        loginPage.add(new StackPane(password, visiblePassword), 0, 2);
        loginPage.add(showPassword, 1, 2);
        loginPage.add(login, 0, 3);
        loginPage.add(register, 1, 3);
        loginPage.setAlignment(Pos.CENTER);

        //and return it
        return loginPage;
    }

    /**
     * The body for the register display.
     *
     * @return the body
     */
    private static GridPane registerScreen() {
        //textfield for the username
        TextField username = new TextField();
        username.setPromptText("username");

        //password field
        PasswordField password = new PasswordField();
        password.setPromptText("password");

        //password field if the user wants to see the password
        TextField visiblePassword = new TextField();
        visiblePassword.setVisible(false);

        //for registration the user should insert twice the same password
        PasswordField passwordTwo = new PasswordField();
        passwordTwo.setPromptText("password again");

        //password two can also be set to visible
        TextField visiblePasswordTwo = new TextField();
        visiblePasswordTwo.setVisible(false);

        //checkbox to toggle between visible password and masked password
        JFXToggleNode showPassword = new JFXToggleNode();
        MaterialDesignIconView showIcon = new MaterialDesignIconView(MaterialDesignIcon.EYE);
        showIcon.setSize("20px");
        showPassword.setGraphic(new Label("Show password", showIcon));
        showPassword.setOnAction(e -> {
            toggleVisibility(visiblePassword, password, showPassword.isSelected());
            toggleVisibility(visiblePasswordTwo, passwordTwo, showPassword.isSelected());
        });

        //checkbox if the user wants his username and password to be remembered
        JFXToggleNode rememberUser = new JFXToggleNode();
        rememberUser.setGraphic(new Label("Remember me"));
        // no action implemented yet......

        // make the register text
        Label registerText = new Label("Register:");
        registerText.setId("loginText");

        //button to register the information the user filled in
        JFXButton register = new JFXButton("Register");
        register.setOnAction(e -> {
            try {
                ApplicationMethods.register(username.getText(), password.getText(),
                        passwordTwo.getText(), rememberUser.isSelected());
            } catch (NullPointerException | IllegalArgumentException
                    | IllegalAccessException | FileNotFoundException exception) {
                registerText.setText(exception.getMessage());
                registerText.setTextFill(Paint.valueOf("#FF0000"));
            }
        });

        JFXButton back = new JFXButton("go Back");
        back.setOnAction(e -> {
            loginScene();
        });


        //creates the gridpane with all the nodes in it
        GridPane registerPage = new GridPane();
        registerPage.setVgap(5);
        registerPage.setHgap(10);
        registerPage.add(registerText, 0, 0);
        registerPage.add(username, 0, 1);
        registerPage.add(new StackPane(password, visiblePassword), 0, 2);
        registerPage.add(rememberUser, 1, 2);
        registerPage.add(new StackPane(passwordTwo, visiblePasswordTwo), 0, 3);
        registerPage.add(showPassword, 1, 3);
        registerPage.add(register, 0, 4);
        registerPage.add(back, 1, 4);

        registerPage.setAlignment(Pos.CENTER);

        //and returns it
        return registerPage;
    }



    /*
     * the main screen
     */
    static void mainScreen() {
        //make the navigation tab pane
        JFXTabPane navigation = new JFXTabPane();
        navigation.setPrefSize(500, 700);

        //  make the home tab
        Tab homeTab = new Tab();
        homeTab.setText("Home");
        homeTab.setContent(homeScreen());

        // make the category tab
        Tab categoryTab = new Tab();
        categoryTab.setText("Category");
        categoryTab.setContent(categoryScreen());

        // make the stats tab
        Tab statsTab = new Tab();
        statsTab.setText("Stats");
        statsTab.setContent(statsScreen());

        // make the leaderboard tab
        Tab leaderboardTab = new Tab();
        leaderboardTab.setText("Competition");
        leaderboardTab.setContent(competitionScreen());

        // add all the tabs
        navigation.getTabs().addAll(homeTab, categoryTab,statsTab, leaderboardTab);

        //put into a framework
        Pane body = new Pane();
        body.getChildren().addAll(navigation);

        //and displayed
        Scene mainScene = new Scene(body, 500, 600);
        mainScene.getStylesheets().add(new File(theme).toURI().toString());
        show(mainScene);
    }

    /**
     * makes the home screen.
     * @return home screen
     */
    private static Pane homeScreen() {

        // makes the home navigation bar
        JFXTabPane homeNavigation = new JFXTabPane();
        homeNavigation.setPrefSize(500,500);

        // makes the your world tab
        Tab homeTab = new Tab();
        homeTab.setText("Your world");
        homeTab.setContent(yourWorldScreen());

        // makes the settings tab
        Tab settingsTab = new Tab();
        settingsTab.setText("Settings");
        settingsTab.setContent(settingsScreen());

        // adds the tabs to the navigation bar
        homeNavigation.getTabs().addAll(homeTab, settingsTab);

        // makes the page and adds the nodes
        Pane homePage = new Pane();
        homePage.getChildren().addAll(homeNavigation);

        // returns the page
        return homePage;
    }

    /**
     * makes the your world screen.
     * @return your world screen
     */
    private static GridPane yourWorldScreen() {
        // makes the level bar
        ProgressBar levelBar = new ProgressBar(0.5);
        levelBar.setPrefSize(400, 10);

        // makes the level text
        Label levelStatus = new Label("Lv. INSERT HERE LEVEL");
        levelStatus.setId("levelStatus");

        // makes a container for the level attributes
        VBox levelContainer = new VBox();
        levelContainer.getChildren().addAll(levelStatus, levelBar);

        // makes the page and adds the nodes
        GridPane yourWorldPage = new GridPane();
        yourWorldPage.add(levelContainer, 0,0);
        yourWorldPage.setAlignment(Pos.BOTTOM_CENTER);

        // returns the page
        return yourWorldPage;
    }

    /**
     * makes the settings screen.
     * @return settings screen
     */
    private static VBox settingsScreen() {
        // set the status of the dark mode
        String status = "Enable";

        // makes the dark mode button
        JFXToggleNode darkTheme = new JFXToggleNode();
        MaterialDesignIconView darkThemeIcon =
                new MaterialDesignIconView(MaterialDesignIcon.THEME_LIGHT_DARK);
        darkThemeIcon.setSize("50px");
        darkTheme.setPrefSize(500, 100);
        darkTheme.setGraphic(new Label(status + " dark theme", darkThemeIcon));

        // if the dark mode is enabled, we will have the disable button
        if (theme.equals("src/styles/mainSceneDarkTheme.css")) {
            darkTheme.setSelected(true);
            status = "Disable";
        }

        // if you toggle the button, you change the theme
        darkTheme.setOnAction(e -> {
            System.out.println(darkTheme.isSelected());
            if (darkTheme.isSelected()) {
                theme = "src/styles/mainSceneDarkTheme.css";
                mainScreen();
            } else {
                theme = "src/styles/mainSceneDefaultTheme.css";
                mainScreen();
            }
        });

        // make the logout button
        JFXButton logoutButton = new JFXButton();
        MaterialDesignIconView logoutIcon = new MaterialDesignIconView(MaterialDesignIcon.LOGOUT);
        logoutIcon.setSize("50px");
        logoutButton.setGraphic(new Label("Log out", logoutIcon));
        logoutButton.setPrefSize(500, 100);

        // if we press the button we log out
        logoutButton.setOnAction(e -> {
            // TODO
            // log out method does not work...
            client.Communication.logout();
            loginScene();

        });

        // make the page and add the nodes
        VBox settingsPage = new VBox(10);
        settingsPage.getChildren().addAll(darkTheme, logoutButton);
        settingsPage.setAlignment(Pos.CENTER);

        // return the page
        return settingsPage;
    }

    /**
     * make the category screen.
     * @return the category screen
     */
    private static Pane categoryScreen() {
        // make the category navigation bar
        JFXTabPane categoryNavigation = new JFXTabPane();
        categoryNavigation.setPrefSize(500, 600);

        // make the transport tab
        Tab transportTab = new Tab();
        transportTab.setText("Transport");
        transportTab.setContent(transportScreen());

        // make the food tab
        Tab foodTab = new Tab();
        foodTab.setText("Food");
        foodTab.setContent(foodScreen());

        // make the energy tab
        Tab energyTab = new Tab();
        energyTab.setText("Energy");
        energyTab.setContent(energyScreen());

        // make the extra tab
        Tab extraTab = new Tab();
        extraTab.setText("Extra");
        extraTab.setContent(extraScreen());

        // add all the tabs to the navigation bar
        categoryNavigation.getTabs().addAll(transportTab, foodTab, energyTab, extraTab);

        // make the category page
        Pane categoryPage = new Pane();
        categoryPage.getChildren().addAll(categoryNavigation);

        // return the page
        return categoryPage;

    }

    /**
     * makes the transport screen.
     * @return the transport screen
     */
    private static GridPane transportScreen() {

        //button for the cycle action
        JFXButton cycle = new JFXButton();
        FontAwesomeIconView bikeIcon = new FontAwesomeIconView(FontAwesomeIcon.BICYCLE);
        bikeIcon.setSize("50px");
        cycle.setGraphic(bikeIcon);
        cycle.setPrefSize(500,100);

        // when you press the button you add a action
        cycle.setOnAction(e -> {
            Transport.addCycleAction();
        });

        //button for the public transport action
        JFXButton publicTransport = new JFXButton();
        MaterialDesignIconView subwayIcon = new MaterialDesignIconView(MaterialDesignIcon.SUBWAY);
        subwayIcon.setSize("50px");
        publicTransport.setGraphic(subwayIcon);
        publicTransport.setPrefSize(500,100);

        // when you press the button you add an action
        publicTransport.setOnAction(e -> {
            Transport.addPublicTransportAction();
        });

        //button for the car action
        JFXButton car = new JFXButton();
        FontAwesomeIconView carIcon = new FontAwesomeIconView(FontAwesomeIcon.AUTOMOBILE);
        carIcon.setSize("50px");
        car.setGraphic(carIcon);
        car.setPrefSize(500,100);

        // when you press the button you will add an action
        car.setOnAction(e -> {
            Transport.addCarAction();
        });

        //button for the plane action
        JFXButton plane = new JFXButton();
        FontAwesomeIconView planeIcon = new FontAwesomeIconView(FontAwesomeIcon.PLANE);
        planeIcon.setSize("50px");
        plane.setGraphic(planeIcon);
        plane.setPrefSize(500,100);

        // when you press a button you will add an action
        plane.setOnAction(e -> {
            Transport.addPlaneAction();
        });

        // make the transport page
        GridPane transportPage = new GridPane();
        transportPage.setVgap(10);
        transportPage.add(cycle, 0 ,0);
        transportPage.add(publicTransport, 0, 1);
        transportPage.add(car, 0, 2);
        transportPage.add(plane, 0,3 );
        transportPage.setAlignment(Pos.CENTER);

        // return the page
        return transportPage;
    }

    /**
     * make the food screen.
     * @return
     */
    private static GridPane foodScreen() {

        // makes the veggie toggle
        JFXToggleNode veggie = new JFXToggleNode();
        MaterialDesignIconView cowIcon = new MaterialDesignIconView(MaterialDesignIcon.COW);
        veggie.setGraphic(new Label("Meat", cowIcon));
        cowIcon.setSize("50px");
        veggie.setMinSize(500,100);

        // makes the locally toggle
        JFXToggleNode locally = new JFXToggleNode();
        FontAwesomeIconView locallyIcon = new FontAwesomeIconView(FontAwesomeIcon.MAP_MARKER);
        locallyIcon.setSize("50px");
        locally.setGraphic(new Label("Locally", locallyIcon));
        locally.setMinSize(500,100);

        // makes the bio toggle
        JFXToggleNode bio = new JFXToggleNode();
        FontAwesomeIconView leafIcon = new FontAwesomeIconView(FontAwesomeIcon.LEAF);
        leafIcon.setSize("50px");
        bio.setGraphic(new Label("Bio", leafIcon));
        bio.setMinSize(500,100);

        //makes send button
        JFXButton send = new JFXButton("Add action");
        send.setMinSize(500, 100);

        // when you press the send button, it will look what is selected and add those actions
        send.setOnAction(e -> {
            FoodCategory.addAction(!veggie.isSelected(), locally.isSelected(), bio.isSelected());

            // then sets it to false to select it again
            veggie.setSelected(false);
            locally.setSelected(false);
            bio.setSelected(false);
        });

        //make the page and will add the nodes
        GridPane foodPage = new GridPane();
        foodPage.add(veggie, 0, 0);
        foodPage.add(locally, 0, 1);
        foodPage.add(bio, 0, 2);
        foodPage.add(send, 0, 3);
        foodPage.setVgap(10);
        foodPage.setAlignment(Pos.CENTER);

        // return the body
        return foodPage;
    }

    /**
     * makes the energy screen.
     * @return the energy screen
     */
    static GridPane energyScreen() {

        // makes the water time button
        JFXButton waterTime = new JFXButton();
        MaterialDesignIconView waterIcon = new MaterialDesignIconView(MaterialDesignIcon.WATER);
        waterIcon.setSize("50px");
        waterTime.setGraphic(new Label("Water time", waterIcon));
        waterTime.setPrefSize(500, 100);

        // when pressed it will send an action
        waterTime.setOnAction(e -> {
            Energy.addReduceWater();
        });

        // makes the energy button
        JFXButton energyTime = new JFXButton();
        MaterialDesignIconView energyIcon = new MaterialDesignIconView(MaterialDesignIcon.FLASH);
        energyIcon.setSize("50px");
        energyTime.setGraphic(new Label("Energy time", energyIcon));
        energyTime.setPrefSize(500, 100);

        // when pressed it will send an action
        energyTime.setOnAction(e -> {
            Energy.addReduceEnergyAction();
        });

        // makes the page and adds the nodes
        GridPane energyPage = new GridPane();
        energyPage.setVgap(10);
        energyPage.add(waterTime, 0, 0);
        energyPage.add(energyTime, 0, 1);
        energyPage.setAlignment(Pos.CENTER);

        // returns the page
        return energyPage;
    }

    /**
     * makes the extra screen.
     * @return the extra screen
     */
    static GridPane extraScreen() {

        // makes the clean surronding button
        JFXButton cleanSurronding = new JFXButton();
        OctIconView trashIcon = new OctIconView(OctIcon.TRASHCAN);
        trashIcon.setSize("50px");
        cleanSurronding.setGraphic(new Label("Clean surronding", trashIcon));
        cleanSurronding.setPrefSize(500, 100);

        // when pressed it will send the action
        cleanSurronding.setOnAction(e -> {
            Extra.addCleanSurroundingAction();
        });

        // makes the recycle button
        JFXButton recycle = new JFXButton();
        MaterialDesignIconView recycleIcon = new MaterialDesignIconView(MaterialDesignIcon.RECYCLE);
        recycleIcon.setSize("50px");
        recycle.setGraphic(new Label("Recycle", recycleIcon));
        recycle.setPrefSize(500, 100);

        // when pressed it will send the action
        recycle.setOnAction(e -> {
            Extra.addRecycleAction();
        });

        // makes the page and adds the nodes
        GridPane extraPage = new GridPane();
        extraPage.setVgap(10);
        extraPage.add(cleanSurronding, 0,0 );
        extraPage.add(recycle,0,1);
        extraPage.setAlignment(Pos.CENTER);

        // returns the page
        return extraPage;
    }

    /**
     * make the stats screen.
     * @return the stats screen
     */
    private static Pane statsScreen() {
        // make the stats navigation bar
        JFXTabPane statsNavigation = new JFXTabPane();
        statsNavigation.setPrefSize(500,600);

        // make the overview tab
        Tab overview = new Tab();
        overview.setText("Overview");
        overview.setContent(overviewScreen());

        // add all the tabs to the navigation bar
        statsNavigation.getTabs().addAll(overview);

        // make the stats page
        Pane statsPage = new Pane();
        statsPage.getChildren().addAll(statsNavigation);

        return statsPage;
    }

    /**
     * make the overview screen.
     * @return the overview screen
     */
    private static VBox overviewScreen() {

        // make the refresh button
        JFXButton refresh = new JFXButton("refresh");

        // make the recent activites text
        Label history = new Label("Recent Activities: \t\t date: \t\t\t time: \n "
                + client.Communication.getLastThreeActions());
        history.setId("history");

        // if the refresh button is pressed, we display the last three recent activities
        refresh.setOnAction(e -> {
            history.setText("Recent Activities: \t\t date: \t\t\t time: \n "
                    + client.Communication.getLastThreeActions());
        });

        // make the overview page
        VBox overviewPage = new VBox();
        overviewPage.getChildren().addAll(history, refresh);

        // return the page
        return overviewPage;
    }

    /**
     * make the competition screen.
     * @return the competition screen
     */
    private static Pane competitionScreen() {
        // make the competition navigation bar
        JFXTabPane competitionNavigation = new JFXTabPane();
        competitionNavigation.setPrefSize(500,600);

        // make the leaderboard tab
        Tab leaderboard = new Tab();
        leaderboard.setText("Leaderboard");
        leaderboard.setContent(leaderboardScreen());

        // make the friends tab
        Tab friends = new Tab();
        friends.setText("Friends");
        friends.setContent(friendsScreen());

        // make the friends request tab
        Tab request = new Tab();
        request.setText("Friend requests");
        request.setContent(friendRequestScreen());

        // add all the tabs to the navigation bar
        competitionNavigation.getTabs().addAll(leaderboard, friends, request);

        // make the leaderboard page
        Pane competitionPage = new Pane();
        competitionPage.getChildren().addAll(competitionNavigation);

        return competitionPage;
    }

    /**
     * make the leaderboard screen.
     * @return the leaderboard screen
     */
    private static VBox leaderboardScreen() {
        //array for testing purposes
        String[] placeholder = {"Marit 10000 10", "Gerrie 9000 9", "Harold 8000 8", "RobbieJetje 7000 7", "GeertjeWilders 6000 6", "MarkieRutje 5000 5", "LavendelSnuifer 4000 4", "JesseKlavertje4 3000 3", "theFBI 2000 2", "Trump 1000 0"};

        // make the leaderboard title
        Label header = new Label("Leaderboard");
        header.setId("title");

        // make the leaderboard
        GridPane leaderboard = new GridPane();
        leaderboard.setVgap(5);
        leaderboard.setHgap(10);
        leaderboard.setId("leaderboard");
        leaderboard.setAlignment(Pos.CENTER);
        leaderboard.setPrefWidth(500);

        // make the header
        leaderboard.add(new Label("Pos"), 0, 0);
        leaderboard.add(new Label("Username"), 1, 0);
        leaderboard.add(new Label("Points"), 2, 0);
        leaderboard.add(new Label("Level"), 3, 0);

        // place all the people in the leaderboard
        int pos = 1;
        for (String users : placeholder) {
            String[] parts = users.split("\\s");
            leaderboard.add(new Label(pos + "."), 0, pos);
            leaderboard.add(new Label(parts[0]), 1, pos);
            leaderboard.add(new Label(parts[1]), 2, pos);
            leaderboard.add(new Label(parts[2]), 3, pos);
            pos++;
        }

        // places the user in the leaderboard
        leaderboard.add(new Label("69."), 0, 11);
        leaderboard.add(new Label("Your Username"), 1, 11);
        leaderboard.add(new Label("1"), 2, 11);
        leaderboard.add(new Label("0"), 3, 11);

        // makes the leaderboard page
        VBox leaderboardPage = new VBox();
        leaderboardPage.getChildren().addAll(header, leaderboard);

        // returns the page
        return leaderboardPage;
    }

    /**
     * make the friends screen.
     * @return the friends screen
     */
    private static VBox friendsScreen() {
        // makes an array list for testing purposes
        ArrayList<String> friends = new ArrayList<>(Arrays.asList("Rachel", "Monica", "Phoebe", "Joey", "Ross", "Chandler" ,"more friends", "more friends", "more friends", "more friends",
                "more friends", "more friends", "more friends"));

        // makes the title
        Label amountOfFriends = new Label("");
        amountOfFriends.setId("title");

        // makes the friendlist
        GridPane friendsList = new GridPane();
        friendsList.setVgap(5);
        friendsList.setAlignment(Pos.CENTER);
        friendsList.setId("friends");

        // fills the friendlist with your friends
        if (!friends.isEmpty()) {
            amountOfFriends.setText(friends.size() + " friends:");

            int pos = 1;
            for (String friend : friends) {
                friendsList.add(new Label(friend), 0, pos++);
            }
        } else {
            amountOfFriends.setText("You have no friends");
        }

        // makes the scroll pane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(500, 400);
        scrollPane.setContent(friendsList);

        // makes the search field
        TextField searchField = new TextField();
        searchField.setPromptText("search");
        searchField.setPrefWidth(300);
        searchField.setAlignment(Pos.CENTER);

        // makes the search button
        JFXButton searchButton = new JFXButton("Search");
        searchButton.setId("smallButton");
        searchButton.setOnAction(e -> {
            System.out.println(searchField.getText());
        });

        // puts the search field and search button together
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);
        searchBar.getChildren().addAll(searchField, searchButton);

        // makes the friends page
        VBox friendsPage = new VBox(5);
        friendsPage.getChildren().addAll(amountOfFriends, scrollPane, searchBar);

        // returns the friendspage
        return friendsPage;
    }

    /**
     * make the friend request screen.
     * @return the friend request screen
     */
    private static VBox friendRequestScreen() {
        // makes an arraylist for testing purposes
        ArrayList<String> friends = new ArrayList<>(Arrays.asList("Rachel", "Monica", "Phoebe", "Joey", "Ross", "Chandler"));

        // makes the title
        Label nrRequest = new Label(friends.size() + " friend requests:");
        nrRequest.setId("title");

        // make the username, accept button and decline button containers
        VBox requests = new VBox(5);
        VBox acceptButton = new VBox(10);
        VBox declineButton = new VBox(10);

        // puts all the friendrequests and buttons in the container
        for (String request: friends) {
            // make the username label and the buttons
            Label user = new Label(request);
            JFXButton accept = new JFXButton("Accept");
            accept.setId("smallButton");
            JFXButton decline = new JFXButton("Decline");
            decline.setId("smallButton");

            // if we accept we remove the request and add the person
            accept.setOnAction(e -> {
                System.out.println("Accepted: " + request);

                requests.getChildren().remove(user);
                acceptButton.getChildren().remove(accept);
                declineButton.getChildren().remove(decline);

                friends.remove(request);
                nrRequest.setText(friends.size() + " friend requests:");
            });

            // if we decline we remove the request
            decline.setOnAction(e -> {
                System.out.println("Declined: " + request);

                requests.getChildren().remove(user);
                acceptButton.getChildren().remove(accept);
                declineButton.getChildren().remove(decline);

                friends.remove(request);
                nrRequest.setText(friends.size() + " friend requests:");
            });

            // and the username and buttons in their containers
            requests.getChildren().add(user);
            acceptButton.getChildren().add(accept);
            declineButton.getChildren().add(decline);
        }

        // make one container for the username and buttons
        HBox container = new HBox(10);
        container.getChildren().addAll(requests, acceptButton, declineButton);

        // make the scroll pane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(500, 400);
        scrollPane.setContent(container);

        // make the request page
        VBox requestPage = new VBox();
        requestPage.getChildren().addAll(nrRequest, scrollPane);

        // return the request page
        return requestPage;
    }



    /**
     * Toggle visibility between Textfield and PasswordField.
     * @param visible the Textfield
     * @param invisible the PasswordField
     * @param show whether to show the password
     */
    static void toggleVisibility(TextField visible, PasswordField invisible, boolean show) {
        if (show) {
            invisible.setVisible(false);
            visible.setText(invisible.getText());
            visible.setVisible(true);
        } else {
            invisible.setVisible(true);
            invisible.setText(visible.getText());
            visible.setVisible(false);
        }
    }

    /**
     * shows the given scene to the user.
     * @param scene scene
     */
    private static void show(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }
}