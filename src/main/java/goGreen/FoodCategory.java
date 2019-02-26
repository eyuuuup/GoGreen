package goGreen;
import java.util.Scanner;

/**
 * The food category will handle adding an action in the foodcategory.
 * And distubuting the foodcategory points
 * @author Marit Radder
 * @author Erwin van Dam
 */
public class FoodCategory {
    private static boolean[] actions;

    /**
     *The foodCategory constructor, which makes an array of false booleans which are the actions.
     */
    public FoodCategory(){
        actions = new boolean[]{false, false, false};
    }

    /**
     * gets the actions array.
     * @return actions, it returns the actions array.
     */
    public static boolean[] getActions(){
        return actions;
    }

    /**
     * sets the actions array to the array you give as parameter.
     * @param newActions, is the array you want actions to change in.
     */
    public static void setActions(boolean[] newActions){
        actions = newActions;
    }


    /**
     * asks you a couple of questions, and the answer will be a boolean.
     * If your boolean is the same as the current boolean in your actions or your previous action was a good action,
     * this would be the array {false, true, true}.
     * Then the result will be {true, true, true}.
     * @param sc, is the scanner for the user input.
     */
    public static void addAction(Scanner sc){
        //ask questions, later will become buttons
        System.out.println("Did it contain meat? (true / false)");
        actions[0] |= !Boolean.parseBoolean(sc.next());

        System.out.println("Was it biological (true / false)");
        actions[1] |= Boolean.parseBoolean(sc.next());

        System.out.println("Was it locally produced(true / false)");
        actions[2] |= Boolean.parseBoolean(sc.next());
    }

    /**
     * this method looks at your actions array and will add 50 points for every true in that array.
     * @return foodscore, is an integer which represents the points you got from your actions
     */
    public static int twelveHourReset(){
        int foodScore = 0;
        // add points to total score
        for(boolean action: actions){
            // for every good action add 50 points
            if(action){
                foodScore += 50;
                action = false;
            }
        }
        return foodScore;
    }
}
