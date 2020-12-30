//package cz.cvut.bikesharingproject.service;
//
//import cz.cvut.bikesharingproject.BikesharingProjectApplication;
//import cz.cvut.bikesharingproject.dao.ParkingStationDao;
//import cz.cvut.bikesharingproject.model.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static cz.cvut.bikesharingproject.utils.Generator.*;
//import static org.junit.Assert.assertEquals;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@Transactional
//@ComponentScan(basePackageClasses = BikesharingProjectApplication.class)
//public class ParkingStationServiceTest {
//
//    @Autowired
//    private ParkingStationService sut;
//
//    @Autowired
//    private BikeService bikeService;
//
//    @Autowired
//    private ParkingStationDao parkingStationDAO;
//
//    @Test
//    public void getAllParkingStationsWithFreeBikesShowsHowManyParkingStationsHaveBikes() {
//        final ParkingStation parkingStation1 = generateParkingStation();
//        parkingStationDAO.persist(parkingStation1);
//        final ParkingStation parkingStation2 = generateParkingStation();
//        parkingStationDAO.persist(parkingStation2);
//        final Bike bike1 = generateBike();
//        final Bike bike2 = generateBike();
//        bike1.setLastParkingStation(parkingStation1);
//        bike2.setLastParkingStation(parkingStation2);
//        bikeService.addBike(bike1);
//        bikeService.addBike(bike2);
//        assertEquals(2, sut.getAllParkingStationsWithFreeBikes().size());
//    }
//}
