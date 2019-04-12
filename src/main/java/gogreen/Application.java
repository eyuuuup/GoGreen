package gogreen;

import gogreen.server.holders.Action;
import gogreen.server.holders.Challenge;
import gogreen.server.ComCached;
import gogreen.server.holders.CompareFriends;
import gogreen.server.DescriptionTooLong;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.octicons.OctIconView;
import gogreen.actions.Energy;
import gogreen.actions.Extra;
import gogreen.actions.Food;
import gogreen.actions.OneTimeEvent;
import gogreen.actions.Transport;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.ConnectIOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class Application extends javafx.application.Application {
    //the stage this application uses
    private static Stage stage;
    private static String theme;

    //launches the app
    public static void main(String[] args) {
        launch();
    }

    /**
     * This method displays the Login screen.
     */
    private static void loginScene() {
        Scene loginScene = new Scene(loginScreen(), 1000, 600);
        loginScene.getStylesheets().add(new File(theme).toURI().toString());
        show(loginScene);
    }

    /**
     * This method displays the register screen.
     */
    private static void registerScene() {
        Scene registerScene = new Scene(registerScreen(), 1000, 600);
        registerScene.getStylesheets().add(new File(theme).toURI().toString());
        show(registerScene);
    }

    /**
     * The login screen.
     *
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
        showPassword.setId("loginButtons");
        showPassword.setOnAction(e -> {
            toggleVisibility(
                    visiblePassword, password, showPassword.isSelected());
        });

        //checkbox if the user wants the application to remember the username and password
        JFXToggleNode rememberUser = new JFXToggleNode();
        rememberUser.setGraphic(new Label("Remember me"));
        rememberUser.setId("loginButtons");

        // make the login titles
        Label loginText = new Label("Login:");
        loginText.setId("loginText");

        //button to log in with the given credentials
        JFXButton login = new JFXButton("Login");
        login.setId("loginButton");
        login.setOnAction(e -> {
            try {
                ApplicationMethods.login(
                        username.getText(), password.getText(), rememberUser.isSelected());
            } catch (IllegalAccessException e1) {
                loginText.setText(e1.getMessage());
                loginText.setTextFill(Paint.valueOf("#FF0000"));
            }
        });

        //button if the user wants to register instead of to log in
        JFXButton register = new JFXButton("Register");
        register.setId("loginButtons");
        register.setOnAction(e -> {
            registerScene();
        });

        Label usernameTitle = new Label("Username:");
        usernameTitle.setId("loginTitles");
        Label passwordTitle = new Label("Password:");
        passwordTitle.setId("loginTitles");

        //create the page
        GridPane loginPage = new GridPane();
        loginPage.setId("loginPage");
        loginPage.add(loginText, 0, 0);
        loginPage.add(usernameTitle, 0, 1);
        loginPage.add(username, 1, 1);
        loginPage.add(rememberUser, 2, 1);
        loginPage.add(passwordTitle, 0, 2);
        loginPage.add(new StackPane(password, visiblePassword), 1, 2);
        loginPage.add(showPassword, 2, 2);
        loginPage.add(login, 1, 3);
        loginPage.add(register, 2, 3);

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
        showPassword.setId("loginButtons");
        showPassword.setOnAction(e -> {
            toggleVisibility(visiblePassword, password, showPassword.isSelected());
            toggleVisibility(visiblePasswordTwo, passwordTwo, showPassword.isSelected());
        });

        //checkbox if the user wants his username and password to be remembered
        JFXToggleNode rememberUser = new JFXToggleNode();
        rememberUser.setId("loginButtons");
        rememberUser.setGraphic(new Label("Remember me"));

        // make the register text
        Label registerText = new Label("Register:");
        registerText.setId("loginText");

        //button to register the information the user filled in
        JFXButton register = new JFXButton("Register");
        register.setId("loginButton");
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
        back.setId("loginButtons");
        back.setOnAction(e -> {
            loginScene();
        });

        Label usernameTitle = new Label("Username:");
        usernameTitle.setId("loginTitles");
        Label passwordTitle = new Label("Password:");
        passwordTitle.setId("loginTitles");

        //creates the gridpane with all the nodes in it
        GridPane registerPage = new GridPane();
        registerPage.setId("loginPage");
        registerPage.add(registerText, 0, 0);
        registerPage.add(usernameTitle, 0, 1);
        registerPage.add(username, 1, 1);
        registerPage.add(passwordTitle, 0, 2);
        registerPage.add(new StackPane(password, visiblePassword), 1, 2);
        registerPage.add(rememberUser, 2, 2);
        registerPage.add(new StackPane(passwordTwo, visiblePasswordTwo), 1, 3);
        registerPage.add(showPassword, 2, 3);
        registerPage.add(register, 1, 4);
        registerPage.add(back, 2, 4);

        //and returns it
        return registerPage;
    }

    static void loadingScreen() {
        // WIP
        System.out.println("Loading...");

    }

    /**
     * the main screen.
     */
    static void mainScreen() {

        //make the navigation tab pane
        JFXTabPane navigation = new JFXTabPane();
        navigation.setId("mainNavigation");

        //  make the home tab
        Tab homeTab = new Tab();
        homeTab.setText("Home");
        homeTab.setContent(homeScreen());

        // make the category tab
        Tab categoryTab = new Tab();
        categoryTab.setText("Add action");
        categoryTab.setContent(categoryScreen());

        // make the stats tab
        Tab statsTab = new Tab();
        statsTab.setText("Stats");
        statsTab.setContent(statsScreen());

        // make the leaderboard tab
        Tab leaderboardTab = new Tab();
        leaderboardTab.setText("Friends & Competition");
        leaderboardTab.setContent(competitionScreen());

        // add all the tabs
        navigation.getTabs().addAll(homeTab, categoryTab, statsTab, leaderboardTab);

        //put into a framework
        Pane body = new Pane();
        body.getChildren().addAll(navigation);

        //and displayed
        Scene mainScene = new Scene(body, 1000, 600);
        mainScene.getStylesheets().add(new File(theme).toURI().toString());
        show(mainScene);
    }

    /**
     * makes the home screen.
     *
     * @return home screen
     */
    private static Pane homeScreen() {

        // makes the home navigation bar
        JFXTabPane homeNavigation = new JFXTabPane();
        homeNavigation.setId("secondNavigation");
        homeNavigation.setPrefSize(500, 575);

        // makes the your world tab
        Tab homeTab = new Tab();
        homeTab.setText("Your world");
        homeTab.setContent(yourWorldScreen());

        // makes the settings tab
        Tab settingsTab = new Tab();
        settingsTab.setText("Settings");
        settingsTab.setContent(settingsScreen());

        // make the about tab
        Tab aboutTab = new Tab();
        aboutTab.setText("About");
        aboutTab.setContent(aboutScreen());

        // adds the tabs to the navigation bar
        homeNavigation.getTabs().addAll(homeTab, settingsTab, aboutTab);

        // makes the page and adds the nodes
        Pane homePage = new Pane();
        homePage.getChildren().addAll(homeNavigation);

        // returns the page
        return homePage;
    }

    /**
     * makes the sidebar.
     *
     * @return the sidebar
     */
    private static GridPane sideBar() {

        // makes all the labels to display
        Label username = new Label("@" + ApplicationMethods.decodeUsername(ApplicationMethods.getUsername()));

        double savedAmount = ApplicationMethods.getSavedCarbon();
        Label  reducedCO2  = new Label(savedAmount + "kg CO\u2082 saved");

        int   points     = ApplicationMethods.getPoints();
        Label pointsText = new Label(points + " Points");

        int   followers     = ApplicationMethods.getFollowersSize();
        Label followersText = new Label(followers + " Followers");

        int   following     = ApplicationMethods.getFollowingSize();
        Label followingText = new Label(following + " Followed");

        // puts everything into a container
        GridPane sideBar = new GridPane();
        sideBar.setId("sideBar");
        sideBar.add(username, 0, 0);
        sideBar.add(reducedCO2, 0, 1);
        sideBar.add(pointsText, 0, 2);
        sideBar.add(followersText, 0, 3);
        sideBar.add(followingText, 0, 4);

        return sideBar;
    }

    /**
     * makes the your world screen.
     *
     * @return your world screen
     */
    private static BorderPane yourWorldScreen() {
        int points = ApplicationMethods.getPoints();
        int level  = ApplicationMethods.getLevel(points);

        // make the your world images
        String planetLink = "file:src/planets/levelOneWorld.gif";
        if (33 <= level && level < 66) {
            planetLink = "file:src/planets/levelTwoWorld.gif";
        } else if (level >= 66) {
            planetLink = "file:src/planets/levelThreeWorld.gif";
        }

        // make the your world view
        Image world = new Image(planetLink);
        ImageView yourWorldView = new ImageView();
        yourWorldView.setImage(world);

        // makes the level text
        Label levelStatus = new Label("Lv. " + level);
        levelStatus.setId("levelStatus");

        // make the total points text
        Label totalPoints = new Label(points + " points");
        totalPoints.setId("levelStatus");

        // makes a container for the points
        HBox pointsAndStatus = new HBox(250);
        pointsAndStatus.setId("commonContainer");
        pointsAndStatus.getChildren().addAll(levelStatus, totalPoints);

        // makes the level bar
        ProgressBar levelBar = new ProgressBar(ApplicationMethods.getLevelProgress(points));

        // makes a container for the level attributes
        VBox levelContainer = new VBox();
        levelContainer.setId("levelContainer");
        levelContainer.getChildren().addAll(levelBar, pointsAndStatus);

        // makes the page and adds the nodes
        GridPane yourWorldCenter = new GridPane();
        yourWorldCenter.setId("yourWorldPage");
        yourWorldCenter.add(levelContainer, 0, 0);
        yourWorldCenter.add(yourWorldView, 0, 1);
        yourWorldCenter.setAlignment(Pos.BOTTOM_CENTER);

        // makes the page and sets the sidebar
        BorderPane yourWorldPage = new BorderPane();
        yourWorldPage.setCenter(yourWorldCenter);
        yourWorldPage.setLeft(sideBar());

        // returns the page
        return yourWorldPage;
    }

    /**
     * makes the settings screen.
     *
     * @return settings screen
     */
    private static BorderPane settingsScreen() {
        // set the status of the dark mode
        String status = "Enable";

        // makes the dark mode button
        JFXToggleNode darkTheme = new JFXToggleNode();
        MaterialDesignIconView darkThemeIcon =
                new MaterialDesignIconView(MaterialDesignIcon.THEME_LIGHT_DARK);
        darkThemeIcon.setSize("50px");
        darkTheme.setGraphic(new Label("Enable dark theme", darkThemeIcon));
        darkTheme.setId("settingButtons");

        // if the dark mode is enabled, we will have the disable button
        if (theme.equals("src/styles/mainSceneDarkTheme.css")) {
            darkTheme.setSelected(true);
            darkTheme.setGraphic(new Label("Disable dark theme", darkThemeIcon));
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
        logoutButton.setId("settingButtons");

        // if we press the button we log out
        logoutButton.setOnAction(e -> {
            ComCached.logout();
            loginScene();
        });


        // make the page and add the nodes
        VBox settingsCenter = new VBox(10);
        settingsCenter.setId("settingPage");
        settingsCenter.getChildren().addAll(darkTheme, logoutButton);

        // makes the page and sets the sidebar
        BorderPane settingsPage = new BorderPane();
        settingsPage.setCenter(settingsCenter);
        settingsPage.setLeft(sideBar());

        // return the page
        return settingsPage;
    }

    /**
     * makes the about page.
     *
     * @return the about page
     */
    private static BorderPane aboutScreen() {

        // makes the label with the about text
        Label aboutText = new Label("Welcome to our App *insert app name* \n"
                + "One of my favorite quotes is that "
                + "if you move one grain of sand in the Sahara, you changed the whole Sahara. "
                + "And that is what we wanted to do. "
                + "We wanted to change the world, but we realised we couldn't do it alone. "
                + "So we decided to encourage other people to change the world with us. "
                + "This is one of the reasons we made this app, the other reason is that we "
                + "all want a good grade on our school project. \n\n"
                + "About the content of the app: \n"
                + "With the app you can track your CO\u2082 reduction, and score points with it."
                + "You can also have a little competition with "
                + "friends and send them challenges. \n\n"
                + "About the team: \n"
                + "* Eyüp, one of our server.database guys \n"
                + "* Elias, our other server.database guy \n"
                + "* Shruti, our server girl \n"
                + "* Marko, our handy man with extra focus on the server \n"
                + "* Guym, our gogreen.server and calculation guy \n"
                + "* Erwin, our gogreen.server and API guy \n"
                + "* Marit, our GUI girl \n\n"
                + "But this app wouldn't be possible without the brighter climate API");
        aboutText.setId("aboutText");

        // make the your world view
        Image apiButton = new Image("file:src/aboutPicture/apiButton.png");
        ImageView apiButtonView = new ImageView();
        apiButtonView.setImage(apiButton);

        // makes a container
        VBox aboutContainer = new VBox();
        aboutContainer.setId("longTextContainer");
        aboutContainer.getChildren().addAll(aboutText, apiButtonView);

        // makes the title
        Label aboutTitle = new Label("About");
        aboutTitle.setId("title");

        // makes the scrollpane
        ScrollPane aboutScroll = new ScrollPane();
        aboutScroll.setId("aboutPage");
        aboutScroll.setContent(aboutContainer);

        VBox aboutCenter = new VBox();
        aboutCenter.getChildren().addAll(aboutTitle, aboutScroll);

        // makes the about page and sets the sidebar
        BorderPane aboutPage = new BorderPane();
        aboutPage.setCenter(aboutCenter);
        aboutPage.setLeft(sideBar());

        return aboutPage;
    }

    /**
     * make the category screen.
     *
     * @return the category screen
     */
    private static Pane categoryScreen() {
        // make the category navigation bar
        JFXTabPane categoryNavigation = new JFXTabPane();
        categoryNavigation.setId("secondNavigation");

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

        // make the one time events tab
        Tab oteTab = new Tab();
        oteTab.setText("Luxury items");
        oteTab.setContent(oteScreen());

        // add all the tabs to the navigation bar
        categoryNavigation.getTabs().addAll(transportTab, foodTab, energyTab, extraTab, oteTab);

        // make the category page
        Pane categoryPage = new Pane();
        categoryPage.getChildren().addAll(categoryNavigation);

        // return the page
        return categoryPage;
    }

    /**
     * makes the transport screen.
     *
     * @return the transport screen
     */
    private static BorderPane transportScreen() {

        // make the description box
        TextField transportDescription = new TextField();
        transportDescription.setPromptText("Write a description here");
        transportDescription.setId("description");

        // make the text field
        TextField distance = new TextField();
        distance.setPromptText("Distance");
        distance.setId("distance");

        // make the error label
        Label transportInfo = new Label("Please fill in your travelled distance, \n"
                + "and select your method of transportation:");
        transportInfo.setId("information");

        //button for the cycle action
        JFXButton cycle = new JFXButton();
        FontAwesomeIconView bikeIcon = new FontAwesomeIconView(FontAwesomeIcon.BICYCLE);
        bikeIcon.setSize("50px");
        cycle.setGraphic(bikeIcon);
        cycle.setId("actionButton");

        // when you press the button you add a action, or get an error which will be displayed
        cycle.setOnAction(e -> {
            try {
                int distanceInt = Integer.parseInt(distance.getText());
                checkDescriptionLength(transportDescription.getText());
                System.out.println(transportDescription.getText());
                transportInfo.setText("");
                Transport.addCycleAction(distanceInt, transportDescription.getText());
                refresh();
            } catch (NumberFormatException exception) {
                transportInfo.setText("Please only use numbers!");
            } catch (ConnectIOException | IllegalArgumentException exception) {
                exception.printStackTrace();
                transportInfo.setText("Number is too high \nDid you really cycle that far?");
            } catch (DescriptionTooLong exception) {
                transportInfo.setText("You description is too long. \nChill down and please use less then 150 characters.");
            }
        });

        //button for the public transport action
        JFXButton publicTransport = new JFXButton();
        MaterialDesignIconView subwayIcon = new MaterialDesignIconView(MaterialDesignIcon.SUBWAY);
        subwayIcon.setSize("50px");
        publicTransport.setGraphic(subwayIcon);
        publicTransport.setId("actionButton");

        // when you press the button you add an action or get an error which will be displayed
        publicTransport.setOnAction(e -> {
            try {
                checkDescriptionLength(transportDescription.getText());
                int distanceInt = Integer.parseInt(distance.getText());
                System.out.println(transportDescription.getText());
                transportInfo.setText("");
                Transport.addPublicTransportAction(distanceInt, transportDescription.getText());
                refresh();
            } catch (NumberFormatException exception) {
                // throw error
                transportInfo.setText("Please only use numbers!");
            } catch (ConnectIOException | IllegalArgumentException exception) {
                exception.printStackTrace();
                transportInfo.setText("Number is too high \n"
                        + "Did you really used public transport that far?");
            } catch (DescriptionTooLong exception) {
                transportInfo.setText("You description is too long. \nChill down and please use less then 150 characters.");
            }
        });

        //button for the car action
        JFXButton car = new JFXButton();
        FontAwesomeIconView carIcon = new FontAwesomeIconView(FontAwesomeIcon.AUTOMOBILE);
        carIcon.setSize("50px");
        car.setGraphic(carIcon);
        car.setId("actionButton");

        // when you press the button you will add an action and
        // if you get an error it will be displayed
        car.setOnAction(e -> {
            try {
                checkDescriptionLength(transportDescription.getText());
                int distanceInt = Integer.parseInt(distance.getText());
                System.out.println(transportDescription.getText());
                transportInfo.setText("");
                Transport.addCarAction(distanceInt, transportDescription.getText());
                refresh();
            } catch (NumberFormatException exception) {
                // throw error
                transportInfo.setText("Please only use numbers!");
            } catch (ConnectIOException | IllegalArgumentException exception) {
                exception.printStackTrace();
                transportInfo.setText("Number is too high \n"
                        + "Did you really drove your car that far?");
            } catch (DescriptionTooLong exception) {
                transportInfo.setText("You description is too long. \nChill down and please use less then 150 characters.");
            }
        });

        //button for the plane action
        JFXButton plane = new JFXButton();
        FontAwesomeIconView planeIcon = new FontAwesomeIconView(FontAwesomeIcon.PLANE);
        planeIcon.setSize("50px");
        plane.setGraphic(planeIcon);
        plane.setId("actionButton");

        // when you press a button you will add an action
        // and if you get an error it will be displayed
        plane.setOnAction(e -> {
            try {
                checkDescriptionLength(transportDescription.getText());
                int distanceInt = Integer.parseInt(distance.getText());
                System.out.println(transportDescription.getText());
                transportInfo.setText("");
                Transport.addPlaneAction(distanceInt, transportDescription.getText());
                refresh();
            } catch (NumberFormatException exception) {
                // throw error
                transportInfo.setText("Please only use numbers!");
            } catch (ConnectIOException | IllegalArgumentException exception) {
                exception.printStackTrace();
                transportInfo.setText("Number is too high \n"
                        + "You can't fly around the world multiple times!");
            } catch (DescriptionTooLong exception) {
                transportInfo.setText("You description is too long. \nChill down and please use less then 150 characters.");
            }
        });

        // make the kilometer label and the distance container
        Label km = new Label("km");
        km.setId("information");

        HBox distanceContainer = new HBox(10);
        distanceContainer.setId("commonContainer");
        distanceContainer.getChildren().addAll(distance, km);


        // make the transport page
        GridPane transportCenter = new GridPane();
        transportCenter.add(transportInfo, 0, 0);
        transportCenter.add(distanceContainer, 0, 1);
        transportCenter.add(transportDescription, 0, 2);
        transportCenter.add(cycle, 0, 3);
        transportCenter.add(publicTransport, 0, 4);
        transportCenter.add(car, 0, 5);
        transportCenter.add(plane, 0, 6);

        transportCenter.setId("transportPage");

        // make the page and set the sidebar
        BorderPane transportPage = new BorderPane();
        transportPage.setCenter(transportCenter);
        transportPage.setLeft(sideBar());

        // return the page
        return transportPage;
    }

    /**
     * make the food screen.
     *
     * @return
     */
    private static BorderPane foodScreen() {
        // info label
        Label foodInfo = new Label("Please select the characteristics of your meal:");
        foodInfo.setId("information");

        // make the description box
        TextField foodDescription = new TextField();
        foodDescription.setPromptText("Write a description here");
        foodDescription.setId("description");

        //makes send button
        JFXButton send = new JFXButton("Approve");
        send.setId("actionButton");

        // make the check boxes
        JFXCheckBox veggie = new JFXCheckBox("Veggie");
        JFXCheckBox locally = new JFXCheckBox("Locally");
        JFXCheckBox bio     = new JFXCheckBox("Biological");

        // when you press the send button, it will look what is selected and add those actions
        send.setOnAction(e -> {
            try {
                checkDescriptionLength(foodDescription.getText());
                Food.addAction(veggie.isSelected(), locally.isSelected(), bio.isSelected(), foodDescription.getText());
                System.out.println(foodDescription.getText());
                refresh();
            } catch (ConnectIOException | IllegalArgumentException e1) {
                foodInfo.setText("You have reached the daily limit of food actions! \n");
                e1.printStackTrace();
            } catch (DescriptionTooLong exception) {
                foodInfo.setText("You description is too long. \nChill down and please use less then 150 characters.");
            }

            // then sets it to false to select it again
            veggie.setSelected(false);
            locally.setSelected(false);
            bio.setSelected(false);
        });

        //make the page and will add the nodes
        GridPane foodCenter = new GridPane();
        foodCenter.add(foodInfo, 0, 0);
        foodCenter.add(veggie, 0, 1);
        foodCenter.add(locally, 0, 2);
        foodCenter.add(bio, 0, 3);
        foodCenter.add(foodDescription, 0, 4);
        foodCenter.add(send, 0, 5);
        foodCenter.setId("foodPage");

        // make the page and set the sidebar
        BorderPane foodPage = new BorderPane();
        foodPage.setCenter(foodCenter);
        foodPage.setLeft(sideBar());

        // return the body
        return foodPage;
    }

    /**
     * makes the energy screen.
     *
     * @return the energy screen
     */
    private static BorderPane energyScreen() {

        // make the labels
        Label waterTime = new Label("Add shower time.");
        waterTime.setId("information");
        Label waterInfo = new Label("Slide to the amount of minutes you showered:");
        waterInfo.setId("error");

        // make the description textfield
        TextField waterDescription = new TextField();
        waterDescription.setPromptText("Write a description here");
        waterDescription.setId("description");

        // makes the add button
        JFXButton addWater = new JFXButton("Add shower time");
        addWater.setId("smallButton");

        // make the water time slider
        JFXSlider waterTimeSlider = new JFXSlider(0, 60, 0);

        // make the water container
        VBox waterContainer = new VBox(10);
        waterContainer.setId("waterContainer");
        waterContainer.getChildren().addAll(waterTime, waterInfo,
                waterTimeSlider, waterDescription, addWater);

        // if the add button is pressed, checks the conditions and sends the action
        addWater.setOnAction(e -> {
            int value = (int) Math.round(waterTimeSlider.getValue());
            if (value != 0) {
                waterInfo.setText("");
                try {
                    checkDescriptionLength(waterDescription.getText());
                    System.out.println(waterDescription.getText());
                    Energy.addReduceWater(value, waterDescription.getText());
                    System.out.println(value);
                    refresh();
                } catch (ConnectIOException e1) {
                    e1.printStackTrace();
                } catch (DescriptionTooLong exception) {
                    waterInfo.setText("You description is too long. \nChill down and please use less then 150 characters.");
                }
            } else {
                waterInfo.setText("Please fill in the amount of minutes you showered.");
            }
        });

        // make the labels
        Label temperature = new Label("Add house temperature.");
        temperature.setId("information");
        Label temperatureInfo = new Label("Slide to your house temperature:");
        temperatureInfo.setId("error");

        // make the description textfield
        TextField tempratureDescription = new TextField();
        tempratureDescription.setPromptText("Write a description here");
        tempratureDescription.setId("description");

        // make the button
        JFXButton addTemperature = new JFXButton("Add house temperature");
        addTemperature.setId("smallButton");

        // make the water time slider
        JFXSlider temperatureSlider = new JFXSlider(15, 30, 15);

        // make the temperature container
        VBox temperatureContainer = new VBox(10);
        temperatureContainer.setId("tempratureContainer");
        temperatureContainer.getChildren().addAll(temperature, temperatureInfo,
                temperatureSlider, tempratureDescription, addTemperature);

        // when pressed it will send an action
        addTemperature.setOnAction(e -> {
            int value = (int) Math.round(temperatureSlider.getValue());
            System.out.println(value);
            try {
                checkDescriptionLength(tempratureDescription.getText());
                System.out.println(tempratureDescription.getText());
                Energy.addReduceEnergyAction(value, tempratureDescription.getText());
                refresh();
            } catch (ConnectIOException e1) {
                e1.printStackTrace();
            } catch (DescriptionTooLong exception) {
                temperatureInfo.setText("You description is too long. \nChill down and please use less then 150 characters.");
            }
        });


        // makes the page and adds the nodes
        VBox energyCenter = new VBox();
        energyCenter.getChildren().addAll(waterContainer, temperatureContainer);
        energyCenter.setId("energyPage");

        // make the page and set the sidebar
        BorderPane energyPage = new BorderPane();
        energyPage.setCenter(energyCenter);
        energyPage.setLeft(sideBar());

        // returns the page
        return energyPage;
    }

    /**
     * makes the extra screen.
     *
     * @return the extra screen
     */
    private static BorderPane extraScreen() {
        Label extrainfo = new Label("Add extra action:");
        extrainfo.setId("information");

        // make the description field
        TextField extraDescription = new TextField();
        extraDescription.setPromptText("Write a description here");
        extraDescription.setId("description");

        // makes the clean surrounding button
        JFXButton cleanSurrounding = new JFXButton();
        OctIconView trashIcon = new OctIconView(OctIcon.TRASHCAN);
        trashIcon.setSize("50px");
        cleanSurrounding.setGraphic(new Label("Clean surrounding", trashIcon));
        cleanSurrounding.setId("actionButton");

        // when pressed it will send the action
        cleanSurrounding.setOnAction(e -> {
            try {
                checkDescriptionLength(extraDescription.getText());
                System.out.println(extraDescription.getText());
                Extra.addCleanSurroundingAction(extraDescription.getText());
                refresh();
            } catch (DescriptionTooLong exception) {
                extrainfo.setText("You description is too long. \nChill down and please use less then 150 characters.");
            }
        });

        // makes the recycle button
        JFXButton recycle = new JFXButton();
        MaterialDesignIconView recycleIcon = new MaterialDesignIconView(MaterialDesignIcon.RECYCLE);
        recycleIcon.setSize("50px");
        recycle.setGraphic(new Label("Recycle", recycleIcon));
        recycle.setId("actionButton");

        // when pressed it will send the action
        recycle.setOnAction(e -> {
            try {
                checkDescriptionLength(extraDescription.getText());
                System.out.println(extraDescription.getText());
                Extra.addRecycleAction(extraDescription.getText());
                refresh();
            } catch (DescriptionTooLong exception) {
                extrainfo.setText("You description is too long. \nChill down and please use less then 150 characters.");
            }

        });

        // makes the page and adds the nodes
        GridPane extraCenter = new GridPane();
//        extraCenter.add(extrainfo, 0, 0);
        extraCenter.add(extraDescription, 0, 0);
        extraCenter.add(cleanSurrounding, 0, 1);
        extraCenter.add(recycle, 0, 2);
        extraCenter.setId("extraPage");

        // makes the page and sets the sidebar
        BorderPane extraPage = new BorderPane();
        extraPage.setCenter(extraCenter);
        extraPage.setLeft(sideBar());

        // returns the page
        return extraPage;
    }

    /**
     * makes the one time events screen.
     *
     * @return the one time events page
     */
    private static BorderPane oteScreen() {
        // makes the label
        Label oteInfo = new Label("Select according to your lifestyle:");
        oteInfo.setId("information");

        // makes the solar panel toggle
        JFXToggleButton solarPanels = new JFXToggleButton();
        solarPanels.setText("Solar panels");
        solarPanels.setSelected(ApplicationMethods.isSolarPanel());
        solarPanels.setOnAction(e -> {
            if (solarPanels.isSelected()) {
                OneTimeEvent.addSolarPanelAction();
                refresh();
            }
        });

        // makes the electric car toggle
        JFXToggleButton electricCar = new JFXToggleButton();
        electricCar.setSelected(ApplicationMethods.isElectricCar());
        electricCar.setText("Electric car");
        electricCar.setOnAction(e -> {
            if (electricCar.isSelected()) {
                OneTimeEvent.addElectricCarAction();
                refresh();
            }
        });

        // makes the joined a group toggle
        JFXToggleButton joinedGroup = new JFXToggleButton();
        joinedGroup.setSelected(ApplicationMethods.isEnvGroup());
        joinedGroup.setText("Joined environment group");
        joinedGroup.setOnAction(e -> {
            if (joinedGroup.isSelected()) {
                OneTimeEvent.addEvGroupAction();
                refresh();
            }
        });

        // makes the one time event page
        GridPane oteCenter = new GridPane();
        oteCenter.add(oteInfo, 0, 0);
        oteCenter.add(solarPanels, 0, 1);
        oteCenter.add(electricCar, 0, 2);
        oteCenter.add(joinedGroup, 0, 3);
        oteCenter.setId("otePage");

        // makes the page and sets the sidebar
        BorderPane otePage = new BorderPane();
        otePage.setCenter(oteCenter);
        otePage.setLeft(sideBar());

        return otePage;
    }

    /**
     * make the stats screen.
     *
     * @return the stats screen
     */
    private static Pane statsScreen() {
        // make the stats navigation bar
        JFXTabPane statsNavigation = new JFXTabPane();
        statsNavigation.setId("secondNavigation");

        // make the overview tab
        Tab overview = new Tab();
        overview.setText("Overview");
        overview.setContent(overviewScreen());

        // make the history tab
        Tab history = new Tab();
        history.setText("History");
        history.setContent(historyScreen());

        // add all the tabs to the navigation bar
        statsNavigation.getTabs().addAll(overview, history);

        // make the stats page
        Pane statsPage = new Pane();
        statsPage.getChildren().addAll(statsNavigation);

        return statsPage;
    }

    /**
     * make the overview screen.
     *
     * @return the overview screen
     */
    private static BorderPane overviewScreen() {
        // get the amount saved
        double amountSaved = ApplicationMethods.getSavedCarbon();

        // makes the labels
        Label amountSavedLabel = new Label(amountSaved + " kg CO\u2082 saved");
        amountSavedLabel.setId("title");

        // makes the xAxis and yAxis
        double[]         data  = ComCached.getRecentCOSavings();
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setTickLabelsVisible(false);

        // makes the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);

        // sets the title and make data
        lineChart.setTitle("Recent CO\u2082 reductions");
        XYChart.Series series = new XYChart.Series();
        lineChart.setLegendVisible(false);

        // sets the data
        double total = ApplicationMethods.getSavedCarbon();
        for (int i = data.length; i > 0; i--) {
            series.getData().add(new XYChart.Data(i, total));
            total -= data[i - 1];
        }
        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false);

        // make the overview page
        GridPane overviewCenter = new GridPane();
        overviewCenter.add(amountSavedLabel, 0, 0);
        overviewCenter.add(lineChart, 0, 1);
        overviewCenter.setId("overviewPage");

        // makes the page and sets the sidebar
        BorderPane overviewPage = new BorderPane();
        overviewPage.setCenter(overviewCenter);
        overviewPage.setLeft(sideBar());

        // return the page
        return overviewPage;
    }

    private static BorderPane historyScreen() {
        // make the recent activites text
        Label historyTitle = new Label("Recent activities");
        historyTitle.setId("title");

       // makes the list
        GridPane historyList = new GridPane();
        historyList.setId("historyList");

        // makes the header
        GridPane historyHeader = new GridPane();
        historyHeader.setId("historyList");
        historyHeader.add(new Label("Recent activity:"), 0, 0);
        historyHeader.add(new Label("Date:"), 1, 0);
        historyHeader.add(new Label("Description:"), 2, 0);

        // sets the column width
        ColumnConstraints activityCol = new ColumnConstraints();
        activityCol.setMinWidth(220);
        activityCol.setMaxWidth(220);
        ColumnConstraints dateCol = new ColumnConstraints();
        dateCol.setMinWidth(180);
        dateCol.setMaxWidth(180);
        ColumnConstraints descriptionCol = new ColumnConstraints();
        descriptionCol.setMaxWidth(275);

        historyList.getColumnConstraints().addAll(activityCol, dateCol, descriptionCol);
        historyHeader.getColumnConstraints().addAll(activityCol, dateCol, descriptionCol);

        // add the history to the page
        int pos = 0;
        DateFormat formatter = new SimpleDateFormat("d MMM YYYY \nHH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        for (Action a : ComCached.getLastThreeActions()) {
            Label action = new Label(a.getAction());
            action.setWrapText(true);

            Label date = new Label(formatter.format(new Date(a.getDate())) + " (UTC)");
            date.setWrapText(true);

            Label description = new Label(a.getDescription());
            description.setWrapText(true);

            historyList.add(action, 0, pos);
            historyList.add(date, 1, pos);
            historyList.add(description, 2, pos);
            pos++;
        }

        // makes the scrollpane
        ScrollPane historyPane = new ScrollPane();
        historyPane.setContent(historyList);
        historyPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        historyPane.setId("historyScrollPane");

        // makes the page
        VBox historyCenter = new VBox();
        historyCenter.getChildren().addAll(historyTitle, historyPane);

        // makes the page and sets the sidebar
        BorderPane historyPage = new BorderPane();
        historyPage.setCenter(historyCenter);
        historyPage.setLeft(sideBar());

        return historyPage;
    }

    /**
     * make the competition screen.
     *
     * @return the competition screen
     */
    private static Pane competitionScreen() {
        // make the competition navigation bar
        JFXTabPane competitionNavigation = new JFXTabPane();
        competitionNavigation.setId("secondNavigation");

        // make the leaderboard tab
        Tab leaderboard = new Tab();
        leaderboard.setText("Leaderboard");
        leaderboard.setContent(leaderboardScreen());

        // make the friends tab
        Tab friends = new Tab();
        friends.setText("Following");
        friends.setContent(friendsScreen());

        // make the friends request tab
        Tab request = new Tab();
        request.setText("Followers");
        request.setContent(friendRequestScreen());

        // make the challenge tab
        Tab challenge = new Tab();
        challenge.setText("Challenges");
        challenge.setContent(challengeScreen());

        // add all the tabs to the navigation bar
        competitionNavigation.getTabs().addAll(leaderboard, challenge, friends, request);

        // make the leaderboard page
        Pane competitionPage = new Pane();
        competitionPage.getChildren().addAll(competitionNavigation);

        return competitionPage;
    }

    /**
     * make the leaderboard screen.
     *
     * @return the leaderboard screen
     */
    private static BorderPane leaderboardScreen() {

        // make the leaderboard title
        Label header = new Label("Leaderboard");
        header.setId("title");

        // make the leaderboard
        GridPane leaderboard = new GridPane();
        leaderboard.setId("leaderboard");

        // make the header
        leaderboard.add(new Label("Pos"), 0, 0);
        leaderboard.add(new Label("Username"), 1, 0);
        leaderboard.add(new Label("Points"), 2, 0);
        leaderboard.add(new Label("Level"), 3, 0);

        // get the top ten
        ArrayList<CompareFriends> topTen = ComCached.getLeaderboard();

        // place all the people in the leaderboard
        int pos = 1;
        for (CompareFriends users : topTen) {
            String username = ApplicationMethods.decodeUsername(users.getUsername());
            int score = users.getScore();
            int level = ApplicationMethods.getLevel(score);
            leaderboard.add(new Label(pos + "."), 0, pos);
            leaderboard.add(new Label(username), 1, pos);
            leaderboard.add(new Label(String.valueOf(score)), 2, pos);
            leaderboard.add(new Label(String.valueOf(level)), 3, pos);
            pos++;
        }

        // makes the leaderboard page
        VBox leaderboardCenter = new VBox();
        leaderboardCenter.getChildren().addAll(header, leaderboard);

        // make the page and sets the sidebar
        BorderPane leaderboardPage = new BorderPane();
        leaderboardPage.setCenter(leaderboardCenter);
        leaderboardPage.setLeft(sideBar());

        // returns the page
        return leaderboardPage;
    }

    /**
     * make the friends screen.
     *
     * @return the friends screen
     */
    private static BorderPane friendsScreen() {

        // make the title
        Label title = new Label("You follow:");
        title.setId("title");

        // gets the friendlist
        GridPane friendsList = followingList();
        friendsList.setId("friendsList");

        // makes the scroll pane
        ScrollPane followingList = new ScrollPane();
        followingList.setPrefSize(500, 400);
        followingList.setContent(friendsList);

        // makes the search field
        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        searchField.setPrefWidth(300);
        searchField.setAlignment(Pos.CENTER);

        // makes the search button
        JFXButton searchButton = new JFXButton("Search");
        searchButton.setId("smallButton");
        searchButton.setOnAction(e -> {
            String user = ApplicationMethods.encodeUsername(searchField.getText());
            if(!ComCached.addFriend(user)) {
                searchField.setText("");
                searchField.setPromptText("Friend not found");
            }
            followingList.setContent(followingList());
        });

        // puts the search field and search button together
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);
        searchBar.getChildren().addAll(searchField, searchButton);


        // makes the friends page
        VBox friendsCenter = new VBox(5);
        friendsCenter.getChildren().addAll(title, followingList, searchBar);

        // makes the page and sets the sidebar
        BorderPane friendsPage = new BorderPane();
        friendsPage.setCenter(friendsCenter);
        friendsPage.setLeft(sideBar());

        // returns the friendspage
        return friendsPage;
    }

    /**
     * makes a gridpane of the people you follow.
     *
     * @return the gridpane with people you follow
     */
    private static GridPane followingList() {
        // makes the friendlist
        GridPane friendsList = new GridPane();
        friendsList.setId("friendsList");

        // getting the friends
        ArrayList<CompareFriends> friends = ComCached.getFriends();

        // fills the friendlist with your friends
        if (!friends.isEmpty()) {
            int pos = 1;
            for (CompareFriends friend : friends) {
                String username = ApplicationMethods.decodeUsername(friend.getUsername());
                int score = friend.getScore();
                int level = ApplicationMethods.getLevel(score);
                friendsList.add(new Label(username), 0, pos);
                friendsList.add(new Label(score + " points"), 1, pos);
                friendsList.add(new Label("Level " + level), 2, pos);
                pos++;
            }
        }

        return friendsList;
    }

    /**
     * make the friend request screen.
     *
     * @return the friend request screen
     */
    private static BorderPane friendRequestScreen() {
        // get the followers
        ArrayList<CompareFriends> friends = ComCached.getFollowers();

        // makes the title
        Label nrRequest = new Label(friends.size() + " followers:");
        nrRequest.setId("title");

        // make the gridpane for the people who follow you
        GridPane followersContainer = new GridPane();
        followersContainer.setId("followersContainer");

        int pos = 1;
        // puts all the friendrequests and buttons in the container
        for (CompareFriends followers : friends) {
            String username = ApplicationMethods.decodeUsername(followers.getUsername());
            int    score    = followers.getScore();
            int    level    = ApplicationMethods.getLevel(score);

            // make the username label
            Label user = new Label(username);
            followersContainer.add(user, 0, pos);
            followersContainer.add(new Label(score + " points"), 1, pos);
            followersContainer.add(new Label("Level " + level), 2, pos);
            pos++;
        }

        // make one container for the username and buttons
        HBox container = new HBox(10);
        container.getChildren().addAll(followersContainer);

        // make the scroll pane
        ScrollPane followersList = new ScrollPane();
        followersList.setId("followersList");
        followersList.setContent(container);

        // make the request page
        VBox requestCenter = new VBox();
        requestCenter.getChildren().addAll(nrRequest, followersList);

        // makes the page and sets the sidebar
        BorderPane requestPage = new BorderPane();
        requestPage.setCenter(requestCenter);
        requestPage.setLeft(sideBar());

        // return the request page
        return requestPage;
    }

    /**
     * makes the challenge page.
     *
     * @return the challenge page
     */
    private static BorderPane challengeScreen() {
        // ask a friend to challenge
        Label challengeInfo = new Label("Who is the first to reach...");
        challengeInfo.setId("information");

        TextField goalField = new TextField();
        goalField.setId("challengeTextfield");
        goalField.setPromptText("fill in the amount of points");

        TextField userField = new TextField();
        userField.setId("challengeTextfield");
        userField.setPromptText("fill in the user");

        JFXButton addChallenge = new JFXButton("add the challenge");
        addChallenge.setId("smallButton");

        addChallenge.setOnAction(e -> {
            try {

                int    goal = Integer.parseInt(goalField.getText());
                String user = ApplicationMethods.encodeUsername(userField.getText());
                if (ApplicationMethods.getUsername().equals(user)){
                    challengeInfo.setText("You can't challenge yourself!");
                }else if (ComCached.addChallenge(user, goal)) {
                    refresh();
                } else {
                    challengeInfo.setText("Try someone else");
                }
            } catch (NumberFormatException exception) {
                challengeInfo.setText("Please fill in a number as goal");
            }
        });

        HBox addChallengeContainer = new HBox();
        addChallengeContainer.setId("addChallengeContainer");
        addChallengeContainer.getChildren().addAll(goalField, userField, addChallenge);

        VBox addChallengeVbox = new VBox();
        addChallengeVbox.getChildren().addAll(challengeInfo, addChallengeContainer);

        ScrollPane challengeContainer = new ScrollPane();
        challengeContainer.setId("challengeLists");
        challengeContainer.setContent(challengeList());

        ScrollPane receivedChallenge = new ScrollPane();
        receivedChallenge.setId("challengeLists");
        receivedChallenge.setContent(receivedList());

        Label challengeTitle = new Label("Current challenges");
        challengeTitle.setId("information");
        Label receivedTitle = new Label("Received challenges");
        receivedTitle.setId("information");

        VBox challengeCenter = new VBox();
        challengeCenter.setId("challengePage");
        challengeCenter.getChildren().addAll(addChallengeVbox, challengeTitle, challengeContainer, receivedTitle, receivedChallenge);

        // makes the page and sets the sidebar
        BorderPane challengePage = new BorderPane();
        challengePage.setCenter(challengeCenter);
        challengePage.setLeft(sideBar());

        // return the request page
        return challengePage;

    }

    private static GridPane challengeList() {
        // getting the challenges you accepted
        ArrayList<Challenge> challenges         = ComCached.getChallenges();
        GridPane             challengeContainer = new GridPane();
        challengeContainer.setId("challenges");

        try {
            int pos = 0;
            for (Challenge c : challenges) {
                if (c.getState() == 0) continue;

                String name  = c.getUserA();
                int    start = c.getScoreA();
                if (c.isOnA()) {
                    name = c.getUserB();
                    start = c.getScoreB();
                }

                Label goal = new Label("Goal: " + c.getGoal());
                challengeContainer.add(goal, 0, pos);

                String username = ApplicationMethods.decodeUsername(name);
                Label  user     = new Label("User: " + username);
                challengeContainer.add(user, 1, pos);

                if (c.getState() == 10 && c.isOnA()) {
                    // I won
                    challengeContainer.add(new Label("You won!"), 2, pos);
                } else if (c.getState() == 11 && !c.isOnA()) {
                    // opponent won
                    challengeContainer.add(new Label("You lost!"), 2, pos);
                } else {
                    ProgressBar progress = new ProgressBar(ApplicationMethods.getChallengeProgress(start, c.getGoal()));
                    progress.setId("challengeProgress");
                    challengeContainer.add(new Label("Progress:"), 2, pos);
                    challengeContainer.add(progress, 3, pos);
                }

                pos++;
            }
        } catch (NullPointerException e) {
            Label info = new Label("no challenges");
            info.setId("information");
            challengeContainer.getChildren().add(info);
        }

        return challengeContainer;
    }

    private static GridPane receivedList() {
        // getting the challenges that need to be accepted
        GridPane receivedChallenge = new GridPane();
        receivedChallenge.setId("challenges");

        ArrayList<Challenge> challenges = ComCached.getChallenges();
        try {
            int pos = 0;
            for (Challenge c : challenges) {
                if (c.getState() != 0) continue;

                String name = c.getUserA();
                if (c.isOnA()) name = c.getUserB();

                Label goal = new Label("Goal: " + c.getGoal());
                receivedChallenge.add(goal, 0, pos);

                String username = ApplicationMethods.decodeUsername(name);
                Label  user     = new Label("User: " + username);
                receivedChallenge.add(user, 1, pos);

                if (!c.isOnA()) {
                    JFXButton accept = new JFXButton("Accept Challenge");
                    accept.setId("smallButton");
                    receivedChallenge.add(accept, 2, pos);

                    accept.setOnAction(e -> {
                        if (ComCached.acceptChallenge(c)) {
                            refresh();
                        } else {
                            System.out.println("nope");
                        }
                    });
                } else {
                    Label wait = new Label("Waiting for response");
                    receivedChallenge.add(wait, 2, pos);
                }

                pos++;
            }
        } catch (NullPointerException e) {
            System.out.println("null pointer");
            Label info = new Label("no recieved challenges");
            info.setId("information");
            receivedChallenge.add(info, 0, 0);
        }

        return receivedChallenge;
    }

    /**
     * Toggle visibility between Textfield and PasswordField.
     *
     * @param visible   the Textfield
     * @param invisible the PasswordField
     * @param show      whether to show the password
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
     *
     * @param scene scene
     */
    private static void show(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }

    /**
     * refreshes the program.
     */
    private static void refresh() {
        ApplicationMethods.setPresets();
        mainScreen();
    }

    private static void checkDescriptionLength(String description) throws DescriptionTooLong {
        if (description.length() > 150) {
            throw new DescriptionTooLong("The description needs to be less then 150 characters");
        }

    }

    /**
     * this method starts the application.
     *
     * @param stage stage
     */
    @Override
    public void start(Stage stage) {
        ApplicationMethods.onLoad();

        this.stage = stage;
        stage.getIcons().add(new Image("file:src/planets/icon.gif"));
        stage.setResizable(false);
        stage.setTitle("GoGreen");

        // sets the theme
        theme = "src/styles/mainSceneDefaultTheme.css";

        //the silentLogin will login for the user
        if (ComCached.silentLogin()) {
            ApplicationMethods.setPresets();
            mainScreen();
        } else {
            loginScene();
        }
    }

}