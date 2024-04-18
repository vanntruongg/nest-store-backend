package mailservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class CommonConstant {

  public static final String BASE_URL_CLIENT = "http://localhost:3000";
  public static final String VERIFY_EMAIL = "/process-verify-email";

  public static final String RESET_PASSWORD = "/reset-password";
}
