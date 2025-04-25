package server_utility.database;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private long id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString(){
        return "User{id: " + id + ", username: " + username + ", password: " + password + "}";
    }
}
