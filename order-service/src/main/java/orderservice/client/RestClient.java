package orderservice.client;

import lombok.RequiredArgsConstructor;
import orderservice.common.CommonResponse;
import orderservice.constant.ApiEndpoint;
import orderservice.entity.PaymentMethod;
import orderservice.entity.dto.OrderDetailDto;
import orderservice.entity.dto.OrderDto;
import orderservice.entity.dto.OrderRequest;
import orderservice.enums.OrderStatus;
import orderservice.exception.ErrorCode;
import orderservice.exception.InsufficientProductQuantityException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<Map<Integer, Integer>> requestEntity = new HttpEntity<>(stockUpdate, headers);
    try {
      ResponseEntity<CommonResponse<Object>> response = restTemplate.exchange(
              url,
              HttpMethod.POST,
              requestEntity,
              new ParameterizedTypeReference<>() {
              }
      );
      if (response.getBody() != null) {
        if (!response.getBody().isSuccess()) {
          throw new InsufficientProductQuantityException(ErrorCode.UNPROCESSABLE_ENTITY, response.getBody().getMessage());
        }
      }
    } catch (InsufficientProductQuantityException exception) {
      throw new InsufficientProductQuantityException(ErrorCode.UNPROCESSABLE_ENTITY, exception.getMessage());
    }
  }

  public void removeItemsFromCart(String email, @NotNull List<OrderDetailDto> orderDetailDTOs) {
    HttpHeaders headers = new HttpHeaders();

    List<Integer> productIds = orderDetailDTOs.stream().map(OrderDetailDto::getProductId).toList();
    String url = ApiEndpoint.CART_SERVICE_URL + "/" + email + "/remove-items";

    HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(productIds, headers);
    restTemplate.exchange(
            url,
            HttpMethod.DELETE,
            requestEntity,
            new ParameterizedTypeReference<CommonResponse<Object>>() {
            }
    );
  }

  public void sendMailConfirmOrder(OrderDto request) {
    String url = ApiEndpoint.MAIL_SERVICE_URL + "/mail/confirm-order";
    request.setOrderStatus(OrderStatus.findOrderStatus(request.getOrderStatus()).getName());
    restTemplate.postForLocation(url, request);
  }
}
