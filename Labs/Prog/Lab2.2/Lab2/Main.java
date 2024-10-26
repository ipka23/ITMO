import moves.physical.BrutalSwing;
import moves.physical.FuryAttack;
import moves.physical.HeadSmash;
import moves.special.DreamEater;
import moves.status.Confide;
import moves.status.DoubleTeam;
import moves.status.MetalSound;
import pokemons.AegislashBlade;
import pokemons.Honedge;
import pokemons.Swablu;
import ru.ifmo.se.pokemon.*;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
//         if (p1.hasType(Type.STEEL)){
//             System.out.println("YES");
//         }
//         else {
//             System.out.println("NO");
//         }

        Pokemon p1 = new Honedge("pokemon", 1);
        Pokemon p2 = new Swablu("pokemon", 1);
        Battle b = new Battle();
        b.addAlly(p1);
        b.addFoe(p2);
        b.go();
//        Effect sleep = new Effect().condition(Status.SLEEP);
//        p1.addEffect(sleep);
//        Confide dt = new Confide();
//        System.out.println(p1.getStat(Stat.ACCURACY));
//        System.out.println(p2.getStat(Stat.EVASION));
//        System.out.println(p1.getStat(Stat.ACCURACY) / p1.getStat(Stat.EVASION));
//        dt.attack(p1, p2);
//        dt.attack(p1, p2);
//        dt.attack(p1, p2);
//        dt.attack(p1, p2);
    }
}