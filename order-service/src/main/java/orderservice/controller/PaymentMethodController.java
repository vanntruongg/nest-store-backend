package orderservice.controller;

import lombok.RequiredArgsConstructor;
import orderservice.common.CommonResponse;
import orderservice.constant.MessageConstant;
import orderservice.service.PaymentMethodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static orderservice.constant.ApiEndpoint.METHODS;

@RestController
@RequiredArgsConstructor
public class PaymentMethodController {
  private final PaymentMethodService paymentMethodService;

  @GetMapping(METHODS)
  public ResponseEntity<CommonResponse<Object>> getAllPaymentMethod() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(paymentMethodService.getAllPaymentMethod())
            .build());
  }
}
