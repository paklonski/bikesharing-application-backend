package cz.cvut.bikesharingproject.dao;

import cz.cvut.bikesharingproject.model.Trip;
import org.springframework.stereotype.Repository;

@Repository
public class TripDao extends BaseDao<Trip> {

    public TripDao() {
        super(Trip.class);
    }
}
