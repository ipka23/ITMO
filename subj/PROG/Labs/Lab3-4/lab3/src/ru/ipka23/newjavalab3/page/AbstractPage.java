package ru.ipka23.newjavalab3.page;


public abstract class AbstractPage {
    protected int numberOfPage;


    public AbstractPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }


    public int getNumberOfPage() {
        return numberOfPage;
    }
}
