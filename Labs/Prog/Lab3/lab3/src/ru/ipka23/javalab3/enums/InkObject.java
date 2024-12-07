package ru.ipka23.javalab3.enums;

public enum InkObject {
    BLOT("клякса"),
    KRIVULKA("кривулька"),
    KRENDEL("крендель");
    private final String name;
    private InkObject(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

