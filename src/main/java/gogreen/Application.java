package gogreen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class Application extends javafx.application.Application {
    //the stage this application uses
    private Stage stage;

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
        show(loginScene);
    }

    /**
     * This method shows the login screen with a specific error.
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
     * @param text the error
     */
    private void registerScreenError(String text) {
        GridPane body = registerScreenBody();
        body.getChildren().remove(0);

        //the new error
        Label loginText = new Label(text);
        loginText.setMinWidth(200);
        loginText.setMaxWidth(200);
        loginText.setTextFill(Paint.valueOf("#FF0000"));
        loginText.setAlignment(Pos.CENTER);

        //and add it to the body
        body.add(loginText, 0, 0);
        Scene registerScene = new Scene(body, 500, 250);
        show(registerScene);
    }

    /**
     * The body for the register display.
     * @return the body
     */
    private GridPane registerScreenBody() {
        //label which shows what the user should do
        Label loginText = new Label("Create new account:");
        loginText.setMinWidth(200);
        loginText.setMaxWidth(200);
        loginText.setAlignment(Pos.CENTER);

        //textfield for the username
        TextField userName = new TextField();
        userName.setPromptText("username");
        userName.setMaxWidth(200);
        userName.setAlignment(Pos.CENTER);

        //password field
        PasswordField password = new PasswordField();
        password.setPromptText("password");
        password.setMaxWidth(200);
        password.setAlignment(Pos.CENTER);

        //password field if the user wants to see the password
        TextField visiblePassword = new TextField();
        visiblePassword.setVisible(false);
        visiblePassword.setMaxWidth(200);
        visiblePassword.setAlignment(Pos.CENTER);

        //for registration the user should insert twice the same password
        PasswordField passwordTwo = new PasswordField();
        passwordTwo.setPromptText("password again");
        passwordTwo.setMaxWidth(200);
        passwordTwo.setAlignment(Pos.CENTER);

        //password two can also be set to visible
        TextField visiblePasswordTwo = new TextField();
        visiblePasswordTwo.setVisible(false);
        visiblePasswordTwo.setMaxWidth(200);
        visiblePasswordTwo.setAlignment(Pos.CENTER);

        //checkbox to toggle between visible password and masked password
        CheckBox showPassword = new CheckBox();
        showPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                if (showPassword.isSelected()) {
                    password.setVisible(false);
                    visiblePassword.setText(password.getText());
                    visiblePassword.setVisible(true);
                } else {
                    password.setVisible(true);
                    password.setText(visiblePassword.getText());
                    visiblePassword.setVisible(false);
                }
            }
        });

        //checkbox to toggle between visible password two and masked password two
        CheckBox showPasswordTwo = new CheckBox();
        showPasswordTwo.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //if the password was visible it is set to masked
                //otherwise the password is set to visible
                if (showPasswordTwo.isSelected()) {
                    passwordTwo.setVisible(false);
                    visiblePasswordTwo.setText(passwordTwo.getText());
                    visiblePasswordTwo.setVisible(true);
                } else {
                    passwordTwo.setVisible(true);
                    passwordTwo.setText(visiblePasswordTwo.getText());
                    visiblePasswordTwo.setVisible(false);
                }
            }
        });

        //checkbox if the user wants his username and password to be remembered
        CheckBox rememberUser = new CheckBox();

        //button to register the stuff the user filled in
        Button register = new Button("Register");
        register.setMaxWidth(200);
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //checks whether the name is according to the rules
                String name = userName.getText();
                try {
                    checkName(name);
                    //and whether the password is long enough
                    //and whether the both given passwords are the same
                    //and whether the server accepts the registration
                    if (password.getText().equals(passwordTwo.getText())) {
                        if (password.getText().length() > 7) {
                            StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
                            String encryptedPassword = passwordEncryptor.encryptPassword(password.getText());

                            String encodedUserName = "";
                            try {
                                encodedUserName = (String) Base64.getEncoder().encodeToString(
                                        name.getBytes("utf-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            if (client.Communication.register(name,
                                    encryptedPassword, rememberUser.isSelected())) {
                                categoryScreen();
                            }
                        } else {
                            registerScreenError("Password too short");
                        }
                    } else {
                        registerScreenError("Passwords not equal");
                    }
                } catch (IllegalArgumentException | NullPointerException exception) {
                    registerScreenError(exception.getMessage());
                }
            }
        });

        //creates the gridpane with all the nodes in it
        GridPane body = new GridPane();
        body.setVgap(5);
        body.setHgap(10);
        body.add(loginText, 0,0);
        body.add(userName, 0, 1);
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
     * @return the body
     */
    private GridPane loginBody() {
        //label that tells the user what to do
        Label loginText = new Label("Login:");
        loginText.setMinWidth(200);
        loginText.setMaxWidth(200);
        loginText.setAlignment(Pos.CENTER);

        //textfield for the username
        TextField userName = new TextField();
        userName.setPromptText("username");
        userName.setMaxWidth(200);
        userName.setAlignment(Pos.CENTER);

        //passwordfield
        PasswordField password = new PasswordField();
        password.setPromptText("password");
        password.setMaxWidth(200);
        password.setAlignment(Pos.CENTER);

        //passwordfield for if the user wants to see the password
        TextField visiblePassword = new TextField();
        visiblePassword.setVisible(false);
        visiblePassword.setMaxWidth(200);
        visiblePassword.setAlignment(Pos.CENTER);

        //checkbox to toggle between visible password and masked password
        CheckBox showPassword = new CheckBox();
        showPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //if the password was masked, it is set to visible
                //or the other way around
                if (showPassword.isSelected()) {
                    password.setVisible(false);
                    visiblePassword.setText(password.getText());
                    visiblePassword.setVisible(true);
                } else {
                    password.setVisible(true);
                    password.setText(visiblePassword.getText());
                    visiblePassword.setVisible(false);
                }
            }
        });

        //checkbox if the user wants the application to remember the username and password
        CheckBox rememberUser = new CheckBox();

        //button to log in with the given credentials
        Button login = new Button("Login");
        login.setMaxWidth(200);
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //checks whether the given name and password belong to an existing account
                StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
                String encryptedPassword = passwordEncryptor.encryptPassword(password.getText());

                String encodedUserName = "";
                try {
                    encodedUserName = (String) Base64.getEncoder().encodeToString(
                            userName.getText().getBytes("utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (client.Communication.login(encodedUserName, encryptedPassword,
                        rememberUser.isSelected())) {
                    categoryScreen();
                }
            }
        });

        //button if the user wants to register instead of to log in
        Button register = new Button("or register");
        register.setMaxWidth(200);
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                registerScreen();
            }
        });

        //create the body
        GridPane body = new GridPane();
        body.setVgap(5);
        body.setHgap(10);
        body.add(loginText, 0,0);
        body.add(userName, 0, 1);
        body.add(new StackPane(password, visiblePassword), 0, 2);
        body.add(showPassword, 1, 2);
        body.add(new Label("show password"), 2, 2);
        body.add(login, 0,3);
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
    private void categoryScreen() {
        //button that redirects to the food category
        Button food = new Button("food");
        food.setMinSize(200, 200);
        food.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //action if you choose food
                foodCategoryScreen();
            }
        });

        //button that redirects to the transport category
        Button transport = new Button("transport");
        transport.setMinSize(200, 200);
        transport.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                transportScreen();
            }
        });

        //button that redirects to the energy category
        Button energy = new Button("energy");
        energy.setMinSize(200, 200);
        energy.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //action if you choose energy
            }
        });

        //button that redirects to the extra category
        Button extra = new Button("extra");
        extra.setMinSize(200, 200);
        extra.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //action if you choose transport
            }
        });

        //put into a framework
        HBox hboxOne = new HBox();
        hboxOne.getChildren().addAll(food, transport);
        HBox hboxTwo = new HBox();
        hboxTwo.getChildren().addAll(extra, energy);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hboxOne, hboxTwo);

        //and displayed
        Scene categories = new Scene(vbox, 400, 400);
        show(categories);
    }

    /**
     * The transport screen.
     */
    private void transportScreen() {
        //button for the cycle action
        Button cycle = new Button("cycle");
        cycle.setMinSize(200, 200);
        cycle.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                Transport.addCycleAction();
            }
        });

        //button for the public transport action
        Button publicTransport = new Button("public Transport");
        publicTransport.setMinSize(200, 200);
        publicTransport.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                Transport.addPublicTransportAction();
            }
        });

        //button for the car action
        Button car = new Button("car");
        car.setMinSize(200, 200);
        car.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                Transport.addCarAction();
            }
        });

        //button for the plane action
        Button plane = new Button("plane");
        plane.setMinSize(200, 200);
        plane.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                Transport.addPlaneAction();
            }
        });

        //the buttons are put into a framework
        HBox hboxtop = new HBox();
        hboxtop.getChildren().addAll(cycle, publicTransport);
        HBox hboxBottom = new HBox();
        hboxBottom.getChildren().addAll(car, plane);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hboxtop, hboxBottom);

        //and displayed to the user
        Scene categories = new Scene(vbox, 400, 400);
        show(categories);
    }

    /**
     * The food screen.
     */
    private void foodCategoryScreen() {
        //checkbox for vegetarian meal
        CheckBox veggie = new CheckBox("It was veggie");
        veggie.setMinSize(200,20);

        //checkbox for locally produced food
        CheckBox locally = new CheckBox("It was locally");
        locally.setMinSize(200,20);

        //checkbox for biological food
        CheckBox bio = new CheckBox("It was bio");
        bio.setMinSize(200,20);

        //sends the checked items to the server
        Button send = new Button("add action");
        send.setMinSize(200, 50);
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //looks what is selected
                System.out.println(veggie.isSelected() + ", "
                        + locally.isSelected() + ", " +  bio.isSelected());
                FoodCategory.addAction(veggie.isSelected(), locally.isSelected(), bio.isSelected());
            }
        });

        //the nodes are put in a vertical box
        VBox vbox = new VBox();
        vbox.getChildren().addAll(veggie,locally,bio,send);

        //and displayed to the user
        Scene actions = new Scene(vbox, 400, 400);
        show(actions);
    }

    /**
     * Checks whether a given name is according to the rules.
     * @param testName the name to test
     * @return boolean correct name
     * @throws NullPointerException     if null
     * @throws IllegalArgumentException if invalid
     */
    public static boolean checkName(String testName)
            throws NullPointerException, IllegalArgumentException {
        //check if name is null
        if (testName == null) {
            throw new NullPointerException("Name equals null!");
        }

        //check whether name is too long
        if (testName.length() >= 16) {
            throw new IllegalArgumentException("Name is too long!");
        }

        //check if name is too short
        if (testName.length() <= 0) {
            throw new IllegalArgumentException("Name is too short!");
        }

        //check if all characters are permitted
        checkCharacters(testName);

        //for the invalid name test the name should consist of lowercase letters
        testName = testName.toLowerCase();

        //check whether the name is not offensive
        try {
            File file = new File("doc/resources/InvalidNamesComma.txt");
            Scanner sc   = new Scanner(file).useDelimiter(", ");
            while (sc.hasNext()) {
                if (testName.contains(sc.next())) {
                    throw new IllegalArgumentException("Offensive name!");
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * checks the characters in the new name.
     * @param testName the new name
     */
    private static void checkCharacters(String testName) {
        //check if all characters in the name are valid characters
        for (char c : testName.toCharArray()) {
            if (!(Character.toString(c).toLowerCase()).matches("[a-zA-Z]")) {
                throw new IllegalArgumentException("Invalid character!");
            }
        }
    }

    /**
     * shows the given scene to the user.
     * @param scene scene
     */
    private void show(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }
}
