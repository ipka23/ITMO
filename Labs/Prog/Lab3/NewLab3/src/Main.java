import ru.ipka23.newjavalab3.actions.*;
import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.classes.*;
import ru.ipka23.newjavalab3.enums.*;
import ru.ipka23.newjavalab3.exeptions.InvalidNumberOfPage;
import ru.ipka23.newjavalab3.exeptions.NumberOfPageValidator;
import ru.ipka23.newjavalab3.inkobject.Blot;
import ru.ipka23.newjavalab3.page.BookPage;
import ru.ipka23.newjavalab3.page.NotebookPage;
import ru.ipka23.newjavalab3.pageobjects.Book;
import ru.ipka23.newjavalab3.pageobjects.Notebook;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        NumberOfPageValidator validator = new NumberOfPageValidator();

        Neznayka neznayka = new Neznayka();

        Benefit benefit = new Benefit();

        Blot blot1 = new Blot();
        Blot blot2 = new Blot();

        MoodStatus moodStatus = new MoodStatus();
        BookPage bookPage1 = new BookPage(1);
        BookPage bookPage2 = new BookPage(2);

        List<BookPage> bookPages = Arrays.asList(bookPage1, bookPage2);
        NotebookPage notebookPage1 = new NotebookPage(1);
        NotebookPage notebookPage2 = new NotebookPage(2);
        List<NotebookPage> notebookPages = Arrays.asList(notebookPage1, notebookPage2);
        Notebook notebook = new Notebook(notebookPages);




        GameAction gameAction = new GameAction();
        SittingAction sittingAction = new SittingAction();
        ReadingAction readingAction = new ReadingAction();
        WritingAction writingAction = new WritingAction();
        BlotAction blotAction = new BlotAction();
        ThinkingAction thinkingAction = new ThinkingAction();

        WriterAction writer = new WriterAction();


        Letter printedLetter = new Letter(LetterType.PRINTED);
        Letter notBeautifulWrittenLetter = new Letter(LetterType.WRITTEN, LetterCharacteristic.NOT_BEAUTIFUL);
        Letter upperCaseBeautifulLetter = new Letter(LetterType.UPPERCASE, LetterCharacteristic.BEAUTIFUL);
        Letter lowerCaseBeautifulLetter = new Letter(LetterType.LOWERCASE, LetterCharacteristic.BEAUTIFUL);
        List<Letter> lettersList = Letter.createLettersList(upperCaseBeautifulLetter, lowerCaseBeautifulLetter);


        Experience experience = new Experience();
        Effort effort = new Effort();



        gameAction.play(neznayka, Game.TOWNS, false);
        gameAction.play(neznayka, Game.FOOTBALL, false);


        sittingAction.sit(neznayka, ObjectForSitting.TABLE);


        readingAction.setReadingFrequency(neznayka, Frequency.EVERY_DAY);
        readingAction.startReading(neznayka);
        readingAction.read(neznayka, bookPages);
        System.out.println(benefit.get(neznayka));
        readingAction.getReadingFrequency(neznayka);
        readingAction.finishReading(neznayka);


        writingAction.startWriting(neznayka, notebook);
        writingAction.writeLetter(neznayka, notebookPages, printedLetter, false);
        writingAction.writeLetter(neznayka, notebookPages, notBeautifulWrittenLetter, true);
        writingAction.write(neznayka, lettersList, experience);
        effort.isTryingHard(neznayka);
        effort.setPatience(neznayka);
        effort.isDoingHardWork(neznayka);
        effort.tryingHardStatus(neznayka);
        writingAction.write(neznayka, lettersList, experience);


        blotAction.makeTheBlot(neznayka, blot1, notebookPage1);
        blotAction.makeTheBlot(neznayka, blot2, notebookPage2);
        writingAction.finishWriting(neznayka, notebook);
        blotAction.lickTheBlot(neznayka, blot1);
        blotAction.lickTheBlot(neznayka, blot2);
        blot1.hasLongTail();
        blot2.hasLongTail();
        NotebookPage.hasBlots(notebookPages);


        moodStatus.changeMood(neznayka, notebookPages);
        thinkingAction.think(neznayka, effort, notebookPages);
        writer.tell();
    }
}