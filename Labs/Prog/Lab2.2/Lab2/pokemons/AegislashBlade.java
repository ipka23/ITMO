package pokemons;


import ru.ifmo.se.pokemon.Move;
import ru.ifmo.se.pokemon.Pokemon;

public class AegislashBlade extends Pokemon {
    public AegislashBlade(String name, int lvl){
        super(name, lvl);
        setStats(60, 140, 50, 140, 50, 60);
        setMove();
    }

}
