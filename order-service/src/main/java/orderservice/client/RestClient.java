package orderservice.client;

import lombok.RequiredArgsConstructor;
import orderservice.common.CommonResponse;
import orderservice.constant.CommonConstant;
import orderservice.entity.dto.OrderDetailDto;
import orderservice.exception.ErrorCode;
import orderservice.exception.InsufficientProductQuantityException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestClient {
  private final RestTemplate restTemplate;

  public void updateProductQuantity(int productId, int quantity) {
    String url = CommonConstant.PRODUCT_SERVICE_URL + "/product/" + productId + "/update-quantity?quantity=" + quantity;

    try {
      ResponseEntity<CommonResponse<Object>> response = restTemplate.exchange(
              url,
              HttpMethod.PATCH,
              null,
              new ParameterizedTypeReference<>() {
              });
      if (response.getBody() != null && !response.getBody().isSuccess()) {
        throw new InsufficientProductQuantityException(ErrorCode.UNPROCESSABLE_ENTITY, response.getBody().getMessage());
      }
    } catch (InsufficientProductQuantityException exception) {
      throw new InsufficientProductQuantityException(ErrorCode.UNPROCESSABLE_ENTITY, exception.getMessage());
    }
  }

  public void removeItemsFromCart(String email, @NotNull List<OrderDetailDto> orderDetailDTOs) {
    List<Integer> productIds = orderDetailDTOs.stream().map(OrderDetailDto::getProductId).toList();
    String url = CommonConstant.CART_SERVICE_URL + "/cart/" + email + "/remove-items?productIds=" + productIds;
    restTemplate.delete(url);
  }
}
