package vantruong.nststore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import vantruong.nststore.service.OrderDetailService;

@RestController
@RequiredArgsConstructor
public class OrderDetailController {
  private final OrderDetailService orderDetailService;
}
