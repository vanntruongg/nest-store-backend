package nststore.userservice.user;

import lombok.RequiredArgsConstructor;
import nststore.userservice.common.CommonResponse;
import nststore.userservice.constant.MessageConstant;
import nststore.userservice.dto.UserCredentialDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nststore.userservice.constant.ApiEndpoint.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping(USER_CREATE)
  public void createUser(@RequestBody UserCredentialDto userCredentialDto) {
    userService.createUser(userCredentialDto);
  }

  @GetMapping(USER_GET_ALL)
  public ResponseEntity<CommonResponse<Object>> getAllUser() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_ALL_USER_SUCCESS)
            .data(userService.findAll())
            .build());
  }

  @GetMapping(USER_GET_BY_EMAIL)
  public ResponseEntity<CommonResponse<Object>> getUserByEmail(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_ALL_USER_SUCCESS)
            .data(userService.findByEmail(email))
            .build());
  }
}
