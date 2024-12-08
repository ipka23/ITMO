package ru.ipka23.newjavalab3.classes;

public class Experience {
    private int experience;


    public Experience() {
        experience = 0;
    }


    public int get() {
        return experience;
    }


    public String describe() {
        if (experience <= 2) {
            return "Первое время ";
        }
        else {
            return "с опытом";
        }
    }


    public void enhance(int exp) {
        experience += exp;
    }
}
