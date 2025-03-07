import utility.exceptions.AddBreak;
import entities.MusicBand;
import utility.interfaces.Console;
import utility.consoles.StandartConsole;

import java.util.ArrayList;
import java.util.Collection;

public class TestMain {
    public static void main(String[] args) throws AddBreak {
        ArrayList<MusicBand> musicBands;
        Console console = new StandartConsole(null, null);

        musicBands = MusicBandUtil.createMusicBands();

//        for (var e : musicBands) {
//            console.println(e);
//        }

        TestFileManager fileManager = new TestFileManager("C:\\Users\\ilyai\\OneDrive\\Рабочий стол\\ITMO\\subj\\PROG\\Labs\\Lab5.0\\newLab5\\src\\test\\resources\\testBands2.json", console);

//        fileManager.writeCollectionToFile(new HashSet<>(musicBands));

        Collection<MusicBand> bands = fileManager.readCollectionFromFile();
        console.println("Количество загруженных групп: " + bands.size());
        for (var band : bands) {
            console.println(band);
        }

    }
}