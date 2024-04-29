package authservice.controller;

import authservice.common.CommonResponse;
import authservice.constant.MessageConstant;
import authservice.entity.dto.*;
import authservice.service.IdentityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import static authservice.constant.ApiEndpoint.*;


@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class IdentityController {

  private final IdentityService identityService;

  @PostMapping(REGISTER)
  public ResponseEntity<CommonResponse<Object>> addNewUser(@RequestBody RegisterRequest userDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REGISTER_SUCCESS)
            .data(identityService.register(userDto))
            .build());
  }

  @PostMapping(LOGIN)
  public ResponseEntity<CommonResponse<Object>> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.LOGIN_SUCCESS)
            .data(identityService.login(request))
            .build());
  }

  @PostMapping(VERIFY_EMAIL)
  public ResponseEntity<CommonResponse<Object>> verifyEmail(@RequestParam(value = "token") String token) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.VERIFY_SUCCESS)
            .data(identityService.processVerifyEmail(token))
            .build());
  }

  @PostMapping(REQUEST_VERIFY)
  public ResponseEntity<CommonResponse<Object>> requestVerifyAccount(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REQUEST_VERIFY_SUCCESS)
            .data(identityService.requestVerifyAccount(email))
            .build());
  }

  @PostMapping(FORGOT_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> requestForgotPassword(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REQUEST_RESET_PASSWORD_SUCCESS)
            .data(identityService.requestForgotPassword(email))
            .build());
  }

  @PostMapping(RESET_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> resetPassword(@RequestBody ResetPasswordRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.RESET_PASSWORD_SUCCESS)
            .data(identityService.resetPassword(request))
            .build());
  }

  @PostMapping(REFRESH_TOKEN)
  public ResponseEntity<CommonResponse<Object>> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
    try {
      return ResponseEntity.ok().body(CommonResponse.builder()
              .isSuccess(true).data(identityService.refreshToken(request)).build());
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
              .isSuccess(false).message(MessageConstant.REFRESH_TOKEN_FAIL).build());
    }
  }

  @PostMapping(LOGOUT)
  public ResponseEntity<CommonResponse<Object>> logout(HttpServletRequest request, HttpServletResponse response) {
    if (identityService.logout(request, response)) {
      return ResponseEntity.ok().body(CommonResponse.builder().isSuccess(true).message(MessageConstant.LOGOUT_SUCCESS).build());
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder().isSuccess(false).build());
    }
  }

  @GetMapping(USER_GET_ALL)
  public ResponseEntity<CommonResponse<Object>> getAllUser() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(identityService.getAllUser())
            .build());
  }

  @GetMapping(USER_GET)
  public ResponseEntity<CommonResponse<Object>> getUserByEmail(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(identityService.getUserByEmail(email))
            .build());
  }

  @GetMapping(GET_PROFILE)
  public ResponseEntity<CommonResponse<Object>> getProfile() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(identityService.getProfile())
            .build());
  }


  @PostMapping(UPDATE_USER)
  public ResponseEntity<CommonResponse<Object>> updateUser(@RequestBody UserDto userDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_USER_SUCCESS)
            .data(identityService.updateUser(userDto))
            .build());
  }

  @PostMapping(UPDATE_PHONE)
  public ResponseEntity<CommonResponse<Object>> addPhoneNumber(@PathVariable("phone") String phone) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_USER_SUCCESS)
            .data(identityService.addPhoneNumber(phone))
            .build());
  }

  @PostMapping(UPDATE_ADDRESS)
  public ResponseEntity<CommonResponse<Object>> addAddress(@PathVariable("address") String address) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_USER_SUCCESS)
            .data(identityService.addAddress(address))
            .build());
  }

  @PostMapping(USER_CHANGE_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CHANGE_PASSWORD_SUCCESS)
            .data(identityService.changePassword(changePasswordRequest))
            .build());
  }

  @DeleteMapping(DELETE_USER)
  public ResponseEntity<CommonResponse<Object>> deleteUser(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_USER_SUCCESS)
            .data(identityService.deleteUser(email))
            .build());
  }
  @GetMapping(COUNT_USER)
  public ResponseEntity<CommonResponse<Object>> getUserCount() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(identityService.getUserCount())
            .build());
  }

  @GetMapping("/get/current-user")
  public ResponseEntity<CommonResponse<Object>> getCurrentUser() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(identityService.getCurrentUser())
            .build());
  }
}
