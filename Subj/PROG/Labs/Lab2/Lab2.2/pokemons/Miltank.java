package pokemons;

import moves.physical.RockSlide;
import moves.physical.WakeUpSlap;
import moves.special.Blizzard;
import moves.status.DoubleTeam;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Miltank extends Pokemon {
    public Miltank(String name, int lvl){
        super(name, lvl);
        setType(Type.NORMAL);
        setMove(new WakeUpSlap(), new RockSlide(), new Blizzard(), new DoubleTeam());
        setStats(95, 80, 105, 40, 70, 100);
    }
}
