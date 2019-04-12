package server;

import server.database.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);

        // connect to the server.database
        Database.connect();

        // prepare statements for querying
        Database.prepare();

        // when closing the app disconnect from the server.database
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Closing server.database");
                Database.disconnect();
            }
        }, "Shutdown-thread"));
    }
}

