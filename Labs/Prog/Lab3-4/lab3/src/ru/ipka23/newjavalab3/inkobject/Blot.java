package ru.ipka23.newjavalab3.inkobject;

import ru.ipka23.newjavalab3.enums.InkObject;

public class Blot extends AbstractInkObject {
    private boolean longTail = false;
    private boolean blotIsOnThePage = false;


    public Blot() {
        super(InkObject.BLOT.getName(), InkObject.BLOT);
    }


    public boolean hasLongTail() {
        return longTail;

    }


    public void setLongTail() {
        longTail = true;
    }


    public void changeName(String name) {
        this.name = name;
    }


    public boolean isOnThePage() {
        return blotIsOnThePage;
    }


    public void setBlotOnThePage() {
        blotIsOnThePage = true;
    }
}
