package nststore.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  @NotBlank
  private String email;
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  private String phone;
  private String address;
}
