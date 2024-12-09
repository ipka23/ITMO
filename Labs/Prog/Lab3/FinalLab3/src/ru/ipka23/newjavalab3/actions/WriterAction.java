package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.AbstractCharacter;
import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.classes.Experience;
import ru.ipka23.newjavalab3.classes.Letter;
import ru.ipka23.newjavalab3.enums.Frequency;
import ru.ipka23.newjavalab3.enums.Game;
import ru.ipka23.newjavalab3.enums.ObjectForSitting;
import ru.ipka23.newjavalab3.interfaces.Playable;
import ru.ipka23.newjavalab3.interfaces.Readable;
import ru.ipka23.newjavalab3.interfaces.Sitable;
import ru.ipka23.newjavalab3.interfaces.Writable;
import ru.ipka23.newjavalab3.page.BookPage;
import ru.ipka23.newjavalab3.page.NotebookPage;
import ru.ipka23.newjavalab3.pageobjects.Notebook;

import java.util.List;

public class WriterAction implements Playable, Readable, Sitable, Writable {

    @Override
    public void play(AbstractCharacter character, Game game, boolean isPlaying) {

    }

    @Override
    public boolean getPlayingStatus(AbstractCharacter character) {
        return false;
    }

    @Override
    public void read(Neznayka neznayka, List<BookPage> pages) {

    }

    @Override
    public void startReading(Neznayka neznayka) {

    }

    @Override
    public boolean finishReading(Neznayka neznayka) {
        return false;
    }

    @Override
    public void setReadingFrequency(Neznayka neznayka, Frequency frequency) {

    }

    @Override
    public String getReadingFrequency(Neznayka neznayka) {
        return "";
    }

    @Override
    public boolean isReading(Neznayka neznayka) {
        return false;
    }

    @Override
    public void sit(AbstractCharacter abstractCharacter, ObjectForSitting objectForSitting) {

    }

    @Override
    public boolean getSittingStatus(AbstractCharacter abstractCharacter) {
        return false;
    }

    @Override
    public void write(Neznayka neznayka, List<Letter> letters, Experience experience) {

    }

    @Override
    public void writeLetter(Neznayka neznayka, List<NotebookPage> pages, Letter letter, boolean b) {

    }

    @Override
    public void startWriting(Neznayka neznayka, Notebook notebook) {

    }

    @Override
    public void finishWriting(Neznayka neznayka, Notebook notebook) {

    }
}
