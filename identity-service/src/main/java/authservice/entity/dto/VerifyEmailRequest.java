package authservice.entity.dto;

import authservice.entity.Token;
import lombok.Getter;

@Getter
public class VerifyEmailRequest {
  private String email;
  private Token token;
}
