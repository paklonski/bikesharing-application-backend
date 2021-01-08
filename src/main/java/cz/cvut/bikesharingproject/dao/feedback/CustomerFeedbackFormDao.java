package cz.cvut.bikesharingproject.dao.feedback;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.model.feedback.CustomerFeedbackForm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerFeedbackFormDao extends BaseDao<CustomerFeedbackForm> {

    protected CustomerFeedbackFormDao() {
        super(CustomerFeedbackForm.class);
    }

    public List<CustomerFeedbackForm> findOpen() {
        return em.createNamedQuery("Feedback.findOpen", CustomerFeedbackForm.class).getResultList();
    }
}
