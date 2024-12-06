package ru.ipka23.javalab3.classes;

public class Book {
    private String title;
    private Page[] pages;
    public Book(String title) {
        this.title = title;
    }
    public Book(Page[] pages) {
        this.pages = pages;
    }
    public Book(String title, Page[] pages) {
        this.title = title;
        this.pages = pages;
    }
    public String getTitle(){
        return title;
    }
    @Override
    public String toString(){
        return "Книга: " + title + "\n" + "Количество страниц: " + pages.length;
    }


}
