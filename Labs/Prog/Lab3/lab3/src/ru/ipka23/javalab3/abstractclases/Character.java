package ru.ipka23.javalab3.abstractclases;

public abstract class Character {
    private String name;
    public Character(String name) {
        this.name = name;
    }

    public void read() {

    }
    public void write() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
