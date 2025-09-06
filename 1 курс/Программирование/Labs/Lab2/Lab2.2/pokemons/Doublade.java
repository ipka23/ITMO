package pokemons;

import moves.status.MetalSound;

public class Doublade extends Honedge{
    public Doublade(String name, int lvl){
        super(name, lvl);
        addMove(new MetalSound());
        setStats(59, 110, 150, 45, 49, 35);
    }

}
