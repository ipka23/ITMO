package pokemons;

import moves.physical.HeadSmash;

public class AegislashBlade extends Doublade{
    public AegislashBlade(String name, int level){
        super(name, level);
        setStats(60, 140, 50, 140, 50, 60);
        addMove(new HeadSmash());
    }
}
