package pokemons;

import moves.special.DragonBreath;
import ru.ifmo.se.pokemon.Type;

public class Altaria extends Swablu{
    public Altaria(String name, int lvl){
        super(name, lvl);
        addType(Type.DRAGON);
        addMove(new DragonBreath());
        setStats(75, 70, 90, 70, 105, 80);
    }
}
