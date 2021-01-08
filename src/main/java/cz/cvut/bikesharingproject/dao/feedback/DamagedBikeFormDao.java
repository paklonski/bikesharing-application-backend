package cz.cvut.bikesharingproject.dao.feedback;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.model.feedback.DamagedBikeForm;
import org.springframework.stereotype.Repository;

@Repository
public class DamagedBikeFormDao extends BaseDao<DamagedBikeForm> {

    public DamagedBikeFormDao() {
        super(DamagedBikeForm.class);
    }
}
