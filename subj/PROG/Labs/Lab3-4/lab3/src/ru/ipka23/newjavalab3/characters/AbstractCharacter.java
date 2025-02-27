package ru.ipka23.newjavalab3.characters;



public abstract class AbstractCharacter {

    private String name;


    public AbstractCharacter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}