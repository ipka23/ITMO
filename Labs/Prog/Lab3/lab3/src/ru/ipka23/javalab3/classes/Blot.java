package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclases.AbstractInkObject;

public class Blot extends AbstractInkObject {
    private boolean longTail = false;
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

}
