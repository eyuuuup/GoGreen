package gogreen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("GoGreen");
        loginScreen(stage);
    }

    /**
     * The login screen.
     * @param stage the stage
     */
    private void loginScreen(Stage stage) {
        Label loginText = new Label("Login: ");
        loginText.setPadding(new Insets(0, 100, 0, 100));

        TextField userName = new TextField("username");
        userName.setMaxWidth(200);
        userName.setAlignment(Pos.CENTER);

        Button login = new Button("Login");
        login.setMaxWidth(200);
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                try {
                    checkName(name);
                    if (Communication.login(name, "password")) {
                        categoryScreen(stage);
                    }
                } catch (IllegalArgumentException exception) {
                    loginText.setText(exception.getMessage() + ", please change the username");
                } catch (NullPointerException exception) {
                    loginText.setText("Something went wrong, please login again");
                }
            }
        });

        Button register = new Button("Or register");
        register.setMaxWidth(200);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(loginText, userName, login, register);
        vbox.setAlignment(Pos.CENTER);

        Scene loginScreen = new Scene(vbox, 500, 250);
        show(loginScreen, stage);
    }

    /**
     * Category screen.
     * @param stage stage
     */
    private void categoryScreen(Stage stage) {
        Button food = new Button("food");
        food.setMinSize(200, 200);
        food.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //action if you choose food
                foodCategoryScreen(stage);
            }
        });

        Button transport = new Button("transport");
        transport.setMinSize(200, 200);
        transport.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                transportScreen(stage);
            }
        });

        Button energy = new Button("energy");
        energy.setMinSize(200, 200);
        energy.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //action if you choose energy
            }
        });

        Button extra = new Button("extra");
        extra.setMinSize(200, 200);
        extra.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //action if you choose transport
            }
        });

        HBox hboxOne = new HBox();
        hboxOne.getChildren().addAll(food, transport);
        HBox hboxTwo = new HBox();
        hboxTwo.getChildren().addAll(extra, energy);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hboxOne, hboxTwo);

        Scene categories = new Scene(vbox, 400, 400);
        show(categories, stage);
    }

    /**
     * The transport screen.
     * @param stage stage
     */
    private void transportScreen(Stage stage) {
        Button cycle = new Button("cycle");
        cycle.setMinSize(200, 200);
        cycle.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                Transport.addCycleAction();
            }
        });

        Button publicTransport = new Button("public Transport");
        publicTransport.setMinSize(200, 200);
        publicTransport.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                Transport.addPublicTransportAction();
            }
        });

        Button car = new Button("car");
        car.setMinSize(200, 200);
        car.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                Transport.addCarAction();
            }
        });

        Button plane = new Button("plane");
        plane.setMinSize(200, 200);
        plane.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                Transport.addPlaneAction();
            }
        });

        HBox hboxtop = new HBox();
        hboxtop.getChildren().addAll(cycle, publicTransport);
        HBox hboxBottom = new HBox();
        hboxBottom.getChildren().addAll(car, plane);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hboxtop, hboxBottom);

        Scene categories = new Scene(vbox, 400, 400);
        show(categories, stage);
    }

    /**
     * The food screen.
     * @param stage stage
     */
    private void foodCategoryScreen(Stage stage) {
        CheckBox veggie = new CheckBox("It was veggie");
        veggie.setMinSize(200,20);

        CheckBox locally = new CheckBox("It was locally");
        locally.setMinSize(200,20);

        CheckBox bio = new CheckBox("It was bio");
        bio.setMinSize(200,20);

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


        VBox vbox = new VBox();
        vbox.getChildren().addAll(veggie,locally,bio,send);

        Scene actions = new Scene(vbox, 400, 400);
        show(actions, stage);
    }

    /**
     * Checks whether a given name is according to the rules.
     * @param testName the name to test
     * @return boolean correct name
     * @throws NullPointerException     if null
     * @throws IllegalArgumentException if invalid
     */
    public static boolean checkName(final String testName)
            throws NullPointerException, IllegalArgumentException {
        if (testName == null) {
            throw new NullPointerException("Name equals null");
        }

        final int maxSize = 16;
        if (testName.length() >= maxSize) {
            throw new IllegalArgumentException("Name is too long");
        }
        if (testName.length() <= 0) {
            throw new IllegalArgumentException("Name is too short");
        }

        checkCharacters(testName);

        //check whether the name is not offensive
        try {
            File file = new File("doc/resources/InvalidNamesComma.txt");
            Scanner sc   = new Scanner(file).useDelimiter(", ");
            while (sc.hasNext()) {
                if (testName.contains(sc.next())) {
                    throw new IllegalArgumentException("Offensive name");
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
                throw new IllegalArgumentException("Invalid character " + c);
            }
        }
    }

    /**
     * shows the given scene to the user.
     * @param scene scene
     * @param stage stage
     */
    private void show(Scene scene, Stage stage) {
        stage.setScene(scene);
        stage.show();
    }
}
