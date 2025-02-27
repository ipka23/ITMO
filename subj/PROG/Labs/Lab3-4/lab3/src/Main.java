import ru.ipka23.newjavalab3.actions.*;
import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.classes.*;
import ru.ipka23.newjavalab3.enums.*;
import ru.ipka23.newjavalab3.inkobject.Blot;
import ru.ipka23.newjavalab3.page.BookPage;
import ru.ipka23.newjavalab3.page.NotebookPage;
import ru.ipka23.newjavalab3.pageobjects.Notebook;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Neznayka neznayka = new Neznayka();
        NotebookPage notebookPage1 = new NotebookPage(1);
        NotebookPage notebookPage2 = new NotebookPage(2);
        List<NotebookPage> notebookPages = Arrays.asList(notebookPage1, notebookPage2);
        Notebook notebook = new Notebook(notebookPages);


        WritingAction writingAction = new WritingAction();
        GameAction gameAction = new GameAction();
        SittingAction sittingAction = new SittingAction();
        ReadingAction readingAction = new ReadingAction();
        BlotAction blotAction = new BlotAction();


        Letter printedLetter = new Letter(LetterType.PRINTED);
        Letter notBeautifulWrittenLetter = new Letter(LetterType.WRITTEN, LetterCharacteristic.NOT_BEAUTIFUL);
        Letter upperCaseBeautifulLetter = new Letter(LetterType.UPPERCASE, LetterCharacteristic.BEAUTIFUL);
        Letter lowerCaseBeautifulLetter = new Letter(LetterType.LOWERCASE, LetterCharacteristic.BEAUTIFUL);
        List<Letter> lettersList = Letter.createLettersList(upperCaseBeautifulLetter, lowerCaseBeautifulLetter);


        gameAction.dontPlay(neznayka, Game.TOWNS);
        gameAction.dontPlay(neznayka, Game.FOOTBALL);
        gameAction.tell();

        sittingAction.sit(neznayka, ObjectForSitting.TABLE);
        sittingAction.tell();

        BookPage bookPage1 = new BookPage(1);
        BookPage bookPage2 = new BookPage(2);

        List<BookPage> bookPages = Arrays.asList(bookPage1, bookPage2);
        readingAction.startReading();
        readingAction.read(neznayka, bookPages);
        readingAction.finishReading();
        readingAction.tell();




        Experience experience = new Experience();

        writingAction.startWriting(neznayka, notebook);
        writingAction.cantWriteLetter(neznayka, printedLetter);
        writingAction.writeLetter(neznayka, notBeautifulWrittenLetter);
        writingAction.write(lettersList, experience);
        Effort.tryHard();
        writingAction.writeBeautifulLetters(neznayka, lettersList);
        writingAction.tell();


        Blot blot1 = new Blot();
        Blot blot2 = new Blot();

        blotAction.makeTheBlot(neznayka, blot1, notebookPage1);
        blotAction.makeTheBlot(neznayka, blot2, notebookPage2);
        blotAction.lickTheBlot(blot1);
        blotAction.lickTheBlot(blot2);
        blotAction.tell();

        NotebookPage.setPagesWithBlots(notebookPages);
        NotebookPage.getBlotCount();
        MoodStatus moodStatus = new MoodStatus();
        moodStatus.changeMood();
        moodStatus.getMood(neznayka);
        Effort.isDoingHardWork();
        Effort.setPatience();
        ThinkingAction.think();
    }
}