package authservice.entity.dto;

import lombok.Getter;

@Getter
public class VerifyEmailRequest {
  private String token;
}
