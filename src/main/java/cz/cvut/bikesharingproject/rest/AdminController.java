//package cz.cvut.bikesharingproject.rest;
//
//import cz.cvut.bikesharingproject.model.User;
//import cz.cvut.bikesharingproject.rest.utils.RestUtils;
//import cz.cvut.bikesharingproject.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping(value = "/api/v1/admin")
//public class AdminController {
//
//    private final UserService userService;
//
//    @Autowired
//    public AdminController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> register(@RequestBody User user) {
//        System.out.println("jjj");
//        userService.persist(user);
//        log.info("User {} is successfully registered.", user);
//        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }
//}
