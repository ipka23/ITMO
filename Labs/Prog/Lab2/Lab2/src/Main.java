import pokemons.Honedge;
import ru.ifmo.se.pokemon.Battle;

public class Main
{
    public static void main(String[] args)
    {
        Battle b = new Battle();
        Honedge p1 = new Honedge("Чужой", 1);
        Pokemon p2 = new Pokemon("Хищник", 1);
        b.addAlly(p1);
        b.addFoe(p2);
        b.go();
    }
}