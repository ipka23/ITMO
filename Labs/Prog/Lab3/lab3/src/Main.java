import ru.ipka23.javalab3.classes.Blot;
import ru.ipka23.javalab3.classes.Book;
import ru.ipka23.javalab3.classes.Neznayka;
import ru.ipka23.javalab3.classes.Page;
import ru.ipka23.javalab3.enums.Game;
import ru.ipka23.javalab3.enums.ObjectForSitting;

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
        Page page = new Page(1);
        Page page2 = new Page(2);
        Page[] pages = new Page[2];
        Book book = new Book("Книга", pages);
        System.out.println(book);

    }
}