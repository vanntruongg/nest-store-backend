package vantruong.nststore.service;

import vantruong.nststore.dto.UserDto;

public interface MailService {
  public void senMail(UserDto userDto);
}
