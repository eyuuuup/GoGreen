package server;

import database.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    
    public static void main(String[] args) {
        
        SpringApplication.run(Main.class, args);
        
        // connect to the database
        Database.connect();
        
        // when closing the app disconnect from the database
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Closing database");
                Database.disconnect();
            }
        }, "Shutdown-thread"));
    }
}

