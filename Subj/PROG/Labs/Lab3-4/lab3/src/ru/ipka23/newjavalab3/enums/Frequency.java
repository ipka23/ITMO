package ru.ipka23.newjavalab3.enums;

public enum Frequency {
    EVERY_DAY("каждый день"),
    EVERY_WEEK("каждую неделю"),
    EVERY_MONTH("каждый месяц"),
    EVERY_YEAR("каждый год");


    private final String frequency;


    Frequency(String frequency) {
        this.frequency = frequency;
    }


    public String getFrequency() {
        return this.frequency;
    }
}
