package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.dao.TripDao;
import cz.cvut.bikesharingproject.exception.NotFoundException;
import cz.cvut.bikesharingproject.model.Bike;
import cz.cvut.bikesharingproject.model.Trip;
import cz.cvut.bikesharingproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TripService {

    private final TripDao tripDAO;

    @Autowired
    public TripService(TripDao tripDAO) {
        this.tripDAO = tripDAO;
    }

//    private Trip firstSetup() {
//        Trip trip = new Trip();
//        trip.setFinished(false);
//        trip.setDistance(0.0);
//        trip.setTotalFee(0.0);
//        trip.setStartTime(LocalDateTime.now());
//        trip.setFinishTime(LocalDateTime.now());
//        return trip;
//    }
//
//    @Transactional
//    public Trip createTrip(User user, Bike bike) {
//        Objects.nonNull(user);
//        Objects.nonNull(bike);
//        if (bike.isRent()) {
//            throw new NotFoundException("Bike is reserved. Try to find another.");
//        }
//        Trip trip = firstSetup();
//        trip.setUser(user);
//        trip.setBike(bike);
//        tripDAO.persist(trip);
//        return trip;
//    }

    @Transactional
    public List<Trip> getAllTrips() {
        return tripDAO.findAll();
    }

//    @Transactional
//    public Trip updateTripAfterFinishing(Trip trip) {
//        Objects.nonNull(trip);
//        double rentDuration = trip.getBike().getRentDuration();
//        double pricePerMinute = trip.getBike().getPricePerMinute();
//        trip.setFinished(true);
//        trip.setDistance(rentDuration);
//        trip.setFinishTime(LocalDateTime.now());
//        trip.setTotalFee(rentDuration * pricePerMinute);
//        return tripDAO.update(trip);
//    }

//    @Transactional
//    public Trip hideTrip(Integer id) {
//        Trip tripToUpdate = tripDAO.find(id);
//        if (tripToUpdate.isFinished()) {
//            tripToUpdate.setVisible(false);
//            return tripToUpdate;
//        } else {
//            throw new NotFoundException("Bike is not found.");
//        }
//    }
}
