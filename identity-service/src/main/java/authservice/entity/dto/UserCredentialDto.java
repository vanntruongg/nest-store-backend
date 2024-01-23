package authservice.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UserCredentialDto {
  @NotEmpty
  private String firstName;
  @NotEmpty
  private String lastName;
  @NotEmpty
  private String email;
  @NotEmpty
  private String password;
}
