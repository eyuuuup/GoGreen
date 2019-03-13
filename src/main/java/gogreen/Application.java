package gogreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;

public class Application extends javafx.application.Application {
    //the stage this application uses
    private static Stage stage;

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
        if (client.Communication.silentLogin()) {
            categoryScreen();
        } else {
            loginScreen();
        }
    }

    /**
     * This method displays the Login screen.
     */
    private void loginScreen() {
        GridPane body = loginBody();
        Scene loginScene = new Scene(body, 500, 250);
        //File f = new File("src/styles/style.css");
        //loginScene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        loginScene.getStylesheets().add(new File("src/styles/Style.css").toURI().toString());
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
        Scene registerScene = new Scene(body, 500, 250);
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
        });

        //checkbox to toggle between visible password two and masked password two
        CheckBox showPasswordTwo = new CheckBox();
        showPasswordTwo.setOnAction(e -> {
            ApplicationMethods.toggleVisibility(
                    visiblePasswordTwo, passwordTwo, showPasswordTwo.isSelected());
        });

        //checkbox if the user wants his username and password to be remembered
        CheckBox rememberUser = new CheckBox();

        Label registerText = new Label("Register:");

        //button to register the stuff the user filled in
        Button register = new Button("Register");
        register.setOnAction(e -> {
            try {
                ApplicationMethods.register(username.getText(), password.getText(),
                        passwordTwo.getText(), rememberUser.isSelected());
            } catch (NullPointerException | IllegalArgumentException exception) {
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
        body.add(showPassword, 1, 2);
        body.add(showPasswordTwo, 1, 3);
        body.add(new Label("show password"), 2, 2);
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
        visiblePassword.setVisible(false);

        //checkbox to toggle between visible password and masked password
        CheckBox showPassword = new CheckBox();
        showPassword.setOnAction(e -> {
            ApplicationMethods.toggleVisibility(
                    visiblePassword, password, showPassword.isSelected());
        });

        //checkbox if the user wants the application to remember the username and password
        CheckBox rememberUser = new CheckBox();

        //button to log in with the given credentials
        Button login = new Button("Login");
        login.setOnAction(e -> {
            ApplicationMethods.login(
                    username.getText(), password.getText(), rememberUser.isSelected());
        });

        //button if the user wants to register instead of to log in
        Button register = new Button("or register");
        register.setOnAction(e -> {
            registerScreen();
        });

        //create the body
        GridPane body = new GridPane();
        body.setVgap(5);
        body.setHgap(10);
        body.add(new Label("Login"), 0, 0);
        body.add(username, 0, 1);
        body.add(new StackPane(password, visiblePassword), 0, 2);
        body.add(showPassword, 1, 2);
        body.add(new Label("show password"), 2, 2);
        body.add(login, 0, 3);
        body.add(rememberUser, 1, 3);
        body.add(new Label("Remember me"), 2, 3);
        body.add(register, 0, 5);
        body.setAlignment(Pos.CENTER);

        //and return it
        return body;
    }

    /**
     * Category screen.
     */
    static void categoryScreen() {

        //make the navigation tab pane
        JFXTabPane navigation = new JFXTabPane();
        navigation.setPrefSize(500, 500);

        //  make the home tab
        Tab homeTab = new Tab();
        homeTab.setText("Home");


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

        // make the extra tab
        Tab extraTab = new Tab();
        extraTab.setText("Extra");

        navigation.getTabs().addAll(homeTab, transportTab, foodTab, energyTab, extraTab);


        //put into a framework
        Pane body = new Pane();
        body.getChildren().addAll(navigation);

        //and displayed
        Scene categories = new Scene(body, 500, 500);
        show(categories);
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
        cycle.setPrefSize(200,200);
        cycle.setOnAction(e -> {
            Transport.addCycleAction();
        });

        //button for the public transport action
        JFXButton publicTransport = new JFXButton();
        MaterialDesignIconView subwayIcon = new MaterialDesignIconView(MaterialDesignIcon.SUBWAY);
        subwayIcon.setSize("50px");
        publicTransport.setGraphic(subwayIcon);
        publicTransport.setPrefSize(230,230);
        publicTransport.setOnAction(e -> {
            Transport.addPublicTransportAction();
        });

        //button for the car action
        JFXButton car = new JFXButton();
        FontAwesomeIconView carIcon = new FontAwesomeIconView(FontAwesomeIcon.AUTOMOBILE);
        carIcon.setSize("50px");
        car.setGraphic(carIcon);
        car.setPrefSize(230,230);
        car.setOnAction(e -> {
            Transport.addCarAction();
        });

        //button for the plane action
        JFXButton plane = new JFXButton();
        FontAwesomeIconView planeIcon = new FontAwesomeIconView(FontAwesomeIcon.PLANE);
        planeIcon.setSize("50px");
        plane.setGraphic(planeIcon);
        plane.setPrefSize(230,230);
        plane.setOnAction(e -> {
            Transport.addPlaneAction();
        });


        GridPane transportPage = new GridPane();
        transportPage.add(cycle, 0 ,0);
        transportPage.add(publicTransport, 0, 1);
        transportPage.add(car, 1, 0);
        transportPage.add(plane, 1,1 );
        transportPage.setVgap(10);
        transportPage.setHgap(10);
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
        JFXButton send = new JFXButton("add action");
        send.setMinSize(500, 100);
        send.setOnAction(e -> {
            //looks what is selected
            FoodCategory.addAction(veggie.isSelected(), locally.isSelected(), bio.isSelected());

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

        // return the body
        return foodPage;
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
