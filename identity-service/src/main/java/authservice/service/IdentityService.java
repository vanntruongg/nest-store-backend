package authservice.service;

import authservice.entity.User;
import authservice.entity.dto.RefreshTokenRequest;
import authservice.entity.dto.UserCredentialDto;
import authservice.entity.dto.LoginRequest;
import authservice.entity.dto.LoginResponse;

public interface IdentityService {

  Boolean register(UserCredentialDto userDto);

  LoginResponse login(LoginRequest request);

  void sendVerificationEmail(User user, String token);

  Boolean processVerifyEmail(String token);

  LoginResponse refreshToken(RefreshTokenRequest request);
}
