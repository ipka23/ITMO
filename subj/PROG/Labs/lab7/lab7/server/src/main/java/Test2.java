import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Test2 {
    public static void main(String[] args) {
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Не удалось подключить драйвер PostgreSQL!");
            System.exit(1);
        }
    }
}
