package vantruong.nststore.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import vantruong.nststore.entity.PaymentMethod;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {
  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String CHAR_SEQUENCE_3 = "%s %s %s";
  public static final String CHAR_SEQUENCE_4 = "%s %s %s %s";
  public static final String CHAR_SEQUENCE_5 = "%s %s %s %s %s";
  public static final String SUCCESS = "thành công!";

//  actions
  public static final String FIND = "Tìm";

//  status
  public static final String NOT_FOUND = "không tìm thấy!";

  public static final String PAYMENT_METHOD = "Phương thức thanh toán";
  public static final String ORDER = "Đặt hàng";


  public static final String GET_LINK_PAYMENT_SUCCESS = "Lấy đường dẫn thanh toán thành công!";
  public static final String ORDER_SUCCESS = String.format(CHAR_SEQUENCE_2, ORDER, SUCCESS);
  public static final String PAYMENT_METHOD_NOT_FOUND = String.format(CHAR_SEQUENCE_2, PAYMENT_METHOD, NOT_FOUND);
  public static final String FIND_SUCCESS = String.format(CHAR_SEQUENCE_2, FIND, SUCCESS);
}
