package services;

import entities.UserEntity;
import exceptions.UserAlreadyExistsException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import utils.PasswordManager;

@Stateless
public class UserService {
    @EJB
    private DatabaseManager databaseManager;

    public boolean checkUser(String login, String password) {
        UserEntity userEntity = databaseManager.findUser(login);
        if (userEntity == null) {
            return false;
        }
        else return PasswordManager.checkPassword(password, userEntity.getPassword());
    }

    public boolean registerUser(String login, String password) {
        try {
            UserEntity userEntity = new UserEntity(login, password);
            databaseManager.saveUser(userEntity);
        }
        catch (UserAlreadyExistsException e) {
            return false;
        }
        return true;
    }
}
