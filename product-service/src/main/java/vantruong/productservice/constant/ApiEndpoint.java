package vantruong.productservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

//  params

  public static final String ID_PARAM = "/{id}";

  // actions
  public static final String GET = "/get";
  public static final String CREATE = "/create";

  public static final String PRODUCT = "/product";
  public static final String PRODUCTS = "/products";
  public static final String CATEGORY = "/category";

  public static final String PRODUCT_GET_BY_ID = GET + ID_PARAM;
  public static final String PRODUCT_GET_BY_CATEGORY_ID = GET + CATEGORY + ID_PARAM;
  public static final String CREATE_PRODUCT = CREATE;
}
