package cz.cvut.bikesharingproject.dao.feedback;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.model.feedback.RentalProblemForm;
import org.springframework.stereotype.Repository;

@Repository
public class RentalProblemFormDao extends BaseDao<RentalProblemForm> {

    public RentalProblemFormDao() {
        super(RentalProblemForm.class);
    }
}
