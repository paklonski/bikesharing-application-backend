//package cz.cvut.bikesharingproject.service;
//
//import cz.cvut.bikesharingproject.BikesharingProjectApplication;
//import cz.cvut.bikesharingproject.dao.ParkingStationDao;
//import cz.cvut.bikesharingproject.model.Bike;
//import cz.cvut.bikesharingproject.model.ParkingStation;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import static cz.cvut.bikesharingproject.model.enums.CityDistrict.PRAHA_2;
//import static cz.cvut.bikesharingproject.utils.Generator.*;
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@Transactional
//@ComponentScan(basePackageClasses = BikesharingProjectApplication.class)
//public class BikeServiceTest {
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private BikeService sut;
//
//    @Autowired
//    private ParkingStationDao parkingStationDAO;
//
//    @Test
//    public void addBikeAddsAnInstanceToPersistenceContext() {
//        final ParkingStation parkingStation = generateParkingStation();
//        parkingStationDAO.persist(parkingStation);
//        final Bike bike = generateBike();
//        bike.setLastParkingStation(parkingStation);
//        sut.addBike(bike);
//        assertNotNull(bike.getId());
//        final Bike result = em.find(Bike.class, bike.getId());
//        assertNotNull(result);
//        assertEquals(bike.getId(), result.getId());
//    }
//
//    @Test
//    public void getAllBikesShowsBikes() {
//        final ParkingStation parkingStation = generateParkingStation();
//        parkingStationDAO.persist(parkingStation);
//        final Bike bike1 = generateBike();
//        bike1.setLastParkingStation(parkingStation);
//        final Bike bike2 = generateBike();
//        bike2.setLastParkingStation(parkingStation);
//        bike2.setRent(true);
//        sut.addBike(bike1);
//        sut.addBike(bike2);
//        assertNotNull(bike1.getId());
//        assertNotNull(bike2.getId());
//        final Bike result1 = em.find(Bike.class, bike1.getId());
//        final Bike result2 = em.find(Bike.class, bike2.getId());
//        assertNotNull(result1);
//        assertNotNull(result2);
//        assertEquals(2, sut.getAllBikes().size());
//    }
//
//    @Test
//    public void getLocationByIdReturnsWhereBikeIsLocated() {
//        final ParkingStation parkingStation = generateParkingStation();
//        parkingStationDAO.persist(parkingStation);
//        final Bike bike = generateBike();
//        bike.setLastParkingStation(parkingStation);
//        sut.addBike(bike);
//        assertNotNull(bike.getId());
//        final Bike result = em.find(Bike.class, bike.getId());
//        assertNotNull(result);
//        assertEquals(PRAHA_2, sut.getLocationById(bike.getId()));
//    }
//}
