import ru.ipka23.javalab3.classes.*;
import ru.ipka23.javalab3.enums.*;

import java.util.List;
public class Main {
    public static void main(String[] args) {
        Neznayka neznayka = new Neznayka();
        List<Game> gamesList = Game.createGameList(Game.TOWNS, Game.FOOTBALL);
        for (Game game : gamesList) {
            neznayka.play(game, false);
        }
        Blot blot = new Blot();
        neznayka.sit(ObjectForSitting.TABLE);
        BookPage bookPage1 = new BookPage(1);
        BookPage bookPage2 = new BookPage(2);
        NotebookPage notebookPage1 = new NotebookPage(1);
        NotebookPage notebookPage2 = new NotebookPage(2);
        List<BookPage> bookPages = Book.setBookPages(bookPage1, bookPage2);
        List<NotebookPage> notebookPages = Notebook.setNotebookPages(notebookPage1, notebookPage2);
        Notebook notebook = new Notebook(notebookPages);
        Letter printedLetter = new Letter(LetterType.PRINTED);
        Letter notBeautifulWrittenLetter = new Letter(LetterType.WRITTEN, LetterCharacteristic.NOT_BEAUTIFUL);
        Letter upperCaseBeautifulLetter = new Letter(LetterType.UPPERCASE, LetterCharacteristic.BEAUTIFUL);
        Letter lowerCaseBeautifulLetter = new Letter(LetterType.LOWERCASE, LetterCharacteristic.BEAUTIFUL);
        List<Letter> lettersList = Letter.createLettersList(upperCaseBeautifulLetter, lowerCaseBeautifulLetter);




        neznayka.setReadingFrequency(Frequency.EVERY_DAY);
        neznayka.startReading(bookPages);
        neznayka.read(bookPages, 0.7);
//        page1.readStatus();
//        page2.readStatus();
        neznayka.finishReading(bookPages);
        neznayka.startWriting(notebook);
        neznayka.writeLetter(notebookPages, printedLetter, false);
        neznayka.writeLetter(notebookPages, notBeautifulWrittenLetter, true);
        neznayka.write(lettersList);
        neznayka.write(lettersList);
        neznayka.makeBlot(blot, 0.5, notebookPage1);
        neznayka.makeBlot(blot, 0.5, notebookPage2);
        neznayka.lickTheBlot(blot);
        blot.hasLongTail();
        NotebookPage.hasBlots(notebookPages);
        neznayka.mood(notebookPages);
    }
}