package goGreen;
import java.util.Scanner;
public class FoodCategory {

    private static int foodScore;
    private static boolean[] actions = new boolean[3];


    public static void display(){
        // small textual interface
        System.out.println("Choose what you want to do in the Food Category\n" +
                "1 - Add an action\n" +
                "2 - Reset\n" +
                "3 - Return");

        // make scanner for input
        Scanner sc = new Scanner(System.in);
        String input  = sc.next();

        // get the multiple choiches
        switch(input){
            case "1":
                // add an action
                addAction(actions);
                break;
            case "2":
                // when we reset, we reset and print the score
                actions = twelveHourReset(actions);
                System.out.println("Your current score is " + foodScore);
                break;
            case "3":
                // we go back
                break;
                default: System.out.println("That is not an option, choose again");
        }
    }
    public static boolean[] addAction(boolean[] prev ){
        // setup scanner
        Scanner sc = new Scanner(System.in);

        //ask questions, later will become buttons
        System.out.println("Did it contain meat? (true / false)");
        Boolean containsMeat = Boolean.parseBoolean(sc.next()) ;

        System.out.println("Did it contain artificial additives (true / false)");
        Boolean isBiological = Boolean.parseBoolean(sc.next());

        System.out.println("Was it not locally produced(true / false)");
        Boolean isLocally = Boolean.parseBoolean(sc.next());


        // when it is true then it is bad
        // override to good to bad
        // should check if it is empty, then also override
        if(prev[0] == false && containsMeat == true){
            prev[0] = containsMeat;
        }
        if(prev[1] == false && isBiological == true){
            prev[1] = isBiological;
        }
        if(prev[2] == false && isLocally == true){
            prev[2] = isLocally;
        }

        return prev;

    }

    public static int getPoints(boolean[] actions){
        int goodActionCounter = 0;
        // look at how much good actions there are in the array
        for(int i = 0; i < actions.length; i++){
            if(actions[i] == true){
                goodActionCounter++;
            }

        }

        // multiply by point multiplier
        return goodActionCounter * 50;
    }

    public static boolean[] twelveHourReset(boolean[] actions){
        // add points to total score
        foodScore = foodScore +  getPoints(actions);

        // clear the actions array, and return it
        boolean[] newActions = new boolean[3];
        return newActions;
    }

    public static int getFoodScore(){
        return foodScore;
    }
}
