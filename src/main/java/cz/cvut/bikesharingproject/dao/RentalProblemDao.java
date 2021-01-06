package cz.cvut.bikesharingproject.dao;

import cz.cvut.bikesharingproject.model.RentalProblem;
import org.springframework.stereotype.Repository;

@Repository
public class RentalProblemDao extends BaseDao<RentalProblem> {

    public RentalProblemDao() {
        super(RentalProblem.class);
    }
}
