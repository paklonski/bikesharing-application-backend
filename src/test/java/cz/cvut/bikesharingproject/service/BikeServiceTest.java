package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.BikesharingProjectApplication;
import cz.cvut.bikesharingproject.dao.ParkingStationDao;
import cz.cvut.bikesharingproject.model.Bike;
import cz.cvut.bikesharingproject.model.ParkingStation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Arrays;

import static cz.cvut.bikesharingproject.model.enums.District.PRAHA_2;
import static cz.cvut.bikesharingproject.utils.Generator.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@ComponentScan(basePackageClasses = BikesharingProjectApplication.class)
public class BikeServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private BikeService sut;

    @Autowired
    private ParkingStationDao parkingStationDao;

    @Test
    public void addBikeAddsAnInstanceToPersistenceContext() {
        final ParkingStation parkingStation = generateParkingStation();
        final Bike bike = generateBike();

        bike.setCurrentParkingStation(parkingStation);
        parkingStationDao.persist(parkingStation);
        sut.persist(bike);

        final Bike result = em.find(Bike.class, bike.getId());

        assertEquals(bike.getId(), result.getId());
    }

    @Test
    public void findFreeReturnsAllFreeBikes() {
        final ParkingStation parkingStation = generateParkingStation();
        final Bike bike1 = generateBike();
        final Bike bike2 = generateBike();

        bike1.setCurrentParkingStation(parkingStation);
        bike2.setCurrentParkingStation(parkingStation);
        parkingStation.getCurrentBikes().addAll(Arrays.asList(bike1, bike2));
        parkingStationDao.persist(parkingStation);
        sut.persist(bike1);
        sut.persist(bike2);

        assertNotNull(bike1.getId());
        final Bike updatedBike1 = em.find(Bike.class, bike1.getId());
        updatedBike1.setRent(true);
        sut.update(updatedBike1);

        assertEquals(1, sut.findFree().size());
    }

    @Test
    public void getLocationByIdReturnsDistrictInWhereBikeIsLocated() {
        final ParkingStation parkingStation = generateParkingStation();
        final Bike bike = generateBike();

        bike.setCurrentParkingStation(parkingStation);
        parkingStationDao.persist(parkingStation);
        sut.persist(bike);

        assertNotNull(bike.getId());
        final Bike result = em.find(Bike.class, bike.getId());

        assertEquals(PRAHA_2, sut.getLocation(result).getLocation().getDistrict());
    }
}
