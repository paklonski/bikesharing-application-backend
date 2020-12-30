package cz.cvut.bikesharingproject.dao;

import cz.cvut.bikesharingproject.model.Bike;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BikeDao extends BaseDao<Bike> {

    public BikeDao() {
        super(Bike.class);
    }

    @Override
    public List<Bike> findAll() {
        return em.createQuery("SELECT b FROM Bike b WHERE NOT b.isRemoved", Bike.class).getResultList();
    }

    public List<Bike> findRent() {
        return em.createNamedQuery("Bike.findRent", Bike.class).getResultList();
    }

    public List<Bike> findFree() {
        return em.createQuery("SELECT b FROM Bike b WHERE NOT b.isRemoved AND NOT b.isRent", Bike.class).getResultList();
    }
}
