package mailservice.controller;

import lombok.RequiredArgsConstructor;
import mailservice.dto.OrderDto;
import mailservice.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mailservice.dto.SendMailVerifyUserRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("mail")
public class MailController {
  private final MailService mailService;

  @PostMapping("/verify")
  public void sendVerificationEmail(@RequestBody SendMailVerifyUserRequest request) {
    mailService.sendVerificationEmail(request);
  }

  @PostMapping("/forgot-password")
  public void sendResetPassword(@RequestBody SendMailVerifyUserRequest request) {
    mailService.sendResetPassword(request);
  }

  @PostMapping("/confirm-order")
  public void confirmOrder(@RequestBody OrderDto orderDto) {
    mailService.confirmOrder(orderDto);
  }
}
