package ru.ipka23.newjavalab3.page;

import ru.ipka23.newjavalab3.exeptions.InvalidNumberOfPage;
import ru.ipka23.newjavalab3.exeptions.NumberOfPageValidator;

public class BookPage extends AbstractPage  {
    private boolean readFlag = false;
    private NumberOfPageValidator validator = new NumberOfPageValidator();


    public BookPage(int numberOfPage) {
        super(numberOfPage);
        try {
            validator.validatePageNumber(numberOfPage);
        } catch (InvalidNumberOfPage e) {
            throw new RuntimeException(e);
        }
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
