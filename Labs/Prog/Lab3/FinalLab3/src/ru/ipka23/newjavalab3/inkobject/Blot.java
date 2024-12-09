package ru.ipka23.newjavalab3.inkobject;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.enums.InkObject;

public class Blot extends AbstractInkObject {
    private boolean longTail = false;
    private boolean blotIsOnThePage = false;


    public Blot() {
        super(InkObject.BLOT.getName(), InkObject.BLOT);
    }


    public void hasLongTail() {
        if (longTail) {
            changeName("комета");
            System.out.println("Кляксы с длинным хвостом он называл " + getName() + ".");
        }

    }


    public void setLongTail() {
        longTail = true;
    }


    private void changeName(String name) {
        this.name = name;
    }


    public boolean isOnThePage() {
        return blotIsOnThePage;
    }


    public void setBlotOnThePage() {
        blotIsOnThePage = true;
    }
}
