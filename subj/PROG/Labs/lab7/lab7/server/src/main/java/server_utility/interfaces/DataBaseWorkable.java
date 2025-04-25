package server_utility.interfaces;

import common_entities.MusicBand;

import java.util.HashSet;

public interface DataBaseWorkable {
    void connectToDB();
    HashSet<MusicBand> loadCollectionFromDB();
    void insertIntoDB(MusicBand band);
    void updateDB(MusicBand band, long id);
    void registerUser(String username, String password);
    void declareTables();
}
