package pokemons;


import moves.physical.HeadSmash;


public class AegislashBlade extends Doublade {
    private final int HP = 60;

    public AegislashBlade(String name, int lvl){
        super(name, lvl);
        addMove(new HeadSmash());
        setStats(HP, 50, 140, 50, 140, 60);
    }

}
