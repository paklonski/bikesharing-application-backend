package cz.cvut.bikesharingproject.dao;

import cz.cvut.bikesharingproject.model.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BikeDao extends BaseDao<Bike> {

    private TripDao tripDao;

    @Autowired
    public BikeDao(TripDao tripDao) {
        super(Bike.class);
    }

    @Override
    public List<Bike> findAll() {
        return em.createNamedQuery("Bike.findAll", Bike.class).getResultList();
    }

//    public List<Bike> findRent() {
//        List<Trip> opened = tripDao.findOpen();
//        Stream<Bike> bikeStream = opened.stream().map(Trip::getBike);
//        return bikeStream.collect(Collectors.toList());
//    }

    public List<Bike> findRent() {
        return em.createNamedQuery("Bike.findRent", Bike.class).getResultList();
    }

    public List<Bike> findFree() {
        return em.createNamedQuery("Bike.findFree", Bike.class).getResultList();
    }
}
