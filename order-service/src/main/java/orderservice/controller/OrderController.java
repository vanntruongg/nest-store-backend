package orderservice.controller;

import lombok.RequiredArgsConstructor;
import orderservice.common.CommonResponse;
import orderservice.constant.MessageConstant;
import orderservice.entity.dto.OrderRequest;
import orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static orderservice.constant.ApiEndpoint.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @GetMapping(ORDERS)
  public ResponseEntity<CommonResponse<Object>> getAllOrder() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND)
            .data(orderService.getAllOrder())
            .build());
  }

  @PostMapping(CREATE_ORDER)
  public ResponseEntity<CommonResponse<Object>> createOrder(@RequestBody OrderRequest orderRequest) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.ORDER_SUCCESS)
            .data(orderService.createOrder(orderRequest))
            .build());
  }

  @GetMapping(GET_BY_EMAIL)
  public ResponseEntity<CommonResponse<Object>> getOrderByEmail(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getOrderByEmail(email))
            .build());
  }

  @GetMapping(GET_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getOrderById(@PathVariable("id") int id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getOrderById(id))
            .build());
  }

  @GetMapping(GET_BY_STATUS)
  public ResponseEntity<CommonResponse<Object>> getOrderByStatus(@RequestParam("status") String status) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getOrderByStatus(status))
            .build());
  }

  @GetMapping(GET_BY_EMAIL_AND_STATUS)
  public ResponseEntity<CommonResponse<Object>> getOrderByEmailAndStatus(
          @RequestParam("email") String email,
          @RequestParam("status") String status) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getOrderByEmailAndStatus(email, status))
            .build());
  }

  @PostMapping(UPDATE_STATUS)
  public ResponseEntity<CommonResponse<Object>> updateStatus(
          @RequestParam("id") int id,
          @RequestParam("status") String status) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_SUCCESS)
            .data(orderService.updateStatus(id, status))
            .build());
  }

  @GetMapping(GET_TOTAL_ORDER)
  public ResponseEntity<CommonResponse<Object>> getTotalOrderCountByStatus() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getTotalOrderCountByStatus())
            .build());
  }

  @GetMapping(COUNT_ORDER_BY_MONTH)
  public ResponseEntity<CommonResponse<Object>> getCountOrderByMonth() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getCountOrderByMonth())
            .build());
  }

  @GetMapping(STATISTIC_REVENUE)
  public ResponseEntity<CommonResponse<Object>> getRevenue(@RequestParam("year") Integer year, @RequestParam(value = "month", required = false) Integer month) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getRevenue(year, month))
            .build());
  }

  @GetMapping(STATISTIC_ORDER)
  public ResponseEntity<CommonResponse<Object>> statisticOrder(@RequestParam("year") Integer year, @RequestParam(value = "month", required = false) Integer month) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.statisticOrder(year, month))
            .build());
  }

}
