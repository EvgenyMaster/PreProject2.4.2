package crud.dao;

import crud.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserDaoHibernate implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> userList() {
        return entityManager.createQuery("select e from User e", User.class).getResultList();
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(int id) {
        try {
            User user = entityManager.find(User.class, id);
            if (user != null) {
                entityManager.remove(user);
            }
        } catch (NullPointerException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void edit(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    public User getByName(String name) {
        return entityManager.createQuery("FROM User WHERE name = :name", User.class).setParameter("name", name).getSingleResult();
    }
}