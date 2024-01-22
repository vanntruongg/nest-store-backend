package authservice.controller;

import authservice.entity.dto.UserCredentialDto;
import authservice.common.CommonResponse;
import authservice.constant.MessageConstant;
import authservice.entity.dto.LoginRequest;
import authservice.entity.dto.VerifyEmailRequest;
import authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static authservice.constant.ApiEndpoint.*;


@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping(REGISTER)
  public ResponseEntity<CommonResponse<Object>> addNewUser(@RequestBody UserCredentialDto userDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.LOGIN_SUCCESS)
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

  @PostMapping("/verify-email")
  public ResponseEntity<CommonResponse<Object>> verifyEmail(@RequestBody VerifyEmailRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.LOGIN_SUCCESS)
            .data(authService.verifyEmail(request))
            .build());
  }

}
