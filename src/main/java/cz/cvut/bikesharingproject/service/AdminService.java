package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminDao adminDAO;

    @Autowired
    public AdminService(AdminDao adminDAO) {
        this.adminDAO = adminDAO;
    }
}
