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
    public List<Page> createPages(Page... pages) {
        return Arrays.asList(pages);
    }
    private int countOfReadenPages = 0;
    public void readStatus(AbstractCharacter character, Page... pages) {
        if (flag) {
            System.out.println(character.getName() + " прочитал страницу №" + pages.getNumberOfPage());
        }
        else {
            System.out.println(page.getNumberOfPage() + "ещё не была прочитана персонажем " + character.getName());
        }
    }
    public void setFlag() {
        this.flag = true;
    }
    public void countOfReadenPages(int cnt) {
        this.countOfReadenPages += cnt;
    }
    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    @Override
    public String toString() {
        return "страница " + numberOfPage;
    }
}
