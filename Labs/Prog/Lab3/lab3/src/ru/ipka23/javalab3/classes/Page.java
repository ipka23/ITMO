package ru.ipka23.javalab3.classes;

public class Page {
    private int count;
    public Page(int count) {
        this.count = count;
        System.out.println("Создано " + count + " страниц");
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
