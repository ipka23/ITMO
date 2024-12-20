package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclasses.AbstractPage;

import java.util.List;

public class NotebookPage extends AbstractPage {
    private boolean writeFlag = false;
    private boolean hasBlot = false;
    private static boolean hasBlotOnEveryPage = false;
    private static boolean hasBlotAlmostOnEveryPage = false;
    private static boolean hasNoBlots = false;
    public NotebookPage(int numberOfPage) {
        super(numberOfPage);
    }
    public void readStatus() {
        if (writeFlag) {
            System.out.println("страница " + numberOfPage + " исписана");
        }
        else {
            System.out.println("страница " + numberOfPage + " не исписана");
        }
    }
    public void setWriteFlag() {
        writeFlag = true;
    }
    public void setBlotOnThePage(){
        hasBlot = true;
    }
    public boolean hasBlot(){
        return hasBlot;
    }

    public static boolean isHasBlotOnEveryPage() {
        return hasBlotOnEveryPage;
    }

    public static boolean isHasBlotAlmostOnEveryPage() {
        return hasBlotAlmostOnEveryPage;
    }

    public static boolean isHasNoBlots() {
        return hasNoBlots;
    }

    //
    public static void hasBlots(List<NotebookPage> pages) {
        if (pages.get(0).hasBlot() && pages.get(1).hasBlot()) {
            hasBlotOnEveryPage = true;
            hasNoBlots = false;
            System.out.println("Такие кометы были у него на каждой странице.");
        }
        else if (!pages.get(0).hasBlot() && !pages.get(1).hasBlot()) {
            hasNoBlots = true;
            System.out.println("Ни на одной странице не было кометы.");
        }
        else {
            hasBlotAlmostOnEveryPage = true;
            hasNoBlots = false;
            System.out.println("Такие кометы были у него почти на каждой странице.");
        }
    }

    @Override
    public String toString() {
        return "страница в тетради № " + numberOfPage;
    }
}
