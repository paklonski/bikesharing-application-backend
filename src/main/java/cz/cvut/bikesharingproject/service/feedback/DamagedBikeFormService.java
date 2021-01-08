package cz.cvut.bikesharingproject.service.feedback;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.dao.feedback.DamagedBikeFormDao;
import cz.cvut.bikesharingproject.model.Bike;
import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.model.enums.CustomerFeedbackFormState;
import cz.cvut.bikesharingproject.model.feedback.DamagedBikeForm;
import cz.cvut.bikesharingproject.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DamagedBikeFormService extends BaseService<DamagedBikeForm> {

    private DamagedBikeFormDao damagedBikeDao;

    @Autowired
    public DamagedBikeFormService(DamagedBikeFormDao damagedBikeDao) {
        this.damagedBikeDao = damagedBikeDao;
    }

    @Override
    protected BaseDao<DamagedBikeForm> getBaseDao() {
        return damagedBikeDao;
    }

    @Transactional
    public void createDamagedBikeForm(User user, Bike bike, DamagedBikeForm damagedBike) {
        damagedBike.setStatus(CustomerFeedbackFormState.OPENED);
        damagedBike.setTimeOfCreation(LocalDateTime.now());
        damagedBike.setBike(bike);
        damagedBike.getParticipants().add(user);
        bike.getDamageHistory().add(damagedBike);
        super.persist(damagedBike);
    }

    @Transactional
    public void handle(User admin, DamagedBikeForm form) {
        form.getParticipants().add(admin);
        form.setStatus(CustomerFeedbackFormState.IN_PROGRESS);
        super.update(form);
    }

    @Transactional
    public void close(DamagedBikeForm form, DamagedBikeForm updates) {
        form.setComment(updates.getComment());
        form.setStatus(CustomerFeedbackFormState.CLOSED);
        super.update(form);
    }
}
