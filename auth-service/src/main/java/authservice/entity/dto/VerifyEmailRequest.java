package authservice.entity.dto;

import lombok.Getter;

@Getter
public class VerifyEmailRequest {
  private String email;
  private String token;
}
