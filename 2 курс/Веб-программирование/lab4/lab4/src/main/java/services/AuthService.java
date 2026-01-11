package services;

import dto.AuthRequest;
import dto.AuthResponse;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import persistence.dao.UserDAO;
import persistence.entities.UserEntity;

import java.util.HashMap;

@Stateless
public class AuthService {

    @EJB
    private UserDAO db;

    @EJB
    private JwtService jwtService;

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

    public HashMap<String, String> logIn(UserEntity user) {
        HashMap<String, String> loginParams = new HashMap<>();

        if (db.userExist(user)) {
            if (db.passwordIsCorrect(user)) {
                UserEntity dbUser = db.findUserByLogin(user.getLogin());

                String jwt = jwtService.generateToken(user.getLogin());

                loginParams.put("isValid", "true");
                loginParams.put("message", "Вход выполнен!");
                loginParams.put("jwt", jwt);
                loginParams.put("userId", dbUser.getId().toString());
            } else {
                loginParams.put("isValid", "false");
                loginParams.put("message", "Неверный пароль!");
            }
        } else {
            loginParams.put("isValid", "false");
            loginParams.put("message", "Пользователь " + user.getLogin() + " не найден!");
        }

        return loginParams;
    }

    public HashMap<String, String> register(UserEntity user) {
        HashMap<String, String> registerParams = new HashMap<>();
        if (db.userExist(user)) {
            registerParams.put("isValid", "false");
            registerParams.put("message", "Имя пользователя " + user.getLogin() + " уже занято!");
        } else {
            db.addUser(user);
            UserEntity savedUser = db.findUserByLogin(user.getLogin());
            
            String jwt = jwtService.generateToken(user.getLogin());
            registerParams.put("isValid", "true");
            registerParams.put("message", "Регистрация выполнена!");
            registerParams.put("jwt", jwt);
            registerParams.put("userId", savedUser.getId().toString());
        }
        return registerParams;
    }
}
