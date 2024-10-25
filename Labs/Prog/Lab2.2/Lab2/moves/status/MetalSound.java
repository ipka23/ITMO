package moves.status;

import ru.ifmo.se.pokemon.*;

public class MetalSound extends StatusMove {
    private static final Type TYPE = Type.STEEL;
    private static final double ACCURACY = 0.85;
    public MetalSound() {
        super(TYPE, 0, ACCURACY);
    }
    private int specialDefenceModificator = 1;
    @Override
    protected void applyOppEffects(Pokemon pokemon){
        if (specialDefenceModificator <= 3){
            Effect lowerSpecialDefence = new Effect().stat(Stat.SPECIAL_DEFENSE, -2);
            specialDefenceModificator += 1;
//            System.out.println(specialDefenceModificator);
        }
        else {
            Effect lowerSpecialDefence = new Effect().chance(0).stat(Stat.SPECIAL_DEFENSE, -2);
        }
    }

    @Override
    protected double calcSameTypeAttackBonus(Pokemon att, Pokemon def){
        Effect attackBonus = new Effect().stat(Stat.ATTACK, 1);
        att.addEffect(attackBonus);
    }
    @Override
    public String describe() {
        return "использует Metal Sound";
    }
}
