package pokemons;
import moves.MetalSound;


public class Doublade extends Honedge {
    public Doublade(String name, int level){
        super(name, level);
        setStats(59, 110, 150, 45, 49, 35);
        setMove(new MetalSound());
    }
}
