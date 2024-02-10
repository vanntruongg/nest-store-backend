package vantruong.nststore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vantruong.nststore.common.CommonResponse;
import vantruong.nststore.constant.MessageConstant;
import vantruong.nststore.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @GetMapping("orders")
  public ResponseEntity<CommonResponse<Object>> getAllOrder() {
    return ResponseEntity.ok().body(CommonResponse.builder()
                    .isSuccess(true)
                    .message(MessageConstant.FIND)
                    .data(orderService.getAllOrder())
            .build());
  }
}
