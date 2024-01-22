package authservice.service;

import authservice.entity.dto.UserCredentialDto;
import authservice.entity.dto.VerifyEmailRequest;
import authservice.entity.dto.LoginRequest;
import authservice.entity.dto.LoginResponse;

public interface AuthService {

  Boolean register(UserCredentialDto userDto);

  LoginResponse login(LoginRequest request);

  Boolean verifyEmail(VerifyEmailRequest request);
}
