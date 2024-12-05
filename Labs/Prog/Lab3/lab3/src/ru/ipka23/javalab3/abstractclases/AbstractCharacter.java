package ru.ipka23.javalab3.abstractclases;

import ru.ipka23.javalab3.classes.Book;
import ru.ipka23.javalab3.enums.Game;
import ru.ipka23.javalab3.enums.ObjectForSitting;
import ru.ipka23.javalab3.interfaces.Playable;
import ru.ipka23.javalab3.interfaces.Readable;
import ru.ipka23.javalab3.interfaces.Sitable;

public abstract class AbstractCharacter implements Playable, Sitable, Readable {
    private String name;
    private boolean readingIsFinished;
    private boolean writingIsFinished;
    public AbstractCharacter(String name) {
        this.name = name;
        System.out.println("Персонаж " + name + " создан");
    }


    public void sit(ObjectForSitting objectForSitting){
        if (ObjectForSitting.TABLE == objectForSitting){
            System.out.println(getName() + " сел за " + objectForSitting.getObjectForSittingName());
        }
        else {
            System.out.println(getName() + " сел на " + objectForSitting.getObjectForSittingName());
        }
    }

    public void play(Game game, boolean b) {
        if (b){
            System.out.println(getName() + " играет в " + game.getGame());
        }
        else {
            System.out.println(getName() + " не играет в " + game.getGame());
        }
    }


    @Override
    public void read(Book book) {
        System.out.println(getName() + " читал " + book.getTitle());
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
