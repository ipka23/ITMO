package ru.ipka23.newjavalab3.pageobjects;

import ru.ipka23.newjavalab3.page.AbstractPage;

import java.util.List;

public abstract class AbstractPageObject<pageType extends AbstractPage> {
    private List<pageType> pages;


    public AbstractPageObject(List<pageType> pages) {
        this.pages = pages;
    }


    public List<pageType> getPages() {
        return pages;
    }


    protected void setPages(List<pageType> pages) {
        this.pages = pages;
    }
}
