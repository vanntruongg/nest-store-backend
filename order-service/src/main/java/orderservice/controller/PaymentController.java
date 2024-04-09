package orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import orderservice.common.CommonResponse;
import orderservice.constant.MessageConstant;
import orderservice.service.PaymentService;

import java.io.UnsupportedEncodingException;

import static orderservice.constant.ApiEndpoint.GET_URL_PAYMENT;

@RestController
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentService paymentService;

  @GetMapping(GET_URL_PAYMENT)
  public ResponseEntity<CommonResponse<Object>> getOrderPayment(@RequestParam("amount") long amount) throws UnsupportedEncodingException {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.GET_LINK_PAYMENT_SUCCESS)
            .data(paymentService.createUrlPaymentOrder(amount))
            .build());
  }
}
