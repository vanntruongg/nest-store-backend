package authservice.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class RefreshTokenRequest {
  @NotEmpty
  private String refreshToken;
}
