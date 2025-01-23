package ru.ipka23.javalab3.abstractclasses;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractPage {
    protected int numberOfPage;

    public AbstractPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }
    public List<AbstractPage> createPages(AbstractPage... pages) {
        return Arrays.asList(pages);
    }


    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    @Override
    public String toString() {
        return "страница " + numberOfPage;
    }
}
