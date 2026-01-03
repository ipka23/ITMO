package services;

import dto.AuthRequest;
import dto.AuthResponse;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import persistence.dao.UserDAO;
import persistence.entities.UserEntity;

@Stateless
public class AuthService {

    @EJB
    private UserDAO db;
    @PostConstruct
    public void init() {
        System.out.println("=== AuthService INIT ===");
        System.out.println("DB class: " + (db != null ? db.getClass().getName() : "NULL"));
        System.out.println("DB toString: " + db);
    }

    public AuthResponse validate(AuthRequest request) {
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return new AuthResponse(false, "Поле пароля не может быть пустым!");
        }
        if (request.getLogin() == null || request.getLogin().isEmpty()) {
            return new AuthResponse(false, "Поле имени пользователя не может быть пустым!");
        } else if (request.getLogin().length() > 30) {
            return new AuthResponse(false, "Имя пользователя не может быть длиннее 30 символов!");
        } else {
            AuthResponse response = new AuthResponse();
            response.setValid(true);
            return response;
        }
    }

    public AuthResponse logIn(UserEntity user) {
        if (db.userExist(user)) {
            if (db.passwordIsCorrect(user)) {
                UserEntity dbUser = db.findUserByLogin(user.getLogin());
                return new AuthResponse("Вход выполнен!", dbUser.getId().toString());
            } else return new AuthResponse(false, "Неверный пароль!");
        } else {
            return new AuthResponse(false, "Пользователь " + user.getLogin() + " не найден!");
        }
    }

    public AuthResponse register(UserEntity user) {
//        UserEntity dbUser = db.findUserByLogin(user.getLogin());
        if (db.userExist(user)) {
            return new AuthResponse(false, "Имя пользователя " + user.getLogin() + " уже занято!");
        } else {
            db.addUser(user);
            return new AuthResponse("Регистрация выполнена!", user.getId().toString());
        }
    }
}
