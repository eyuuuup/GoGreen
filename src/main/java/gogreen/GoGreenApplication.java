package gogreen;

import client.Communication;

import java.util.Scanner;

/**
 * This class represents the Textual User Interface for the application.
 */
public final class GoGreenApplication {
    /**
     * This method implements the welcome screen where you log in.
     * @param args arguments
     */
    public static void main(final String[] args) {
        // preparing the scanner and input
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(
                              "┌────────────────────────────────────────┐\n"
                            + "│ Login or Register:                     │\n"
                            + "├────────────────────────────────────────┤\n"
                            + "│ 1 - Login                              │\n"
                            + "│ 2 - Register                           │\n"
                            + "└────────────────────────────────────────┘\n"
                            + "Choice: "
            );

            // multiple options with multiple methods
            final int choice = Integer.parseInt(sc.next());
            String username = "";
            switch (choice) {
                case 1:
                    System.out.print("Login, please enter Username: ");
                    username = sc.next();
                    System.out.println();
                    if (Communication.login(username)) {
                        System.out.println("Login successful, welcome " + username + "!");
                        displayCategories(sc);
                        return;
                    } else {
                        System.out.println("Login failed!");
                    }
                    break;
                case 2:
                    System.out.print("Register, please enter Username: ");
                    username = sc.next();
                    System.out.println();
                    if (Communication.register(username)) {
                        System.out.println("Registration successful, welcome " + username + "!");
                        displayCategories(sc);
                        return;
                    } else {
                        System.out.println("Registration failed!");
                    }
                    break;
                default:
                    System.out.println("That is not a valid choice, choose again");
                    break;
            }
        }
    }

    /**
     * This method represents the main display to choose a Category.
     */
    private static void displayCategories(Scanner sc) {
        while (true) {
            System.out.print(
                              "┌────────────────────────────────────────┐\n"
                            + "│ CHOOSE THE CATEGORY OF YOUR ACTION:    │\n"
                            + "├────────────────────────────────────────┤\n"
                            + "│ 1 - The Food category                  │\n"
                            + "│ 2 - The Transport category             │\n"
                            + "│ 3 - The Energy category                │\n"
                            + "│ 4 - The Extra category                 │\n"
                            + "│ 5 - Exit the program                   │\n"
                            + "└────────────────────────────────────────┘\n"
                            + "Choice: "
            );

            // multiple options with multiple methods
            final int choice = Integer.parseInt(sc.next());
            switch (choice) {
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
                    System.out.println("That is not a valid choice,"
                            + " choose again");
                    break;
            }
        }
    }

    /**
     * this is the display if you chose the Category Transport.
     *
     * @param sc input scanner
     */
    private static void displayTransport(final Scanner sc) {
        //textual interface
        System.out.print(
                          "┌────────────────────────────────────────┐\n"
                        + "│ CHOOSE YOUR ACTION:                    │\n"
                        + "├────────────────────────────────────────┤\n"
                        + "│ 1 - Cycle                              │\n"
                        + "│ 2 - Car                                │\n"
                        + "│ 3 - Public Transport                   │\n"
                        + "│ 4 - plane                              │\n"
                        + "└────────────────────────────────────────┘\n"
                        + "Choice: "
        );

        final int choice = Integer.parseInt(sc.next());
        switch (choice) {
            case 1:
                //add cycle action
                Transport.addCycleAction();
                break;
            case 2:
                //add car action
                Transport.addCarAction();
                break;
            case 3:
                //add public transport action
                Transport.addPublicTransportAction();
                break;
            case 4:
                //add plane action
                Transport.addPlaneAction();
                break;
            default:
                //not implemented, so turn back to home screen
                System.out.println("bye!");
                break;
        }
    }

    /**
     * this is the display if you chose the Category Food.
     *
     * @param sc Input scanner
     */
    private static void displayFood(final Scanner sc) {
        // small textual interface
        System.out.print(
                          "┌────────────────────────────────────────────────┐\n"
                        + "│Choose what you want to do in the Food Category │\n"
                        + "├────────────────────────────────────────────────┤\n"
                        + "│ 1 - add an action                              │\n"
                        + "│ 2 - return                                     │\n"
                        + "└────────────────────────────────────────────────┘\n"
                        + "Choice: "
        );

        final int choice = Integer.parseInt(sc.next());
        switch (choice) {
            case 1:
                // add an action
                FoodCategory.addAction(sc);
                break;
            default:
                //go back to the category display
                System.out.println("bye!");
                break;
        }
    }

}
