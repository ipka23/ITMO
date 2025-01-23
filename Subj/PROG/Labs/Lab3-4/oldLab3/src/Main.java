import ru.ipka23.javalab3.classes.*;
import ru.ipka23.javalab3.enums.*;

import java.util.List;
public class Main {
    public static void main(String[] args) {
        Neznayka neznayka = new Neznayka();
        List<Game> gamesList = Game.createGameList(Game.TOWNS, Game.FOOTBALL);
        for (Game game : gamesList) {
            neznayka.dontPlay(game);
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
        neznayka.read(bookPages);

        neznayka.finishReading();
        neznayka.startWriting(notebook);
        neznayka.cantWriteLetter(notebookPage1, printedLetter);
        neznayka.canWriteLetter(notebookPage2, notBeautifulWrittenLetter);
        neznayka.write(lettersList);
        neznayka.write(lettersList);
        neznayka.makeBlot(blot, notebookPage1);
        neznayka.makeBlot(blot, notebookPage2);
        neznayka.lickTheBlot(blot);
        blot.blotWithLongTail();
        NotebookPage.hasBlots(notebookPages);
        neznayka.mood();
        neznayka.tell();
    }
}