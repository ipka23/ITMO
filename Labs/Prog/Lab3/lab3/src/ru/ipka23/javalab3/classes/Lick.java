package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclases.AbstractInkObject;


public class Lick {
    private Blot blot;
    public Lick(Blot blot) {
        this.blot = blot;
    }

    public void lickTheBlot(Blot blot) {
        blot.setLongTail();
    }



}
