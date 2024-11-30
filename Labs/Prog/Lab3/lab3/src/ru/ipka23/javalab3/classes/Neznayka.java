package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclases.Character;
import ru.ipka23.javalab3.enums.Game;
import ru.ipka23.javalab3.interfaces.Playable;

public class Neznayka extends Character implements Playable {
    public Neznayka(String name){
        super(name);
    }

    public String play(Game game) {
       return getName() + " играет в " + game.getGame();
    }

}