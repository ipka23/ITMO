package moves;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {
    public Confide(){
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon){
        for (int i = -1; i > -6; i--) {
            pokemon.setMod(Stat.SPECIAL_ATTACK, i);
        }
    }
    @Override
    public String describe(){
        return "использует Confide";
    }
}
