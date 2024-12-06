package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclasses.AbstractCharacter;

import java.util.Arrays;
import java.util.List;

public class Page {
    private int numberOfPage;
    private boolean flag = false;
    public Page(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }
    public Page[] createPages(Page[] pages) {
        return pages;
    }
    private int countOfReadenPages = 0;
    public void readStatus(AbstractCharacter character, Page page) {
        if (flag) {
            System.out.println(character.getName() + " прочитал страницу №" + page.getNumberOfPage());
        }
        else {
            System.out.println(page.getNumberOfPage() + " ещё не была прочитана персонажем " + character.getName());
        }
    }
    public void setReadFlag() {
        this.flag = true;
    }
    public int countOfReadenPages(Page[] pages) {
        return pages.length;
    }
    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    @Override
    public String toString() {
        return "страница " + numberOfPage;
    }
}
