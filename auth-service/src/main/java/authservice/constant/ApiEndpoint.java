package authservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {
  public static final String AUTH = "auth";
  public static final String LOGIN = "/login";
  public static final String REGISTER = "/register";

}
