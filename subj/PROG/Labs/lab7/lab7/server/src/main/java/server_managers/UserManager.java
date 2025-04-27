package server_managers;

import common_utility.network.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common_utility.database.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class UserManager {
    private List<User> users = new ArrayList<>();
    private Map<Long, User> userHashMap = new HashMap<>();
    private long freeId;
    private DatabaseManager dbManager;
    private Logger log = LoggerFactory.getLogger("UserManager");

    public UserManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void addUser(User user) {
        long id = getFreeId();
        user.setId(id);
        users.add(user);
        userHashMap.put(id, user);
    }

    public boolean registerUser(User user) {
        if (dbManager.registerUser(user)) {
            addUser(user);
            dbManager.setUser(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean logInUser(User user) throws SQLException {
        if (dbManager.validatePassword(user.getUsername(), user.getPassword())) {
            dbManager.setUser(user);
            return true;
        }
        return false;
    }


    public long getFreeId() {
        Connection connection =  dbManager.getConnection();
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("SELECT COALESCE(MAX(id), 0) FROM users");
            ResultSet rs = stmt.getResultSet();
            rs.next();
            freeId = rs.getLong(1) + 1;

            if (freeId != 1){
                PreparedStatement ps = connection.prepareStatement("SELECT setval(user_id_seq, ?)");
                ps.setLong(1, freeId - 1);
                ps.executeUpdate();
            } else {
                connection.createStatement().execute("ALTER SEQUENCE user_id_seq RESTART WITH 1");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return freeId;
    }
}
