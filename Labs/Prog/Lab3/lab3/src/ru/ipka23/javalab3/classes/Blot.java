package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclasses.AbstractInkObject;

public class Blot extends AbstractInkObject {
    private boolean longTail = false;
    private boolean blotIsOnThePage = false;
    private Neznayka neznayka;
    public Blot() {
        super("клякса");
    }
    public void hasLongTail() {
        if (longTail) {
            changeName("комета");
            System.out.println("Такие кляксы с длинным хвостом он называл " + getName() + ".");
        }
        else {
            changeName("клякса");
            System.out.println("обычная клякса");
        }

    }
    public void setLongTail() {
        this.longTail = true;
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