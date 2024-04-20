package authservice.service;

import authservice.entity.User;
import authservice.entity.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface IdentityService {

  Boolean register(RegisterRequest userDto);

  LoginResponse login(LoginRequest request);

  void sendVerificationEmail(User user, String token);
  void sendForgotPassword(User user, String token);

  Boolean processVerifyEmail(String token);

  LoginResponse refreshToken(RefreshTokenRequest request);

  List<User> getAllUser();

  User getUserByEmail(String email);

  User updateUser(UserDto userDto);

  Boolean changePassword(ChangePasswordRequest changePasswordRequest);

  Boolean deleteUser(String email);

  User getProfile();

  Boolean addPhoneNumber(String phone);

  Boolean addAddress(String address);

  Boolean logout(HttpServletRequest request, HttpServletResponse response);

  Long getUserCount();

  Boolean requestVerifyAccount(String email);

  Boolean requestForgotPassword(String email);

  Boolean resetPassword(ResetPasswordRequest request);
}
