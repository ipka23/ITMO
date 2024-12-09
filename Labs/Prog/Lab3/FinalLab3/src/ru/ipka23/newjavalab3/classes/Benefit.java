package ru.ipka23.newjavalab3.classes;

import ru.ipka23.newjavalab3.characters.Neznayka;

public class Benefit {
    private String benefitStatus;


    public String get(Neznayka neznayka){
        benefitStatus = "большая польза";
        return "и от этого была, конечно, " + benefitStatus + ".";
    }
}
