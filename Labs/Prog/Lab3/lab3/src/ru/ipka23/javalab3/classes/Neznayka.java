package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclases.AbstractCharacter;

public class Neznayka extends AbstractCharacter {
    private double ch = 0.0f;
    public Neznayka(){
        super("Незнайка");
    }


    public final void chance(double ch) {
        this.ch = ch;
        if (ch < 0 || ch > 1) {
            throw new IllegalArgumentException("Шанс должен быть в пределах от 0 до 1");
        }
    }
    public final Neznayka makeBlot() {
        if (Math.random() <= this.ch) {
            System.out.println(getName() + " поставил кляксу!");
        }
        else {
            System.out.println(getName() + " не поставил кляксу");
        }
        return this;
    }
}