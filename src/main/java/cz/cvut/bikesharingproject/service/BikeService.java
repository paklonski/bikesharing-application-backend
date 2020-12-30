package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.dao.BikeDao;
import cz.cvut.bikesharingproject.exception.InsufficientAmountException;
import cz.cvut.bikesharingproject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BikeService extends BaseService<Bike> {

    private BikeDao bikeDao;

    private ParkingStationService parkingStationService;

    @Autowired
    public BikeService(BikeDao bikeDao, ParkingStationService parkingStationService) {
        this.bikeDao = bikeDao;
        this.parkingStationService = parkingStationService;
    }

    @Override
    protected BaseDao<Bike> getBaseDao() {
        return bikeDao;
    }

    @Transactional
    public List<Bike> findRent() {
        return bikeDao.findRent();
    }

    @Transactional
    public void setLocation(Bike bike, ParkingStation parkingStation) {
        Objects.requireNonNull(bike);
        Objects.requireNonNull(parkingStation);
        if (parkingStationService.isPlaceForNewBike(parkingStation)) {
            bike.setCurrentParkingStation(parkingStation);
            parkingStation.getCurrentBikes().add(bike);
            bikeDao.update(bike);
        } else {
            throw new InsufficientAmountException("The parking station " + parkingStation + " is full.");
        }
    }

    @Transactional
    public ParkingStation getLocation(Bike bike) {
        Objects.requireNonNull(bike);
        return bike.getCurrentParkingStation();
    }
//
//    @Transactional
//    public List<Bike> findFree() {
//    }
//
//    @Transactional
//    public List<Bike> getBroken() {
//    }
//
//    @Transactional
//    public List<Trip> getTripHistory() {
//    }
//
//    @Transactional
//    public List<User> getUserHistory() {
//    }
//
//    @Transactional
//    public List<ParkingStation> getParkingStationHistory() {
//    }
//
//    @Transactional
//    public List<ParkingStation> getParkingStationHistory(LocalDateTime from, LocalDateTime to) {
//    }
//
//    @Transactional
//    public List<CustomerSupportForm> getDamageHistory() {
//    }
//
//    @Transactional
//    public LocalTime getTotalRentalTime() {
//    }
//
//    @Transactional
//    public boolean checkBikeAvailability(Integer id) {
//    }
//
//    @Transactional
//    public Bike updateParkingStation(Bike bike, ParkingStation parkingStation) {
//    }
//
//    @Transactional
//    public Bike setIsRent(Bike bike, boolean isRent) {
//    }
}

