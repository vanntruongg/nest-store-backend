package authservice.service.Impl;

import authservice.common.Utils;
import authservice.constant.MessageConstant;
import authservice.constant.RoleConstant;
import authservice.entity.Token;
import authservice.entity.User;
import authservice.entity.dto.*;
import authservice.enums.AccountStatus;
import authservice.enums.TokenType;
import authservice.exception.DuplicationException;
import authservice.exception.ErrorCode;
import authservice.exception.ExpiredException;
import authservice.exception.NotFoundException;
import authservice.feign.MailClient;
import authservice.repository.UserCredentialRepository;
import authservice.security.JwtService;
import authservice.security.UserDetailsImpl;
import authservice.service.IdentityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private final MailClient mailClient;

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

      SendMailVerifyUserRequest request = SendMailVerifyUserRequest.builder()
              .email(newUser.getEmail())
              .name(newUser.getFirstName())
              .token(tokenVerifyUser)
              .build();

      mailClient.senMailVerifyUser(request);
      return true;
    } catch (DuplicationException ex) {
      log.error("Error during user registration: {}", ex.getMessage(), ex);
      throw ex;
    }
  }

  @Override
  public Boolean verifyEmail(VerifyEmailRequest request) {
    User user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.USER_NOT_FOUND));

    List<Token> tokens = user.getTokens();
    Iterator<Token> iterator = tokens.iterator();

    boolean tokenFound = false;
    while (iterator.hasNext()) {
      Token token = iterator.next();
      if (TokenType.VERIFICATION.getTokenType().equals(token.getTokenType())) {
        if (LocalDateTime.now().isBefore(token.getExpiredDate())) {
          user.setVerify(true);
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

}
