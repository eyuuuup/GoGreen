import java.util.Scanner;

public class goGreenApplication {


        public static void main(String[] args){
            System.out.println(display());
            choice();
        }

        public static String display(){
            return "Choose in the category of your action: \n" +
                    "1 - The food category \n" +
                    "2 - The transport category \n" +
                    "3 - The energy category \n" +
                    "4 - The extra category \n" +
                    "5 - Exit the program";
        }

        public static void choice(){
            // preparing the scanner and input
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            System.out.println(input);

            // multiple options with multiple methods
            switch (input) {
                case "1":
                    System.out.println("You choose food");
                    // implement go to food method here
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
                    System.exit(0);
                    break;
                default:
                    System.out.println("That is not a valid choiche, choose again");
                    break;


            }
            //then we can choose again
            System.out.println(display());
            choice();
        }

    }


