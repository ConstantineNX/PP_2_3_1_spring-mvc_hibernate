package service;

import dao.UserDao;
import model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;


@Service
public class UserDaoServiceImp implements UserDaoService {

    public UserDaoServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    private final UserDao userDao;

    @Transactional
    @Override
    public User saveUser(User user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getFirstName());
        Objects.requireNonNull(user.getEmail());
        if (user.getFirstName().trim().isEmpty() || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("The user must have at least a name and an email address");
        } else if (user.getFirstName().length() < 3 || user.getFirstName().length() > 30) {
            throw new IllegalArgumentException("The first name must be between 3 and 30 characters");
        }

        return userDao.saveUser(user);
    }

    @Transactional
    @Override
    public User update(User user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getId());
        User user1 = userDao.findById(user.getId());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setAge(user.getAge());
        user1.setCity(user.getCity());
        user1.setEmail(user.getEmail());
        user1.setPhone(user.getPhone());
        return userDao.update(user1);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        User user = userDao.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userDao.delete(user.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        User user = userDao.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("Invalid user id");
        }
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
