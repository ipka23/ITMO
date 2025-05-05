package server_utility.database;

public enum StatementValue {
    DECLARE_TABLES("""
                    CREATE SEQUENCE IF NOT EXISTS user_id_seq
                        START WITH 1
                        INCREMENT BY 1;
            
                    CREATE SEQUENCE IF NOT EXISTS musicBand_id_seq
                        START WITH 1
                        INCREMENT BY 1;
            
            
                    CREATE TABLE IF NOT EXISTS Users(
                        id BIGINT PRIMARY KEY DEFAULT nextval('user_id_seq'),
                        username VARCHAR(40) UNIQUE,
                        password BYTEA,
                        salt TEXT
                                                    );
            
                    CREATE TABLE IF NOT EXISTS MusicBands(
                        id BIGINT PRIMARY KEY DEFAULT nextval('musicBand_id_seq'),
                        owner varchar(40),
                        name VARCHAR(40) UNIQUE,
                        coordinates_x INT,
                        coordinates_y REAL,
                        creationDate DATE,
                        numberOfParticipants BIGINT,
                        singlesCount BIGINT,
                        establishmentDate DATE,
                        genre TEXT,
                        album_name TEXT,
                        album_tracks BIGINT,
                        album_length BIGINT,
                        album_sales DOUBLE PRECISION
                                                         );
            """),
    UPDATE_MUSIC_BAND("""
                    UPDATE musicBands SET
                    name = ?,
                    coordinates_x = ?,
                    coordinates_y = ?,
                    creationDate = ?,
                    numberOfParticipants = ?,
                    singlesCount = ?,
                    establishmentDate = ?,
                    genre = ?,
                    album_name = ?,
                    album_tracks = ?,
                    album_length = ?,
                    album_sales = ?
                    WHERE id = ?
                    """),
    ADD_USER("INSERT INTO users(username, password, salt) VALUES(?, ?, ?)"),
    SELECT_PASSWORD_AND_SALT("SELECT password, salt FROM users WHERE username = ?"),
    SELECT_MUSIC_BAND("SELECT * FROM musicBands"),
    ADD_MUSIC_BAND("INSERT INTO musicBands(owner, name, coordinates_x, coordinates_y, creationdate, numberofparticipants, singlescount, establishmentdate, genre, album_name, album_tracks, album_length, album_sales) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
    REMOVE_MUSIC_BAND_BY_ID("DELETE FROM musicBands WHERE id = ?"),
    SELECT_ID("SELECT id FROM musicBands"),
    GET_MAX_ID("SELECT COALESCE(MAX(id), 0) FROM musicbands"),
    TRUNCATE_MUSIC_BANDS("TRUNCATE TABLE musicbands"),
    SYNC_SEQUENCE_ID("SELECT setval('musicBand_id_seq', ?)"),
    RESTART_ID_SEQ("ALTER SEQUENCE musicBand_id_seq RESTART WITH 1"),
    GET_OWNER("SELECT owner FROM musicBands WHERE id = ?"),
    SELECT_USER("SELECT * FROM users WHERE username = ?")
    ;

    private String statement;

    StatementValue(String statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return statement;
    }
}