import ru.ipka23.javalab3.classes.Blot;
import ru.ipka23.javalab3.classes.Book;
import ru.ipka23.javalab3.classes.Neznayka;
import ru.ipka23.javalab3.classes.Page;
import ru.ipka23.javalab3.enums.Frequency;
import ru.ipka23.javalab3.enums.Game;
import ru.ipka23.javalab3.enums.ObjectForSitting;

import java.util.Arrays;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        Neznayka neznayka = new Neznayka();
        List<Game> gamesList = Game.createGameList(Game.TOWNS, Game.FOOTBALL);
        for (Game game : gamesList) {
            neznayka.play(game, false);
        }
        Blot blot1 = new Blot();
        neznayka.sit(ObjectForSitting.TABLE);
        Page page1 = new Page(1);
        Page page2 = new Page(2);
        List<Page> pages = Page.setPages(page1, page2);
        neznayka.setReadingFrequency(Frequency.EVERY_DAY);
        neznayka.startReading(pages);
        neznayka.read(pages, 0.7);
//        page1.readStatus();
//        page2.readStatus();
        if (neznayka.finishReading(pages)){
        }




    }
}