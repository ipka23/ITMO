package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclasses.AbstractCharacter;
import ru.ipka23.javalab3.enums.Frequency;
import ru.ipka23.javalab3.enums.Game;
import ru.ipka23.javalab3.enums.Mood;

import java.util.List;

public class Neznayka extends AbstractCharacter {
    private Mood mood;
    public Neznayka() {
        super("Незнайка");
    }


    @Override
    public void play(Game game, boolean b) {
        if (b) {
            System.out.println(getName() + " играет в " + game.getGame());
        } else {
            System.out.print("Вместо того чтобы " + getName() + " играть в " + game.getGame() + ", ");
        }
    }

    public void makeBlot(Blot blot, double chance, NotebookPage page) {
        if (chance < 0 || chance > 1) {
            throw new IllegalArgumentException("Шанс должен быть в пределах от 0 до 1");
        }
        if (Math.random() <= chance) {
            System.out.print(getName() + " поставил кляксу, и ");
            blot.setBlotOnThePage();
            page.setBlotOnThePage();
        } else {
            System.out.println(getName() + " не поставил кляксу");
        }
    }

    private void setMood(Mood mood) {
        this.mood = mood;
    }
    public void lickTheBlot(Blot blot) {
        if (blot.isOnThePage()) {
            blot.setLongTail();
            System.out.println("слизал " + blot.getName() + " языком.");
        }
    }

    public void mood(List<NotebookPage> pages) {
        if (NotebookPage.isHasBlotOnEveryPage()) {
            setMood(Mood.UPSET);
            System.out.println(getName() + " приуныл.");
        }
        else if (NotebookPage.isHasBlotAlmostOnEveryPage()) {
            setMood(Mood.GOOD);
            System.out.println(getName() + " не унывал.");
        }
        else {
            setMood(Mood.HAPPY);
            System.out.println(getName() + " счастлив.");
        }
    }
}

