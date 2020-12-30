package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.dao.CustomerSupportFormDao;
import cz.cvut.bikesharingproject.model.CustomerSupportForm;
import cz.cvut.bikesharingproject.model.Trip;
import cz.cvut.bikesharingproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerSupportFormService extends BaseService<CustomerSupportForm> {

    private CustomerSupportFormDao customerSupportFormDAO;

    @Autowired
    public CustomerSupportFormService(CustomerSupportFormDao customerSupportFormDAO) {
        this.customerSupportFormDAO = customerSupportFormDAO;
    }

    @Override
    protected BaseDao<CustomerSupportForm> getBaseDao() {
        return customerSupportFormDAO;
    }
//
//    @Transactional
//    public CustomerSupportForm createCustomerSupportForm(User user, Trip trip, String description) {
//        Objects.requireNonNull(user);
//        Objects.requireNonNull(trip);
//        Objects.requireNonNull(description);
//        CustomerSupportForm customerSupportForm = new CustomerSupportForm();
//        customerSupportForm.setDescription(description);
//        customerSupportForm.setUser(user);
//        customerSupportFormDAO.persist(customerSupportForm);
//        return customerSupportForm;
//    }
//
//    @Transactional
//    public List<CustomerSupportForm> getAllCustomerSupportFormsByUserId(Integer id) {
//        Objects.requireNonNull(id);
//        Stream<CustomerSupportForm> forms = customerSupportFormDAO.findAll().stream().filter(c -> c.getUser().getId().equals(id));
//        return forms.collect(Collectors.toList());
//    }
//
//    @Transactional
//    public List<CustomerSupportForm> getAllCustomerSupportFormsThatIsOpened(Integer id) {
//        Objects.requireNonNull(id);
//        Stream<CustomerSupportForm> forms = customerSupportFormDAO.findAll().stream().filter(c -> !c.isClosed());
//        return forms.collect(Collectors.toList());
//    }
//
//    @Transactional
//    public void closeCustomerSupportForm(CustomerSupportForm csf) {
//        Objects.requireNonNull(csf);
//        csf.setClosed(true);
//        customerSupportFormDAO.update(csf);
//    }
}
