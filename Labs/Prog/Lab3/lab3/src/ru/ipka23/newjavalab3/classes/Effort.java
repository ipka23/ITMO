package ru.ipka23.newjavalab3.classes;

public class Effort {
    public static boolean istryingHard;
    private static boolean hasPatience;
    private static boolean doingHardWork;


    public static void tryHard() {
        istryingHard = true;
    }


    public static void setPatience() {
        hasPatience = true;
    }


    public static boolean patienceStatus() {
        return hasPatience;
    }


    public static void isDoingHardWork() {
        doingHardWork = true;
    }


    public static boolean doingHardWorkStatus() {
        return doingHardWork;
    }
}
