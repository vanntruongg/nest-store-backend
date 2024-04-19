package orderservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {
  public static final String PRODUCT_SERVICE_URL = "http://localhost:9002";
  public static final String CART_SERVICE_URL = "http://localhost:9004";
  //  params
  public static final String ID_PARAM = "/{id}";
  public static final String EMAIL = "/email";
  public static final String STATUS = "/status";

  // actions
  public static final String GET = "/get";
  public static final String CREATE = "/create";

  public static final String ORDER = "/order";
  public static final String ORDERS = "/orders";
  public static final String ORDER_DETAIL = "/order-detail";
  public static final String METHODS = "/methods";
  public static final String COUNT = "/count";
  public static final String MONTH = "/month";

  private static final String REVENUE = "/revenue";
  public static final String GET_URL_PAYMENT = "/get-link-payment";
  public static final String UPDATE = "/update";
  public static final String GET_TOTAL_ORDER = "/total-order";
  public static final String CREATE_ORDER = CREATE;
  public static final String GET_BY_EMAIL = GET + EMAIL;
  public static final String GET_BY_STATUS = GET + STATUS;
  public static final String GET_BY_EMAIL_AND_STATUS = GET + EMAIL + STATUS;
  public static final String GET_BY_ID = GET + ID_PARAM;
  public static final String UPDATE_STATUS = UPDATE + STATUS;
  public static final String COUNT_ORDER_BY_MONTH = COUNT + MONTH;
  public static final String GET_ALL_REVENUE = GET + REVENUE;
  public static final String GET_REVENUE_BY_MONTH = GET + REVENUE + MONTH;

  //

}
