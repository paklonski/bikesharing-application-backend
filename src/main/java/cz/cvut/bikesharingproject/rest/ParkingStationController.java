package cz.cvut.bikesharingproject.rest;

import cz.cvut.bikesharingproject.exception.NotFoundException;
import cz.cvut.bikesharingproject.exception.ValidationException;
import cz.cvut.bikesharingproject.model.Bike;
import cz.cvut.bikesharingproject.model.ParkingStation;
import cz.cvut.bikesharingproject.model.enums.District;
import cz.cvut.bikesharingproject.rest.utils.RestUtils;
import cz.cvut.bikesharingproject.service.BikeService;
import cz.cvut.bikesharingproject.service.ParkingStationService;
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
@RequestMapping("/api/v1/stations")
public class ParkingStationController {

    private final BikeService bikeService;

    private final ParkingStationService parkingStationService;

    @Autowired
    public ParkingStationController(BikeService bikeService, ParkingStationService parkingStationService) {
        this.bikeService = bikeService;
        this.parkingStationService = parkingStationService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addParkingStation(@RequestBody ParkingStation parkingStation) {
        parkingStationService.persist(parkingStation);
        log.info("{} is successfully added.", parkingStation);
        final HttpHeaders httpHeaders = RestUtils.createLocationHeaderFromCurrentUri(
                "/{id}", parkingStation.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParkingStation getParkingStation(@PathVariable Integer id) {
        final ParkingStation parkingStation = parkingStationService.find(id);
        if (parkingStation == null) {
            throw NotFoundException.create("Parking station", id);
        }
        return parkingStation;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParkingStation> getAllParkingStations() {
        return parkingStationService.findAll();
    }

    @GetMapping(value = "/districts/{district}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParkingStation> getAllByLocation(@PathVariable District district) {
        return parkingStationService.findAllByLocation(district);
    }

    @GetMapping(value = "/free-bikes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParkingStation> getAllWithFreeBikes() {
        return parkingStationService.findAllWithFreeBikes();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/{stationId}/bikes/{bikeId}/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addBikeToParkingStation(@PathVariable Integer stationId,
                                        @PathVariable Integer bikeId) {
        final ParkingStation parkingStation = parkingStationService.find(stationId);
        final Bike bike = bikeService.find(bikeId);
        parkingStationService.addBikeToParkingStation(parkingStation, bike);
        log.info("{} has been added to {}.", bike, parkingStation);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateParkingStation(@PathVariable Integer id,
                                     @RequestBody ParkingStation parkingStation) {
        Objects.requireNonNull(parkingStation);
        final ParkingStation parkingStationToUpdate = parkingStationService.find(id);
        if (!parkingStationToUpdate.getId().equals(parkingStation.getId())) {
            throw new ValidationException("The parking station ID in the request does not match " +
                    "the parking station ID in the database.");
        }
        parkingStationService.update(parkingStation);
        log.info("{} up to date.", parkingStation);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/update-capacity", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateParkingStationCapacity(@PathVariable Integer id,
                                             @RequestBody ParkingStation updates) {
        Objects.requireNonNull(updates);
        final ParkingStation parkingStationToUpdate = parkingStationService.find(id);
        if (!parkingStationToUpdate.getId().equals(updates.getId())) {
            throw new ValidationException("The parking station ID in the request does not match " +
                    "the parking station ID in the database.");
        }
        parkingStationToUpdate.setCapacity(updates.getCapacity());
        parkingStationService.update(parkingStationToUpdate);
        log.info("{} up to date.", parkingStationToUpdate);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeParkingStation(@PathVariable Integer id) {
        final ParkingStation parkingStationToRemove = parkingStationService.find(id);
        if (parkingStationToRemove == null) {
            return;
        }
        parkingStationService.remove(parkingStationToRemove);
        log.info("{} is successfully removed.", parkingStationToRemove);
    }
}
