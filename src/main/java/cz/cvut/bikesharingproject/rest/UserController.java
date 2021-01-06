package cz.cvut.bikesharingproject.rest;

import cz.cvut.bikesharingproject.exception.NotFoundException;
import cz.cvut.bikesharingproject.exception.ValidationException;
import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.rest.utils.RestUtils;
import cz.cvut.bikesharingproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody User user) {
        userService.persist(user);
        log.info("{} is successfully registered.", user);
        final HttpHeaders httpHeaders = RestUtils.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Integer id) {
        final User user = userService.find(id);
        if (user == null) {
            throw NotFoundException.create("User", id);
        }
        return user;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable Integer id, @RequestBody User user) {
        Objects.requireNonNull(id);
        final User userToUpdate = userService.find(id);
        if (!userToUpdate.getId().equals(user.getId())) {
            throw new ValidationException("The user ID in the request does not match the user ID in the database.");
        }
        userService.update(user);
        log.info("{} up to date.", user);
    }

    @PatchMapping(value = "/{id}/update-phone-number", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserPhoneNumber(@PathVariable Integer id, @RequestBody User partialUpdate) {
        Objects.requireNonNull(partialUpdate);
        final User userToUpdate = userService.find(id);
        if (!userToUpdate.getId().equals(partialUpdate.getId())) {
            throw new ValidationException("The user ID in the request does not match the user ID in the database.");
        }
        userToUpdate.setPhoneNumber(partialUpdate.getPhoneNumber());
        userService.update(userToUpdate);
        log.info("{} up to date.", userToUpdate);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable Integer id) {
        final User userToRemove = userService.find(id);
        if (userToRemove == null) {
            return;
        }
        userService.remove(userToRemove);
        log.info("{} is successfully removed.", userToRemove);
    }
}
