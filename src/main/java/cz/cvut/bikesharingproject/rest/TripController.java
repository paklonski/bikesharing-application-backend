package cz.cvut.bikesharingproject.rest;

import cz.cvut.bikesharingproject.exception.NotFoundException;
import cz.cvut.bikesharingproject.exception.ValidationException;
import cz.cvut.bikesharingproject.model.Bike;
import cz.cvut.bikesharingproject.model.ParkingStation;
import cz.cvut.bikesharingproject.model.Trip;
import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.rest.utils.RestUtils;
import cz.cvut.bikesharingproject.service.BikeService;
import cz.cvut.bikesharingproject.service.ParkingStationService;
import cz.cvut.bikesharingproject.service.TripService;
import cz.cvut.bikesharingproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/trips")
public class TripController {

    private final TripService tripService;
    private final UserService userService;
    private final BikeService bikeService;
    private final ParkingStationService parkingStationService;

    @Autowired
    public TripController(TripService tripService,
                          UserService userService,
                          BikeService bikeService,
                          ParkingStationService parkingStationService) {
        this.tripService = tripService;
        this.userService = userService;
        this.bikeService = bikeService;
        this.parkingStationService = parkingStationService;
    }

    @PostMapping(value = "/users/{userId}/bikes/{bikeId}/start")
    public ResponseEntity<Void> startTrip(@PathVariable Integer userId,
                                          @PathVariable Integer bikeId) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(bikeId);
        final User user = userService.find(userId);
        final Bike bike = bikeService.find(bikeId);
        Trip trip = tripService.startTrip(user, bike);
        log.info("{} is renting by {}. {} is successfully created.", bike, user, trip);
        final HttpHeaders httpHeaders = RestUtils.createLocationHeaderFromCurrentUri("/{id}", trip.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{tripId}/users/{userId}/stations/{stationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finishTrip(@PathVariable Integer tripId,
                           @PathVariable Integer userId,
                           @PathVariable Integer stationId) {
        Objects.requireNonNull(userId);
        final Trip tripToFinish = tripService.find(tripId);
        final ParkingStation stationToFinish = parkingStationService.find(stationId);
        if (!tripToFinish.getUser().getId().equals(userId)) {
            throw new ValidationException("The user with ID: " + userId + " is not " +
                    "the owner of the trip with ID: " + tripId + " .");
        }
        tripService.finishTrip(tripToFinish, stationToFinish);
        log.info("{} is finished.", tripToFinish);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Trip getTrip(@PathVariable Integer id) {
        final Trip trip = tripService.find(id);
        if (trip == null) {
            throw NotFoundException.create("Bike", id);
        }
        return trip;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Trip> getAllTrips() {
        return tripService.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/open", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Trip> getOpenTrips() {
        return tripService.findOpen();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Trip getCurrentTripByUserId(@PathVariable Integer userId) {
        return tripService.findCurrentTripByUserId(userId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTrip(@PathVariable Integer id) {
        final Trip tripToRemove = tripService.find(id);
        if (tripToRemove == null) {
            return;
        }
        tripService.remove(tripToRemove);
        log.info("{} is successfully removed.", tripToRemove);
    }
}
