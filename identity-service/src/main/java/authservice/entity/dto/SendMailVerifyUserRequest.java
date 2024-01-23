package authservice.entity.dto;

import authservice.entity.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SendMailVerifyUserRequest {
  private String email;
  private String name;
  private Token token;
}
