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
            System.out.println("User ");
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
        try {
            if (dbManager.validatePassword(user.getUsername(), user.getPassword())) {
                dbManager.setUser(user);
                return new Response(true, "Вход выполнен!");
            } else {
                return new Response(false, "Неверный пароль!");
            }
        } catch (RuntimeException e) {
            return new Response(false, e.getMessage());
        }
    }



}
