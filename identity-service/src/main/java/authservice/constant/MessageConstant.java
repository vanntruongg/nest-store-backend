package authservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {

  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String CHAR_SEQUENCE_3 = "%s %s %s";
  public static final String CHAR_SEQUENCE_4 = "%s %s %s %s";
  public static final String CHAR_SEQUENCE_5 = "%s %s %s %s %s";


//  actions
  public static final String FIND = "Find";
  public static final String SUCCESS = "successfully!";
  public static final String FAILED = "failed!";
  public static final String LOGIN = "Login";
  public static final String REGISTER = "Register";
  public static final String UPDATE = "Update";
  public static final String DELETE = "Delete";
  public static final String REFRESH_TOKEN = "Refresh token";

  public static final String EMAIL = "Email";
  public static final String EXISTED = "Existed";
  public static final String NOT_FOUND = "not found!";
  public static final String EMAIL_EXISTED = "Email was existed!";
  public static final String USER = "User";
  public static final String CHANGE_PASSWORD = "Change password";

  public static final String LOGIN_SUCCESS = String.format(CHAR_SEQUENCE_2, LOGIN, SUCCESS);
  public static final String REGISTER_SUCCESS = String.format(CHAR_SEQUENCE_2, REGISTER, SUCCESS);
  public static final String REFRESH_TOKEN_FAIL = String.format(CHAR_SEQUENCE_2, REFRESH_TOKEN, FAILED);
  public static final String FIND_SUCCESS = String.format(CHAR_SEQUENCE_2, FIND, SUCCESS);
  public static final String USER_NOT_FOUND = String.format(CHAR_SEQUENCE_2, USER, NOT_FOUND);
  public static final String UPDATE_USER_SUCCESS = String.format(CHAR_SEQUENCE_3, UPDATE, USER, SUCCESS);
  public static final String CHANGE_PASSWORD_SUCCESS = String.format(CHAR_SEQUENCE_2, CHANGE_PASSWORD, SUCCESS);
  public static final String DELETE_USER_SUCCESS = String.format(CHAR_SEQUENCE_3, DELETE, USER, SUCCESS);
  public static final String OLD_PASSWORD_NOT_MATCHES = "Old password is not matches!";
  public static final String EXPIRED_TOKEN_VERIFICATION = "Verification token has expired. Please request a new one.";
  public static final String INVALID_TOKEN_VERIFICATION = "Invalid or expired verification token.";
  public static final String UNVERIFIED_ACCOUNT = "Unverified Account!";
}
