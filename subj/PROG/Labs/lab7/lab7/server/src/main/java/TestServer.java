import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_managers.DatabaseManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class TestServer {
//    Scanner scanner;
    private StringBuilder credentials = new StringBuilder();
    private String username = null;
    private String password = null;
    private String url = "jdbc:postgresql://localhost:15432/studs";
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
                log.info("Credentials have been received!");
                DatabaseManager dbManager = new DatabaseManager(url, username, password);
                dbManager.connectToDB();
            }
        } catch (FileNotFoundException e) {
            log.error("Database credentials file not found!");
            System.exit(1);
        } catch (NullPointerException e) {
            log.error("Incorrect database credentials!");
            System.exit(1);
        }
    }
}
