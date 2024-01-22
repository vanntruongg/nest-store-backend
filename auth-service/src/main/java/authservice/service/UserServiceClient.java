package authservice.service;

import authservice.entity.dto.UserCredentialDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:9002")
public interface UserServiceClient {
  @PostMapping("/user/create")
  void createUser(@RequestBody UserCredentialDto userCredentialDto);
}
