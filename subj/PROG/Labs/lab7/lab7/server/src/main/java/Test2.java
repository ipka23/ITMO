import java.sql.*;


public class Test2 {
    public static void main(String[] args) {
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:15432/studs", "s467204", "8S268WDKoQNNirxx");
            Statement statement = connection.createStatement();
            statement.execute("SELECT * FROM musicBands");
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                if (rs.getString("name").equals("U2")){
                    System.out.println(rs.getString("name"));
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Не удалось подключить драйвер PostgreSQL!");
            System.exit(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
