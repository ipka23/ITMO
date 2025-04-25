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

--todo добавить проверки CHECK в MusicBands и заменить genre на еnum