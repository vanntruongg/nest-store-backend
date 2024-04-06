package orderservice.controller;

import lombok.RequiredArgsConstructor;
import orderservice.service.OrderDetailService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderDetailController {
  private final OrderDetailService orderDetailService;
}
