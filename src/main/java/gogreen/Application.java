package gogreen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
                if(Communication.login(userName.getText(), "password")) {
                    categoryScreen(stage);
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
            }
        });

        Button transport = new Button("transport");
        transport.setMinSize(200, 200);
        transport.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //action if you choose transport
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

    public void show(Scene scene, Stage stage) {
        stage.setScene(scene);
        stage.show();
    }
}
