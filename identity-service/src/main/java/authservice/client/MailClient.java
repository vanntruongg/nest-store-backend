package authservice.client;

import authservice.constant.CommonConstant;
import authservice.entity.User;
import authservice.entity.dto.SendMailVerifyUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MailClient {
  private final RestTemplate restTemplate;
  public void sendVerificationEmail(User user, String token) {
    SendMailVerifyUserRequest request = SendMailVerifyUserRequest.builder()
            .email(user.getEmail())
            .name(user.getFirstName())
            .token(token)
            .build();
    restTemplate.postForLocation(CommonConstant.EMAIL_URL + "/mail/verify", request);
  }

  public void sendForgotPassword(User user, String token) {
    SendMailVerifyUserRequest request = SendMailVerifyUserRequest.builder()
            .email(user.getEmail())
            .name(user.getFirstName())
            .token(token)
            .build();
    restTemplate.postForLocation(CommonConstant.EMAIL_URL + "/mail/forgot-password", request);
  }
}