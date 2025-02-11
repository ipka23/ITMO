import models.Ask;
import models.MusicBand;
import utility.Console;
import utility.StandartConsole;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    static List<MusicBand> musicBands = new ArrayList<MusicBand>();
    public static void main(String[] args) throws Ask.AskBreak {
        Console console = new StandartConsole();
        console.selectConsoleScanner();
        musicBands.add(Ask.askMusicBand(console, 120));
        for (var v : musicBands) {
            System.out.println(v);
        }
    }
}