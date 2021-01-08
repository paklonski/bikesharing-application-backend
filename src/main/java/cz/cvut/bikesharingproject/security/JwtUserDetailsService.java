package cz.cvut.bikesharingproject.security;

import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.security.jwt.JwtUser;
import cz.cvut.bikesharingproject.security.jwt.JwtUserFactory;
import cz.cvut.bikesharingproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found.");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("User with username '{}' successfully loaded.", username);
        return jwtUser;
    }
}
