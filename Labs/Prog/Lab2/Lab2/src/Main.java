import pokemons.Doublade;
import pokemons.Honedge;
import pokemons.Miltank;
import ru.ifmo.se.pokemon.Battle;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Honedge p1 = new Honedge("Мазурова", 1);
        Miltank p2 = new Miltank("Молочный танк", 1);
        Doublade p3 = new Doublade("Шпага", 1);
        b.addAlly(p1);
        b.addFoe(p2);
        b.addAlly(p3);
        b.go();
    }
}