package pokemons;


import moves.physical.HeadSmash;
import ru.ifmo.se.pokemon.Move;
import ru.ifmo.se.pokemon.Pokemon;

public class AegislashBlade extends Doublade {
    public AegislashBlade(String name, int lvl){
        super(name, lvl);
        addMove(new HeadSmash());
        setStats(60, 50, 140, 50, 140, 60);
    }

}
