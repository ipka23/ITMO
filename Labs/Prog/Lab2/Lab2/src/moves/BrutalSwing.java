package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Pokemon;

public class BrutalSwing extends PhysicalMove
{
    public BrutalSwing()
    {
        super(Type.DARK, 60, 100);
    }
    @Override
    protected void applySelfEffects(Pokemon p)
    {
        p.setMod(Stat.SPECIAL_ATTACK, 1);
    }

    @Override
    protected String describe()
    {
    return "использует Brutal Swing";
    }
}
