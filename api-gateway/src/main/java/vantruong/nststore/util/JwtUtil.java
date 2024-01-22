package vantruong.nststore.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.security.Key;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;


  @Value("${jwt.prefix}")
  private String jwtPrefix;


  private Key getSignKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String extractEmailFromToken(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    return claims.get("email", String.class);
  }
  public List<String> extractRolesFromToken(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    List<?> roles = claims.get("roles", List.class);

    // if list role not null or not empty, convert to string and return
    if (roles != null && !roles.isEmpty()) {
      return roles.stream().map(Object::toString).collect(Collectors.toList());
    }

    return Collections.emptyList();
  }


  public Claims getAllClaimsFromToken(String token) {
    return  Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
  }

  public boolean hasRole(ServerHttpRequest request, List<String> requiredRoles) {
    if (requiredRoles == null) return true;
    String jwt = parseJwt(request);
    List<String> rolesFromToken = extractRolesFromToken(jwt);
    return rolesFromToken.stream()
            .anyMatch(requiredRoles::contains);
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

  public String parseJwt(ServerHttpRequest request) {
    String authHeaders = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
    if (StringUtils.hasText(authHeaders) && authHeaders.startsWith(jwtPrefix)) {
      return authHeaders.substring(jwtPrefix.length());
    }
    return null;
  }
}
