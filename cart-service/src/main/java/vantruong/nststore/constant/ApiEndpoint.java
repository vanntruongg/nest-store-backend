package vantruong.nststore.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

  //  params
  public static final String EMAIL_PARAM = "/{email}";
  public static final String ID_PARAM = "/{id}";

  // actions
  public static final String GET = "/get";

  public static final String CART = "/cart";

  public static final String GET_ALL = "/get-all";
  public static final String REMOVE = "/remove";

  public static final String CART_GET_ALL = GET_ALL + EMAIL_PARAM;
  public static final String ADD_TO_CART = "/add";
  public static final String REMOVE_FROM_CART = REMOVE + EMAIL_PARAM + ID_PARAM;

}
