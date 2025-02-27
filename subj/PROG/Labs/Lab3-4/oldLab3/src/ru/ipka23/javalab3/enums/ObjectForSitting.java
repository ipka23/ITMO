package ru.ipka23.javalab3.enums;

public enum ObjectForSitting {
    TABLE ("стол"),
    CHAIR("стул");

    private final String name;

    ObjectForSitting(String name) {
        this.name = name;
    }

    public String getObjectForSittingName() {
        return name;
    }
}
