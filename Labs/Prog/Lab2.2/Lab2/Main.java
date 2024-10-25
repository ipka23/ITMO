import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        while (count < 6) {
            System.out.println("count = "+count + " evasion = " + (count + 1));
            count += 1;
        }

    }
}
