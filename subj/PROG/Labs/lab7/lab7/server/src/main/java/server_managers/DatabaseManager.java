package server_managers;

import common_entities.Album;
import common_entities.Coordinates;
import common_entities.MusicBand;
import common_entities.MusicGenre;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_utility.database.StatementValue;
import server_utility.database.UserManager;
import server_utility.interfaces.DataBaseWorkable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseManager implements DataBaseWorkable {
    private String url;
    private String username;
    private String password;
    private Logger log = LoggerFactory.getLogger("DatabaseManager");
    @Getter
    private Connection connection;
    @Setter
    private CollectionManager collectionManager;
    private UserManager userManager;
    private String salt = "&^(*hed@#E";
    private String pepper = "2(^&*l_d(3!#";
    // пул потоков для обработки клиентов
    private ExecutorService clientThreadPool = Executors.newCachedThreadPool();

    public DatabaseManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        try {
            connection = DriverManager.getConnection(url, username, password);
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
                registerUser(username, password);
            }
        } catch (SQLException e) {
            log.error("Database connection failed!");
        }
//        finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                log.error("Database connection failed!");
//            }
//            log.info("Database connection is closed!");
//        }
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
    public HashSet<MusicBand> loadCollectionFromDB() {
        HashSet<MusicBand> collection = new HashSet<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(StatementValue.SELECT_MUSIC_BAND.toString());
            while (rs.next()) {
                MusicBand musicBand = getMusicBandFromResultSet(rs);
                collection.add(musicBand);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return collection;
    }

    public MusicBand getMusicBandFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        String name = rs.getString(2);
        Integer x = rs.getInt(3);
        float y = rs.getInt(4);
        LocalDate creationDate = rs.getDate(5).toLocalDate();
        Long numberOfParticipants = rs.getLong(6);
        Long singlesCount = rs.getLong(7);
        LocalDate establishmentDate = rs.getDate(8).toLocalDate();
        MusicGenre genre = MusicGenre.valueOf(rs.getString(9));
        String album_name = rs.getString(10);
        Long tracks = rs.getLong(11);
        long length = rs.getLong(12);
        Double sales = rs.getDouble(13);
        return new MusicBand(id, name, new Coordinates(x, y), creationDate, numberOfParticipants, singlesCount, establishmentDate, genre, new Album(album_name, tracks, length, sales));
    }

    @Override
    public void insertIntoDB(MusicBand band) {
        try (PreparedStatement ps = connection.prepareStatement(StatementValue.ADD_MUSIC_BAND.toString())) {
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
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

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
    public void registerUser(String username, String password) {
        try (PreparedStatement ps = connection.prepareStatement(StatementValue.ADD_USER.toString());) {
            ps.setString(1, username);
            ps.setBytes(2, encryptPassword(password, salt));
            ps.setString(3, salt);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Username already exists!");
        }
//        catch (UnsupportedEncodingException e) {
//            log.error("Unsupported encoding!");
//        }
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


    public void saveCollectionToDB() {
        try {
            Set<Long> ids = new HashSet<>();
            Statement statement = connection.createStatement();
            statement.execute(StatementValue.SELECT_ID.toString());
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                ids.add(rs.getLong("id"));
            }

            try (PreparedStatement ps = connection.prepareStatement(StatementValue.ADD_MUSIC_BAND.toString())) {
                for (MusicBand band : collectionManager.getCollection()) {
                    if (ids.contains(band.getId())) continue;
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
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }
}
