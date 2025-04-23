import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_utility.database.DBHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Scanner;

public class TestServer {
//    Scanner scanner;
    private StringBuilder credentials = new StringBuilder();
    private String username;
    private String password;
    private String URL = "jdbc:postgresql://pg:5432/studs";
    DBHandler dbHandler = new DBHandler();
    private Logger log = LoggerFactory.getLogger("TestServer");

    public static void main(String[] args) {
        TestServer server = new TestServer();

        server.connect();
    }
    public void connect() {
        try (Scanner scanner = new Scanner(new FileReader("credentials.txt"))) {
            String line = scanner.nextLine().trim();
            while (scanner.hasNextLine()){
                if (!line.isEmpty()) {
                    username = line;
                    line = scanner.nextLine().trim();
                }
                if (!line.isEmpty()) {
                    password = line;
                }
                log.info("Данные для входа получены!");
                dbHandler.connectToDB(URL, username, password);
            }
        } catch (FileNotFoundException e) {
            log.error("Файл с данными для входа на Helios не найден!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
