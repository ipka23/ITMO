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
            System.out.println(longTail);
        }
        else {
            changeName("клякса");
            System.out.println(longTail);
        }

    }
    public void setLongTail() {
        this.longTail = true;
    }
    private void changeName(String name) {
        this.name = name;
    }

    public boolean isOnThePage() {
        return blotIsOnThePage;
    }

    public void setBlotOnThePage() {
        this.blotIsOnThePage = true;
        System.out.println("клякса на странице!");
    }
}