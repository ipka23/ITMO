package ru.ipka23.newjavalab3.classes;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.enums.Mood;
import ru.ipka23.newjavalab3.page.NotebookPage;

import java.util.List;

public class MoodStatus {
    private Mood mood;


    private void setMood(Mood mood) {
        this.mood = mood;
    }


    private String getMood() {
        return mood.getMood();
    }


    private void dontGetUpset(Neznayka neznayka) {
        setMood(Mood.NOT_UPSET);
        System.out.print("Но у " + neznayka.getName() + " было " + Mood.NOT_UPSET.getMood() + " настроение, ведь он знал, что ");
    }


    public void changeMood(Neznayka neznayka, List<NotebookPage> pages) {
        if (NotebookPage.doesItHasNoBlots()){
            setMood(Mood.HAPPY);
            System.out.println(neznayka.getName() + " счастлив, потому что не поставил ни одной кляксы!");
        }
        else {
            setMood(Mood.GOOD);
            dontGetUpset(neznayka);
        }
    }
}
