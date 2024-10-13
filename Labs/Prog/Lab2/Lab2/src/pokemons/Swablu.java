package pokemons;

import moves.status.Confide;
import moves.special.DreamEater;
import moves.physical.FuryAttack;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Swablu extends Pokemon {
    public Swablu(String name, int level) {
        super(name, level);
        setType(Type.NORMAL, Type.FLYING);
        setStats(45, 40, 60, 40, 75, 50);
        setMove(new Confide(), new FuryAttack(), new DreamEater());
    }
}
