package crud.service;

import crud.model.User;

import java.util.List;

public interface UserService {

    void insertUser(User user);

    void deleteUser(int id);

    User getUserById(int id);

    void updateUser(User user);

    List<User> listUsers();
}
