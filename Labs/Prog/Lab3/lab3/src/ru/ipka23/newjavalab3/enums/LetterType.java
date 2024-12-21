package ru.ipka23.newjavalab3.enums;

public enum LetterType {
    WRITTEN("письменная буква"),
    PRINTED("печатная буква"),
    UPPERCASE("заглавная"),
    LOWERCASE("маленькая");

    private final String letter;

    LetterType(String letter) {
        this.letter = letter;
    }


    public String getLetter() {
        return letter;
    }
}
