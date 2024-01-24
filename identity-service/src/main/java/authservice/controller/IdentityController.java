package authservice.controller;

import authservice.entity.dto.RefreshTokenRequest;
import authservice.entity.dto.UserCredentialDto;
import authservice.common.CommonResponse;
import authservice.constant.MessageConstant;
import authservice.entity.dto.LoginRequest;
import authservice.service.IdentityService;
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

  private final IdentityService authService;

  @PostMapping(REGISTER)
  public ResponseEntity<CommonResponse<Object>> addNewUser(@RequestBody UserCredentialDto userDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REGISTER_SUCCESS)
            .data(authService.register(userDto))
            .build());
  }

  @PostMapping(LOGIN)
  public ResponseEntity<CommonResponse<Object>> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.LOGIN_SUCCESS)
            .data(authService.login(request))
            .build());
  }

  @PostMapping(VERIFY_EMAIL)
  public ResponseEntity<CommonResponse<Object>> verifyEmail(@RequestParam(value = "token") String token) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.LOGIN_SUCCESS)
            .data(authService.processVerifyEmail(token))
            .build());
  }

  @PostMapping(REFRESH_TOKEN)
  public ResponseEntity<CommonResponse<Object>> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
    try {
      return ResponseEntity.ok().body(CommonResponse.builder()
              .isSuccess(true).data(authService.refreshToken(request)).build());
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
              .isSuccess(false).message(MessageConstant.REFRESH_TOKEN_FAIL).build());
    }
  }

}
