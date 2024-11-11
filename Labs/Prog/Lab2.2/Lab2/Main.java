import pokemons.*;
import ru.ifmo.se.pokemon.*;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        Pokemon p1 = new Honedge("Спичка", 1);
        Pokemon p2 = new Doublade("Нож", 1);
        Pokemon p3 = new AegislashBlade("Меч", 1);
        Pokemon p4 = new Swablu("Говорун", 1);
        Pokemon p5 = new Altaria("Летун", 1);
        Pokemon p6 = new Miltank("Молоко", 1);
        Battle b = new Battle();
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
//         if (p1.hasType(Type.STEEL)){
//             System.out.println("YES");
//         }
//         else {
//             System.out.println("NO");
//         }

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