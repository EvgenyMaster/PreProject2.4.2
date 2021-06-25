package crud.service;


import crud.dao.UserDAO;
import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ServiceDB implements UserDetailsService {
    private UserDAO userDAO;

    @Autowired
    public ServiceDB(UserDAO usersDAO) {
        this.userDAO = usersDAO;
    }

    @Transactional
    public void insertUser(User user) {
        userDAO.add(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userDAO.delete(id);
    }

    @Transactional
    public User getUserById(int id) {
        return userDAO.getById(id);
    }

    @Transactional
    public void updateUser(User user) {
        userDAO.edit(user);
    }

    @Transactional
    public List<User> listUsers() {
        return userDAO.userList();
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userDAO.getByName(name);
    }
}