package authservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {
  public static final String AUTH = "auth";
  public static final String LOGIN = "/login";
  public static final String LOGOUT = "/logout";
  public static final String REGISTER = "/register";

  public static final String ADMIN = "/admin";
  public static final String USER = "/user";
  public static final String GET = "/get";
  public static final String UPDATE = "/update";
  public static final String DELETE = "/delete";
  public static final String COUNT = "/count";
  public static final String COUNT_USER = USER + COUNT;
  public static final String CHANGE_PASSWORD = "/change-password";

  public static final String EMAIL = "/email";
  public static final String PHONE = "/phone";
  public static final String ADDRESS = "/address";
  public static final String EMAIL_PARAM = "/{email}";
  public static final String PHONE_PARAM = "/{phone}";
  public static final String ADDRESS_PARAM = "/{address}";

  public static final String VERIFY_EMAIL = "/verify-email";
  public static final String REFRESH_TOKEN = "/refresh-token";
  public static final String USER_GET = USER + EMAIL_PARAM;
  public static final String UPDATE_USER = USER + UPDATE;
  public static final String USER_CHANGE_PASSWORD = USER + CHANGE_PASSWORD;
  public static final String DELETE_USER = USER + DELETE + EMAIL_PARAM;
  public static final String USER_GET_ALL = USER;
  public static final String GET_PROFILE = "/profile";;
  public static final String UPDATE_PHONE = UPDATE + PHONE + PHONE_PARAM;
  public static final String UPDATE_ADDRESS = UPDATE + ADDRESS + ADDRESS_PARAM;
  public static final String ADMIN_UPDATE = ADMIN + UPDATE;
}
