import entities.Album;
import entities.Coordinates;
import entities.MusicBand;
import entities.MusicGenre;

import java.util.ArrayList;

public class MusicBandUtil {
    public static ArrayList<MusicBand> createMusicBands() {
        ArrayList<MusicBand> bands = new ArrayList<MusicBand>();

//        MusicBand band1 = null;
//        band1.setId(1);
//        band1.setName("band1");
//        band1.setCoordinates(new Coordinates(1, 1));
//        band1.setNumberOfParticipants(1L);
//        band1.setSinglesCount(1L);
////        band1.setEstablishmentDate(new Date());
//        band1.setMusicGenre(MusicGenre.HIP_HOP);
//        band1.setBestAlbum(new Album("bestalbum1", 1L, 1,1D));
//
//        bands.add(band1);
//
//        MusicBand band2 = null;
//        band2.setId(2);
//        band2.setName("band2");
//        band2.setCoordinates(new Coordinates(2, 2));
//        band2.setNumberOfParticipants(2L);
//        band2.setSinglesCount(2L);
////        band2.setEstablishmentDate(new Date());
//        band2.setMusicGenre(MusicGenre.HIP_HOP);
//        band2.setBestAlbum(new Album("bestalbum2", 2L, 2,2D));
//
//        bands.add(band2);

        MusicBand band1 = new MusicBand(1, "band1", new Coordinates(1, 1), 1L, 1L, MusicGenre.HIP_HOP, new Album("bestalbum1", 1L, 1, 1D));
        band1.setId(1);

        bands.add(band1);

        MusicBand band2 = new MusicBand(2,"band2", new Coordinates(2, 2), 2L, 2L, MusicGenre.HIP_HOP, new Album("bestalbum2", 2L, 2, 2D));
        band2.setId(2);

        bands.add(band2);
        return bands;
    }
}
