package ru.ipka23.newjavalab3.enums;

public enum LetterCharacteristic {
    BEAUTIFUL("красивая"),
    NOT_BEAUTIFUL("некрасивая");

    private final String letter;


    LetterCharacteristic(String letter) {
        this.letter = letter;
    }


    public String getLetter() {
        return letter;
    }
}