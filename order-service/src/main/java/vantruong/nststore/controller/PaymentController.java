package vantruong.nststore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vantruong.nststore.common.CommonResponse;
import vantruong.nststore.constant.MessageConstant;
import vantruong.nststore.service.PaymentService;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentService paymentService;

  @GetMapping("/get-url")
  public ResponseEntity<CommonResponse<Object>> getOrderPayment(@RequestParam("amount") long amount) throws UnsupportedEncodingException {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.GET_LINK_PAYMENT_SUCCESS)
            .data(paymentService.createUrlPaymentOrder(amount))
            .build());
  }
}
