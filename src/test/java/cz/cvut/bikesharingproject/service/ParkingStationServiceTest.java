package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.BikesharingProjectApplication;
import cz.cvut.bikesharingproject.dao.ParkingStationDao;
import cz.cvut.bikesharingproject.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static cz.cvut.bikesharingproject.utils.Generator.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@ComponentScan(basePackageClasses = BikesharingProjectApplication.class)
public class ParkingStationServiceTest {

    @Autowired
    private ParkingStationService sut;

    @Autowired
    private BikeService bikeService;

    @Autowired
    private ParkingStationDao parkingStationDao;

    @Test
    public void getAllWithFreeBikesReturnsCountOnes() {
        final ParkingStation parkingStation1 = generateParkingStation();
        final ParkingStation parkingStation2 = generateParkingStation();
        final Bike bike1 = generateBike();
        bike1.setCurrentParkingStation(parkingStation1);
        parkingStation1.getCurrentBikes().add(bike1);

        parkingStationDao.persist(parkingStation1);
        parkingStationDao.persist(parkingStation2);
        bikeService.persist(bike1);

        assertEquals(1, sut.findAllWithFreeBikes().size());
    }
}
