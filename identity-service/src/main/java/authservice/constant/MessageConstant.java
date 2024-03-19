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
  public static final String FIND = "Tìm";
  public static final String SUCCESS = "thành công!";
  public static final String FAILED = "lỗi!";
  public static final String LOGIN = "Đăng nhập";
  public static final String REGISTER = "Đăng ký";
  public static final String UPDATE = "Cập nhật";
  public static final String DELETE = "Xóa";
  public static final String REFRESH_TOKEN = "Refresh token";

  public static final String EMAIL = "Email";
  public static final String EXISTED = "tồn tại";
  public static final String NOT_FOUND = "không tìm thấy!";
  public static final String EMAIL_EXISTED = "Email đã tồn tại!";
  public static final String USER = "Người dùng";
  public static final String CHANGE_PASSWORD = "Thay đổi mật khẩu";

  public static final String LOGIN_SUCCESS = String.format(CHAR_SEQUENCE_2, LOGIN, SUCCESS);
  public static final String REGISTER_SUCCESS = String.format(CHAR_SEQUENCE_2, REGISTER, SUCCESS);
  public static final String REFRESH_TOKEN_FAIL = String.format(CHAR_SEQUENCE_2, REFRESH_TOKEN, FAILED);
  public static final String FIND_SUCCESS = String.format(CHAR_SEQUENCE_2, FIND, SUCCESS);
  public static final String USER_NOT_FOUND = String.format(CHAR_SEQUENCE_2, USER, NOT_FOUND);
  public static final String UPDATE_USER_SUCCESS = String.format(CHAR_SEQUENCE_3, UPDATE, USER, SUCCESS);
  public static final String CHANGE_PASSWORD_SUCCESS = String.format(CHAR_SEQUENCE_2, CHANGE_PASSWORD, SUCCESS);
  public static final String DELETE_USER_SUCCESS = String.format(CHAR_SEQUENCE_3, DELETE, USER, SUCCESS);
  public static final String ACCOUNT_NOT_FOUND = "Tài khoản không tồn tại.";
  public static final String OLD_PASSWORD_NOT_MATCHES = "Mật khẩu cũ không đúng!";
  public static final String EXPIRED_TOKEN_VERIFICATION = "Mã xác minh đã hết hạn, vui lòng yêu cầu một mã mới.";
  public static final String INVALID_TOKEN_VERIFICATION = "Mã xác minh không hợp lệ hoặc đã hết hạn.";
  public static final String UNVERIFIED_ACCOUNT = "Tài khoản chưa được xác minh!";
  public static final String PASSWORD_INCORRECT = "Mật khẩu không chính xác.";
}
