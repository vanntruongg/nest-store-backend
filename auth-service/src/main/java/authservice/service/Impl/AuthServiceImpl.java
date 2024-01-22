package authservice.service.Impl;

import authservice.constant.MessageConstant;
import authservice.constant.RoleConstant;
import authservice.entity.Role;
import authservice.entity.UserCredential;
import authservice.entity.dto.LoginRequest;
import authservice.entity.dto.LoginResponse;
import authservice.entity.dto.UserCredentialDto;
import authservice.entity.dto.VerifyEmailRequest;
import authservice.enums.AccountStatus;
import authservice.exception.DuplicationException;
import authservice.exception.ErrorCode;
import authservice.repository.UserCredentialRepository;
import authservice.security.JwtService;
import authservice.security.UserDetailsImpl;
import authservice.service.AuthService;
import authservice.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
  private final UserCredentialRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserServiceClient userServiceClient;
  @Override
  public LoginResponse login(LoginRequest request) {
    var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String accessToken = jwtService.generateAccessToken(userDetails);
    String refreshToken = jwtService.generateRefreshToken(userDetails);
    return LoginResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }

  @Override
  @Transactional
  public Boolean register(UserCredentialDto userDto) {
    try {
      if(repository.existsByEmail(userDto.getEmail())) {
        throw new DuplicationException(ErrorCode.NOT_FOUND, MessageConstant.EMAIL_EXISTED);
      }

      UserCredential newUser = UserCredential.builder()
              .email(userDto.getEmail())
              .password(passwordEncoder.encode(userDto.getPassword()))
              .roles(List.of(new Role(RoleConstant.ROLE_USER)))
              .isVerify(false)
              .status(AccountStatus.PENDING_VERIFICATION)
              .build();

      repository.save(newUser);
      userServiceClient.createUser(userDto);
      return true;
    } catch (DuplicationException ex) {
      log.error("Error during user registration: {}", ex.getMessage(), ex);
      throw ex;
    }
  }

  @Override
  public Boolean verifyEmail(VerifyEmailRequest request) {
    return null;
  }

}
