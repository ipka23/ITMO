package ru.ipka23.newjavalab3.pageobjects;

import ru.ipka23.newjavalab3.page.BookPage;

import java.util.Arrays;
import java.util.List;

public class Book extends AbstractPageObject<BookPage> {
    private String title;


    public Book(List<BookPage> pages) {
        super(pages);
    }


    public Book(String title, List<BookPage> pages) {
        super(pages);
        this.title = title;
    }


    public String getTitle() {
        return title;
    }


    public static List<BookPage> setBookPages(BookPage... bookPages) {
        return Arrays.asList(bookPages);
    }
}
