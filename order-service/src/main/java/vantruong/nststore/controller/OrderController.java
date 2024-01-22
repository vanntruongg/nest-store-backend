package vantruong.nststore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import vantruong.nststore.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
}
