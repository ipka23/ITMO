package ru.ipka23.javalab3.enums;

public enum Mood {
    HAPPY("счастлив"),
    UPSET("грустное"),
    GOOD("хорошее");
    private final String mood;
    Mood(String mood) {
        this.mood = mood;
    }
    public String getMood() {
        return mood;
    }
}
