package cz.cvut.bikesharingproject.rest;

import cz.cvut.bikesharingproject.dto.AuthenticationRequestDto;
import cz.cvut.bikesharingproject.dto.AuthenticationResponseDto;
import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.security.jwt.JwtTokenProvider;
import cz.cvut.bikesharingproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            String password = requestDto.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User with username " + username + " not found.");
            }
            String token = jwtTokenProvider.createToken(username, user.getRole());
            AuthenticationResponseDto responseDto = new AuthenticationResponseDto();
            responseDto.setUsername(username);
            responseDto.setToken(token);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password.");
        }
    }

    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getCurrent() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").split(" ")[1];
        return userService.findByUsername(jwtTokenProvider.getUsername(token));
    }
}

























