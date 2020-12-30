package cz.cvut.bikesharingproject.dao;

import cz.cvut.bikesharingproject.model.ParkingStation;
import org.springframework.stereotype.Repository;

@Repository
public class ParkingStationDao extends BaseDao<ParkingStation> {

    public ParkingStationDao() {
        super(ParkingStation.class);
    }

    public Integer getCountOfBikes(ParkingStation parkingStation) {
        return parkingStation.getCurrentBikes().size();
    }
}
