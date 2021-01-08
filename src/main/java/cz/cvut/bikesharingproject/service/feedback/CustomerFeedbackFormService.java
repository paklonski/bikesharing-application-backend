package cz.cvut.bikesharingproject.service.feedback;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.dao.feedback.CustomerFeedbackFormDao;
import cz.cvut.bikesharingproject.model.feedback.CustomerFeedbackForm;
import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.model.enums.CustomerFeedbackFormState;
import cz.cvut.bikesharingproject.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerFeedbackFormService extends BaseService<CustomerFeedbackForm> {

    private CustomerFeedbackFormDao customerFeedbackFormDao;

    @Autowired
    public CustomerFeedbackFormService(CustomerFeedbackFormDao customerFeedbackFormDao) {
        this.customerFeedbackFormDao = customerFeedbackFormDao;
    }

    @Override
    protected BaseDao<CustomerFeedbackForm> getBaseDao() {
        return customerFeedbackFormDao;
    }

    @Transactional
    public void add(User user, CustomerFeedbackForm feedback) {
        feedback.setStatus(CustomerFeedbackFormState.OPENED);
        feedback.setTimeOfCreation(LocalDateTime.now());
        feedback.getParticipants().add(user);
        user.getFeedbackForms().add(feedback);
        super.persist(feedback);
    }

    @Transactional
    public List<CustomerFeedbackForm> findOpen() {
        return customerFeedbackFormDao.findOpen();
    }

    @Transactional
    public void close(User admin, CustomerFeedbackForm feedback, CustomerFeedbackForm updates) {
        feedback.getParticipants().add(admin);
        feedback.setComment(updates.getComment());
        feedback.setStatus(CustomerFeedbackFormState.CLOSED);
        super.update(feedback);
    }
}
