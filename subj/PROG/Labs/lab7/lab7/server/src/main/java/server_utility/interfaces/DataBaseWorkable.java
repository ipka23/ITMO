package server_utility.interfaces;

import common_entities.MusicBand;
import common_utility.database.User;

import java.util.HashSet;

public interface DataBaseWorkable {
    void connectToDB();
    HashSet<MusicBand> loadCollectionFromDB();
    void insertIntoDB(MusicBand band);
    void updateDB(MusicBand band, long id);
    boolean registerUser(User user);
    void declareTables();
}
