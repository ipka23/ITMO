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
        neznayka.sit(ObjectForSitting.CHAIR);
        neznayka.makeBlot().chance(0.5);
        Blot blot = new Blot();
        blot.setLongTail();
        System.out.println(blot.getName());
        blot.hasLongTail();
        System.out.println(blot.getName());
        Blot blot2 = new Blot();
        System.out.println(blot2.getName());
        Book book = new Book("Book", new Page(7));
        System.out.println(book.getCountOfPages());
    }
}
