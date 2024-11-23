package moves.physical;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class BrutalSwing extends PhysicalMove{
    private static final Type TYPE = Type.DARK;
    private static final double POWER = 60;
    private static final double ACCURACY = 100;

    public BrutalSwing(){
         super(TYPE, POWER, ACCURACY);
    }
    @Override
    protected String describe(){
        return "использует Brutal Swing";
    }
}
