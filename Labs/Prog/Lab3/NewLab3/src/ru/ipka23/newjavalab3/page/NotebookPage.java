package ru.ipka23.newjavalab3.page;

import ru.ipka23.newjavalab3.exeptions.InvalidNumberOfPage;
import ru.ipka23.newjavalab3.exeptions.NumberOfPageValidator;

import java.util.List;

public class NotebookPage extends AbstractPage {

    private boolean hasBlot = false;
    private static boolean hasBlotOnEveryPage = false;
    private static boolean hasBlotAlmostOnEveryPage = false;
    private static boolean hasNoBlots = false;
    private NumberOfPageValidator validator = new NumberOfPageValidator();

    public NotebookPage(int numberOfPage) {
        super(numberOfPage);
        try {
            validator.validatePageNumber(numberOfPage);
        } catch (
                InvalidNumberOfPage e) {
            throw new RuntimeException(e);
        }
    }



    public void setBlotOnThePage(){
        hasBlot = true;
    }


    public boolean hasBlot(){
        return hasBlot;
    }


    public static boolean doesItHasBlotOnEveryPage() {
        return hasBlotOnEveryPage;
    }


    public static boolean doesItHasBlotAlmostOnEveryPage() {
        return hasBlotAlmostOnEveryPage;
    }


    public static boolean doesItHasNoBlots() {
        return hasNoBlots;
    }


    public static void setPagesWithBlots(List<NotebookPage> pages) {
        if (pages.get(0).hasBlot() && pages.get(1).hasBlot()) {
            hasBlotOnEveryPage = true;
            hasNoBlots = false;
        }
        else if (!pages.get(0).hasBlot() && !pages.get(1).hasBlot()) {
            hasNoBlots = true;
        }
        else {
            hasBlotAlmostOnEveryPage = true;
            hasNoBlots = false;

        }
    }

    public static void getBlotCount() {
        if (hasBlotOnEveryPage) {
            System.out.println("Такие кометы были у него на каждой странице.");
        }
        if (hasNoBlots){
            System.out.println("Ни на одной странице не было кометы.");
        }
        if (hasBlotAlmostOnEveryPage) {
            System.out.println("Такие кометы были у него почти на каждой странице.");
        }
    }

    @Override
    public String toString() {
        return "странице № " + numberOfPage;
    }
}
