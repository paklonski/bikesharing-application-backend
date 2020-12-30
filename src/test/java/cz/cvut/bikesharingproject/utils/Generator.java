//package cz.cvut.bikesharingproject.utils;
//
//import cz.cvut.bikesharingproject.model.Bike;
//import cz.cvut.bikesharingproject.model.ParkingStation;
//import cz.cvut.bikesharingproject.model.User;
//import cz.cvut.bikesharingproject.model.enums.CityDistrict;
//
//import java.time.LocalDate;
//import java.util.Random;
//
//public class Generator {
//
//    private static final Random rand = new Random();
//
//    public static User generateUser() {
//        final User user = new User();
//        user.setName("Name" + rand.nextInt());
//        user.setSurname("Surname" + rand.nextInt());
//        user.setEmail("name.surname" + rand.nextInt() + "@google.com");
//        user.setPassword(Integer.toString(rand.nextInt()));
//        user.setPhoneNo(Integer.toString(rand.nextInt()));
//        user.setActive(true);
//        user.setBikeOwner(false);
//        user.setCreditCardNumber(Integer.toString(rand.nextInt()));
//        user.setCreditCardExpirationDate(LocalDate.of(2022, 10, 31));
//        return user;
//    }
//
//    public static ParkingStation generateParkingStation() {
//        final ParkingStation parkingStation = new ParkingStation();
//        parkingStation.setName("Praha2"+ rand.nextInt());
//        parkingStation.setBikeCount(1);
//        parkingStation.setInService(true);
//        parkingStation.setCapacity(3);
//        parkingStation.setDistrict(CityDistrict.PRAHA_2);
//        return parkingStation;
//    }
//
//    public static Bike generateBike() {
//        final Bike bike = new Bike();
//        bike.setBrand("BMX" + rand.nextInt());
//        bike.setModel("TT-55" + rand.nextInt());
//        bike.setRent(false);
//        bike.setPricePerMinute(1.5);
//        bike.setRentDuration(0.0);
//        bike.setRemoved(false);
//        return bike;
//    }
//}
