package goGreen;

import java.util.Scanner;

public class GoGreenApplication {

    //for testing and demo purposes this class has a private field food and user
    private static FoodCategory food = new FoodCategory();
    private static User user = new User("user", 1);

    public static void main(String[] args) {
        choice();
    }

    //this is the display to choose between the categories
    private static void display() {
        System.out.print(
                "┌────────────────────────────────────────┐\n" +
                "│ CHOOSE THE CATEGORY OF YOUR ACTION:    │\n" +
                "├────────────────────────────────────────┤\n" +
                "│ 1 - The Food category                  │\n" +
                "│ 2 - The Transport category             │\n" +
                "│ 3 - The Energy category                │\n" +
                "│ 4 - The Extra category                 │\n" +
                "│ 5 - Exit the program                   │\n" +
                "└────────────────────────────────────────┘\n" +
                "Choice: "
        );
    }

    private static void choice() {
        // preparing the scanner and input
        Scanner sc = new Scanner(System.in);

        while (true) {
            display();

            // multiple options with multiple methods
            switch (Integer.parseInt(sc.next())) {
                case 1:
                    System.out.println("You choose food");
                    displayFood(sc);
                    break;
                case 2:
                    System.out.println("You choose transport");
                    displayTransport(sc);
                    break;
                case 3:
                    System.out.println("You choose energy");
                    //implement go to energy method here
                    break;
                case 4:
                    System.out.println("You choose extra");
                    // implement go to extra method here
                    break;
                case 5:
                    System.out.println("Shutting down the program");
                    return;
                default:
                    System.out.println("That is not a valid choice, choose again");
                    break;
            }

            System.out.println("Hi " + user.getName() + "! Your current score = " + user.getPoints() + " points!");
        }

    }

    //this is the display if you chose the Category Transport
    private static void displayTransport(Scanner sc) {
        //textual interface
        System.out.print(
                        "┌────────────────────────────────────────┐\n" +
                        "│ CHOOSE YOUR ACTION:                    │\n" +
                        "├────────────────────────────────────────┤\n" +
                        "│ 1 - Cycle                              │\n" +
                        "│ 2 - Car                                │\n" +
                        "│ 3 - Public Transport                   │\n" +
                        "│ 4 - plane                              │\n" +
                        "└────────────────────────────────────────┘\n" +
                        "Choice: "
        );
        switch (Integer.parseInt(sc.next())) {
            case 1:
                //add a cycle action
                user.addPoints(TransportCycle.addAction());
                System.out.println("You chose cycling, you received " + TransportCycle.addAction() + " points for that");
                break;
            case 2:
                //add car action
                user.addPoints(Transport.addCarAction());
                System.out.println("You chose car, you received " + Transport.addCarAction() + " points for that");
                break;
            case 3:
                //add public transport action
                user.addPoints(Transport.addPublicTransportAction());
                System.out.println("You chose public transport, you received " + Transport.addPublicTransportAction() + " points for that");
                break;
            case 4:
                //add plane action
                user.addPoints(Transport.addPlaneAction());
                System.out.println("You chose plane, you received " + Transport.addPlaneAction() + " points for that");
                break;
            default:
                //not implemented, so turn back to home screen
                System.out.println("Not implemented yet, bye!");
                break;
        }
    }

    //this is the display if you chose the Category Food
    private static void displayFood(Scanner sc) {
        // small textual interface
        System.out.print(
                        "┌────────────────────────────────────────────────┐\n" +
                        "│Choose what you want to do in the Food Category │\n" +
                        "├────────────────────────────────────────────────┤\n" +
                        "│ 1 - add an action                              │\n" +
                        "│ 2 - reset                                      │\n" +
                        "│ 3 - return                                     │\n" +
                        "└────────────────────────────────────────────────┘\n" +
                        "Choice: "
        );

        switch(Integer.parseInt(sc.next())){
            case 1:
                // add an action
                food.addAction(sc);
                break;
            case 2:
                // when we reset, we reset and print the score
                int points = food.twelveHourReset();
                System.out.println("You chose reset, you received " + points + " food points for the last 12 hours");
                user.addPoints(points);
                break;
            default:
                //go back to the category display
                System.out.println("bye!");
                break;
        }
    }

}