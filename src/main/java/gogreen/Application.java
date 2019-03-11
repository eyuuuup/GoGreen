package gogreen;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
        //button that redirects to the food category
        Button food = new Button("food");
        food.setOnAction(e -> {
            foodCategoryScreen();
        });

        //button that redirects to the transport category
        Button transport = new Button("transport");
        transport.setOnAction(e -> {
            transportScreen();
        });

        //button that redirects to the energy category
        Button energy = new Button("energy");
        energy.setOnAction(e -> {
            //action if you choose energy
        });

        //button that redirects to the extra category
        Button extra = new Button("extra");
        extra.setOnAction(e -> {
            //action if you choose transport
        });

        //put into a framework
        FlowPane body = new FlowPane();
        body.getChildren().addAll(food, transport, energy, extra);

        //and displayed
        Scene categories = new Scene(body, 400, 400);
        show(categories);
    }

    /**
     * The transport screen.
     */
    private static void transportScreen() {
        //button for the cycle action
        Button cycle = new Button("cycle");
        cycle.setOnAction(e -> {
            Transport.addCycleAction();
        });

        //button for the public transport action
        Button publicTransport = new Button("public Transport");
        publicTransport.setOnAction(e -> {
            Transport.addPublicTransportAction();
        });

        //button for the car action
        Button car = new Button("car");
        car.setOnAction(e -> {
            Transport.addCarAction();
        });

        //button for the plane action
        Button plane = new Button("plane");
        plane.setOnAction(e -> {
            Transport.addPlaneAction();
        });

        //the buttons are put into a framework
        FlowPane body = new FlowPane();
        body.getChildren().addAll(cycle, publicTransport, car, plane);

        //and displayed to the user
        Scene categories = new Scene(body, 400, 400);
        show(categories);
    }

    /**
     * The food screen.
     */
    private static void foodCategoryScreen() {
        //checkbox for vegetarian meal
        CheckBox veggie = new CheckBox("It was veggie");
        veggie.setMinSize(200, 20);

        //checkbox for locally produced food
        CheckBox locally = new CheckBox("It was locally");
        locally.setMinSize(200, 20);

        //checkbox for biological food
        CheckBox bio = new CheckBox("It was bio");
        bio.setMinSize(200, 20);

        //sends the checked items to the server
        Button send = new Button("add action");
        send.setMinSize(200, 50);
        send.setOnAction(e -> {
            //looks what is selected
            FoodCategory.addAction(veggie.isSelected(), locally.isSelected(), bio.isSelected());
        });

        //the nodes are put in a vertical box
        VBox vbox = new VBox();
        vbox.getChildren().addAll(veggie, locally, bio, send);

        //and displayed to the user
        Scene actions = new Scene(vbox, 400, 400);
        show(actions);
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
