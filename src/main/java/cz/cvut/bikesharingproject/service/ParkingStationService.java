package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.dao.ParkingStationDao;
import cz.cvut.bikesharingproject.exception.InsufficientAmountException;
import cz.cvut.bikesharingproject.exception.NotFoundException;
import cz.cvut.bikesharingproject.model.Bike;
import cz.cvut.bikesharingproject.model.ParkingStation;
import cz.cvut.bikesharingproject.model.enums.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ParkingStationService extends BaseService<ParkingStation> {

    private final ParkingStationDao parkingStationDao;

    private final BikeService bikeService;

    @Autowired
    public ParkingStationService(ParkingStationDao parkingStationDAO, BikeService bikeService) {
        this.parkingStationDao = parkingStationDAO;
        this.bikeService = bikeService;
    }

    @Override
    protected BaseDao<ParkingStation> getBaseDao() {
        return parkingStationDao;
    }

    @Transactional
    public void addBikeToParkingStation(ParkingStation parkingStation, Bike bike) {
        Objects.requireNonNull(parkingStation);
        Objects.requireNonNull(bike);
        if (!isPlaceForNewBike(parkingStation)) {
            throw new InsufficientAmountException("The parking station " + parkingStation + " is full.");
        }
        resetStationBikeInfo(bike);
        updateStationBikeInfo(parkingStation, bike);
        bikeService.update(bike);
    }

    public void removeBikeFromParkingStation(ParkingStation parkingStation, Bike bike) {
        Objects.requireNonNull(parkingStation);
        Objects.requireNonNull(bike);
        if (!parkingStation.getCurrentBikes().contains(bike)) {
            throw new NotFoundException(bike + "is not found at " + parkingStation);
        }
        resetStationBikeInfo(bike);
        bike.setRent(true);
    }

    @Transactional
    public List<ParkingStation> findAllByLocation(District district) {
        Objects.requireNonNull(district);
        Stream<ParkingStation> parkingStations = findAll()
                .stream().filter(c -> c.getLocation().getDistrict().equals(district));
        return parkingStations.collect(Collectors.toList());
    }

    @Transactional
    public List<ParkingStation> findAllWithFreeBikes() {
        Stream<ParkingStation> parkingStations = findAll()
                .stream().filter(c -> parkingStationDao.getCountOfBikes(c) > 0);
        return parkingStations.collect(Collectors.toList());
    }

    @Transactional
    public boolean isPlaceForNewBike(ParkingStation parkingStation) {
        return parkingStation.getCurrentBikes().size() < parkingStation.getCapacity();
    }

    private void resetStationBikeInfo(Bike bike) {
        if (bike.getCurrentParkingStation() != null) {
            bike.getCurrentParkingStation().getCurrentBikes().remove(bike);
            bike.setCurrentParkingStation(null);
        }
    }

    private void updateStationBikeInfo(ParkingStation parkingStation, Bike bike) {
        parkingStation.getCurrentBikes().add(bike);
        bike.setCurrentParkingStation(parkingStation);
    }
}
