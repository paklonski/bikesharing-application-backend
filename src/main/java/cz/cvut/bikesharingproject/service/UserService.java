package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.dao.UserDao;
import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class UserService extends BaseService<User> {

    private final UserDao userDao;

    @Override
    protected BaseDao<User> getBaseDao() {
        return userDao;
    }

//        private final BCryptPasswordEncoder passwordEncoder;

//    @Autowired
//    public UserService(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
//        this.userDao = userDao;
//        this.passwordEncoder = passwordEncoder;
//    }

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void persist(User user) {
        Objects.requireNonNull(user);
        user.setBalance(new BigDecimal("0.0"));
        user.setRole(Role.USER);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        super.persist(user);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public void makePayment(User user, BigDecimal amount) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(amount);
        user.setBalance(user.getBalance().subtract(amount));
        if (user.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            user.setBlocked(true);
        }
    }
}
