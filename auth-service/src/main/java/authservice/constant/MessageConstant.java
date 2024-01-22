package authservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {

  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String CHAR_SEQUENCE_3 = "%s %s %s";
  public static final String CHAR_SEQUENCE_4 = "%s %s %s %s";
  public static final String CHAR_SEQUENCE_5 = "%s %s %s %s %s";

  public static final String SUCCESS = "successfully!";
  public static final String LOGIN = "Login";
  public static final String REGISTER = "Register";

  public static final String EMAIL = "Email";
  public static final String EXISTED = "Existed";
  public static final String EMAIL_EXISTED = "Email was existed!";
  public static final String ROLE_NOT_FOUND = "Role not found!";

  public static final String LOGIN_SUCCESS = String.format(CHAR_SEQUENCE_2, LOGIN, SUCCESS);
  public static final String REGISTER_SUCCESS = String.format(CHAR_SEQUENCE_2, REGISTER, SUCCESS);
}
