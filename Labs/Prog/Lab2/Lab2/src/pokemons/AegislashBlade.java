package pokemons;

import moves.BrutalSwing;
import moves.DoubleTeam;
import moves.HeadSmash;
import moves.MetalSound;
import ru.ifmo.se.pokemon.Type;

public class AegislashBlade extends Doublade{
    public AegislashBlade(String name, int level){
        super(name, level);
        setType(Type.STEEL, Type.GHOST);
        setStats(60, 50, 140, 50, 140, 60);
        setMove(new BrutalSwing(), new DoubleTeam(), new MetalSound(), new HeadSmash());
    }
}
