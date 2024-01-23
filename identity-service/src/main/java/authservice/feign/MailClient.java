package authservice.feign;

import authservice.entity.dto.SendMailVerifyUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MAIL-SERVICE", url = "http://localhost:9002")
public interface MailClient {
  @PostMapping("/verify")
  void senMailVerifyUser(@RequestBody SendMailVerifyUserRequest request);
}
