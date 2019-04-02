package gogreen;

import client.Communication;
import client.CompareFriends;
import com.jfoenix.controls.*;

import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
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

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.ConnectIOException;
import java.util.ArrayList;

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
        ApplicationMethods.onLoad();

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
        Scene loginScene = new Scene(loginScreen(),1000,600);
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
        JFXToggleNode          showPassword = new JFXToggleNode();
        MaterialDesignIconView showIcon     = new MaterialDesignIconView(MaterialDesignIcon.EYE);
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
        loginPage.setId("loginPage");
        loginPage.add(loginText, 0, 0);
        loginPage.add(username, 0, 1);
        loginPage.add(rememberUser, 1, 1);
        loginPage.add(new StackPane(password, visiblePassword), 0, 2);
        loginPage.add(showPassword, 1, 2);
        loginPage.add(login, 0, 3);
        loginPage.add(register, 1, 3);
        
        //and return it
        return loginPage;
    }
    
    /**
     * The body for the register display.
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
        JFXToggleNode          showPassword = new JFXToggleNode();
        MaterialDesignIconView showIcon     = new MaterialDesignIconView(MaterialDesignIcon.EYE);
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
        registerPage.setId("loginPage");
        registerPage.add(registerText, 0, 0);
        registerPage.add(username, 0, 1);
        registerPage.add(new StackPane(password, visiblePassword), 0, 2);
        registerPage.add(rememberUser, 1, 2);
        registerPage.add(new StackPane(passwordTwo, visiblePasswordTwo), 0, 3);
        registerPage.add(showPassword, 1, 3);
        registerPage.add(register, 0, 4);
        registerPage.add(back, 1, 4);

        //and returns it
        return registerPage;
    }
    
    /*
     * the main screen
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
        Scene mainScene = new Scene(body, 1000, 600);
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
        homeNavigation.setId("secondNavigation");
        homeNavigation.setPrefSize(500,575);

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
     * makes the your world screen.
     * @return your world screen
     */
    private static GridPane yourWorldScreen() {
        int points = client.Communication.getMyTotalScore();
        int level = ApplicationMethods.getLevel(points);

        // make the your world images
        String planetLink = "file:src/planets/levelOneWorld.gif";
        if (33 <= level && level < 66) {
            planetLink = "file:src/planets/levelTwoWorld.gif";
        } else if (level >= 66 ) {
            planetLink = "file:src/planets/levelThreeWorld.gif";
        }


        // make the your world view
        Image world = new Image(planetLink);
        ImageView yourWorldView = new ImageView();
        yourWorldView.setImage(world);

        // makes the level bar
        ProgressBar levelBar = new ProgressBar(ApplicationMethods.getLevelProgress(points));
        
        // makes the level text
        Label levelStatus = new Label("Lv. " + level);
        levelStatus.setId("levelStatus");

        // make the total points text
        Label totalPoints = new Label(points + " points");
        totalPoints.setId("levelStatus");

        HBox pointsAndStatus = new HBox(250);
        pointsAndStatus.setId("commonContainer");
        pointsAndStatus.getChildren().addAll(levelStatus, totalPoints);

        // makes a container for the level attributes
        VBox levelContainer = new VBox();
        levelContainer.setId("commonContainer");
        levelContainer.getChildren().addAll(levelBar, pointsAndStatus);

        
        // makes the page and adds the nodes
        GridPane yourWorldPage = new GridPane();
        yourWorldPage.setId("yourWorldPage");
        yourWorldPage.add(levelContainer, 0,0);
        yourWorldPage.add(yourWorldView,0,1);
        
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
        darkTheme.setGraphic(new Label(status + " dark theme", darkThemeIcon));
        darkTheme.setId("settingButtons");

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
        logoutButton.setId("settingButtons");
        
        // if we press the button we log out
        logoutButton.setOnAction(e -> {
            // TODO
            // log out method does not work...
            client.Communication.logout();
            loginScene();
            
        });
        
        // make the page and add the nodes
        VBox settingsPage = new VBox(10);
        settingsPage.setId("settingPage");
        settingsPage.getChildren().addAll(darkTheme, logoutButton);

        // return the page
        return settingsPage;
    }

    private static ScrollPane aboutScreen() {

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
                + "* Eyüp, one of our database guys \n"
                + "* Elias, our other database guy \n"
                + "* Shruti, our server girl \n"
                + "* Marko, our handy man with extra focus on the server \n"
                + "* Guym, our client and calculation guy \n"
                + "* Erwin, our client and API guy \n"
                + "* Marit, our GUI girl \n\n"
                + "But this app wouldn't be possible without the brighter climate API");
        aboutText.setId("aboutText");

        // make the your world view
        Image apiButton = new Image("file:src/aboutPicture/apiButton.png");
        ImageView apiButtonView = new ImageView();
        apiButtonView.setImage(apiButton);

        VBox aboutContainer = new VBox();
        aboutContainer.setId("longTextContainer");
        aboutContainer.getChildren().addAll(aboutText, apiButtonView);

        ScrollPane aboutPage = new ScrollPane();
        aboutPage.setId("aboutPage");
        aboutPage.setContent(aboutContainer);

        return aboutPage;
    }
    
    /**
     * make the category screen.
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
        oteTab.setText("One Time Event");
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
     * @return the transport screen
     */
    private static GridPane transportScreen() {

        // make the text field
        TextField distance = new TextField();
        distance.setPromptText("Distance");

        // make the error label
        Label errorMessage = new Label("");
        errorMessage.setId("error");

        //button for the cycle action
        JFXButton cycle = new JFXButton();
        FontAwesomeIconView bikeIcon = new FontAwesomeIconView(FontAwesomeIcon.BICYCLE);
        bikeIcon.setSize("50px");
        cycle.setGraphic(bikeIcon);
        cycle.setId("actionButton");


        // when you press the button you add a action
        cycle.setOnAction(e -> {
            try {
                int distanceInt = Integer.parseInt(distance.getText());
                errorMessage.setText("");
                Transport.addCycleAction(distanceInt);
                refresh();
            } catch (NumberFormatException exception) {
                errorMessage.setText("Please only use numbers");
            } catch (ConnectIOException | IllegalArgumentException exception) {
                exception.printStackTrace();
            }
        });
        
        //button for the public transport action
        JFXButton publicTransport = new JFXButton();
        MaterialDesignIconView subwayIcon = new MaterialDesignIconView(MaterialDesignIcon.SUBWAY);
        subwayIcon.setSize("50px");
        publicTransport.setGraphic(subwayIcon);
        publicTransport.setId("actionButton");

        // when you press the button you add an action
        publicTransport.setOnAction(e -> {
            try {
                int distanceInt = Integer.parseInt(distance.getText());
                errorMessage.setText("");
                Transport.addPublicTransportAction(distanceInt);
                refresh();
            } catch (NumberFormatException exception) {
                // throw error
                errorMessage.setText("Please only use numbers");
            } catch (ConnectIOException | IllegalArgumentException exception) {
                exception.printStackTrace();
            }
        });
        
        //button for the car action
        JFXButton car = new JFXButton();
        FontAwesomeIconView carIcon = new FontAwesomeIconView(FontAwesomeIcon.AUTOMOBILE);
        carIcon.setSize("50px");
        car.setGraphic(carIcon);
        car.setId("actionButton");

        // when you press the button you will add an action
        car.setOnAction(e -> {
            try {
                int distanceInt = Integer.parseInt(distance.getText());
                errorMessage.setText("");
                Transport.addCarAction(distanceInt);
                refresh();
            } catch (NumberFormatException exception) {
                // throw error
                errorMessage.setText("Please only use numbers");
            } catch (ConnectIOException | IllegalArgumentException exception) {
                exception.printStackTrace();
            }
        });
        
        //button for the plane action
        JFXButton plane = new JFXButton();
        FontAwesomeIconView planeIcon = new FontAwesomeIconView(FontAwesomeIcon.PLANE);
        planeIcon.setSize("50px");
        plane.setGraphic(planeIcon);
        plane.setId("actionButton");

        // when you press a button you will add an action
        plane.setOnAction(e -> {
            try {
                int distanceInt = Integer.parseInt(distance.getText());
                errorMessage.setText("");
                Transport.addPlaneAction(distanceInt);
                refresh();
            } catch (NumberFormatException exception) {
                // throw error
                errorMessage.setText("Please only use numbers");
            } catch (ConnectIOException | IllegalArgumentException exception) {
                exception.printStackTrace();
            }
        });

        // make the kilometer label and the distance container
        Label km = new Label("km");
        km.setId("title");

        HBox distanceContainer = new HBox(10);
        distanceContainer.setId("commonContainer");
        distanceContainer.getChildren().addAll(distance, km, errorMessage);


        // make the transport page
        GridPane transportPage = new GridPane();
        transportPage.add(cycle, 0 ,0);
        transportPage.add(publicTransport, 0, 1);
        transportPage.add(car, 0, 2);
        transportPage.add(plane, 0,3 );
        transportPage.add(distanceContainer ,0,4);
        transportPage.setId("transportPage");
        
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
        veggie.setId("actionButton");

        // makes the locally toggle
        JFXToggleNode locally = new JFXToggleNode();
        FontAwesomeIconView locallyIcon = new FontAwesomeIconView(FontAwesomeIcon.MAP_MARKER);
        locallyIcon.setSize("50px");
        locally.setGraphic(new Label("Locally", locallyIcon));
        locally.setId("actionButton");

        // makes the bio toggle
        JFXToggleNode bio = new JFXToggleNode();
        FontAwesomeIconView leafIcon = new FontAwesomeIconView(FontAwesomeIcon.LEAF);
        leafIcon.setSize("50px");
        bio.setGraphic(new Label("Bio", leafIcon));
        bio.setId("actionButton");

        //makes send button
        JFXButton send = new JFXButton("Add action");
        send.setId("actionButton");
        
        // when you press the send button, it will look what is selected and add those actions
        send.setOnAction(e -> {
            try {
                Food.addAction(veggie.isSelected(), locally.isSelected(), bio.isSelected());
                refresh();
            } catch (ConnectIOException e1) {
                e1.printStackTrace();
            }

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
        foodPage.setId("foodPage");
        
        // return the body
        return foodPage;
    }
    
    /**
     * makes the energy screen.
     * @return the energy screen
     */
    private static GridPane energyScreen() {
        // makes the water time box
        JFXToggleNode waterTime = new JFXToggleNode();
        waterTime.setGraphic(new Label("Add shower time"));

        // make the water time slider
        JFXSlider waterTimeSlider = new JFXSlider(0,60,0);

        // make instruction and error label
        Label waterInfo = new Label("Slide to the amount you showered");
        waterInfo.setId("error");

        // make the water container
        VBox waterContainer = new VBox(10);
        waterContainer.setId("commonContainer");
        waterContainer.getChildren().addAll(waterTime);

        // makes the add button
        JFXButton addWater = new JFXButton("add shower time");
        addWater.setId("smallButton");

        // if the add button is pressed, checks the conditions and sends the action
        addWater.setOnAction(e -> {
            int value = (int) Math.round(waterTimeSlider.getValue());
            if (value != 0) {
                waterInfo.setText("");
                try {
                    Energy.addReduceWater(value);
                    refresh();
                } catch (ConnectIOException e1) {
                    e1.printStackTrace();
                }
        } else {
                waterInfo.setText("Please fill in the minutes you showered");
            }
        });

        // when pressed the input box will appear
        waterTime.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

            if (!waterTime.isSelected()) {
                waterContainer.getChildren().addAll(waterInfo, waterTimeSlider, addWater);
            } else {
                waterContainer.getChildren().removeAll(waterInfo, waterTimeSlider, addWater);
            }
        });

        // makes the  button
        JFXToggleNode temperature = new JFXToggleNode();
        temperature.setGraphic(new Label("Add house temperature"));

        // make the water time slider
        JFXSlider temperatureSlider = new JFXSlider(15,30,15);

        Label temperatureInfo = new Label("Slide to add your house temperature");
        temperatureInfo.setId("error");

        // make the temperature container
        VBox temperatureContainer = new VBox(10);
        temperatureContainer.setId("commonContainer");
        temperatureContainer.getChildren().addAll(temperature);

        JFXButton addTemperature = new JFXButton("add house temperature");
        addTemperature.setId("smallButton");

        // when pressed it will send an action
        addTemperature.setOnAction(e -> {
            int value = (int) Math.round(temperatureSlider.getValue());
            System.out.println(value);
            try {
                Energy.addReduceEnergyAction(value);
                refresh();
            } catch (ConnectIOException e1) {
                e1.printStackTrace();
            }
        });

        // when the temperature button is pressed, the box will appear
        temperature.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (!temperature.isSelected()) {
                temperatureContainer.getChildren().addAll(temperatureInfo, temperatureSlider, addTemperature);
            } else {
                temperatureContainer.getChildren().removeAll(temperatureInfo, temperatureSlider, addTemperature);
            }
        });

        // makes the page and adds the nodes
        GridPane energyPage = new GridPane();
        energyPage.add(waterContainer, 0, 0);
        energyPage.add(temperatureContainer, 0, 1);
        energyPage.setId("energyPage");

        // returns the page
        return energyPage;
    }
    
    /**
     * makes the extra screen.
     * @return the extra screen
     */
    private static GridPane extraScreen() {
        
        // makes the clean surronding button
        JFXButton cleanSurronding = new JFXButton();
        OctIconView trashIcon = new OctIconView(OctIcon.TRASHCAN);
        trashIcon.setSize("50px");
        cleanSurronding.setGraphic(new Label("Clean surronding", trashIcon));
        cleanSurronding.setId("actionButton");
        
        // when pressed it will send the action
        cleanSurronding.setOnAction(e -> {
            Extra.addCleanSurroundingAction();
            refresh();
        });
        
        // makes the recycle button
        JFXButton recycle = new JFXButton();
        MaterialDesignIconView recycleIcon = new MaterialDesignIconView(MaterialDesignIcon.RECYCLE);
        recycleIcon.setSize("50px");
        recycle.setGraphic(new Label("Recycle", recycleIcon));
        recycle.setId("actionButton");
        
        // when pressed it will send the action
        recycle.setOnAction(e -> {
            Extra.addRecycleAction();
            refresh();
        });
        
        // makes the page and adds the nodes
        GridPane extraPage = new GridPane();
        extraPage.add(cleanSurronding, 0,0 );
        extraPage.add(recycle,0,1);
        extraPage.setId("extraPage");
        
        // returns the page
        return extraPage;
    }

    /**
     * makes the one time events screen.
     * @return the one time events page
     */
    private static GridPane oteScreen() {

        // makes the solar panel toggle
        JFXToggleButton solarPanels = new JFXToggleButton();
        solarPanels.setText("Solar Panels");
        solarPanels.setOnAction(e -> {
            if(solarPanels.isSelected()){
                OneTimeEvent.addSolarPanelAction();
                refresh();
            }
        });

        // makes the electric car toggle
        JFXToggleButton electricCar = new JFXToggleButton();
        electricCar.setText("Electric car");
        electricCar.setOnAction(e -> {
            if (electricCar.isSelected()) {
                OneTimeEvent.addElectricCarAction();
                refresh();
            }
        });

        // makes the joined a group toggle
        JFXToggleButton joinedGroup = new JFXToggleButton();
        joinedGroup.setText("Joined environment group");
        joinedGroup.setOnAction(e -> {
            if (joinedGroup.isSelected()) {
                OneTimeEvent.addEvGroupAction();
                refresh();
            }
        });


        GridPane otePage = new GridPane();
        otePage.add(solarPanels,0,0);
        otePage.add(electricCar,0,1);
        otePage.add(joinedGroup,0,2);
        otePage.setId("otePage");
        return otePage;
    }
    
    /**
     * make the stats screen.
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
     * @return the overview screen
     */
    private static GridPane overviewScreen() {
        // get the amount saved
        double amountSaved = Communication.carbon().getCarbonReduced();

        // makes the labels
        Label amountSavedLabel = new Label(String.valueOf(amountSaved));
        amountSavedLabel.setId("amountSavedNr");

        Label amountSavedText = new Label("kg CO\u2082 saved");
        amountSavedText.setId("amountSavedText");

        // calculates the reference
        double reference = amountSaved * 0.02 ;

        Label referenceIntro = new Label("That is ");
        referenceIntro.setId("referenceText");

        Label referenceLabel = new Label(String.valueOf(reference));
        referenceLabel.setId("referenceNr");

        Label referenceText = new Label("Marit(s)");
        referenceText.setId("referenceText");

        // make the amount saved container
        VBox amountSavedContainer = new VBox();
        amountSavedContainer.getChildren().addAll(amountSavedLabel, amountSavedText);


        // make the reference container
        VBox referenceContainer = new VBox(2);
        referenceContainer.getChildren().addAll(referenceIntro, referenceLabel, referenceText);

        // make the overview page
        GridPane overviewPage = new GridPane();
        overviewPage.add(amountSavedContainer, 0,0);
        overviewPage.add(referenceContainer,1,0);
        overviewPage.setId("overviewPage");

        // return the page
        return overviewPage;
    }

    private static VBox historyScreen() {

        // make the recent activites text
        String historyText = "Recent Activities: \t\t date: \t\t\t time: \n ";
        Label history = new Label("");
        for (client.Action a : client.Communication.getLastThreeActions()) {
            historyText += a.getAction() + "\n";
        }
        history.setText(historyText);
        history.setId("history");
        
        VBox historyPage = new VBox();
        historyPage.getChildren().addAll(history);

        return historyPage;
    }
    
    /**
     * make the competition screen.
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
        ArrayList<CompareFriends> topTen = client.Communication.getLeaderboard();

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

        Label title = new Label("You follow:");
        title.setId("title");

        GridPane friendsList = followingList();

        // makes the scroll pane
        ScrollPane followingList = new ScrollPane();
        followingList.setPrefSize(500, 400);
        followingList.setContent(friendsList);

        // makes the search field
        TextField searchField = new TextField();
        searchField.setPromptText("search");
        searchField.setPrefWidth(300);
        searchField.setAlignment(Pos.CENTER);

        // makes the search button
        JFXButton searchButton = new JFXButton("Search");
        searchButton.setId("smallButton");
        searchButton.setOnAction(e -> {
            String user = ApplicationMethods.encodeUsername(searchField.getText());
            //System.out.println(client.Communication.checkUsername(user));

            //if (client.Communication.checkUsername(user)) {
                client.Communication.addFriend(user);
                followingList.setContent(followingList());
            //}
        });

        // puts the search field and search button together
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);
        searchBar.getChildren().addAll(searchField, searchButton);

        // makes the friends page
        VBox friendsPage = new VBox(5);
        friendsPage.getChildren().addAll(title, followingList, searchBar);

        // returns the friendspage
        return friendsPage;
    }

    private static GridPane followingList() {
        // makes the friendlist
        GridPane friendsList = new GridPane();
        friendsList.setId("friendsList");

        // getting the friends
        ArrayList<CompareFriends> friends = client.Communication.getFriends();

        // fills the friendlist with your friends
        if (!friends.isEmpty()) {
            int pos = 1;
            for (CompareFriends friend : friends) {
                String username = ApplicationMethods.decodeUsername(friend.getUsername());
                int score = friend.getScore();
                int level = ApplicationMethods.getLevel(score);
                friendsList.add(new Label(username), 0, pos);
                friendsList.add(new Label(score + " points"),1,pos);
                friendsList.add(new Label("Level " + level), 2, pos);
                pos++;
            }
        }

        return friendsList;
    }

    /**
     * make the friend request screen.
     * @return the friend request screen
     */
    private static VBox friendRequestScreen() {


        ArrayList<CompareFriends> friends = client.Communication.getFollowers();

        // makes the title
        Label nrRequest = new Label(friends.size() + " followers:");
        nrRequest.setId("title");

        // make the gridpane for the people who follow you
        GridPane followersContainer = new GridPane();
        followersContainer.setId("followersContainer");

        int pos = 1;
        // puts all the friendrequests and buttons in the container
        for (CompareFriends followers: friends) {
            String username = ApplicationMethods.decodeUsername(followers.getUsername());
            int score = followers.getScore();
            int level = ApplicationMethods.getLevel(score);
            // make the username label and the buttons
            Label user = new Label(username);
            followersContainer.add(user, 0,pos);
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
        VBox requestPage = new VBox();
        requestPage.getChildren().addAll(nrRequest, followersList);

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
    
    private static void refresh() {
        mainScreen();
    }
}