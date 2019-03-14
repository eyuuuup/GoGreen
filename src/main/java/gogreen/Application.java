package gogreen;

import client.Communication;
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
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;

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
     *
     * @param stage stage
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("GoGreen");



        //if the user chose to remember the password in the app
        //the silentLogin will login for the user
        theme = "src/styles/mainSceneDefaultTheme.css";
        if (client.Communication.silentLogin()) {
            mainScreen();
        } else {
            loginScreen();
        }
    }

    /**
     * This method displays the Login screen.
     */
    private void loginScreen() {
        GridPane body = loginBody();
        Scene loginScene = new Scene(body, 500, 500);
        //File f = new File("src/styles/style.css");
        //loginScene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        loginScene.getStylesheets().add(new File(theme).toURI().toString());
        show(loginScene);
    }

    /**
     * This method shows the login screen with a specific error.
     *
     * @param text the error
     */
    private void loginScreenError(String text) {
        GridPane body = loginBody();
        body.getChildren().remove(0);

        //the new error to show
        Label loginText = new Label(text);
        loginText.setMinWidth(200);
        loginText.setMaxWidth(200);
        loginText.setTextFill(Paint.valueOf("#FF0000"));
        loginText.setAlignment(Pos.CENTER);

        //and add it to the body
        body.add(loginText, 0, 0);
        Scene loginScene = new Scene(body, 500, 250);
        show(loginScene);
    }

    /**
     * This method displays the register screen.
     */
    private void registerScreen() {
        GridPane body = registerScreenBody();
        Scene registerScene = new Scene(body, 500, 500);
        registerScene.getStylesheets().add(new File(theme).toURI().toString());
        show(registerScene);
    }

    /**
     * This method displays the register screen with a specific error.
     *
     * @param text the error
     */
    private void registerScreenError(String text) {
        GridPane body = registerScreenBody();
        body.getChildren().remove(0);

        //the new error
        Label loginText = new Label(text);
        loginText.setTextFill(Paint.valueOf("#FF0000"));

        //and add it to the body
        body.add(loginText, 0, 0);
        Scene registerScene = new Scene(body, 500, 250);
        show(registerScene);
    }

    /**
     * The body for the register display.
     *
     * @return the body
     */
    private GridPane registerScreenBody() {
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
        CheckBox showPassword = new CheckBox();
        showPassword.setOnAction(e -> {
            ApplicationMethods.toggleVisibility(
                    visiblePassword, password, showPassword.isSelected());
            ApplicationMethods.toggleVisibility(
                    visiblePasswordTwo, passwordTwo, showPassword.isSelected());

        });

        //checkbox if the user wants his username and password to be remembered
        CheckBox rememberUser = new CheckBox();

        Label registerText = new Label("Register:");
        registerText.setId("loginText");

        //button to register the stuff the user filled in
        JFXButton register = new JFXButton("Register");
        register.setOnAction(e -> {
            try {
                ApplicationMethods.register(username.getText(), password.getText(),
                        passwordTwo.getText(), rememberUser.isSelected());
            } catch (NullPointerException | IllegalArgumentException | IllegalAccessException | FileNotFoundException exception) {
                registerText.setText(exception.getMessage());
                registerText.setTextFill(Paint.valueOf("#FF0000"));
            }
        });


        //creates the gridpane with all the nodes in it
        GridPane body = new GridPane();
        body.setVgap(5);
        body.setHgap(10);
        body.add(registerText, 0, 0);
        body.add(username, 0, 1);
        body.add(new StackPane(password, visiblePassword), 0, 2);
        body.add(new StackPane(passwordTwo, visiblePasswordTwo), 0, 3);
        body.add(showPassword, 1, 3);
        body.add(new Label("show password"), 2, 3);
        body.add(register, 0, 4);
        body.add(rememberUser, 1, 4);
        body.add(new Label("Remember me"), 2, 4);

        body.setAlignment(Pos.CENTER);

        //and returns it
        return body;
    }

    /**
     * The login screen body.
     *
     * @return the body
     */
    private GridPane loginBody() {
        //textfield for the username
        TextField username = new TextField();
        username.setPromptText("username");

        //passwordfield
        PasswordField password = new PasswordField();
        password.setPromptText("password");

        //passwordfield for if the user wants to see the password
        TextField visiblePassword = new TextField();
        visiblePassword.setPrefHeight(50);
        visiblePassword.setVisible(false);

        //checkbox to toggle between visible password and masked password
        JFXToggleNode showPassword = new JFXToggleNode();
        MaterialDesignIconView showIcon = new MaterialDesignIconView(MaterialDesignIcon.EYE);
        showIcon.setSize("20px");
        showPassword.setGraphic(new Label("Show password", showIcon));
        showPassword.setOnAction(e -> {
            ApplicationMethods.toggleVisibility(
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
            registerScreen();
        });
        Label loginText = new Label("Login");
        loginText.setId("loginText");
        //create the body
        GridPane body = new GridPane();
        body.setVgap(5);
        body.setHgap(10);
        body.add(loginText, 0, 0);
        body.add(username, 0, 1);
        body.add(new StackPane(password, visiblePassword), 0, 2);
        body.add(showPassword, 1, 2);
        body.add(login, 0, 3);
        body.add(rememberUser, 1, 3);
        body.add(register, 0, 5);
        body.setAlignment(Pos.CENTER);

        //and return it
        return body;
    }

    /**
     * Category screen.
     */
    static void mainScreen() {
        //make the navigation tab pane
        JFXTabPane navigation = new JFXTabPane();
        navigation.setPrefSize(500, 600);

        //  make the home tab
        Tab homeTab = new Tab();
        homeTab.setText("Home");
        homeTab.setContent(homeScreen());

        // make the category tab
        Tab categoryTab = new Tab();
        categoryTab.setText("Category");
        categoryTab.setContent(categoryScreen());

        Tab statsTab = new Tab();
        statsTab.setText("Stats");
        statsTab.setContent(statsScreen());

        // make the leaderboard tab
        Tab leaderboardTab = new Tab();
        leaderboardTab.setText("Leaderboard");
        leaderboardTab.setContent(leaderboardScreen());

        navigation.getTabs().addAll(homeTab, categoryTab,statsTab, leaderboardTab);

        //put into a framework
        Pane body = new Pane();
        body.getChildren().addAll(navigation);

        //and displayed
        Scene mainScene = new Scene(body, 500, 600);
        mainScene.getStylesheets().add(new File(theme).toURI().toString());
        show(mainScene);
    }

    private static Pane homeScreen() {
        JFXTabPane homeNavigation = new JFXTabPane();
        homeNavigation.setPrefSize(500,500);

        Tab homeTab = new Tab();
        homeTab.setText("Your world");
        homeTab.setContent(yourWorldScreen());

        Tab settingsTab = new Tab();
        settingsTab.setText("Settings");
        settingsTab.setContent(settingsScreen());



        homeNavigation.getTabs().addAll(homeTab, settingsTab);

        Pane homePage = new Pane();
        homePage.getChildren().addAll(homeNavigation);

        return homePage;
    }

    private static GridPane yourWorldScreen() {
        ProgressBar levelBar = new ProgressBar(0.5);
        levelBar.setPrefSize(400, 10);

        Label levelStatus = new Label("Lv. INSERT HERE LEVEL");
        levelStatus.setId("levelStatus");


        VBox levelContainer = new VBox();
        levelContainer.getChildren().addAll(levelStatus, levelBar);

        GridPane yourWorldPage = new GridPane();
        yourWorldPage.add(levelContainer, 0,0);
        yourWorldPage.setAlignment(Pos.BOTTOM_CENTER);
        return yourWorldPage;
    }
    private static VBox settingsScreen() {

        String status = "Enable";

        JFXToggleNode darkTheme = new JFXToggleNode();
        MaterialDesignIconView darkThemeIcon = new MaterialDesignIconView(MaterialDesignIcon.THEME_LIGHT_DARK);
        darkThemeIcon.setSize("50px");


        darkTheme.setPrefSize(500, 100);
        if (theme.equals("src/styles/mainSceneDarkTheme.css")) {
            darkTheme.setSelected(true);
            status = "Disable";
        }
        darkTheme.setGraphic(new Label(status + " dark theme", darkThemeIcon));

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

        JFXButton logoutButton = new JFXButton();
        MaterialDesignIconView logoutIcon = new MaterialDesignIconView(MaterialDesignIcon.LOGOUT);
        logoutIcon.setSize("50px");
        logoutButton.setGraphic(new Label("Log out", logoutIcon));
        logoutButton.setPrefSize(500, 100);
        logoutButton.setOnAction(e -> {
            // here comes the log out method....

        });

        VBox settingsPage = new VBox(10);
        settingsPage.getChildren().addAll(darkTheme, logoutButton);
        settingsPage.setAlignment(Pos.CENTER);

        return settingsPage;
    }

    private static Pane categoryScreen() {
        JFXTabPane categoryNavigation = new JFXTabPane();
        categoryNavigation.setPrefSize(500, 500);

        // make the transport tab
        Tab transportTab = new Tab();
        transportTab.setText("Transport");
        transportTab.setContent(transportScreen());

        // make the food tab
        Tab foodTab = new Tab();
        foodTab.setText("Food");
        foodTab.setContent(foodCategoryScreen());


        // make the energy tab
        Tab energyTab = new Tab();
        energyTab.setText("Energy");
        energyTab.setContent(energyScreen());

        // make the extra tab
        Tab extraTab = new Tab();
        extraTab.setText("Extra");
        extraTab.setContent(extraScreen());

        categoryNavigation.getTabs().addAll(transportTab, foodTab, energyTab, extraTab);

        Pane categoryBody = new Pane();
        categoryBody.getChildren().addAll(categoryNavigation);

        return categoryBody;

    }

    private static Pane statsScreen() {
        JFXTabPane statsNavigation = new JFXTabPane();
        statsNavigation.setPrefSize(500,500);

        Tab overview = new Tab();
        overview.setText("Overview");
        overview.setContent(overviewScreen());


        statsNavigation.getTabs().addAll(overview);

        Pane statsBody = new Pane();
        statsBody.getChildren().addAll(statsNavigation);

        return statsBody;
    }

    private static VBox overviewScreen() {

        AtomicReference<String> recentActivity = new AtomicReference<>("");
        JFXButton refresh = new JFXButton("refresh");

        Label history = new Label("Recent activities:");
        history.setId("history");

        refresh.setOnAction(e -> {
            if(client.Communication.getLastThreeActions() != null){
                recentActivity.set(Communication.getLastThreeActions());
                changeText(history, "Recent activities: \n" + recentActivity.get());
            } else {
                recentActivity.set("");
                System.out.println(recentActivity.get());
                changeText(history, "Recent activities: \n none");
            }
            overviewScreen();
        });

        VBox overviewPage = new VBox();
        overviewPage.getChildren().addAll(history, refresh);
        return overviewPage;
    }

    private static void changeText(Label label, String text){
        label.setText(text);
    }

    private static Pane leaderboardScreen() {
        JFXTabPane leaderboardNavigation = new JFXTabPane();
        leaderboardNavigation.setPrefSize(500,500);

        Tab leaderboard = new Tab();

        leaderboardNavigation.getTabs().addAll(leaderboard);

        Pane leaderboardBody = new Pane();
        leaderboardBody.getChildren().addAll(leaderboardNavigation);

        return leaderboardBody;
    }


    /**
     * The transport screen.
     */
    private static GridPane transportScreen() {

        //button for the cycle action
        JFXButton cycle = new JFXButton();
        FontAwesomeIconView bikeIcon = new FontAwesomeIconView(FontAwesomeIcon.BICYCLE);
        bikeIcon.setSize("50px");
        cycle.setGraphic(bikeIcon);
        cycle.setPrefSize(500,100);
        cycle.setOnAction(e -> {
            Transport.addCycleAction();
        });

        //button for the public transport action
        JFXButton publicTransport = new JFXButton();
        MaterialDesignIconView subwayIcon = new MaterialDesignIconView(MaterialDesignIcon.SUBWAY);
        subwayIcon.setSize("50px");
        publicTransport.setGraphic(subwayIcon);
        publicTransport.setPrefSize(500,100);
        publicTransport.setOnAction(e -> {
            Transport.addPublicTransportAction();
        });

        //button for the car action
        JFXButton car = new JFXButton();
        FontAwesomeIconView carIcon = new FontAwesomeIconView(FontAwesomeIcon.AUTOMOBILE);
        carIcon.setSize("50px");
        car.setGraphic(carIcon);
        car.setPrefSize(500,100);
        car.setOnAction(e -> {
            Transport.addCarAction();
        });

        //button for the plane action
        JFXButton plane = new JFXButton();
        FontAwesomeIconView planeIcon = new FontAwesomeIconView(FontAwesomeIcon.PLANE);
        planeIcon.setSize("50px");
        plane.setGraphic(planeIcon);
        plane.setPrefSize(500,100);
        plane.setOnAction(e -> {
            Transport.addPlaneAction();
        });


        GridPane transportPage = new GridPane();
        transportPage.setVgap(10);
        transportPage.add(cycle, 0 ,0);
        transportPage.add(publicTransport, 0, 1);
        transportPage.add(car, 0, 2);
        transportPage.add(plane, 0,3 );

        transportPage.setAlignment(Pos.CENTER);

        return transportPage;
    }

    /**
     * The food screen.
     */
    private static GridPane foodCategoryScreen() {

        // makes the veggie toggle plus the icon
        JFXToggleNode veggie = new JFXToggleNode();
        MaterialDesignIconView cowIcon = new MaterialDesignIconView(MaterialDesignIcon.COW);
        veggie.setGraphic(new Label("Meat", cowIcon));
        cowIcon.setSize("50px");
        veggie.setMinSize(500,100);

        // makes the locally toggle plus the icon
        JFXToggleNode locally = new JFXToggleNode();
        FontAwesomeIconView locallyIcon = new FontAwesomeIconView(FontAwesomeIcon.MAP_MARKER);
        locallyIcon.setSize("50px");
        locally.setGraphic(new Label("Locally", locallyIcon));
        locally.setMinSize(500,100);

        // makes the bio toggle plus the icon
        JFXToggleNode bio = new JFXToggleNode();
        FontAwesomeIconView leafIcon = new FontAwesomeIconView(FontAwesomeIcon.LEAF);
        leafIcon.setSize("50px");
        bio.setGraphic(new Label("Bio", leafIcon));
        bio.setMinSize(500,100);

        //makes send button
        JFXButton send = new JFXButton("Add action");
        send.setMinSize(500, 100);
        send.setOnAction(e -> {
            //looks what is selected
            FoodCategory.addAction(!veggie.isSelected(), locally.isSelected(), bio.isSelected());

            // then sets it to false to select it again
            veggie.setSelected(false);
            locally.setSelected(false);
            bio.setSelected(false);
        });

        //make the body and stop the toggles in them
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



    static GridPane energyScreen() {

        JFXButton waterTime = new JFXButton();
        MaterialDesignIconView waterIcon = new MaterialDesignIconView(MaterialDesignIcon.WATER);
        waterIcon.setSize("50px");
        waterTime.setGraphic(new Label("Water time", waterIcon));
        waterTime.setPrefSize(500, 100);
        waterTime.setOnAction(e -> {
            Energy.addReduceWater();
        });

        JFXButton energyTime = new JFXButton();
        MaterialDesignIconView energyIcon = new MaterialDesignIconView(MaterialDesignIcon.FLASH);
        energyIcon.setSize("50px");
        energyTime.setGraphic(new Label("Energy time", energyIcon));
        energyTime.setPrefSize(500, 100);
        energyTime.setOnAction(e -> {
            Energy.addReduceEnergyAction();
        });

        GridPane energyPage = new GridPane();
        energyPage.setVgap(10);
        energyPage.add(waterTime, 0, 0);
        energyPage.add(energyTime, 0, 1);
        energyPage.setAlignment(Pos.CENTER);

        return energyPage;
    }

    static GridPane extraScreen() {

        JFXButton cleanSurronding = new JFXButton();
        OctIconView trashIcon = new OctIconView(OctIcon.TRASHCAN);
        trashIcon.setSize("50px");
        cleanSurronding.setGraphic(new Label("Clean surronding", trashIcon));
        cleanSurronding.setPrefSize(500, 100);
        cleanSurronding.setOnAction(e -> {
            Extra.addCleanSurroundingAction();
        });

        JFXButton recycle = new JFXButton();
        MaterialDesignIconView recycleIcon = new MaterialDesignIconView(MaterialDesignIcon.RECYCLE);
        recycleIcon.setSize("50px");
        recycle.setGraphic(new Label("Recycle", recycleIcon));
        recycle.setPrefSize(500, 100);
        recycle.setOnAction(e -> {
            Extra.addRecycleAction();
        });


        GridPane extraPage = new GridPane();
        extraPage.setVgap(10);
        extraPage.add(cleanSurronding, 0,0 );
        extraPage.add(recycle,0,1);
        extraPage.setAlignment(Pos.CENTER);

        return extraPage;
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
}