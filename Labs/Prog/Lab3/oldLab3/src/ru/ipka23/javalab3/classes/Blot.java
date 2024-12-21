package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclasses.AbstractInkObject;

public class Blot extends AbstractInkObject {
    private boolean longTail = false;
    private boolean blotIsOnThePage = false;
    private Neznayka neznayka;
    public Blot() {
        super("клякса");
    }
    public void blotWithLongTail() {
        System.out.println("Такие кляксы с длинным хвостом он называл " + "комета" + ".");

    }

    public void regularBlot() {
        System.out.println("обычная клякса");
    }
    public void setLongTail() {
        this.longTail = true;
        changeName("комета");
    }


    public boolean getLongTail() {
        return longTail;
    }


    protected void changeName(String name) {
        this.name = name;
    }

    public boolean isOnThePage() {
        return blotIsOnThePage;
    }

    public void setBlotOnThePage() {
        this.blotIsOnThePage = true;
    }
}