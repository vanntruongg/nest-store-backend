package vantruong.cartservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

  public static final String PRODUCT_SERVICE_URL = "http://localhost:9002";

  //  params
  public static final String EMAIL_PARAM = "/{email}";
  public static final String ID_PARAM = "/{id}";

  // actions
  public static final String GET = "/get";

  public static final String CART = "/cart";

  public static final String REMOVE = "/remove";
  public static final String REMOVE_ITEMS = "/remove-items";
  public static final String CART_REMOVE_ITEMS = EMAIL_PARAM + REMOVE_ITEMS;
  private static final String UPDATE = "/update";

  public static final String CART_GET_ALL = "/items";
  public static final String ADD_TO_CART = "/add";
  public static final String REMOVE_FROM_CART = REMOVE;

  public static final String CART_UPDATE = UPDATE;
}
