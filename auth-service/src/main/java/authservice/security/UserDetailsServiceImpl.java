package authservice.security;

import authservice.entity.UserCredential;
import authservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserCredentialRepository repository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserCredential user = repository.findByEmail(username).orElseThrow(() ->
            new UsernameNotFoundException("User credential not found with email: " + username));

    return new UserDetailsImpl(user);
  }
}
