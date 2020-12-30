package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.dao.ParkingStationDao;
import cz.cvut.bikesharingproject.model.ParkingStation;
import cz.cvut.bikesharingproject.model.enums.CityDistrict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ParkingStationService extends BaseService<ParkingStation> {

    private final ParkingStationDao parkingStationDAO;

    @Autowired
    public ParkingStationService(ParkingStationDao parkingStationDAO) {
        this.parkingStationDAO = parkingStationDAO;
    }

    @Override
    protected BaseDao<ParkingStation> getBaseDao() {
        return parkingStationDAO;
    }

    @Transactional
    public List<ParkingStation> findAllByLocation(CityDistrict districtEnum) {
        Objects.requireNonNull(districtEnum);
        Stream<ParkingStation> parkingStations = findAll().stream().filter(
                c -> c.getLocation().getCityDistrict().equals(districtEnum));
        return parkingStations.collect(Collectors.toList());
    }

    @Transactional
    public List<ParkingStation> findAllWithFreeBikes() {
        Stream<ParkingStation> parkingStations = findAll().stream().filter(
                c -> parkingStationDAO.getCountOfBikes(c) > 0);
        return parkingStations.collect(Collectors.toList());
    }

    @Transactional
    public boolean isPlaceForNewBike(ParkingStation parkingStation) {
        return parkingStation.getCurrentBikes().size() < parkingStation.getCapacity();
    }
}
