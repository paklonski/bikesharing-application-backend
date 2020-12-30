package cz.cvut.bikesharingproject.dao;

import cz.cvut.bikesharingproject.model.CustomerSupportForm;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerSupportFormDao extends BaseDao<CustomerSupportForm> {

    public CustomerSupportFormDao() {
        super(CustomerSupportForm.class);
    }
}
