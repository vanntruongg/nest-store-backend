package authservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {
  RESET_PASSWORD("Reset password"),
  VERIFICATION("Verification");

  private final String tokenType;
}
