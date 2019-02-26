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
     * The user will give an input, those will be stored in the actions of the category
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

        //null check
        if(actions == null){
            System.out.println("Your actions list is empty, please first add some actions.");
            return foodScore;
        }

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
