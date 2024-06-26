package mailservice.service;

import mailservice.dto.OrderDto;
import mailservice.dto.UserDto;
import mailservice.dto.SendMailVerifyUserRequest;

public interface MailService {

  void sendVerificationEmail(SendMailVerifyUserRequest request);

  void sendResetPassword(SendMailVerifyUserRequest request);

  void confirmOrder(OrderDto orderDto);
}
