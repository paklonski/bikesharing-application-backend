package cz.cvut.bikesharingproject.dao;

import cz.cvut.bikesharingproject.model.DamagedBike;
import org.springframework.stereotype.Repository;

@Repository
public class DamagedBikeDao extends BaseDao<DamagedBike> {

    public DamagedBikeDao() {
        super(DamagedBike.class);
    }
}
