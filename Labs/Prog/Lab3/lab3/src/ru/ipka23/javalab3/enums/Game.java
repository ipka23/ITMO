package ru.ipka23.javalab3.enums;

public enum Game {
    TOWNS ("городки"),
    FOOTBALL ("футбол"),
    GAME ("игра");

    private final String game;

    Game(String game) {
        this.game = game;
    }
    public String getGame(){
        return game;
    }
    @Override
    public String toString(){
        return game;
    }
}