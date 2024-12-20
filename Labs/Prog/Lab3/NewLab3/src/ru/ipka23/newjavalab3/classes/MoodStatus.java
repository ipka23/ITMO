package ru.ipka23.newjavalab3.classes;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.enums.Mood;
import ru.ipka23.newjavalab3.page.NotebookPage;


public class MoodStatus {
    private Mood mood;


    private void setMood(Mood mood) {
        this.mood = mood;
    }


    public void getMood(Neznayka neznayka) {
        if (mood == Mood.HAPPY) {
            System.out.println(neznayka.getName() + " счастлив, потому что не поставил ни одной кляксы!");
        }
        if (mood == Mood.GOOD) {
            System.out.println("У " + neznayka.getName() + " было хорошее настроение, потому что поставил только одну кляксу");
        }
        if (mood == Mood.NOT_UPSET) {
            System.out.print("Но у " + neznayka.getName() + " было " + Mood.NOT_UPSET.getMood() + " настроение, ведь он знал, что ");
        }
    }





    public void changeMood() {
        if (NotebookPage.doesItHasNoBlots()){
            setMood(Mood.HAPPY);
        }
        if (NotebookPage.doesItHasBlotAlmostOnEveryPage()) {
            setMood(Mood.GOOD);
        }
        else {
            setMood(Mood.NOT_UPSET);
        }
    }
}
