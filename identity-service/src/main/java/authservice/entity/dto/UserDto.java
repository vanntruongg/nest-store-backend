package authservice.entity.dto;

import lombok.Getter;

@Getter
public class UserDto {
  private String email;
  private String firstName;
  private String lastName;
  private String phone;
  private String address;
  private String imageUrl;
}
