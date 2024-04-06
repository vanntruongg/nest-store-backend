package orderservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

//  params
public static final String ID_PARAM = "/{id}";

  // actions
  public static final String GET = "/get";

  public static final String PRODUCT = "/order";
  public static final String ORDER_DETAIL = "/order-detail";
}
