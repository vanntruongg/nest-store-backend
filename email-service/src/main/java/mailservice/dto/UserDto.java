package mailservice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UserDto {
  private String email;
  private String firstName;
  private String lastName;
  private String phone;
  private String address;
  private String imageUrl;
  private List<String> roles;
}
