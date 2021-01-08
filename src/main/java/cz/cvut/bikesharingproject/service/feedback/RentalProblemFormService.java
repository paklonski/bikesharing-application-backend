package cz.cvut.bikesharingproject.service.feedback;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.dao.feedback.RentalProblemFormDao;
import cz.cvut.bikesharingproject.model.feedback.RentalProblemForm;
import cz.cvut.bikesharingproject.model.Trip;
import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.model.enums.CustomerFeedbackFormState;
import cz.cvut.bikesharingproject.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RentalProblemFormService extends BaseService<RentalProblemForm> {

    private RentalProblemFormDao rentalProblemDao;

    @Autowired
    public RentalProblemFormService(RentalProblemFormDao rentalProblemDao) {
        this.rentalProblemDao = rentalProblemDao;
    }

    @Override
    protected BaseDao<RentalProblemForm> getBaseDao() {
        return rentalProblemDao;
    }

    @Transactional
    public void createRentalProblemForm(User user, Trip trip, RentalProblemForm rentalProblem) {
        rentalProblem.setStatus(CustomerFeedbackFormState.OPENED);
        rentalProblem.setTimeOfCreation(LocalDateTime.now());
        rentalProblem.setTrip(trip);
        rentalProblem.getParticipants().add(user);
        user.getFeedbackForms().add(rentalProblem);
        super.persist(rentalProblem);
    }

    @Transactional
    public void handle(User admin, RentalProblemForm form) {
        form.getParticipants().add(admin);
        form.setStatus(CustomerFeedbackFormState.IN_PROGRESS);
        super.update(form);
    }

    @Transactional
    public void close(RentalProblemForm form, RentalProblemForm updates) {
        form.setComment(updates.getComment());
        form.setStatus(CustomerFeedbackFormState.CLOSED);
        super.update(form);
    }
}
