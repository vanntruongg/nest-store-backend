package vantruong.nststore.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import vantruong.nststore.util.JwtUtil;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

  private final RouteValidator validator;
  private final JwtUtil jwtUtil;

  public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil, WebClient.Builder webClientBuilder) {
    super(Config.class);
    this.validator = validator;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public GatewayFilter apply(AuthenticationFilter.Config config) {
    return ((exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      if (validator.isSecured.test(request)) {
        if (isAuthMissing(request)) {
          return onError(exchange, "Authorization header is missing in request");
        }

        if (!jwtUtil.hasRole(exchange.getRequest(), config.getRoles())) {
          var response = exchange.getResponse();
          response.setStatusCode(HttpStatus.UNAUTHORIZED);
          return response.setComplete();
        }
        String jwt = jwtUtil.parseJwt(request);
        if (!jwtUtil.validateToken(jwt)) {
          return onError(exchange, "Authorization header is invalid");
        }
      }
      return chain.filter(exchange);
    });
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    return response.setComplete();
  }

  private boolean isAuthMissing(ServerHttpRequest request) {
    return !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
  }

  @Data
  public static class Config {
    private List<String> roles;
  }

  @Override
  public List<String> shortcutFieldOrder() {
    return List.of("roles");
  }
}
