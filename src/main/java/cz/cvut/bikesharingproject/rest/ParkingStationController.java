package cz.cvut.bikesharingproject.rest;

import cz.cvut.bikesharingproject.exception.NotFoundException;
import cz.cvut.bikesharingproject.exception.ValidationException;
import cz.cvut.bikesharingproject.model.ParkingStation;
import cz.cvut.bikesharingproject.model.enums.CityDistrict;
import cz.cvut.bikesharingproject.rest.utils.RestUtils;
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
import java.util.Objects;

@RestController
@RequestMapping("rest/station")
public class ParkingStationController {

    private static final Logger LOG = LoggerFactory.getLogger(ParkingStationController.class);

    private ParkingStationService parkingStationService;

    @Autowired
    public ParkingStationController(ParkingStationService parkingStationService) {
        this.parkingStationService = parkingStationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addParkingStation(@RequestBody ParkingStation parkingStation) {
        Objects.requireNonNull(parkingStation);
        parkingStationService.persist(parkingStation);
        LOG.debug("The parking station {} is successfully added.", parkingStation);
        final HttpHeaders httpHeaders = RestUtils.createLocationHeaderFromCurrentUri(
                "/{id}", parkingStation.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParkingStation getParkingStation(@PathVariable Integer id) {
        Objects.requireNonNull(id);
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

    @GetMapping(value = "/location", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParkingStation> getAllByLocation(@RequestBody CityDistrict cityDistrict) {
        Objects.requireNonNull(cityDistrict);
        return parkingStationService.findAllByLocation(cityDistrict);
    }

    @GetMapping(value = "/free", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParkingStation> getAllWithFreeBikes() {
        return parkingStationService.findAllWithFreeBikes();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateParkingStationData(@PathVariable Integer id, @RequestBody ParkingStation parkingStation) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(parkingStation);
        final ParkingStation parkingStationToUpdate = parkingStationService.find(id);
        if (!parkingStationToUpdate.getId().equals(parkingStation.getId())) {
            throw new ValidationException("The parking station ID in the request does not match " +
                    "the parking station ID in the database.");
        }
        parkingStationService.update(parkingStation);
        LOG.debug("The parking station {} up to date.", parkingStation);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeParkingStation(@PathVariable Integer id) {
        final ParkingStation parkingStationToRemove = parkingStationService.find(id);
        if (parkingStationToRemove == null) {
            return;
        }
        parkingStationService.remove(parkingStationToRemove);
        LOG.debug("The parking station {} is successfully removed.", parkingStationToRemove);
    }
}
