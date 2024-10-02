package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class BrutalSwing extends PhysicalMove
{
    public BrutalSwing(Type type, double pow, double acc, int priority, int hits)
    {
        super(Type.DARK, pow, acc, priority, hits);

    }
}
