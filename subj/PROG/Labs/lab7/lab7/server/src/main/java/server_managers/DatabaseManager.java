package server_managers;

import common_entities.Album;
import common_entities.Coordinates;
import common_entities.MusicBand;
import common_entities.MusicGenre;
import common_utility.network.Response;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_utility.database.StatementValue;
import common_utility.database.User;
import server_utility.interfaces.DataBaseWorkable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class DatabaseManager implements DataBaseWorkable {
    private String URL;
    @Getter
    private final String DB_OWNER_USERNAME;
    private final String PASSWORD;
    private Logger log = LoggerFactory.getLogger("DatabaseManager");
    @Getter
    private Connection connection;
    @Setter
    private CollectionManager collectionManager;
    private UserManager userManager;
    @Getter
    @Setter
    private User user;
    private String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private SecureRandom secureRandom = new SecureRandom();
    private String pepper = "mCf-zNiWJPyv";


    public DatabaseManager(String URL, String DB_OWNER_USERNAME, String PASSWORD) {
        this.URL = URL;
        this.DB_OWNER_USERNAME = DB_OWNER_USERNAME;
        this.PASSWORD = PASSWORD;
        try {
            connection = DriverManager.getConnection(URL, DB_OWNER_USERNAME, PASSWORD);
        } catch (SQLException e) {
            log.error("Database connection failed!");
        }
    }

    @Override
    public void connectToDB() {
        try {
            if (!connection.isClosed()) {
                log.info("Connection to the database is established!");
                declareTables();
            }
        } catch (SQLException e) {
            log.error("Database connection failed!");
        }
    }

    @Override
    public void declareTables() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(StatementValue.DECLARE_TABLES.toString());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public void loadCollectionFromDB() {
        HashSet<MusicBand> collection = new HashSet<>();
        HashMap<Long, MusicBand> musicBandHashMap = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(StatementValue.SELECT_MUSIC_BAND.toString());
            while (rs.next()) {
                MusicBand musicBand = getMusicBandFromResultSet(rs);
                collection.add(musicBand);
                musicBandHashMap.put(musicBand.getId(), musicBand);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        collectionManager.setCollection(collection);
        collectionManager.setMusicBandsMap(musicBandHashMap);
    }

    public boolean checkUserExists(String username) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement(StatementValue.SELECT_USER.toString());
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public MusicBand getMusicBandFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        String owner = rs.getString(2);
        String name = rs.getString(3);
        Integer x = rs.getInt(4);
        float y = rs.getInt(5);
        LocalDate creationDate = rs.getDate(6).toLocalDate();
        Long numberOfParticipants = rs.getLong(7);
        Long singlesCount = rs.getLong(8);
        LocalDate establishmentDate = rs.getDate(9).toLocalDate();
        MusicGenre genre = MusicGenre.valueOf(rs.getString(10));
        String album_name = rs.getString(11);
        Long tracks = rs.getLong(12);
        long length = rs.getLong(13);
        Double sales = rs.getDouble(14);
        return new MusicBand(id, owner, name, new Coordinates(x, y), creationDate, numberOfParticipants, singlesCount, establishmentDate, genre, new Album(album_name, tracks, length, sales));
    }

    @Override
    public void insertIntoDB(MusicBand band) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(StatementValue.ADD_MUSIC_BAND.toString());
        ps.setString(1, band.getOwner());
        ps.setString(2, band.getName());
        ps.setInt(3, band.getCoordinates().getX());
        ps.setFloat(4, band.getCoordinates().getY());
        ps.setDate(5, java.sql.Date.valueOf(LocalDate.now()));  // err
        ps.setLong(6, band.getNumberOfParticipants());
        ps.setLong(7, band.getSinglesCount());
        ps.setDate(8, java.sql.Date.valueOf(band.getEstablishmentDate()));
        ps.setString(9, band.getGenre().toString());
        ps.setString(10, band.getBestAlbum().getName());
        ps.setLong(11, band.getBestAlbum().getTracks());
        ps.setLong(12, band.getBestAlbum().getLength());
        ps.setDouble(13, band.getBestAlbum().getSales());
        ps.executeUpdate();

        ps.close();
    }


    @Override
    public void updateDB(MusicBand band, long id) {
        try {
            PreparedStatement ps = connection.prepareStatement(StatementValue.UPDATE_MUSIC_BAND.toString());
            ps.setString(1, band.getName());
            ps.setInt(2, band.getCoordinates().getX());
            ps.setFloat(3, band.getCoordinates().getY());
            ps.setDate(4, java.sql.Date.valueOf(band.getCreationDate()));
            ps.setLong(5, band.getNumberOfParticipants());
            ps.setLong(6, band.getSinglesCount());
            ps.setDate(7, java.sql.Date.valueOf(band.getEstablishmentDate()));
            ps.setString(8, band.getGenre().toString());
            ps.setString(9, band.getBestAlbum().getName());
            ps.setLong(10, band.getBestAlbum().getTracks());
            ps.setLong(11, band.getBestAlbum().getLength());
            ps.setDouble(12, band.getBestAlbum().getSales());
            ps.setLong(13, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void registerUser(User user) throws SQLException {
        String salt = secureRandom
                .ints(12, 0, chrs.length())
                .mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        PreparedStatement ps = connection.prepareStatement(StatementValue.ADD_USER.toString());
        ps.setString(1, user.getUsername());
        ps.setBytes(2, encryptPassword(user.getPassword(), salt));
        ps.setString(3, salt);
        ps.executeUpdate();
        ps.close();
    }

    public byte[] encryptPassword(String password, String salt) {
        byte[] hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            hash = md.digest((password + pepper + salt).getBytes(StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException e) {
            log.error("No such algorithm!");
        }
        return hash;
    }


    public boolean validatePassword(String username, String inputPassword) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(StatementValue.SELECT_PASSWORD_AND_SALT.toString());
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            byte[] hashedPassword = rs.getBytes("password");
            String salt = rs.getString("salt");
            byte[] inputHash = encryptPassword(inputPassword, salt);
            return Arrays.equals(inputHash, hashedPassword);
        }
        return false;
    }

}
