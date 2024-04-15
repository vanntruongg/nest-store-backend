package authservice.entity.dto;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
  private String oldPassword;
  private String newPassword;
}
