//package cz.cvut.bikesharingproject.security.jwt;
//
//import cz.cvut.bikesharingproject.model.enums.Role;
//import cz.cvut.bikesharingproject.security.JwtUserDetailsService;
//import io.jsonwebtoken.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Base64;
//import java.util.Date;
//
//@Slf4j
//@Component
//@PropertySource("classpath:jwt.properties")
//public class JwtTokenProvider {
//
//    @Value("${jwt.token.secret}")
//    private String SECRET_KEY;
//
//    @Value("${jwt.token.expired}")
//    private long validityInMilliseconds;
//
//    private JwtUserDetailsService jwtUserDetailsService;
//
//    @Autowired
//    public void setJwtUserDetailsService(JwtUserDetailsService jwtUserDetailsService) {
//        this.jwtUserDetailsService = jwtUserDetailsService;
//    }
//
//    @PostConstruct
//    protected void init() {
//        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
//    }
//
//    public String createToken(String username, Role role) {
//        Claims claims = Jwts.claims().setSubject(username);
//        claims.put("role", role.toString());
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + validityInMilliseconds);
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(validity)
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//
//    public String resolveToken(HttpServletRequest req) {
//        String bearerToken = req.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (JwtException | IllegalArgumentException e) {
//            throw new JwtAuthenticationException("JWT token is expired or invalid.");
//        }
//    }
//
//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(getUsername(token));
//        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
//    }
//
//    public String getUsername(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
