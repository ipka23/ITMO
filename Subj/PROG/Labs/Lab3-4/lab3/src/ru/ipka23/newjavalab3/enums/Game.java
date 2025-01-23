package ru.ipka23.newjavalab3.enums;

import java.util.Arrays;
import java.util.List;

public enum Game {
    TOWNS("городки"),
    FOOTBALL("футбол");
    private final String game;

    Game(String game) {
        this.game = game;
    }

    public String getGame() {
        return this.game;
    }

    public static List<Game> createGameList(Game... games) {
        return Arrays.asList(games);
    }
}