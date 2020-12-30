package cz.cvut.bikesharingproject.rest;

import cz.cvut.bikesharingproject.exception.NotFoundException;
import cz.cvut.bikesharingproject.exception.ValidationException;
import cz.cvut.bikesharingproject.model.Bike;
import cz.cvut.bikesharingproject.model.ParkingStation;
import cz.cvut.bikesharingproject.rest.utils.RestUtils;
import cz.cvut.bikesharingproject.service.BikeService;
import cz.cvut.bikesharingproject.service.ParkingStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/bike")
public class BikeController {

    private static final Logger LOG = LoggerFactory.getLogger(BikeController.class);

    private final BikeService bikeService;

    private final ParkingStationService parkingStationService;

    @Autowired
    public BikeController(BikeService bikeService, ParkingStationService parkingStationService) {
        this.bikeService = bikeService;
        this.parkingStationService = parkingStationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addBike(@RequestBody Bike bike) {
        bikeService.persist(bike);
        LOG.debug("Bike {} is successfully added.", bike);
        final HttpHeaders httpHeaders = RestUtils.createLocationHeaderFromCurrentUri("/{id}", bike.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Bike getBike(@PathVariable Integer id) {
        final Bike bike = bikeService.find(id);
        if (bike == null) {
            throw NotFoundException.create("Bike", id);
        }
        return bike;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bike> getBikes() {
        return bikeService.findAll();
    }

    @GetMapping(value = "/rent", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bike> getRentBikes() {
        return bikeService.findRent();
    }

    @PutMapping(value = "/{bike_id}/station/{station_id}/add-station")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLocation(@PathVariable(name = "bike_id") Integer bikeId,
                            @PathVariable(name = "station_id") Integer parkingStationId) {
        final Bike bike = bikeService.find(bikeId);
        final ParkingStation parkingStation = parkingStationService.find(parkingStationId);
        bikeService.setLocation(bike, parkingStation);
        LOG.debug("The bike {} is located on the parking station {}.", bike, parkingStation);
    }

    @GetMapping(value = "/{bike_id}/station", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParkingStation getLocation(@PathVariable(name = "bike_id") Integer bikeId) {
        final Bike bike = bikeService.find(bikeId);
        return bikeService.getLocation(bike);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBikeData(@PathVariable Integer id, @RequestBody Bike bike) {
        final Bike bikeToUpdate = bikeService.find(id);
        if (!bikeToUpdate.getId().equals(bike.getId())) {
            throw new ValidationException("The bike ID in the request does not match " +
                    "the bike ID in the database.");
        }
        bikeService.update(bike);
        LOG.debug("The bike {} up to date.", bike);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBike(@PathVariable Integer id) {
        final Bike bikeToRemove = bikeService.find(id);
        if (bikeToRemove == null) {
            return;
        }
        bikeService.remove(bikeToRemove);
        LOG.debug("The bike {} is successfully removed.", bikeToRemove);
    }
}
