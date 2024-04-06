package orderservice.controller;

import lombok.RequiredArgsConstructor;
import orderservice.common.CommonResponse;
import orderservice.entity.dto.OrderRequest;
import orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import orderservice.constant.MessageConstant;

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

  @PostMapping("")
  public ResponseEntity<CommonResponse<Object>> createOrder(@RequestBody OrderRequest orderRequest) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.ORDER_SUCCESS)
            .data(orderService.createOrder(orderRequest))
            .build());
  }

  @GetMapping("")
  public ResponseEntity<CommonResponse<Object>> getOrderByStatus(@RequestParam("status") String status) {
    return ResponseEntity.ok().body(CommonResponse.builder()
                    .isSuccess(true)
                    .message(MessageConstant.FIND_SUCCESS)
                    .data(orderService.getOrderByStatus(status))
            .build());
  }
}
