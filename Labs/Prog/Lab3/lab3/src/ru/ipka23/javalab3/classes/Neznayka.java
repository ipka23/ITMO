package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclases.AbstractCharacter;
import ru.ipka23.javalab3.enums.Game;

public class Neznayka extends AbstractCharacter {
    private double ch = 0f;
    public Neznayka(){
        super("Незнайка");
    }


    public void chance(double ch) {
        this.ch = ch;
        if (ch < 0 || ch > 1) {
            throw new IllegalArgumentException("Шанс должен быть в пределах от 0 до 1");
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
    double rand = Math.random();
    public final Neznayka makeBlot() {
        if (rand <= this.ch) {
            System.out.println(getName() + " поставил кляксу!");
        }
        else {
            System.out.println(getName() + " не поставил кляксу");
        }
        return this;
    }
}