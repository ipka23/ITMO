package pokemons;

import moves.BrutalSwing;
import moves.DoubleTeam;
import moves.MetalSound;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.Type;

public class Doublade extends Honedge {
    public Doublade(String name, int level){
        super(name, level);
        setType(Type.STEEL, Type.GHOST);
        setStats(59, 110, 150, 45, 49, 35);
        setMove(new BrutalSwing(), new DoubleTeam(), new MetalSound());
    }
}
