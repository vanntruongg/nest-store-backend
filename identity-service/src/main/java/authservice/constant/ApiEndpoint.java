package authservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {
  public static final String AUTH = "auth";
  public static final String LOGIN = "/login";
  public static final String REGISTER = "/register";

  public static final String USER = "/user";
  public static final String USERS = "/users";
  public static final String GET = "/get";
  public static final String UPDATE = "/update";
  public static final String DELETE = "/delete";
  public static final String CHANGE_PASSWORD = "/change-password";

  public static final String EMAIL = "/email";
  public static final String EMAIL_PARAM = "/{email}";

  public static final String VERIFY_EMAIL = "/verify-email";
  public static final String REFRESH_TOKEN = "/refresh-token";
  public static final String USER_GET_ALL = USERS;
  public static final String USER_GET_BY_EMAIL = USER + GET + EMAIL + EMAIL_PARAM;
  public static final String UPDATE_USER = USER + UPDATE;
  public static final String USER_CHANGE_PASSWORD = USER + CHANGE_PASSWORD;
  public static final String DELETE_USER = USER + DELETE;
}
