package vantruong.productservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

//  params

  public static final String ID_PARAM = "/{id}";

  // actions
  public static final String GET = "/get";

  public static final String PRODUCT = "/product";
  public static final String CATEGORY = "/category";

  public static final String PRODUCT_GET_BY_ID = PRODUCT + GET + ID_PARAM;
}
