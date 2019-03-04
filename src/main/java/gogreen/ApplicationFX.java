package gogreen;

import javafx.stage.Stage;

public class ApplicationFX extends javafx.application.Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("App");
        stage.show();
    }
}
