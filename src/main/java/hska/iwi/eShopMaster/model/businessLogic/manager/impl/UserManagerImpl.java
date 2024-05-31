package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.google.gson.Gson;
import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Role;
import hska.iwi.eShopMaster.model.database.dataobjects.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author knad0001
 */

@RequiredArgsConstructor
public class UserManagerImpl implements UserManager {
    private final HttpDao httpDao = new HttpDao("http://user.default.svc.cluster.local:8084");

    public void registerUser(String username, String name, String lastname, String password, Role role) {
        User user = new User(username, name, lastname, password, role);

        try {
            httpDao.post("/user/register", new Gson().toJson(user), User.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public User getUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }
        try {
            return httpDao.post("/user/findByUsername", new Gson().toJson(new Username(username)), User.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteUserById(int id) {
        try {
            httpDao.delete("/user/" + id, Object.class);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Role getRoleByLevel(int level) {
        try {
            return httpDao.get("/role/" + level, User.class).getRole();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doesUserAlreadyExist(String username) {

        User dbUser = this.getUserByUsername(username);

        if (dbUser != null) {
            return true;
        } else {
            return false;
        }
    }

    @Data
    public class Username {
        private final String username;
    }


    public boolean validate(User user) {
        if (user.getFirstname().isEmpty() || user.getPassword().isEmpty() || user.getRole() == null || user.getLastname() == null || user.getUsername() == null) {
            return false;
        }

        return true;
    }

}
