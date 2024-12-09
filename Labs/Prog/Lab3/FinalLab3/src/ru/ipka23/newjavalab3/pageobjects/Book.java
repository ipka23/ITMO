package ru.ipka23.newjavalab3.pageobjects;

import ru.ipka23.newjavalab3.page.BookPage;

import java.util.List;

public record Book(String name, List<BookPage> bookPages){}