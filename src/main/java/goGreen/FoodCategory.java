package goGreen;
import java.util.Scanner;

public class FoodCategory {
    private static boolean[] actions;

    public FoodCategory(){
        actions = new boolean[]{false, false, false};
    }

    public static void addAction(Scanner sc){
        // setup scanner

        //ask questions, later will become buttons
        System.out.println("Did it contain meat? (true / false)");
        actions[0] = !Boolean.parseBoolean(sc.next());

        System.out.println("Did it contain artificial additives (true / false)");
        //is this bad?
        actions[1] = !Boolean.parseBoolean(sc.next());

        System.out.println("Was it locally produced(true / false)");
        actions[2] = Boolean.parseBoolean(sc.next());
    }

    public static int twelveHourReset(){
        int foodScore = 0;
        // add points to total score
        for(boolean action: actions){
            if(action)foodScore += 50;
            //directly clear action
            action = false;
        }
        return foodScore;
    }
}
