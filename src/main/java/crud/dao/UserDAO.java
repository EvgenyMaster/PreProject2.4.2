package crud.dao;

import crud.model.User;

import java.util.List;

public interface UserDAO {
    List<User> userList();
    void add(User user);
    void delete(int id);
    void edit(User user);
    User getById(int id);
    User getByName(String name);
}