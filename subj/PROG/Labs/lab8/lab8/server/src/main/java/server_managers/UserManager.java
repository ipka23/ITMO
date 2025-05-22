package server_managers;


import common_utility.localization.LanguageManager;
import common_utility.network.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common_utility.database.User;

import java.sql.*;
import java.util.*;

@AllArgsConstructor
@Getter
@Setter
public class UserManager {
    private List<User> users = new ArrayList<>();
    private Map<Long, User> userHashMap = new HashMap<>();
    private long freeId;
    private DatabaseManager dbManager;
    private Logger log = LoggerFactory.getLogger("UserManager");
    private ResourceBundle r;
    private CollectionManager collectionManager;

    public UserManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public UserManager(DatabaseManager dbManager, CollectionManager collectionManager) {
        this.dbManager = dbManager;
        this.collectionManager = collectionManager;
    }

    public void addUser(User user) {
        try (PreparedStatement ps = getDbManager().getConnection().prepareStatement("SELECT id FROM users WHERE username = ?")) {
            ps.setString(1, user.getUsername());
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            rs.next();
            long id = rs.getLong(1);
            user.setId(id);
            users.add(user);
            userHashMap.put(id, user);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Response registerUser(User user) {
        try {
            dbManager.registerUser(user);
            addUser(user);
            dbManager.setUser(user);
            return new Response(true, "Регистрация прошла успешно!");
        } catch (SQLException e) {
            return new Response(false, "Ошибка регистрации: " + e.getMessage());
        }
    }

    public Response logInUser(User user) {
        r = ResourceBundle.getBundle("strings", user.getLocale());
        try {
            if (dbManager.checkUserExists(user.getUsername())) {
                dbManager.setUser(user);
                if (dbManager.validatePassword(user.getUsername(), user.getPassword())) {
                    return new Response(true, r.getString("successLogin"), collectionManager.getCollection());
                } else {
                    return new Response(false, "Неверный пароль!");
                }
            } else {
                return new Response(false, "Такого пользователя нет в базе данных!");
            }

        } catch (SQLException e) {
            return new Response(false, "Ошибка входа: " + e.getMessage());
        }
    }

}
