package ru.ipka23.javalab3.abstractclases;

public abstract class AbstractBlot {
    static boolean longTail = false;
    private String name;
    public AbstractBlot(String name){
        this.name = name;
        System.out.println(getName() + " создана");
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasLongTail() {
        if (longTail) {
            this.name = "комета";
            System.out.println(longTail);
        }
        else {
            this.name = "клякса";
            System.out.println(longTail);
        }
        return longTail;

    }
    public void setLongTail() {
        AbstractBlot.longTail = true;
    }


}