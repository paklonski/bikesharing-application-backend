package cz.cvut.bikesharingproject.dao;

import cz.cvut.bikesharingproject.model.Trip;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TripDao extends BaseDao<Trip> {

    public TripDao() {
        super(Trip.class);
    }

    public List<Trip> findOpen() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trip> cq = cb.createQuery(Trip.class);
        Root<Trip> root = cq.from(Trip.class);
        cq.where(cb.equal(root.get("opened"), true));
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }
}
