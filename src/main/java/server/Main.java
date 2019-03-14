package server;

//import javafx.application.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Main {
    private Main() {}

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

