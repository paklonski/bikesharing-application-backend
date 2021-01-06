package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.dao.TripDao;
import cz.cvut.bikesharingproject.exception.NotFoundException;
import cz.cvut.bikesharingproject.model.Bike;
import cz.cvut.bikesharingproject.model.ParkingStation;
import cz.cvut.bikesharingproject.model.Trip;
import cz.cvut.bikesharingproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TripService extends BaseService<Trip> {

    private final TripDao tripDao;

    private UserService userService;

    private BikeService bikeService;

    private ParkingStationService parkingStationService;

    @Autowired
    public TripService(TripDao tripDAO,
                       UserService userService,
                       BikeService bikeService,
                       ParkingStationService parkingStationService) {
        this.tripDao = tripDAO;
        this.userService = userService;
        this.bikeService = bikeService;
        this.parkingStationService = parkingStationService;
    }

    @Override
    protected BaseDao<Trip> getBaseDao() {
        return tripDao;
    }

    @Transactional
    public List<Trip> findOpen() {
        return tripDao.findOpen();
    }

    @Transactional
    public Trip startTrip(User user, Bike bike) {
        if (bikeService.findRent().contains(bike)) {
            throw new NotFoundException("Bike is reserved. Try to find another.");
        }
        ParkingStation currentParkingStation = bike.getCurrentParkingStation();
        Trip trip = initialSetup();
        trip.setStartParkingStation(currentParkingStation);
        updateUserTripInfo(user, trip);
        updateBikeTripInfo(bike, trip);
        parkingStationService.removeBikeFromParkingStation(currentParkingStation, bike);
        tripDao.persist(trip);
        return trip;
    }

    @Transactional
    public void finishTrip(Trip trip, ParkingStation station) {
        Objects.requireNonNull(trip);
        Objects.requireNonNull(station);
        Bike currentBike = trip.getBike();
        setParametersForClosingTrip(trip, station);
        userService.makePayment(trip.getUser(), trip.getTotalFee());
        currentBike.setRent(false);
        parkingStationService.addBikeToParkingStation(station, currentBike);
        tripDao.update(trip);
    }

    @Transactional
    public Trip findCurrentTripByUserId(Integer userId) {
        Objects.requireNonNull(userId);
        List<Trip> opened = findOpen();
        for (Trip current: opened) {
            if (current.getUser().getId().equals(userId)) {
                return current;
            }
        }
        return null;
    }

    private void setParametersForClosingTrip(Trip trip, ParkingStation station) {
        trip.setFinalParkingStation(station);
        trip.setFinalRentalTime(LocalDateTime.now());
        trip.setDurationInMinutes(countDurationInMinutes(trip));
        trip.setTotalFee(countTotalFee(trip));
        trip.setOpened(false);
    }

    private Trip initialSetup() {
        Trip trip = new Trip();
        trip.setOpened(true);
        trip.setStartRentalTime(LocalDateTime.now());
        trip.setFinalRentalTime(null);
        trip.setDurationInMinutes(null);
        trip.setTotalFee(null);
        return trip;
    }

    private void updateUserTripInfo(User user, Trip trip) {
        user.getRentalHistory().add(trip);
        trip.setUser(user);
    }

    private void updateBikeTripInfo(Bike bike, Trip trip) {
        bike.getTripHistory().add(trip);
        trip.setBike(bike);
    }

    private Long countDurationInMinutes(Trip trip) {
        return Duration.between(trip.getStartRentalTime(), trip.getFinalRentalTime()).toMinutes();
    }

    private BigDecimal countTotalFee(Trip trip) {
        long minutes = countDurationInMinutes(trip);
        BigDecimal pricePerMinute = trip.getBike().getPricePerMinute();
        return pricePerMinute.multiply(BigDecimal.valueOf(minutes));
    }
}
