package gogreen;

import client.Communication;

//Class for operate one time action
public final class OneTimeEvent {

    //Method for RecycleCup use
    public static void RecycleCup() {
        Communication.addAction("RecycleCup", 100);
    }

    //Method for SignInEnvironmentGroup, give point for sign in of environment save group
    public static void SignInEvGroup() {
        Communication.addAction("SignIn", 100);
    }


    //Should I have to make a system for operate the one time event?
    //seperate the onetime action in 3 part.
    public static void addAction(boolean RecycleCup, boolean SignInEvGroup) {

        if (RecycleCup) {
            System.out.println("RecycleCup");
            Communication.addRequest("RecycleCup", 100);
        }
        if (SignInEvGroup) {
            System.out.println("SignInEvGroup");
            Communication.addRequest("SignIn", 100);
        }

    }
}
