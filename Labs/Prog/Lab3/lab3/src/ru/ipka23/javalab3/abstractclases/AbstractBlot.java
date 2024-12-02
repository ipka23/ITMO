package ru.ipka23.javalab3.abstractclases;

public abstract class AbstractBlot {
    static boolean longTail = true;
    private String name;
    public AbstractBlot(){
        System.out.println(getName() + " создана");
    }
    public String getName() {
        return name;
    }

    protected boolean hasLongTail() {
        if (longTail) {
            this.name = "комета";
        } else {
            this.name = "клякса";
        }
        return longTail;
    }
    protected void setLongTail(boolean longTail) {

    }


}