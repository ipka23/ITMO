package pokemons;

import moves.physical.BrutalSwing;
import moves.status.DoubleTeam;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Honedge extends Pokemon {
    public Honedge(String name, int lvl) {
        super(name, lvl);
        setType(Type.STEEL, Type.GHOST);
        setStats(45, 80, 100, 35, 37, 28);
        setMove(new BrutalSwing(), new DoubleTeam());
    }
}
