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
//import javax.persistence.EntityManager;
//
//import static cz.cvut.bikesharingproject.utils.Generator.*;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@Transactional
//@ComponentScan(basePackageClasses = BikesharingProjectApplication.class)
//public class CustomerSupportFormServiceTest {
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private CustomerSupportFormService sut;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private BikeService bikeService;
//
//    @Autowired
//    private TripService tripService;
//
//    @Autowired
//    private ParkingStationDao parkingStationDAO;
//
//    @Test
//    public void getAllCustomerSupportFormsThatIsOpenedShowsCountOfOpenForms() {
//        final ParkingStation parkingStation = generateParkingStation();
//        parkingStationDAO.persist(parkingStation);
//        final User user = generateUser();
//        final Bike bike = generateBike();
//        bike.setLastParkingStation(parkingStation);
//        userService.persist(user);
//        bikeService.addBike(bike);
//        final Trip trip = tripService.createTrip(user, bike);
//        CustomerSupportForm csf1 = sut.createCustomerSupportForm(user, trip, "Hello. Everything is OK.");
//        CustomerSupportForm csf2 = sut.createCustomerSupportForm(user, trip, "Ufff:((");
//        csf2.setClosed(true);
//        assertNotNull(csf1.getId());
//        final CustomerSupportForm csf = em.find(CustomerSupportForm.class, csf1.getId());
//        assertNotNull(csf);
//        assertEquals(1, sut.getAllCustomerSupportFormsThatIsOpened(user.getId()).size());
//    }
//}
