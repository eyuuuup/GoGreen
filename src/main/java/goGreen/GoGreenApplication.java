package goGreen;

import java.util.Scanner;

public class GoGreenApplication {

    public static void main(String[] args) {
        choice();
    }

    private static void display() {
        System.out.print(
                "┌────────────────────────────────────────┐\n" +
                "│ CHOOSE IN THE CATEGORY OF YOUR ACTION: │\n" +
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
        String  input;

        while (true) {
            display();
            input = sc.next();

            // multiple options with multiple methods
            switch (input) {
                case "1":
                    System.out.println("You choose food");
                    FoodCategory.display();
                    break;
                case "2":
                    System.out.println("You choose transport");
                    //implement go to transport method here
                    break;
                case "3":
                    System.out.println("You choose energy");
                    //implement go to energy method here
                    break;
                case "4":
                    System.out.println("You choose extra");
                    // implement go to extra method here
                    break;
                case "5":
                    System.out.println("Shutting down the program");
                    return;
                default:
                    System.out.println("That is not a valid choiche, choose again");
                    break;
            }

        }

    }

}