package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclases.AbstractInkObject;


public class Lick {
    private AbstractInkObject blot;
    public Lick(AbstractInkObject blot) {
        this.blot = blot;
    }
    public AbstractInkObject getBlotName() {
        return blot;
    }

    public void setBlot(AbstractInkObject blot) {
        this.blot = blot;
    }



}
