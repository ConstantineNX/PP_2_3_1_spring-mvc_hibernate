package service;

import model.User;
import java.util.List;

public interface UserDaoService {
        User saveUser(User user);
        User update(User user);
        void delete(Long id);
        User findById(Long id);
        List<User> findAll();
}
