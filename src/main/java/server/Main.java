package server;

import database.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class represents the spring application on which our server runs.
 */

@SpringBootApplication
public class Main {
    /**
     * Starts the Server and connects to the Database.
     * @param args arguments
     */
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);

        // connect to the database
        Database.connect();

        // prepare statements for querying
        Database.prepare();

        // when closing the app disconnect from the database
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Closing database");
                Database.disconnect();
            }
        }, "Shutdown-thread"));
    }
}

