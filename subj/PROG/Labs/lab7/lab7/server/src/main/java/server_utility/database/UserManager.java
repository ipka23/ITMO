package server_utility.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    private Long freeId;

//    public long getFreeId() {
//        while (userHashMap.get(freeId) != null) freeId++;
//        return freeId;
//    }

//    private void addUser(User user) {
//        users.add(user);
//        userHashMap.put(getFreeId(), user);
//    }


}
