package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclasses.AbstractPageObject;

import java.util.List;

public class Book extends AbstractPageObject {
    private String title;
    public Book(List<Page> pages) {
        super(pages);
    }
    public Book(String title, List<Page> pages) {
        super(pages);
        this.title = title;
    }



    public String getTitle(){
        return title;
    }
    @Override
    public String toString(){
        return "Книга: " + title + "\n" + "Содержимое: " + getPages();
    }


}
