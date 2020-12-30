package cz.cvut.bikesharingproject.dao;

import cz.cvut.bikesharingproject.model.Admin;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao extends BaseDao<Admin> {

    public AdminDao() {
        super(Admin.class);
    }
}
