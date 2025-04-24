package server_utility.database;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;

    @Override
    public String toString(){
        return "User{id: " + id + ", username: " + username + ", password: " + password + "}";
    }
}
