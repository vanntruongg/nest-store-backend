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
  public static final String SUCCESS = "successfully!";

//  actions
  public static final String FIND = "Find";

//  status
  public static final String NOT_FOUND = "not found!";

  public static final String PAYMENT_METHOD = "Payment method";

  public static final String GET_LINK_PAYMENT_SUCCESS = "Get link payment successfully!";
  public static final String PAYMENT_METHOD_NOT_FOUND = String.format(CHAR_SEQUENCE_2, PAYMENT_METHOD, NOT_FOUND);
}
