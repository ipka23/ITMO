package ru.ipka23.javalab3.classes;

import java.lang.ref.PhantomReference;
import java.util.Arrays;
import java.util.List;

public class Page {
    private int numberOfPage;
    private boolean flag = false;
    public Page(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }
    public List<Page> createPages(Page... pages) {
        return Arrays.asList(pages);
    }
    public void readStatus() {
        if (flag) {
            System.out.println("прочитана");
        }
        else {
            System.out.println("не прочитана");
        }
    }
    public void setReadFlag() {
        this.flag = true;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }
    public static List<Page> setPages(Page... pages) {
        return Arrays.asList(pages);
    }
    @Override
    public String toString() {
        return "страница " + numberOfPage;
    }
}
