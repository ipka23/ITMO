package ru.ipka23.newjavalab3.enums;

public enum Mood {
    HAPPY("счастлив"),
    NOT_UPSET("не грустное"),
    GOOD("хорошее");
    private final String mood;
    Mood(String mood) {
        this.mood = mood;
    }
    public String getMood() {
        return mood;
    }
}