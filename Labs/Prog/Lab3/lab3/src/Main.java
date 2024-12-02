import ru.ipka23.javalab3.classes.Neznayka;
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
    }
}
