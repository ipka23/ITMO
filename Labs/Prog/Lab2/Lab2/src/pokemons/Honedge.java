package pokemons;

import moves.BrutalSwing;
import moves.DoubleTeam;
import ru.ifmo.se.pokemon.Move;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Honedge extends Pokemon
{
    public Honedge(String name, int level)
    {
        super(name, level);
        setStats(45, 80, 100, 35, 37, 28);
        setType(Type.STEEL, Type.GHOST);
        setMove(new BrutalSwing(), new DoubleTeam());
    }
}
