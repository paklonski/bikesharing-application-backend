package cz.cvut.bikesharingproject.rest;

import cz.cvut.bikesharingproject.exception.NotFoundException;
import cz.cvut.bikesharingproject.exception.ValidationException;
import cz.cvut.bikesharingproject.model.Bike;
import cz.cvut.bikesharingproject.model.ParkingStation;
import cz.cvut.bikesharingproject.rest.utils.RestUtils;
import cz.cvut.bikesharingproject.service.BikeService;
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
@RequestMapping("/api/v1/bikes")
public class BikeController {

    private final BikeService bikeService;

    @Autowired
    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addBike(@RequestBody Bike bike) {
        bikeService.persist(bike);
        log.info("{} is successfully added.", bike);
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bike> getAllBikes() {
        return bikeService.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/rent", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bike> getRentBikes() {
        return bikeService.findRent();
    }

    @GetMapping(value = "/free", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bike> getFreeBikes() {
        return bikeService.findFree();
    }

    @GetMapping(value = "/{bikeId}/station", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParkingStation getLocation(@PathVariable Integer bikeId) {
        final Bike bike = bikeService.find(bikeId);
        return bikeService.getLocation(bike);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBike(@PathVariable Integer id, @RequestBody Bike bike) {
        Objects.requireNonNull(bike);
        final Bike bikeToUpdate = bikeService.find(id);
        if (!bikeToUpdate.getId().equals(bike.getId())) {
            throw new ValidationException("The bike ID in the request does not match " +
                    "the bike ID in the database.");
        }
        bikeService.update(bike);
        log.info("{} up to date.", bike);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/update-price", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBikePrice(@PathVariable Integer id, @RequestBody Bike updates) {
        Objects.requireNonNull(updates);
        final Bike bikeToUpdate = bikeService.find(id);
        if (!bikeToUpdate.getId().equals(updates.getId())) {
            throw new ValidationException("The bike ID in the request does not match " +
                    "the bike ID in the database.");
        }
        bikeToUpdate.setPricePerMinute(updates.getPricePerMinute());
        bikeService.update(bikeToUpdate);
        log.info("{} up to date.", bikeToUpdate);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBike(@PathVariable Integer id) {
        final Bike bikeToRemove = bikeService.find(id);
        if (bikeToRemove == null) {
            return;
        }
        bikeService.remove(bikeToRemove);
        log.info("{} is successfully removed.", bikeToRemove);
    }
}
