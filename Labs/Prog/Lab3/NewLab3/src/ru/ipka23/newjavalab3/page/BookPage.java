package ru.ipka23.newjavalab3.page;

public class BookPage extends AbstractPage  {
    private boolean readFlag = false;


    public BookPage(int numberOfPage) {
        super(numberOfPage);
    }


    public void setReadFlag() {
        readFlag = true;
    }


    public boolean getReadFlag() {
        return readFlag;
    }


    @Override
    public String toString() {
        return "страница в книге № " + numberOfPage;
    }
}
