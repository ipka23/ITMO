package moves.physical;

import ru.ifmo.se.pokemon.*;


public class HeadSmash extends PhysicalMove {
    private static final Type TYPE = Type.ROCK;
    private static final double POWER = 85;
    private static final double ACCURACY = 80;

    public HeadSmash(){
        super(TYPE, POWER, ACCURACY);
    }
    @Override
    protected void applySelfDamage(Pokemon pokemon, double damage){
        Effect recoilDamage = new Effect().stat(Stat.HP, (int)  Math.round(0.5 * damage));
        pokemon.addEffect(recoilDamage);
    }
}
