package cz.cvut.bikesharingproject.rest.feedback;

import cz.cvut.bikesharingproject.exception.ValidationException;
import cz.cvut.bikesharingproject.model.Bike;
import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.model.feedback.DamagedBikeForm;
import cz.cvut.bikesharingproject.rest.AuthenticationController;
import cz.cvut.bikesharingproject.rest.utils.RestUtils;
import cz.cvut.bikesharingproject.service.BikeService;
import cz.cvut.bikesharingproject.service.UserService;
import cz.cvut.bikesharingproject.service.feedback.DamagedBikeFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/feedbacks/damaged-bikes")
public class DamagedBikeFormController {

    private final UserService userService;

    private final BikeService bikeService;

    private final DamagedBikeFormService damagedBikeFormService;

    private final AuthenticationController authController;

    @Autowired
    public DamagedBikeFormController(UserService userService,
                                     BikeService bikeService,
                                     DamagedBikeFormService damagedBikeFormService,
                                     AuthenticationController authController) {
        this.userService = userService;
        this.bikeService = bikeService;
        this.damagedBikeFormService = damagedBikeFormService;
        this.authController = authController;
    }

    @PostMapping(value = "/users/{userId}/bikes/{bikeId}/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addDamagedBikeForm(@PathVariable Integer userId,
                                                 @PathVariable Integer bikeId,
                                                 @RequestBody DamagedBikeForm damagedBikeForm) {
        final User user = userService.find(userId);
        final Bike bike = bikeService.find(bikeId);
        if (user == null || bike == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        damagedBikeFormService.createDamagedBikeForm(user, bike, damagedBikeForm);
        log.info("{} is successfully added.", damagedBikeForm);
        final HttpHeaders httpHeaders = RestUtils.createLocationHeaderFromCurrentUri(
                "/{id}", damagedBikeForm.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/handle")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleDamagedBikeForm(@PathVariable Integer id) {
        final User admin = authController.getCurrent();
        final DamagedBikeForm form = damagedBikeFormService.find(id);
        damagedBikeFormService.handle(admin, form);
        log.info("{} handling.", form);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/close", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeDamagedBikeForm(@PathVariable Integer id,
                                     @RequestBody DamagedBikeForm updates) {
        Objects.requireNonNull(updates);
        final DamagedBikeForm form = damagedBikeFormService.find(id);
        if (!form.getId().equals(updates.getId())) {
            throw new ValidationException("Damaged bike form ID in the request does not match " +
                    "damaged bike form ID in the database.");
        }
        damagedBikeFormService.close(form, updates);
        log.info("{} closed.", form);
    }
}
