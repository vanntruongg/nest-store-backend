package orderservice.client;

import lombok.RequiredArgsConstructor;
import orderservice.common.CommonResponse;
import orderservice.constant.ApiEndpoint;
import orderservice.entity.dto.OrderDetailDto;
import orderservice.exception.ErrorCode;
import orderservice.exception.InsufficientProductQuantityException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RestClient {
  private final RestTemplate restTemplate;

  public void updateProductQuantity(Map<Integer, Integer> stockUpdate) {
    String url = ApiEndpoint.PRODUCT_SERVICE_URL + "/update-quantity-order";

    try {
      ResponseEntity<Object> response = restTemplate.postForEntity(url, stockUpdate, Object.class);
      if (response.getBody() != null && response.getBody() instanceof CommonResponse<?> commonResponse) {
        if (!commonResponse.isSuccess()) {
          throw new InsufficientProductQuantityException(ErrorCode.UNPROCESSABLE_ENTITY, commonResponse.getMessage());
        }
      }
    } catch (InsufficientProductQuantityException exception) {
      throw new InsufficientProductQuantityException(ErrorCode.UNPROCESSABLE_ENTITY, exception.getMessage());
    }
  }

  public void removeItemsFromCart(String email, @NotNull List<OrderDetailDto> orderDetailDTOs) {
      List<Integer> productIds = orderDetailDTOs.stream().map(OrderDetailDto::getProductId).toList();
      String url = ApiEndpoint.CART_SERVICE_URL + "/" + email + "/remove-items?productIds=" + productIds;
      restTemplate.delete(url);
  }
}
