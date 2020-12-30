package cz.cvut.bikesharingproject.dao;

import cz.cvut.bikesharingproject.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }
}
