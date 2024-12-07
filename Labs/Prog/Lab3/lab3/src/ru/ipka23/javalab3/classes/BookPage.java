package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclasses.AbstractPage;

public class BookPage extends AbstractPage {
    private boolean readFlag = false;
    public BookPage(int pageNumber) {
        super(pageNumber);
    }
    public void readStatus() {
        if (readFlag) {
            System.out.println("страница " + numberOfPage + "прочитана");
        }
        else {
            System.out.println("страница " + numberOfPage + "не прочитана");
        }
    }
    public void setReadFlag() {
        readFlag = true;
    }
    @Override
    public String toString() {
        return "страница в книге № " + numberOfPage;
    }
}
