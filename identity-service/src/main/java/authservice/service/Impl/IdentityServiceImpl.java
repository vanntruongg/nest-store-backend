package authservice.service.Impl;

import authservice.common.Utils;
import authservice.constant.CommonConstant;
import authservice.constant.MessageConstant;
import authservice.constant.RoleConstant;
import authservice.entity.Token;
import authservice.entity.User;
import authservice.entity.dto.*;
import authservice.enums.AccountStatus;
import authservice.enums.TokenType;
import authservice.enums.UserRole;
import authservice.exception.*;
import authservice.repository.UserCredentialRepository;
import authservice.security.JwtService;
import authservice.security.SecurityContextHelper;
import authservice.security.UserDetailsImpl;
import authservice.security.UserDetailsServiceImpl;
import authservice.service.IdentityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class IdentityServiceImpl implements IdentityService {
  private final UserCredentialRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;
  private final SecurityContextHelper securityContextHelper;
  //  private final MailClient mailClient;
  private final RestTemplate restTemplate;


  @Override
  public LoginResponse login(LoginRequest request) {
    var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    // check if user has verified the account
    if (!userPrincipal.isVerified()) {
      throw new UnVerifiedAccountException(ErrorCode.DENIED, MessageConstant.UNVERIFIED_ACCOUNT);
    }

    String accessToken = jwtService.generateAccessToken(userPrincipal);
    String refreshToken = jwtService.generateRefreshToken(userPrincipal);
    return LoginResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }

  @Override
  @Transactional
  public Boolean register(RegisterRequest userDto) {
    try {
      if (repository.existsByEmail(userDto.getEmail())) {
        throw new DuplicationException(ErrorCode.NOT_NULL, MessageConstant.EMAIL_EXISTED);
      }

      Token tokenVerifyUser = Utils.generateTokenVerify();

      User newUser = User.builder()
              .email(userDto.getEmail())
              .firstName(userDto.getFirstName())
              .lastName(userDto.getLastName())
              .password(passwordEncoder.encode(userDto.getPassword()))
              .roles(List.of(RoleConstant.ROLE_USER))
              .isVerify(false)
              .status(AccountStatus.PENDING_VERIFICATION)
              .tokens(List.of(tokenVerifyUser))
              .build();

      repository.save(newUser);
      sendVerificationEmail(newUser, tokenVerifyUser.getTokenValue());
      return true;
    } catch (DuplicationException ex) {
      log.error("Error during user registration: {}", ex.getMessage(), ex);
      throw ex;
    }
  }

  @Override
  public void sendVerificationEmail(User user, String token) {
    SendMailVerifyUserRequest request = SendMailVerifyUserRequest.builder()
            .email(user.getEmail())
            .name(user.getFirstName())
            .token(token)
            .build();
    restTemplate.postForLocation(CommonConstant.EMAIL_URL + "/mail/verify", request);
  }

  @Override
  public Boolean processVerifyEmail(String tokenValue) {
    User user = repository.findByTokens_TokenValue(tokenValue)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.USER_NOT_FOUND));

    List<Token> tokens = user.getTokens();
    Iterator<Token> iterator = tokens.iterator();

    boolean tokenFound = false;
    while (iterator.hasNext()) {
      Token token = iterator.next();
      if (TokenType.VERIFICATION.getTokenType().equals(token.getTokenType())) {
        if (LocalDateTime.now().isBefore(token.getExpiredDate())) {
          user.setVerify(true);
          user.setStatus(AccountStatus.ACTIVE);
          iterator.remove();
          tokenFound = true;
        } else {
          iterator.remove();
          throw new ExpiredException(ErrorCode.EXPIRED, MessageConstant.EXPIRED_TOKEN_VERIFICATION);
        }
      }
    }

    if (!tokenFound) {
      throw new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.INVALID_TOKEN_VERIFICATION);
    }

    user.setTokens(tokens);
    repository.save(user);
    return true;
  }

  @Override
  public LoginResponse refreshToken(RefreshTokenRequest request) {
    String token = request.getRefreshToken();

    if (!jwtService.validateToken(token)) {
      throw new BadCredentialsException("Invalid refresh token!");
    }
    String email = jwtService.getEmailFromToken(token);
    UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);

    String accessToken = jwtService.generateAccessToken(userDetails);

    return LoginResponse.builder()
            .accessToken(accessToken)
            .build();
  }

  @Override
  public List<User> getAllUser() {
    return repository.findAll();
  }

  @Override
  public User getUserByEmail(String email) {
    return repository.findByEmail(email).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.USER_NOT_FOUND));
  }

  @Override
  @Transactional
  public Boolean updateUser(UserDto userDto) {
    List<String> accountRoles = securityContextHelper.getRoles();

    User user = getUserByEmail(userDto.getEmail());

    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setAddress(userDto.getAddress());
    user.setPhone(userDto.getPhone());
    user.setImageUrl(userDto.getImageUrl());

    // if list role not empty and account update is admin
    if(!userDto.getRoles().isEmpty() && accountRoles.contains(UserRole.ADMIN.getRole())) {
      user.setRoles(userDto.getRoles());
    }
    repository.save(user);
    return true;
  }

  @Override
  @Transactional
  public Boolean changePassword(ChangePasswordRequest changePasswordRequest) {
    User user = getUserByEmail(changePasswordRequest.getEmail());

    if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
      user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
      repository.save(user);
      return true;
    } else {
      throw new OldPasswordNotMatches(ErrorCode.DENIED, MessageConstant.OLD_PASSWORD_NOT_MATCHES);
    }
  }

  @Override
  public Boolean deleteUser(String email) {
    User user = getUserByEmail(email);
    user.setStatus(AccountStatus.DELETED);
    repository.save(user);
    return true;
  }

  @Override
  public User getProfile() {
    String email = securityContextHelper.getUserId();
    return getUserByEmail(email);
  }

  @Override
  public Boolean addPhoneNumber(String phone) {
    String email = securityContextHelper.getUserId();
    User user = getUserByEmail(email);
    user.setPhone(phone);
    repository.save(user);
    return true;
  }

  @Override
  public Boolean addAddress(String address) {
    String email = securityContextHelper.getUserId();
    User user = getUserByEmail(email);
    user.setAddress(address);
    repository.save(user);
    return true;
  }

  @Override
  public Boolean logout(HttpServletRequest request, HttpServletResponse response) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      return false;
    }

    UserDetailsImpl userDetails = null;
    if (auth instanceof UsernamePasswordAuthenticationToken) {
      userDetails = (UserDetailsImpl) auth.getPrincipal();
    }

    if (userDetails != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
      SecurityContextHolder.getContext().setAuthentication(null);
      auth.setAuthenticated(false);
      return true;
    }
    return false;
  }

}
