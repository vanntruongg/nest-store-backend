package vantruong.cartservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vantruong.cartservice.common.CommonResponse;
import vantruong.cartservice.constant.ApiEndpoint;

@Component
@RequiredArgsConstructor
public class RestClient {

  private final RestTemplate restTemplate;

  public Object getStockProductById(int id) {
    String url = ApiEndpoint.PRODUCT_SERVICE_URL + "/get/stock/" + id;
    ResponseEntity<CommonResponse<Object>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {});

    if (response.getBody() != null) {
      return response.getBody().getData();
    }
    return null;
  }
}
