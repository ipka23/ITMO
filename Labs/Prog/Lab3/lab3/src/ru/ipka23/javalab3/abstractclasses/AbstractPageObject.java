package ru.ipka23.javalab3.abstractclasses;

import ru.ipka23.javalab3.classes.Page;

import java.util.List;

public class AbstractPageObject {
    private List<Page> pages;
    public AbstractPageObject(List<Page> pages) {
        this.pages = pages;
    }
    protected List<Page> getPages() {
        return pages;
    }
    protected void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
