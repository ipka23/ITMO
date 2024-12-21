package ru.ipka23.newjavalab3.classes;

import ru.ipka23.newjavalab3.enums.LetterCharacteristic;
import ru.ipka23.newjavalab3.enums.LetterType;

import java.util.Arrays;
import java.util.List;

public class Letter {
    private LetterType letterType;
    private LetterCharacteristic letterCharacteristic;


    public Letter(LetterType letterType, LetterCharacteristic letterCharacteristic) {
        this.letterType = letterType;
        this.letterCharacteristic = letterCharacteristic;
    }


    public Letter(LetterType letterType) {
        this.letterType = letterType;
    }


    public LetterCharacteristic getLetterCharacteristic() {
        return letterCharacteristic;
    }


    public LetterType getLetterType() {
        return letterType;
    }


    public static List<Letter> createLettersList(Letter... letters) {
        return Arrays.asList(letters);
    }
}