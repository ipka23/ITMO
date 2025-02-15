import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Album;
import models.Coordinates;
import models.MusicBand;
import models.MusicGenre;
import utility.LocalDateAdapter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToJson {
    public static void main(String[] args) {
        List<MusicBand> bands = new ArrayList<MusicBand>();
        bands.add(new MusicBand(1L,"WSTR", new Coordinates(23, 32), 3L, 4L,MusicGenre.POP, new Album("SKRWRD", 6L, 5L, (double) 2))); //
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(bands);
        try (FileWriter fw = new FileWriter("src/test/resources/MusicBands.json")) {
            fw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
