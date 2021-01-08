package cz.cvut.bikesharingproject.rest.feedback;

import cz.cvut.bikesharingproject.exception.ValidationException;
import cz.cvut.bikesharingproject.model.*;
import cz.cvut.bikesharingproject.model.feedback.CustomerFeedbackForm;
import cz.cvut.bikesharingproject.rest.AuthenticationController;
import cz.cvut.bikesharingproject.rest.utils.RestUtils;
import cz.cvut.bikesharingproject.service.feedback.CustomerFeedbackFormService;
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
@RequestMapping("/api/v1/feedbacks")
public class CustomerFeedbackFormController {

    private final UserService userService;

    private final CustomerFeedbackFormService customerFeedbackFormService;

    private final AuthenticationController authController;

    @Autowired
    public CustomerFeedbackFormController(UserService userService,
                                          CustomerFeedbackFormService customerFeedbackFormService,
                                          AuthenticationController authController) {
        this.userService = userService;
        this.customerFeedbackFormService = customerFeedbackFormService;
        this.authController = authController;
    }

    @PostMapping(value = "/users/{userId}/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addFeedback(@PathVariable Integer userId,
                                            @RequestBody CustomerFeedbackForm feedback) {
        final User user = userService.find(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerFeedbackFormService.add(user, feedback);
        log.info("{} is successfully added.", feedback);
        final HttpHeaders httpHeaders = RestUtils.createLocationHeaderFromCurrentUri(
                "/{id}", feedback.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerFeedbackForm> getForms() {
        return customerFeedbackFormService.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/open", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerFeedbackForm> getOpenForms() {
        return customerFeedbackFormService.findOpen();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/close", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeFeedback(@PathVariable Integer id,
                               @RequestBody CustomerFeedbackForm updates) {
        Objects.requireNonNull(updates);
        final User admin = authController.getCurrent();
        final CustomerFeedbackForm feedback = customerFeedbackFormService.find(id);
        if (!feedback.getId().equals(updates.getId())) {
            throw new ValidationException("Feedback ID in the request does not match " +
                    "feedback ID in the database.");
        }
        customerFeedbackFormService.close(admin, feedback, updates);
        log.info("{} closed.", feedback);
    }
}
