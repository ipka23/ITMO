package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclasses.AbstractCharacter;
import ru.ipka23.javalab3.enums.Frequency;
import ru.ipka23.javalab3.enums.Game;

import java.util.List;

public class Neznayka extends AbstractCharacter {
    public Neznayka() {
        super("Незнайка");
    }




    @Override
    public void play(Game game, boolean b) {
        if (b){
            System.out.println(getName() + " играет в " + game.getGame());
        }
        else {
            System.out.print("Вместо того чтобы " + getName() + " играть в " + game.getGame() + ", ");
        }
    }
    public void makeBlot(Blot blot, double chance) {
        if (chance < 0 || chance > 1) {
            throw new IllegalArgumentException("Шанс должен быть в пределах от 0 до 1");
        }
        if (Math.random() <= chance) {
            blot.setBlotOnThePage();
            System.out.println(getName() + " поставил кляксу!");
        }
        else {
            System.out.println(getName() + " не поставил кляксу");
        }
    }
    public void lickTheBlot(Blot blot) {
        if (blot.isOnThePage()){
            blot.setLongTail();
            System.out.println(getName() + " облизал " + blot.getName());
        }
    }


}

