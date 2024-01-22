package vantruong.nststore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
    return httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable).build();
  }

}
