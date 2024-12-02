package ru.ipka23.javalab3.abstractclases;

import ru.ipka23.javalab3.enums.Game;
import ru.ipka23.javalab3.enums.ObjectForSitting;
import ru.ipka23.javalab3.interfaces.Playable;
import ru.ipka23.javalab3.interfaces.Sitable;

public abstract class AbstractCharacter implements Playable, Sitable {
    private String name;
    public AbstractCharacter(String name) {
        this.name = name;
        System.out.println("Персонаж " + name + " создан");
    }


    @Override
    public void sit(ObjectForSitting objectForSitting){
        if (ObjectForSitting.TABLE == objectForSitting){
            System.out.println(getName() + " сел за " + objectForSitting.getObjectForSittingName());
        }
        else {
            System.out.println(getName() + " сел на " + objectForSitting.getObjectForSittingName());
        }
    }
    @Override
    public void play(Game game, boolean b) {
        if (b){
            System.out.println(getName() + " играет в " + game.getGame());
        }
        else {
            System.out.print("вместо того чтобы " + getName() + " играть в " + game.getGame() + ", ");
        }


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
