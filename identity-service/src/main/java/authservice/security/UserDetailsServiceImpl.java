package authservice.security;

import authservice.entity.User;
import authservice.exception.ErrorCode;
import authservice.exception.FormException;
import authservice.exception.NotFoundException;
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
  public UserDetails loadUserByUsername(String username) {
    User user = repository.findByEmail(username).orElseThrow(() ->
            new FormException(ErrorCode.FORM_ERROR, "Tài khoản không tồn tại.", new Throwable("email"))
    );

    return new UserDetailsImpl(user);
  }
}
