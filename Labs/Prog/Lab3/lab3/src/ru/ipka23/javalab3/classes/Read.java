package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclases.Action;

public class Read extends Action {
    private Neznayka neznayka; // Добавлено поле для хранения объекта ru.ipka23.javalab3.classes.Neznayka
    private Book book;

    public Read(Neznayka neznayka, Book book) {
        this.neznayka = neznayka; // Сохраняем объект ru.ipka23.javalab3.classes.Neznayka
        this.book = book;
    }

    @Override
    public String doSomething() {
        return neznayka.getName() + " читает " + book.getTitle(); // Используем методы getName() и getTitle()
    }
}