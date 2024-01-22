package nststore.userservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

  //  params
  public static final String EMAIL_PARAM = "/{email}";

  // actions
  public static final String GET = "/get";
  public static final String CREATE = "/create";

  public static final String USER = "/user";

  public static final String USER_GET_ALL = "/get-all";

  public static final String USER_GET_BY_EMAIL = GET + EMAIL_PARAM;
  public static final String USER_CREATE = CREATE;
}
