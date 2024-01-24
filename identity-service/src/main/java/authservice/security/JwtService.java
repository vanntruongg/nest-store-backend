package authservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtService {

  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expire-time-access-token}")
  private long expireTimeAccessToken;
  @Value("${jwt.expire-time-refresh-token}")
  private long expireTimeRefreshToken;

  private Key getSignKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String generateAccessToken(UserDetailsImpl userDetails) {
    List<String> roles = userDetails.getUser().getRoles();
    return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("email", userDetails.getUsername())
            .claim("roles", roles)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expireTimeAccessToken))
            .signWith(getSignKey(), SignatureAlgorithm.HS512)
            .compact();
  }

  public String generateRefreshToken(UserDetailsImpl userDetails) {
    return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expireTimeRefreshToken))
            .signWith(getSignKey(), SignatureAlgorithm.HS512)
            .compact();
  }

  public String getEmailFromToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token: {}", ex.getMessage());
    } catch (ExpiredJwtException ex) {
      log.error("JWT is expired: {}", ex.getMessage());
    } catch (UnsupportedJwtException ex) {
      log.error("JWT is unsupported: {}", ex.getMessage());
    } catch (IllegalThreadStateException ex) {
      log.error("JWT claims string is empty: {}", ex.getMessage());
    }
    return false;
  }
}
