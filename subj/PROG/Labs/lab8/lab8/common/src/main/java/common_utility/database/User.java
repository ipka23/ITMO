package common_utility.database;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

@Getter
@Setter
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -5735083355079784670L;
    private long id;
    private String username;
    private String password;
    private Locale locale;
    public User(String username, String password, Locale locale) {
        this.username = username;
        this.password = password;
        this.locale = locale;
    }

    @Override
    public String toString(){
        return "User{id: " + id + ", username: " + username + ", password: " + password + "}";
    }
}
