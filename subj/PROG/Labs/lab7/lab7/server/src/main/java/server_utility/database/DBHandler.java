package server_utility.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler {
//    public static final String ADD_USER_REQUEST = "INSERT INTO USERS(username, password) VALUES(?, ?)";
    private String URL = "jdbc:postgresql://pg:5432/studs";
    private String USER = "s467204";
    private String PASSWORD = "8S268WDKoQNNirxx";
    private  Connection connection;
    private Logger log = LoggerFactory.getLogger("DBHandler");


//    public DBHandler(String URL, String USER, String PASSWORD) {
//        this.URL = URL;
//        this.USER = USER;
//        this.PASSWORD = PASSWORD;
//    }

    public void connectToDB(String URL, String USER, String PASSWORD) throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        if (!connection.isClosed()){
            log.info("Соединение с БД установлено!");
        }
        connection.close();
        log.info("Соединение с БД закрыто!");
    }
}
