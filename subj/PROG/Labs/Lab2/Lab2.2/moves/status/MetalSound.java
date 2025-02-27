package moves.status;

import ru.ifmo.se.pokemon.*;

public class MetalSound extends StatusMove {
    private static final Type TYPE = Type.STEEL;
    private static final double ACCURACY = 85;
    public MetalSound() {
        super(TYPE, 0, ACCURACY);
    }
    private int specialDefenceModificator = 0;
    @Override
    protected void applyOppEffects(Pokemon pokemon){
        if (specialDefenceModificator < 3){
            pokemon.setMod(Stat.SPECIAL_DEFENSE, -2);
            specialDefenceModificator += 1;
        }
        else {
            pokemon.setMod(Stat.SPECIAL_DEFENSE, 0);
        }
    }


//    protected double calcSameTypeAttackBonus(Pokemon var1, Pokemon var2) {
//        double var3 = 1.0;
//        if (this.type != Type.NONE) {
//            Type[] var5 = var1.getTypes();
//            int var6 = var5.length;
//
//            for(int var7 = 0; var7 < var6; ++var7) {
//                Type var8 = var5[var7];
//                if (var8 == this.type) {
//                    var3 *= 1.5;
//                }
//            }
//        }
//
//        return var3;
//    }
    @Override
    protected String describe() {
        return "использует Metal Sound";
    }
}
