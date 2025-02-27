import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        StandartConsole console = new StandartConsole();
        Runner runner = new Runner(console);
        runner.run();
    }
}