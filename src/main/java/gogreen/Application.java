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

    public void loginScreen(Stage stage) {
        Label loginText = new Label("Login: ");
        loginText.setPadding(new Insets(0, 100, 0, 100));

        TextField userName = new TextField("username");
        userName.setMaxWidth(200);
        userName.setAlignment(Pos.CENTER);

        Button login = new Button("Login");
        login.setMaxWidth(200);
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String name = userName.getText();
                try{
                    checkName(name);
                    if(Communication.login(name, "password")) {
                        categoryScreen(stage);
                    }
                } catch (Exception exception) {
                    loginText.setText(exception.getMessage() + ", please change the username");
                }
            }
        });

        Button register = new Button("Or register");
        register.setMaxWidth(200);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(loginText, userName, login, register);
        vBox.setAlignment(Pos.CENTER);

        Scene loginScreen = new Scene(vBox, 500, 250);
        show(loginScreen, stage);
    }

    public void categoryScreen(Stage stage) {
        Button food = new Button("food");
        food.setMinSize(200, 200);
        food.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //action if you choose food
                foodCategoryScreen(stage);
            }
        });

        Button transport = new Button("transport");
        transport.setMinSize(200, 200);
        transport.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                transportScreen(stage);
            }
        });

        Button energy = new Button("energy");
        energy.setMinSize(200, 200);
        energy.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //action if you choose energy
            }
        });

        Button extra = new Button("extra");
        extra.setMinSize(200, 200);
        extra.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //action if you choose transport
            }
        });

        HBox hBoxOne = new HBox();
        hBoxOne.getChildren().addAll(food, transport);
        HBox hBoxTwo = new HBox();
        hBoxTwo.getChildren().addAll(extra, energy);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBoxOne, hBoxTwo);

        Scene categories = new Scene(vBox, 400, 400);
        show(categories, stage);
    }

    public void transportScreen(Stage stage) {
        Button cycle = new Button("cycle");
        cycle.setMinSize(200, 200);
        cycle.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Transport.addCycleAction();
            }
        });

        Button publicTransport = new Button("public Transport");
        publicTransport.setMinSize(200, 200);
        publicTransport.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Transport.addPublicTransportAction();
            }
        });

        Button car = new Button("car");
        car.setMinSize(200, 200);
        car.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Transport.addCarAction();
            }
        });

        Button plane = new Button("plane");
        plane.setMinSize(200, 200);
        plane.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Transport.addPlaneAction();
            }
        });

        HBox hBoxtop = new HBox();
        hBoxtop.getChildren().addAll(cycle, publicTransport);
        HBox hBoxbottom = new HBox();
        hBoxbottom.getChildren().addAll(car, plane);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBoxtop, hBoxbottom);

        Scene categories = new Scene(vBox, 400, 400);
        show(categories, stage);
    }

    public void show(Scene scene, Stage stage) {
        stage.setScene(scene);
        stage.show();
    }

    public void foodCategoryScreen(Stage stage){
        CheckBox veggie = new CheckBox("It was veggie");
        veggie.setMinSize(200,20);

        CheckBox locally = new CheckBox("It was locally");
        locally.setMinSize(200,20);

        CheckBox bio = new CheckBox("It was bio");
        bio.setMinSize(200,20);

        Button send = new Button("add action");
        send.setMinSize(200, 50);
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //looks what is selected
                System.out.println(veggie.isSelected() +", " + locally.isSelected() +", " +  bio.isSelected());
                FoodCategory.addAction(veggie.isSelected(), locally.isSelected(), bio.isSelected());
            }
        });


        VBox vBox = new VBox();
        vBox.getChildren().addAll(veggie,locally,bio,send);

        Scene actions = new Scene(vBox, 400, 400);
        show(actions, stage);
    }
}
