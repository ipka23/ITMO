package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class WakeUpSlap extends PhysicalMove {
    public WakeUpSlap() {
        super(Type.FIGHTING, 70, 100);  // type, power, accuracy
    }

    @Override
    protected void applyOppEffects(Pokemon p) {

    }
}
