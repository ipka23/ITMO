package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.enums.Game;
import ru.ipka23.javalab3.enums.LetterCharacteristic;
import ru.ipka23.javalab3.enums.LetterType;

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
    public Letter(LetterCharacteristic letterCharacteristic) {
        this.letterCharacteristic = letterCharacteristic;
    }

    public LetterCharacteristic getLetterCharacteristic() {
        return letterCharacteristic;
    }

    public void setLetterCharacteristic(LetterCharacteristic letterCharacteristic) {
        this.letterCharacteristic = letterCharacteristic;
    }

    public LetterType getLetterType() {
        return letterType;
    }

    public void setLetterType(LetterType letterType) {
        this.letterType = letterType;
    }

    public static List<Letter> createLettersList(Letter... letters) {
        return Arrays.asList(letters);
    }
}
