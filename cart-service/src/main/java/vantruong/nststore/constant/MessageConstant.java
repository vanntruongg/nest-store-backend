package vantruong.nststore.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {
  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String CHAR_SEQUENCE_3 = "%s %s %s";
  public static final String CHAR_SEQUENCE_4 = "%s %s %s %s";
  public static final String CHAR_SEQUENCE_5 = "%s %s %s %s %s";
  public static final String SUCCESS = "successfully!";

//  actions
  public static final String FIND = "Find";

//  status
  public static final String NOT_FOUND = "not found!";

  public static final String USER = "User";

  public static final String FIND_ALL_USER_SUCCESS = String.format(CHAR_SEQUENCE_2, FIND, SUCCESS);

  public static final String USER_NOT_FOUND = String.format(CHAR_SEQUENCE_2, USER, NOT_FOUND);
  public static final String EMAIL_EXISTED = "Email was existed!";
}
