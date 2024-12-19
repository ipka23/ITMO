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
        Neznayka neznayka = new Neznayka();
        NotebookPage notebookPage1 = new NotebookPage(1);
        NotebookPage notebookPage2 = new NotebookPage(2);
        List<NotebookPage> notebookPages = Arrays.asList(notebookPage1, notebookPage2);
        Notebook notebook = new Notebook(notebookPages);

        WritingAction writingAction = new WritingAction();

        WriterAction writer = new WriterAction();

        Letter printedLetter = new Letter(LetterType.PRINTED);
        Letter notBeautifulWrittenLetter = new Letter(LetterType.WRITTEN, LetterCharacteristic.NOT_BEAUTIFUL);
        Letter upperCaseBeautifulLetter = new Letter(LetterType.UPPERCASE, LetterCharacteristic.BEAUTIFUL);
        Letter lowerCaseBeautifulLetter = new Letter(LetterType.LOWERCASE, LetterCharacteristic.BEAUTIFUL);
        List<Letter> lettersList = Letter.createLettersList(upperCaseBeautifulLetter, lowerCaseBeautifulLetter);

        Experience experience = new Experience();

        writingAction.startWriting(neznayka, notebook);
        writingAction.finishWriting(neznayka, notebook);
        writingAction.cantWriteLetter(neznayka, notebookPages, printedLetter);
        writingAction.writeLetter(neznayka, notebookPages, notBeautifulWrittenLetter);
        writingAction.write(neznayka, lettersList, experience);

        writingAction.tell();
    }
}