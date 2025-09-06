package pokemons;

import moves.physical.FuryAttack;
import moves.special.DreamEater;
import moves.status.Confide;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Swablu extends Pokemon {
    public Swablu(String name, int lvl){
        super(name, lvl);
        setType(Type.NORMAL, Type.FLYING);
        int i;
        double randomValue = Math.random();
        if (randomValue < 3 / 8d) {
            i = 2; // 0% - 37.5%
        } else if (randomValue < 2 * 3 / 8d) {
            i = 3; // 37.5% - 75%
        } else if (randomValue < 1 / 8d + 0.75) {
            i = 4; // 75% - 87.5%
        } else {
            i = 5; // 87.5% - 100%
        }
        setMove(new Confide(), new DreamEater(), new FuryAttack(i));
    }
}
