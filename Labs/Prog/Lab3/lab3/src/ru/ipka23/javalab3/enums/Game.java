package ru.ipka23.javalab3.enums;
import java.util.Arrays;
import java.util.List;



public enum Game {
    TOWNS("городки"),
    FOOTBALL("футбол");
    private final String game;

    // Конструктор принимает строку
    Game(String game) {
        this.game = game;
    }

    public String getGame() {
        return this.game;
    }

    // Метод для создания списка игр
    public static List<Game> createGameList(Game... games) {
        return Arrays.asList(games);
    }
}
