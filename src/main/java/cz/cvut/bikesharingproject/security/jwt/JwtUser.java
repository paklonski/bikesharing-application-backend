//package cz.cvut.bikesharingproject.security.jwt;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import cz.cvut.bikesharingproject.model.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.*;
//
//public class JwtUser implements UserDetails {
//
//    private User user;
//
//    private final Collection<? extends GrantedAuthority> authorities;
//
//    public JwtUser(User user, Set<GrantedAuthority> authorities) {
//        Objects.requireNonNull(user);
//        Objects.requireNonNull(authorities);
//        this.user = user;
//        this.authorities = authorities;
////        this.authorities = new HashSet<>();
////        this.authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
////        this.authorities.addAll(authorities);
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.unmodifiableCollection(authorities);
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return user.isEnabled();
//    }
//}
