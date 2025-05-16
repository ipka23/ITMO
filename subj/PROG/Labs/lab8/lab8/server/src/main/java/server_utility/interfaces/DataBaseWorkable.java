package server_utility.interfaces;

import common_entities.MusicBand;
import common_utility.database.User;

import java.sql.SQLException;
import java.util.HashSet;

public interface DataBaseWorkable {
    void connectToDB();
    void loadCollectionFromDB();
    void insertIntoDB(MusicBand band) throws SQLException;
    void updateDB(MusicBand band, long id);
    void registerUser(User user)throws SQLException;
    void declareTables();
}
