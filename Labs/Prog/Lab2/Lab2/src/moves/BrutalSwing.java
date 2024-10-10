package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;


public class BrutalSwing extends PhysicalMove {
    public BrutalSwing() {
        super(Type.DARK, 60, 100); // type, power, accuracy
    }
    @Override
    public String describe() {
        return "использует Brutal Swing";
    }
}
