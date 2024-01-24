package mailservice.service;

import mailservice.dto.UserDto;
import mailservice.dto.SendMailVerifyUserRequest;

public interface MailService {
  public void senMail(UserDto userDto);

  void sendVerificationEmail(SendMailVerifyUserRequest request);
}
