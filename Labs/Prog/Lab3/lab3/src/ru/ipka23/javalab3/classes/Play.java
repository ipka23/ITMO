package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclases.Action;
import ru.ipka23.javalab3.enums.Game;

public class Play extends Action {
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Play(Neznayka neznayka, Game game) {
        this.game = game;
    }
    @Override
    public String doSomething() {
        return "играть " + game;
    }
}