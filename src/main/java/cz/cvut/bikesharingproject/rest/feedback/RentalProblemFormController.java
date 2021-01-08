package cz.cvut.bikesharingproject.rest.feedback;

import cz.cvut.bikesharingproject.exception.ValidationException;
import cz.cvut.bikesharingproject.model.feedback.RentalProblemForm;
import cz.cvut.bikesharingproject.model.Trip;
import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.rest.AuthenticationController;
import cz.cvut.bikesharingproject.rest.utils.RestUtils;
import cz.cvut.bikesharingproject.service.feedback.RentalProblemFormService;
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

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/feedbacks/rental-problems")
public class RentalProblemFormController {

    private UserService userService;

    private final TripService tripService;

    private final RentalProblemFormService rentalProblemService;

    private final AuthenticationController authController;

    @Autowired
    public RentalProblemFormController(UserService userService,
                                       TripService tripService,
                                       RentalProblemFormService rentalProblemService,
                                       AuthenticationController authController) {
        this.userService = userService;
        this.tripService = tripService;
        this.rentalProblemService = rentalProblemService;
        this.authController = authController;
    }

    @PostMapping(value = "/users/{userId}/trips/{tripId}/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addRentalProblemForm(@PathVariable Integer userId,
                                                 @PathVariable Integer tripId,
                                                 @RequestBody RentalProblemForm rentalProblem) {
        final User user = userService.find(userId);
        final Trip trip = tripService.find(tripId);
        if (user == null || trip == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!trip.getUser().getId().equals(userId)) {
            throw new ValidationException("The user with ID: " + userId + " is not " +
                    "the owner of the trip with ID: " + tripId + " .");
        }
        rentalProblemService.createRentalProblemForm(user, trip, rentalProblem);
        log.info("{} is successfully added.", rentalProblem);
        final HttpHeaders httpHeaders = RestUtils.createLocationHeaderFromCurrentUri(
                "/{id}", rentalProblem.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/handle")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleRentalProblemForm(@PathVariable Integer id) {
        final User admin = authController.getCurrent();
        final RentalProblemForm form = rentalProblemService.find(id);
        rentalProblemService.handle(admin, form);
        log.info("{} handling.", form);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/close", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeRentalProblemForm(@PathVariable Integer id,
                                   @RequestBody RentalProblemForm updates) {
        Objects.requireNonNull(updates);
        final RentalProblemForm form = rentalProblemService.find(id);
        if (!form.getId().equals(updates.getId())) {
            throw new ValidationException("Rental problem form ID in the request does not match " +
                    "rental problem form ID in the database.");
        }
        rentalProblemService.close(form, updates);
        log.info("{} closed.", form);
    }
}
