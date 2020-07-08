package jm.security.example.dao;

import jm.security.example.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUser() {
        return em.createQuery("from User").getResultList();
    }

    @Override
    public boolean validateUser(String name, String password) {
        String hql = "from User where name = :paramName AND password = :paramPassword";
        return em.createQuery(hql, User.class)
                .setParameter("paramName", name).setParameter("paramPassword", password)
                .getSingleResult() != null;
    }

    @Override
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByName(String name) {
        String hql = "from User where name = :paramName";
        return em.createQuery(hql, User.class)
                .setParameter("paramName", name)
                .getSingleResult();
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public void deleteUser(User user) {
        em.remove(user);
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }
}
