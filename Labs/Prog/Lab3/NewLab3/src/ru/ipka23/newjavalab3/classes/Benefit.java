package ru.ipka23.newjavalab3.classes;


public class Benefit {
    private static String benefitStatus;
    private static String bigBenefit = "большая польза";
    private static String veryBigBenefit = "очень большая польза";
    public static void setBenefit() {
        benefitStatus = bigBenefit;
    }
    public static void setDoubleBenefit() {
        benefitStatus = veryBigBenefit;
    }
    public static String get(){
        return "и от этого была, " + benefitStatus + ".";
    }
}
