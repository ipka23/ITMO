package ru.ipka23.javalab3.classes;

public class Book {
    private String title;
    private Page page;
    public Book(String title) {
        this.title = title;
        System.out.println("Книга " + title + " создана");
    }
    public Book(Page page) {
        this.page = page;
    }
    public Book(String title, Page page) {
        this.title = title;
        this.page = page;
    }
    public String getTitle(){
        return title;
    }



}
