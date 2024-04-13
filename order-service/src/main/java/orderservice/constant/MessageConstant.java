package orderservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {
  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String CHAR_SEQUENCE_3 = "%s %s %s";
  public static final String CHAR_SEQUENCE_4 = "%s %s %s %s";
  public static final String CHAR_SEQUENCE_5 = "%s %s %s %s %s";
  public static final String SUCCESS = "thành công!";

//  actions
  public static final String FIND = "Tìm";
  private static final String UPDATE = "Cập nhật";

  //  status
  public static final String NOT_FOUND = "Không tìm thấy";

  public static final String PAYMENT_METHOD = "Phương thức thanh toán";
  public static final String ORDER = "Đặt hàng";


  public static final String GET_LINK_PAYMENT_SUCCESS = "Lấy đường dẫn thanh toán thành công!";
  public static final String ORDER_SUCCESS = String.format(CHAR_SEQUENCE_2, ORDER, SUCCESS);
  public static final String PAYMENT_METHOD_NOT_FOUND = String.format(CHAR_SEQUENCE_2, NOT_FOUND, PAYMENT_METHOD);
  public static final String FIND_SUCCESS = String.format(CHAR_SEQUENCE_2, FIND, SUCCESS);
  public static final String UPDATE_SUCCESS = String.format(CHAR_SEQUENCE_2, UPDATE, SUCCESS);
  public static final String ORDER_NOT_FOUND = "Không tìm thấy đơn hàng!";
}
