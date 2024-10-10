package pokemons;

import moves.Blizzard;
import moves.DoubleTeam;
import moves.RockSlide;
import moves.WakeUpSlap;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Miltank extends Pokemon {
    public Miltank(String name, int level){
        super(name, level);
        setStats(90, 80, 105, 40, 70, 100);
        setType(Type.NORMAL);
        setMove(new WakeUpSlap(), new RockSlide(), new Blizzard(), new DoubleTeam());
    }
}
