package cz.cvut.bikesharingproject.utils;

import cz.cvut.bikesharingproject.model.Bike;
import cz.cvut.bikesharingproject.model.Location;
import cz.cvut.bikesharingproject.model.ParkingStation;
import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.model.enums.CustomerFeedbackFormState;
import cz.cvut.bikesharingproject.model.enums.District;
import cz.cvut.bikesharingproject.model.enums.Role;
import cz.cvut.bikesharingproject.model.feedback.CustomerFeedbackForm;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;

public class Generator {

    private static final Random rand = new Random();

    public static Location generateLocation() {
        final Location location = new Location();
        location.setLatitude(11.5567);
        location.setLongitude(67.4446);
        location.setDistrict(District.PRAHA_2);
        return location;
    }
    public static User generateUser() {
        final User user = new User();
        user.setId(rand.nextInt());
        user.setEnabled(true);
        user.setRole(Role.USER);
        user.setBlocked(false);
        user.setUsername("username" + rand.nextInt());
        user.setPassword(Integer.toString(rand.nextInt()));
        user.setName("Name" + rand.nextInt());
        user.setSurname("Surname" + rand.nextInt());
        user.setEmail("name.surname" + rand.nextInt() + "@google.com");
        user.setPhoneNumber(Integer.toString(rand.nextInt()));
        user.setBalance(new BigDecimal("0.0"));
        return user;
    }

    public static CustomerFeedbackForm generateFeedback(String description) {
        final CustomerFeedbackForm feedback = new CustomerFeedbackForm();
        feedback.setId(rand.nextInt(100) + 1);
        feedback.setEnabled(true);
        feedback.setDescription(description);
        feedback.setStatus(CustomerFeedbackFormState.OPENED);
        feedback.setTimeOfCreation(LocalDateTime.now());
        return feedback;
    }

    public static ParkingStation generateParkingStation() {
        final ParkingStation parkingStation = new ParkingStation();
        parkingStation.setId(rand.nextInt(100) + 1);
        parkingStation.setEnabled(true);
        parkingStation.setName("Praha-2-"+ (rand.nextInt(10) + 1));
        parkingStation.setCapacity(2);
        parkingStation.setCurrentBikes(new HashSet<>());
        parkingStation.setLocation(generateLocation());
        return parkingStation;
    }

    public static Bike generateBike() {
        Bike bike = new Bike();
        bike.setId(rand.nextInt(100) + 1);
        bike.setEnabled(true);
        bike.setBrand("BMX");
        bike.setModel("A-" + (rand.nextInt(100) + 1));
        bike.setPricePerMinute(new BigDecimal("1.50"));
        bike.setRent(false);
        return bike;
    }
}
