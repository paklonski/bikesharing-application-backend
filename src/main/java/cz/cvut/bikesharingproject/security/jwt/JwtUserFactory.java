//package cz.cvut.bikesharingproject.security.jwt;
//
//import cz.cvut.bikesharingproject.model.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class JwtUserFactory {
//
//    public static JwtUser create(User user) {
//        Set<GrantedAuthority> roles = new HashSet<>();
//        roles.add(new SimpleGrantedAuthority(user.getRole().toString()));
//        return new JwtUser(user, roles);
//    }
//}
