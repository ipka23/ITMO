import ru.ipka23.javalab3.classes.Neznayka;
import ru.ipka23.javalab3.enums.Game;

public class Main {
    public static void main(String[] args) {
        Neznayka neznayka = new Neznayka("Незнайка");
        System.out.println("вместо того чтобы " + neznayka.play(Game.TOWNS) + " " + "или " + neznayka.play(Game.FOOTBALL));

    }
}
